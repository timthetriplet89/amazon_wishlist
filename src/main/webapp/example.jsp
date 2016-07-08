<%-- 
    Document   : example
    Created on : Jun 24, 2016, 8:48:12 PM
    Author     : Timothy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Wishlist Application</h1>
        
        <form action="SearchProcessing" method="post">
            <label for="searchBox">Enter Search keyword:</label><br>
            <input type="text" name="searchBox"/><br>
            <input type="submit" value="Search"/>
        </form>
    
    </body>
</html>
