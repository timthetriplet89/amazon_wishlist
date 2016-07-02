
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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;




@WebServlet(name = "Connection", urlPatterns = {"/Connection"})
public class Connection extends HttpServlet {

  
    /*String DBUSERNAME = null;
    String DBPASSWORD = null;
    String DBURL = null;*/
    
    
    
//variable for Openshift connection
        //String DBUSERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        //String DBPASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        //String DBURL = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/north_pole";
    
//variables for Kami's local connection
        String DBUSERNAME = "myUser";
        String DBPASSWORD = "myPass";
        String DBURL = "jdbc:mysql://localhost/north_pole";

                   // }

    
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

      //STEP 4: Execute a query
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT id, name, url FROM items";
      ResultSet rs = stmt.executeQuery(sql);

      
      //List<Person> listPeople = new ArrayList();
      
      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
          
         
         int id  = rs.getInt("id");
         String name = rs.getString("name");
         String url = rs.getString("url");
         
        //Person person = new Person(id, first, last, birthdate); 
        //listPeople.add(person);
       
       
        
      }
      
      ServletContext sc = getServletContext();
      RequestDispatcher rd = sc.getRequestDispatcher("/index.jsp");
      //request.setAttribute("id", id);
      //request.setAttribute("name", namee);
      //request.setAttribute("url", url);
      rd.forward(request, response);
      //URL.encode(first)   
      
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

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    

}
