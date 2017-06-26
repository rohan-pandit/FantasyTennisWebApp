


<%@page contentType="text/html" pageEncoding="UTF-8"%>

 <jsp:include page="header.jsp" />
 <%
    String msg = "";
    if (request.getParameter("denyMsg") != null) {
    msg=request.getParameter("denyMsg");
    
 }
%>
<div class="content">
<h3 style="padding-top: 50px"> <%=msg%> </h3>
<br/>
<div><a href="logon.jsp">Log In</a></div>
Not registered?
<div><a href="register.jsp">Register</a></div>
</div>
 <jsp:include page="footer.jsp" />
