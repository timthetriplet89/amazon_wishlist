
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <!-- this will be a foreach statement connecting to database
            and displaying each user as a link that has connected with the current 
            user. Clicking on link will display other user's list in display_list
            section and change the name in the session variable to show the new
            user's list-->
            <h2>${name}'s Friends</h2>
            <ul>
                <li>Mom</li>
                <li>Dad</li>
                <li>Sally</li>
                <li>John Bob</li>
                <li><a href="friendsearch.jsp">Add a friend</a></li>
            </ul>
        </div><br>
        
        <div id="display_list">
            <!-- this will be a foreach statement connecting to database
            and displaying each item saved to the user's list-->
            
            <!-- User name will be stored as session setting and will be populated
            here from sesson variable -->
            <h2>${name}'s list</h2>
            <ul>
                <li>Drum set</li>
                <li>Drone</li>
                <li>Barbie</li>
            </ul>
            
            <br>
            <br>
            
            
        </div><br>
        <p><a href='search.jsp'>Click here to add items to your wish list</a></p>
        <p>Brought to you by the awesomeness that is Team 4</p>
    </body>
</html>
</html>
