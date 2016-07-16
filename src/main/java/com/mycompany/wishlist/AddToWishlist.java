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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ben
 */
@WebServlet(name = "AddToWishlist", urlPatterns = {"/AddToWishlist"})
public class AddToWishlist extends HttpServlet {

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
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddToWishlist</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddToWishlist at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        Integer id = (Integer) request.getSession().getAttribute("id");
        String values[] = request.getParameterValues("items");
        
        List<Item> listItems = (List<Item>) request.getSession().getAttribute("listItems");
        
         //variable for Openshift connection
        String DBUSERNAME = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String DBPASSWORD = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        String DBURL = "jdbc:mysql://" + System.getenv("OPENSHIFT_MYSQL_DB_HOST") + ":" + System.getenv("OPENSHIFT_MYSQL_DB_PORT") + "/north_pole";
        
        try (PrintWriter out = response.getWriter()) {
            
            java.sql.Connection conn = null;
            Statement stmt = null;
            
            try{
               //STEP 2: Register JDBC driver
               Class.forName("com.mysql.jdbc.Driver");

               //STEP 3: Open a connection
               conn = DriverManager.getConnection(DBURL , DBUSERNAME , DBPASSWORD);
               PreparedStatement insertItem = conn.prepareStatement("INSERT INTO items (name, url) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);  // SOURCE:  http://stackoverflow.com/questions/7162989/sqlexception-generated-keys-not-requested-mysql              
               
               // Loop through all the items in the checklist, and add each item to the database
                 for (int i = 0; i < values.length; i++) {
                     Item item = listItems.get(Integer.parseInt(values[i]));   
                     insertItem.setString(1, item.getTitle());
                     insertItem.setString(2, item.getLink());
                     insertItem.executeUpdate();
                     ResultSet rs = insertItem.getGeneratedKeys();    //  http://stackoverflow.com/questions/5513180/java-preparedstatement-retrieving-last-inserted-id
                     Integer lastInsertItemID = new Integer(0);
                     if(rs.next())   
                     {
                         lastInsertItemID = rs.getInt(1);
                     }
                     // Add user_item
                     PreparedStatement insertUserItem = conn.prepareStatement("INSERT INTO user_items (user_id, item_id) VALUES (?, ?)");
                     insertUserItem.setString(1, id.toString());
                     insertUserItem.setString(2, lastInsertItemID.toString());
                     insertUserItem.executeUpdate();
                 }
                 
                 request.getSession().removeAttribute("listItems");
                 request.getRequestDispatcher("/itemAddedConfirmation.jsp").forward(request, response);
                 
             }catch(SQLException se){
                //Handle errors for JDBC
                se.printStackTrace();
               out.println("error description1:" + (se.getMessage()));
             }
        catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace(out);
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

//        processRequest(request, response);
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
