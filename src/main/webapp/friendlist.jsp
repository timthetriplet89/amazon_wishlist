
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <html>
    <head>
        <link rel="icon" type="image/icon" href="logo.png">
        <link rel="stylesheet" type="text/css" href="stylesheet.css">
        <title>Christmas Wish List</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <img src="logo.png" alt="Christmas Wishlist" id="logo">
        <h1>${authname}'s List</h1>
        
        <div id="friend_list">
            ${errorMessage2}
            <c:forEach var="item" items="${wishlist}">
                <!-- http://stackoverflow.com/questions/5618556/java-servlet-request-getparametervalues  -->
                <a href="${item.link}">${item.title}</a><br>
            </c:forEach>
                
                <br><br><br>
                
       </div><br>
        <p><a href='LoadWishlist'>Click here to go back to your list</a></p>
        
    </body>
</html>
</html>
