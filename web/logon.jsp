
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.User.Logon" %>
<%@page language="java" import="model.Player.playerData" %>
<%@page language="java" import="java.sql.DriverManager" %>
<%@page language="java" import="java.sql.Connection" %>

<%
    String test = "";
    String strEmail = "";
    String strPword = "";
    String emailErrorMsg = ""; // be optimistic
    String pwordErrorMsg = ""; // dont show an error upon 1st rendering
    String msg = ""; // this is an overall messsage (beyond field level validation)
    if (request.getParameter("email") != null) {
        strEmail = request.getParameter("email");

        strPword = request.getParameter("pwd");

        DbConn dbc = new DbConn(); // get database connection wrapper object
        msg = dbc.getErr();// Check to see if there was any error trying to connect to the database.
        if (msg.length() == 0) { // no error message, so database connection is OK
            
            playerData loggedOnCust = Logon.find(dbc, strEmail, strPword);

            if (loggedOnCust == null) {
                msg = "Please try again. Email or Password is incorrect.";
            } else if (loggedOnCust.errorMsg.length() > 0) {
                msg = loggedOnCust.errorMsg;
            } else {
                session.setAttribute("cust", loggedOnCust); 
                msg = "Login Successful.";
            }

        }

    }

%>
<jsp:include page="header.jsp"/>
<div class="content">
    <h2 style="text-align: center">Log In</h2>
    <form action="logon.jsp" method="get">
        <label style="text-align: left; width:100px; display:inline-block">Email:</label>
        <input type="text" name="email" value="<%out.print(strEmail);%>"/> 
        <span class="error"><%=emailErrorMsg%></span> 
        <br/>
        <label style="text-align: left; width:100px; display:inline-block">Password:</label>
        <input type="password" name ="pwd" value="<%out.print(strPword);%>"/>
        <span class="error"><%=pwordErrorMsg%></span>
        <br/><br/>
        <input type="submit" value="Log On"/>
        <br/><br/>
        <div class="message">
        <%=msg%>
        </div>
    </form>
</div>
<jsp:include page="footer.jsp"/>
