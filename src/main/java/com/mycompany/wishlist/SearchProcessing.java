/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.wishlist;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 *
 * @author Timothy
 */
@WebServlet(name = "SearchProcessing", urlPatterns = {"/SearchProcessing"})
public class SearchProcessing extends HttpServlet {

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
            out.println("<title>Servlet SearchProcessing</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchProcessing at " + request.getContextPath() + "</h1>");
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
        
        String keyword = request.getParameter("searchBox"); 
        
        searchExample(request, response);
        
//        ItemLookupSample itemLookupSample = new ItemLookupSample();        
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

    /*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    private static final String AWS_ACCESS_KEY_ID = "AKIAJ4GAMTUWWNMCKADA";

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    private static final String AWS_SECRET_KEY = "xcsrWhJEpXD85p6SRiGiwWtvS6UKXo7myr1tiYe9";

    /*
     * Use one of the following end-points, according to the region you are
     * interested in:
     * 
     *      US: ecs.amazonaws.com 
     *      CA: ecs.amazonaws.ca 
     *      UK: ecs.amazonaws.co.uk 
     *      DE: ecs.amazonaws.de 
     *      FR: ecs.amazonaws.fr 
     *      JP: ecs.amazonaws.jp
     * 
     */
    private static final String ENDPOINT = "ecs.amazonaws.com";

    /*
     * The Item ID to lookup. The value below was selected for the US locale.
     * You can choose a different value if this value does not work in the
     * locale of your choice.
     */
    private static final String ITEM_ID = "0545010225";
    
    public static void searchExample(HttpServletRequest request, HttpServletResponse response) {
        /*
         * Set up the signed requests helper 
         */
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        String requestUrl = null;
        String title = null;

        /* The helper can sign requests in two forms - map form and string form */
        /*
         * Here is an example in map form, where the request parameters are stored in a map.
         */
        
try (PrintWriter out = response.getWriter()) {
    
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SearchProcessing</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchProcessing...</h1>");
            
//        out.println("Map form example:");
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
//        params.put("Version", "2009-03-31");
        params.put("Operation", "ItemSearch");
        params.put("ResponseGroup", "Small");     // What does this mean?
        params.put("AssociateTag", "XXXXXXXXXX");
        params.put("Keywords", URLEncoder.encode(request.getParameter("searchBox"), "UTF-8"));
        params.put("ResponseGroup","Images,ItemAttributes,Offers");
        params.put("BrowseNode", "17");
        params.put("SearchIndex", "Books");

        requestUrl = helper.sign(params);
        out.println("<p><a href=\"" + requestUrl + "\">Click Here<//a> to view search results in XML");
//        out.println("Signed Request is \"" + requestUrl + "\""); ////////////////////////////////////////////
//        out.println();
        
//        title = fetchTitle(requestUrl);
//        out.println("Signed Title is \"" + title + "\"");
//        out.println();
//        out.println();
        
        /* Here is an example with string form, where the requests parameters have already been concatenated
         * into a query string. */
//        out.println("String form example:");
//        String queryString = "Service=AWSECommerceService&Version=2009-03-31&Operation=ItemLookup&ResponseGroup=Small&ItemId="
//                + ITEM_ID;
//        requestUrl = helper.sign(queryString);
//        out.println("Request is \"" + requestUrl + "\"");
//
//        title = fetchTitle(requestUrl);
//        out.println("Title is \"" + title + "\"");
//        out.println();
//        
//            out.println("</body>");
//            out.println("</html>");         
} catch(Exception exception) {
    System.out.println("Exception caught");
}        
        
//try (PrintWriter out = response.getWriter()) {
//    
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet SearchProcessing</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet SearchProcessing...</h1>");
//            
//        out.println("Map form example:");
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("Service", "AWSECommerceService");
//        params.put("Version", "2009-03-31");
//        params.put("Operation", "ItemLookup");
//        params.put("ItemId", ITEM_ID);
//        params.put("ResponseGroup", "Small");
//        params.put("AssociateTag", "XXXXXXXXXX");
//
//        requestUrl = helper.sign(params);
//        out.println("Signed Request is \"" + requestUrl + "\"");
//        out.println();
//        title = fetchTitle(requestUrl);
//        out.println("Signed Title is \"" + title + "\"");
//        out.println();
//        /* Here is an example with string form, where the requests parameters have already been concatenated
//         * into a query string. */
//        out.println("String form example:");
//        String queryString = "Service=AWSECommerceService&Version=2009-03-31&Operation=ItemLookup&ResponseGroup=Small&ItemId="
//                + ITEM_ID;
//        requestUrl = helper.sign(queryString);
//        out.println("Request is \"" + requestUrl + "\"");
//
//        title = fetchTitle(requestUrl);
//        out.println("Title is \"" + title + "\"");
//        out.println();
//        
//            out.println("</body>");
//            out.println("</html>");         
//} catch(Exception exception) {
//    System.out.println("Exception caught");
//}
    }

    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    private static String fetchTitle(String requestUrl) {
        System.out.println("Debug statement A");
        String title = null;
        try {
            System.out.println("Debug statement B");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            System.out.println("Debug statement C");
            DocumentBuilder db = dbf.newDocumentBuilder();
            System.out.println("Debug statement D");
            Document doc = db.parse(requestUrl);
            System.out.println("Debug statement E");
            Node titleNode = doc.getElementsByTagName("Title").item(0);
            System.out.println("Debug statement F");
            title = titleNode.getTextContent();
            System.out.println("Debug statement G");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return title;
    }

    
}
