
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.User.Logon" %>
<%@page language="java" import="model.Player.playerData" %>
<%@page language="java" import="java.sql.DriverManager" %>
<%@page language="java" import="java.sql.Connection" %>
<%
    String logonLink = "<a href = 'logon.jsp'>Log On</a>";
    String msg = "";
    playerData loggedOnCust = (playerData) session.getAttribute("cust"); // must type cast the object before use
    String welcome = "";
    if (loggedOnCust != null) { // means user is logged in
        try {
            logonLink = "<a href = 'logoff.jsp'>Log Off</a>";
            String custName = loggedOnCust.firstName;
            //welcome = "<h2 style='color: black; padding-top: 100px'>Welcome " + custName + "</h2>";
        } catch (Exception e) {
            msg += " Exception was thrown: " + e.getMessage();
        }
    }
%>
<!DOCTYPE html> 
<html>    
    <head>
        <title>Fantasy Tennis</title>
        <link rel="stylesheet" type="text/css" href="fantasyTennis.css">
    </head>


    <body>
        <div id="nav">

            <ul>
                <li class="right"><a href="about.jsp">About</a></li>
                <li class="right"><a href="register.jsp">Register</a></li>
                <li class="right"><%out.print(logonLink);%></li>
                <li class="right"><a href="players.jsp">Players</a></li>

                <li class="left"><a href="index.jsp">Home</a></li>



                <!--<li class="left"><a href="myLeague.jsp">My League</a></li>  -->
                <li class="dropdown" style="float: left;">
                    <a class="dropbtn">League</a>
                    <div class="dropdown-content">
                        <a href="myLeague.jsp">My League</a>
                        <a href="draft.jsp">Draft</a>
                        <a href="matches.jsp">Results</a>
                        <a href="standings.jsp">Standings</a>
                        
                    </div>
                </li> 
                 
                <li class="left"><a href="myTeam.jsp">My Team</a></li>
                <li class="left"><a href="tournaments.jsp">Tournaments</a></li>



            </ul>
        </div>
        <div id="title">
            Fantasy Tennis
        </div>
        <div class="container">

            <%out.print(welcome);%>


