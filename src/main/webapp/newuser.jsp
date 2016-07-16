

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="stylesheet.css">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>New User Sign Up</title>
    </head>
    <body>
        <img src="logo.png" alt="Christmas Wishlist" id="logo">
        <h1>Sign Up Page</h1>
        
        <p>Please fill out the form below to create your new account.</p>
        
        <form action="CreateUser" method="POST">
            Name: <input type="text" name="name"><br>
            Username: <input type="text" name="username"><br>
            Password: <input type="password" name="password"><br>
            <input type="submit" value="Create Account">
        </form>
        
        <p class="error">${errorMessage}</p>
        
        <p>Already have an account? <a href="signin.jsp">Click here</a> to login.</p>
        
    </body>
</html>
