
<%@page import="view.TeamView"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.User.Logon" %>
<%@page language="java" import="model.Player.playerData" %>
<%@page language="java" import="java.sql.DriverManager" %>
<%@page language="java" import="java.sql.Connection" %>

<jsp:include page="header.jsp"/>
<title> Teams </title>
<style>
    
    .resultSetFormat {background-color:rgba(51, 51, 51, 0.8);}
    .resultSetFormat th {border: medium solid black; background-color:blue; opacity: 0.75; padding:5px; color: #E0FFFF}
    .resultSetFormat td {border: thin solid black; background-color:rgba(51, 51, 51, 0.8); padding:5px; color: #E0FFFF}
    table {
        margin:auto;
    }
</style>
<%
    String msg = "";
    String Teams="";
    playerData loggedOnCust = (playerData) session.getAttribute("cust"); // must type cast the object before use
    if (loggedOnCust == null) { // means user is not logged in
    try {
            response.sendRedirect("deny.jsp?denyMsg=Please Log In to view the league.");
        } catch (Exception e) {
            msg += " Exception was thrown: " + e.getMessage();
        }
    }
    
    String select_valOne = "";
    
    DbConn dbc = new DbConn();
    if(request.getParameter("Email") != null){
        select_valOne = request.getParameter("Email");
    }
    

    if(select_valOne.equals("")){
        String sql_one = "Select idfantasy_user, email from SP16_2308_tuf62047.fantasy_user " + "ORDER BY email";
        Teams = MakeSelectTag.makeSelect(dbc, "Email", sql_one, "idfantasy_user", "email", "");
    } else{
        select_valOne = request.getParameter("Email");
        String sql_one = "Select idfantasy_user, email from SP16_2308_tuf62047.fantasy_user " + "ORDER BY email";
        Teams = MakeSelectTag.makeSelect(dbc, "Email", sql_one, "idfantasy_user", "email", select_valOne);        
        msg=TeamView.listAllTeams("resultSetFormat", dbc, select_valOne );
    }
    dbc.close();
 %>
<div class="content">
    <form action="myLeague.jsp" method="get">
    Teams: <%=Teams%> <br/>
    <input type="submit" value="Click to Search" />
</form>
    <h2 style="text-align:center">Teams</h2>
    <% out.print(msg);%>
</div>
    
<jsp:include page="footer.jsp"/>
