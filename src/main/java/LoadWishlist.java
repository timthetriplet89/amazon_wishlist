/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mycompany.wishlist.Item;
import com.mycompany.wishlist.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author timth
 */
@WebServlet(urlPatterns = {"/LoadWishlist"})
public class LoadWishlist extends HttpServlet {

    //variable for Openshift connection
    String DBUSERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
    String DBPASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
    String DBURL = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/north_pole";
      
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
try (PrintWriter out = response.getWriter()) {
            
   java.sql.Connection conn = null;
//   Statement stmt = null;
    PreparedStatement stmtGetIds = null;
   try{
      
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      conn = DriverManager.getConnection(DBURL , DBUSERNAME , DBPASSWORD);
      
      // get id of logged-in user
      String id = request.getSession().getAttribute("id").toString();
            
      //STEP 4: Execute a query -- Get the items in the user's wishlist, with a lookup using the user's id in the items_users table
//      stmt = conn.createStatement();
//      PreparedStatement stmt2 = conn.prepareStatement("SELECT item_id FROM user_items WHERE user_id = ? ");
      stmtGetIds = conn.prepareStatement("SELECT item_id FROM user_items WHERE user_id = ? ");
      stmtGetIds.setString(1, id);
      ResultSet rs = stmtGetIds.executeQuery();
      if (!rs.next()){
         String errorMessage2 = "Error- you currently do not have any items in your wishlist.";
         out.println(errorMessage2);
         request.setAttribute("errorMessage2", errorMessage2);
//         request.getRequestDispatcher("/search.jsp").forward(request, response);
      } else {
          rs.beforeFirst();
      }
      
      List<Integer> listItemID = new ArrayList<>();
      Integer item_id;
      
      while(rs.next()){
         //Retrieve each item id
         item_id  = rs.getInt("item_id");
         listItemID.add(item_id);
//         out.println("item_id: " + item_id + "<br>");
      }
      

      // Get the name and url for each item in the user's list 
      List<Item> wishlist = new ArrayList<>();
//      PreparedStatement stmt3 = conn.prepareStatement("SELECT name, url FROM items WHERE id = ? ");
      
      out.println("listItemID.size() = " + listItemID.size());
      for (Integer i = 0; i < listItemID.size(); i++) {
      
        PreparedStatement stmtWishlist = conn.prepareStatement("SELECT name, url FROM items WHERE id = ? ");
        stmtWishlist.setString(1, listItemID.get(i).toString()); 
        ResultSet rsWishlist = stmtWishlist.executeQuery();
        if (!rsWishlist.next()){
           String errorMessage = "Error...";
           out.println(errorMessage);
           request.setAttribute("errorMessage", errorMessage);
//           request.getRequestDispatcher("/search.jsp").forward(request, response);
        } else {
            rsWishlist.beforeFirst();
        }
        
        String itemName;
        String url;

        while(rsWishlist.next()){
           //Retrieve by column name
           itemName = rsWishlist.getString("name");
//           out.println("itemName = " + itemName);
           url = rsWishlist.getString("url");
//           out.println("url = " + url);
           Item item = new Item(itemName, url);
           wishlist.add(item);
//           out.println("item_id: " + item_id);
        }
      }

//DISPLAY USER LIST
      //used a join to only select the user ID and name to be displayed in list
      PreparedStatement userlist = conn.prepareStatement
        ("SELECT users.id, users.name FROM users JOIN connections "
                + "ON users.id = connections.listAuthorId WHERE connections.listViewerId = ?");
      userlist.setString(1, id);
      ResultSet rsUserList = userlist.executeQuery();
      
      //if no connections, show error
      if (!rsUserList.next()){
         String errorMessage1 = "You have no connections";
         request.setAttribute("errorMessage1", errorMessage1);
         request.getRequestDispatcher("/index.jsp").forward(request, response);
      } else {
          rs.beforeFirst();
      }

      // Store the user list
      ArrayList<User> listUsers = new ArrayList<>();
      out.print("listUsers= " + listUsers);
      
      //get id's and names to be displayed
      while(rsUserList.next()){
         //Retrieve by column name
          //ERROR occurring here, Column users.id not found
          out.print("start loop");
         String userid  = rs.getString("id");
         out.print("id=" + userid);
         String authname = rs.getString("name");
         out.print("authname" + authname);
         User user = new User(userid, authname);
         listUsers.add(user);
         
         out.print(userid);
         out.print(authname);
         out.print("listUsersloop=" + listUsers);
      }
      
      //set attributes to be displayed on index.jsp
      request.setAttribute("listUsers", listUsers);
      out.print("listUsers2=" + listUsers);
      
//         request.setAttribute("userid", userid);
//         request.setAttribute("authname", authname);
//    
      //testing output
//         out.print(authname);
//         
//      }
      
         ServletContext sc = getServletContext();      
         RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
         
         request.setAttribute("wishlist", wishlist);
         request.getSession().setAttribute("wishlist", wishlist);
         
         request.setAttribute("listUsers", listUsers);                                    //  UNDER TEST!
         request.getSession().setAttribute("listUsers", listUsers);                       //  UNDER TEST!     
         
         //force error rd.forward(request, response);                                         //  Go To Index.jsp... 
      
      //////////////////////////////////////////
      
      
      //STEP 6: Clean-up environment
      rs.close();
//      stmt.close();
      stmtGetIds.close();
//      stmt3.close();
      conn.close();
      
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
     out.println("error description1:" + (se.getMessage()));
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
      out.println("error description2:" + (e.getMessage()));
   }finally{
      //finally block used to close resources
      try{
         if(stmtGetIds!=null)
            stmtGetIds.close();
      }catch(SQLException se2){
          out.println("error description3:" + (se2.getMessage()));
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
         out.println("error description4:" + (se.getMessage()));
      }//end finally try
   }//end try
        } 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
