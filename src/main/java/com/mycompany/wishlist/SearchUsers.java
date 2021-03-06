

package com.mycompany.wishlist;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.System;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

@WebServlet(name = "SearchUsers", urlPatterns = {"/SearchUsers"})
public class SearchUsers extends HttpServlet {

    //variable for Openshift connection
        String DBUSERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String DBPASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        String DBURL = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/north_pole";
    
        String errorMessage = "";
    
    //variables for Kami's local connection
//        String DBUSERNAME = "myUser";
//        String DBPASSWORD = "myPass";
//        String DBURL = "jdbc:mysql://localhost/north_pole";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
   java.sql.Connection conn = null;
   Statement stmt = null;
   try{
      
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      conn = DriverManager.getConnection(DBURL , DBUSERNAME , DBPASSWORD);
      
      //get Username and Password
      String searchuser = request.getParameter("usersearch");
      String userid = request.getSession().getAttribute("id").toString();
      
      
      //STEP 4: Execute a query
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * FROM users";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("id");
         String name = rs.getString("name");
         String username = rs.getString("username");
         String password = rs.getString("password");
      }
      
      PreparedStatement stmt2 = conn.prepareStatement("SELECT id, name, username FROM users WHERE username = ? ");
      stmt2.setString(1, searchuser);
      rs = stmt2.executeQuery();

      if (!rs.next()){
         String errorMessage = "No user was found. Please try again!";
         request.setAttribute("errorMessage", errorMessage);
         request.getRequestDispatcher("/friendsearch.jsp").forward(request, response);
      } else {
          rs.beforeFirst();
      }
      while(rs.next()){
         //Retrieve by column name
         int authid  = rs.getInt("id");
         String authname = rs.getString("name");
         String username = rs.getString("username");
      
      
      //Insert user into table
      PreparedStatement addfriend = conn.prepareStatement
        ("INSERT INTO connections (listAuthorId, listViewerId) VALUES (?, ?)");
      addfriend.setInt(1, authid);
      addfriend.setString(2, userid);
      addfriend.executeUpdate();
      
      request.setAttribute("authid", authid);
      request.setAttribute("userid", userid);
      request.setAttribute("authname", authname);
      request.getRequestDispatcher("/useradded.jsp").forward(request, response);

      }
   
      
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
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
         if(stmt!=null)
            stmt.close();
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
