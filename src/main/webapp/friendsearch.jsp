<%-- 
    Document   : search
    Created on : Jun 21, 2016, 10:24:54 PM
    Author     : Kam Kam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <link rel="icon" type="image/icon" href="logo.png">
        <link rel="stylesheet" type="text/css" href="stylesheet.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Christmas Wish List</title>
    </head>
    <body>
        <img src="logo.png" alt="Christmas Wishlist" id="logo">
        <h1>Add a friend</h1>
        
        <form action='SearchUsers' method='post'>
            <input type='text' name='usersearch'>
            <input type='submit' value='Search by Username'>
        </form>
        <br/>
        
         
        
        <p>${errorMessage}</p>
            
        
        <br/>    
        <a href='index.jsp'>View your list</a>    
        
    </body>
</html>
