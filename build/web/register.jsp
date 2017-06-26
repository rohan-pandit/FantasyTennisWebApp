
<%@page language="java" import="model.User.*" %>
<%@page language="java" import="dbUtils.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="header.jsp"/>
<%
    StringData user = new StringData();

    // Default constructor sets all error messages to "" - good for 1st rendering
    StringData fieldErrors = new StringData();

    DbConn dbc = new DbConn();

    String msg = "";
    String invalidEmail = "";

    String userSelect = "";
    String select_user = "";

    if (request.getParameter("User") != null) {
        select_user = request.getParameter("User");
    }

    if (request.getParameter("userEmail") != null) { // postback 

        // fill WebUserData object with form data (form data is always String)
        user.fname = request.getParameter("fName");
        user.lname = request.getParameter("lName");
        user.email = request.getParameter("userEmail");
        user.password = request.getParameter("pwd");
        user.passwordConf = request.getParameter("pwdConf");

        if (!(user.email.contains("@"))) {
            invalidEmail = "Please make sure you are entering a valid email.";
        } else {

            msg = dbc.getErr();
            if (msg.length() == 0) { // means no error getting db connection

                // Instantiate Web User Mod object and pass validated String Data to its insert method
                DbMods userMods = new DbMods(dbc);
                msg = userMods.insert(user);
                fieldErrors = userMods.getFieldErrors();
                out.print("[" + fieldErrors.getAllStrings() + "]");

                if (msg.length() == 0) { // empty string means record was sucessfully inserted
                    msg = "Thank you for registering, you will receive a registration confirmation email shortly.";
                    RegistrationConf conf = new RegistrationConf();
                    conf.registrationConf(user.fname, user.email);
                }
            }
        }

        dbc.close(); // NEVER have db connection leaks !!!

    } // postback

%>
<div class="content">
    <form name="myForm" action="register.jsp" method="get">
        <h2> Registration </h2>

        <label style="text-align: left; width:150px; display:inline-block">First Name:</label>
        <input type="text" name="fName" value="<%= user.fname%>"/> 
        <span class="error"><%=fieldErrors.fname%></span> 
        <br/>
        <label style="text-align: left; width:150px; display:inline-block">Last Name:</label>
        <input type="text" name="lName" value="<%= user.lname%>"/> 
        <span class="error"><%=fieldErrors.lname%></span> 
        <br/>
        <label style="text-align: left; width:150px; display:inline-block">Email:</label>
        <input type="text" name="userEmail" value="<%= user.email%>"/> 
        <span class="error"><%=fieldErrors.email%></span> 
        <br/>
        <label style="text-align: left; width:150px; display:inline-block">Password:</label>
        <input type="password" name ="pwd" value="<%= user.password%>"/>
        <span class="error"><%=fieldErrors.password%></span>
        <br/>
        <label style="text-align: left; width:150px; display:inline-block">Confirm Password:</label>
        <input type="password" name="pwdConf" value="<%= user.passwordConf%>"/> 
        <span class="error"><%=fieldErrors.passwordConf%></span> 
        <br/>
        <br/><br/>
        <input type="submit" value="Register"/>
        <br/><br/>
        <div class="message">
            <%=msg%>
            <%=invalidEmail%>
        </div>
    </form>
</div>
<jsp:include page="footer.jsp"/>
