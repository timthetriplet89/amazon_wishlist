<%-- 
    Document   : signin
    Created on : Jul 2, 2016, 1:12:37 PM
    Author     : Kam Kam
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="stylesheet.css">
        <title>Christmas Wish List</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <h1>Sign In Page</h1>
        
       
        <p>Please sign in below to access your wish list.</p>
        
        <form action="UserSignIn" method="POST">
            Username: <input type="text" name="username"><br>
            Password: <input type="password" name="password"><br>
            <input type="submit" value="Sign In">
        </form>
        
        <p class="error">${errorMessage}</p>
    </body>
</html>
