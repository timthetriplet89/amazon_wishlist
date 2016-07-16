/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.wishlist;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

/**
 *
 * @author Kam Kam
 */
@WebServlet(name = "CreateUser", urlPatterns = {"/CreateUser"})
public class CreateUser extends HttpServlet {

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
       
       out.println("DBUSERNAME = " + DBUSERNAME);
       out.println("DBPASSWORD = " + DBPASSWORD); 
       out.println("DBURL = " + DBURL);
      
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      conn = DriverManager.getConnection(DBURL , DBUSERNAME , DBPASSWORD);
      
      //get Username and Password
      String name = request.getParameter("name");
      String username = request.getParameter("username");
      String password = request.getParameter("password");
      
      //Get current usernames from database
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT username FROM users";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         String user = rs.getString("username");
         
         //Check for duplicates
         if (username.equals(username)){
         String errorMessage = "The username is already in use. Please try again!";
         request.setAttribute("errorMessage", errorMessage);
         request.getRequestDispatcher("/newuser.jsp").forward(request, response);
         }
      }
      
      //Insert user into table
      PreparedStatement insertuser = conn.prepareStatement
        ("INSERT INTO users (name, username, password) VALUES (?, ?, ?)");
      insertuser.setString(1, name);
      insertuser.setString(2, username);
      insertuser.setString(3, password);
      insertuser.executeUpdate();
      
      request.setAttribute("name", name);
      request.setAttribute("username", username);
      request.setAttribute("password", password);
      request.getSession().setAttribute("name", name);
      response.sendRedirect("UserSignIn"); 
      
      
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
            conn.close();
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
