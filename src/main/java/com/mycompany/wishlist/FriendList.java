

package com.mycompany.wishlist;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "FriendList", urlPatterns = {"/FriendList"})
public class FriendList extends HttpServlet {

//variable for Openshift connection
    String DBUSERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
    String DBPASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
    String DBURL = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/north_pole";
      
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
try (PrintWriter out = response.getWriter()) {
            
   java.sql.Connection conn = null;
    PreparedStatement stmtGetIds = null;
   try{
      
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      conn = DriverManager.getConnection(DBURL , DBUSERNAME , DBPASSWORD);
      
      // get id of connected user
      String id = request.getParameter("id");
      String authname = request.getParameter("authname");
            
      //STEP 4: Execute a query -- Get the items in the user's wishlist, with a lookup using the user's id in the items_users table
      stmtGetIds = conn.prepareStatement("SELECT item_id FROM user_items WHERE user_id = ? ");
      stmtGetIds.setString(1, id);
      ResultSet rs = stmtGetIds.executeQuery();
      if (!rs.next()){
         String errorMessage2 = "Error- There are no items in this wishlist.";
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
      }
      

      // Get the name and url for each item in the user's list 
      List<Item> wishlist = new ArrayList<>();
      
//      out.println("listItemID.size() = " + listItemID.size());
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
           url = rsWishlist.getString("url");
           Item item = new Item(itemName, url);
           wishlist.add(item);
        }
      }


      
         ServletContext sc = getServletContext();      
         RequestDispatcher rd = sc.getRequestDispatcher("/friendlist.jsp");
         
         request.setAttribute("authname", authname);
         request.setAttribute("wishlist", wishlist);
         request.getSession().setAttribute("wishlist", wishlist);
         
          
         rd.forward(request, response);
         
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
