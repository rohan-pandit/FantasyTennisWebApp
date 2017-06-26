
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page language="java" import="view.TeamView" %>
<%@page language="java" import="dbUtils.DbConn" %>
<%@page language="java" import="model.User.Logon" %>
<%@page language="java" import="model.Player.playerData" %>

<jsp:include page="header.jsp"/>
<div class="content">
    <h3>My Team</h3>
<title>List All Users</title>
<style>
    
    .resultSetFormat {background-color:rgba(51, 51, 51, 0.8);}
    .resultSetFormat th {border: medium solid black; background-color:blue; opacity: 0.75; padding:5px; color: #E0FFFF}
    .resultSetFormat td {border: thin solid black; background-color:rgba(51, 51, 51, 0.8); padding:5px; color: #E0FFFF}
    table {
        margin:auto;
    }
</style>



<%
    DbConn dbc = new DbConn();
    String msg = dbc.getErr(); // returns "" if connection is good, else error msg.
    playerData loggedOnCust = (playerData) session.getAttribute("cust"); // must type cast the object before use

        if (loggedOnCust == null) { // means user is not logged in
            try {

                response.sendRedirect("deny.jsp?denyMsg=Please Log In to view your team.");

            } catch (Exception e) {
                msg += " Exception was thrown: " + e.getMessage();
            }
        } else {

            String user = loggedOnCust.userId;
            msg = dbc.getErr();

            if (msg.length() == 0) { 
                msg=TeamView.listAllTeams("resultSetFormat", dbc,user);
            }
            dbc.close(); 
        }
    // PREVENT DB connection leaks:
    //    EVERY code path that opens a db connection, must also close it.
%>


<% out.print(msg);%>

</div>
<jsp:include page="footer.jsp"/>