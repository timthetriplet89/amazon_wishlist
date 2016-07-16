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
        <link rel="stylesheet" type="text/css" href="stylesheet.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Christmas Wish List</title>
    </head>
    <body>
        <h1>Add an item to your list</h1>
        
        <form action='SearchProcessing' method='post'>
            <input type='text' name='search_box'>
            <input type='submit' name='submit' value='Search'>
        </form>
        <br/>
        
        <form action="AddToWishlist" method="post">
            <c:forEach var="item" items="${listItems}">

                <input type="checkbox" name="items" value="${item.index}" />     <!-- http://stackoverflow.com/questions/5618556/java-servlet-request-getparametervalues  -->
                <a href="${item.link}">${item.title}</a>
                <br />
            </c:forEach>
            <input type="submit" value="Add Item" />
        </form>    
        
        <br/>    
        <a href='index.jsp'>View your list</a>    
        
    </body>
</html>
