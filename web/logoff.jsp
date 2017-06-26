
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.invalidate();
    try {
    response.sendRedirect("index.jsp"); // or wherever you want the user to land after they log off.
    } catch (Exception e) {
    System.out.println("**** Exception was thrown in logoff.jsp: " + e.getMessage());
    }
%>
