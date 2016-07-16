
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
        <h1>Dear Santa,</h1>
        <h2>All I want for Christmas is a Red Ryder BB gun.</h2>
        
        <p>"You'll shoot your eye out, kid!"</p><br>
        
        <div id="user_list">
            <h2>${name}'s Friends</h2>
            <c:forEach var="user" items="${listUsers}">
                <a href="LoadWishlist?id=${user.id}">${user.name}</a><br>
            </c:forEach>
            <a href="friendsearch.jsp">Add a friend</a><br>
        </div><br>
        
        <div id="display_list">
            <h2>${name}'s list</h2>
            
            <c:forEach var="item" items="${wishlist}">
                <!-- http://stackoverflow.com/questions/5618556/java-servlet-request-getparametervalues  -->
                <a href="${item.link}">${item.title}</a><br>
            </c:forEach>
                
                <br><br><br>
                
            <c:forEach var="user" items="${listUsers}">
                <!-- http://stackoverflow.com/questions/5618556/java-servlet-request-getparametervalues  -->
                <a href="FriendList?id=${user.id}">${user.name}</a><br>
            </c:forEach>
            
        </div><br>
        <p><a href='search.jsp'>Click here to add items to your wish list</a></p>
        <p>Brought to you by the awesomeness that is Team 4</p>
    </body>
</html>
</html>
