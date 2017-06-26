
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page language="java" import="view.DraftView" %>
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.Tournament.*" %>
<%@page language="java" import="model.Player.*" %>
<%@page language="java" import="java.io.IOException" %>
<%@page language="java" import="org.jsoup.Jsoup" %>
<%@page language="java" import="org.jsoup.nodes.Document" %>
<%@page language="java" import="org.jsoup.nodes.Element" %>
<%@page language="java" import="org.jsoup.select.Elements" %>
<%@page language="java" import="java.io.IOException" %>
<%@page language="java" import="java.sql.DriverManager" %>
<%@page language="java" import="java.sql.Connection" %>


<%--<%@page language="java" import="nameofclass" %>--%>

<jsp:include page="header.jsp"/>
<div class="content">
    <title>List All Draft</title>
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
        String isDrafted="";

        playerData loggedOnCust = (playerData) session.getAttribute("cust"); 
        if (loggedOnCust == null) { 
            try {

                response.sendRedirect("deny.jsp?denyMsg=Please Log In to view the league.");

            } catch (Exception e) {
                msg += " Exception was thrown: " + e.getMessage();
            }
        } else {

            String drafter = loggedOnCust.userId;

            DbConn dbc = new DbConn();
            msg = dbc.getErr();

            if (msg.length() == 0) { // got open connection

                String playerKey = request.getParameter("playerPK");


                if (playerKey != null && playerKey.length() != 0) {
                    //out.print(playerKey);
                    model.Tournament.DbMods updateDraft = new model.Tournament.DbMods(dbc);
                    isDrafted = updateDraft.ifDrafted(playerKey, dbc);
                    if (isDrafted.length() == 0) {
                        updateDraft.draft(playerKey, drafter);

                    }

                                
                }

                msg = DraftView.listDraft("resultSetFormat", dbc, "javascript:draft", "delete.png");
            }

   
            dbc.close(); 
        }

    %>

    <h2 style="text-align:center">ATP Players</h2>
    <div class="message"><% out.print(isDrafted);%></div>
    <% out.print(msg);%>

    <form name="draftForm" action="draft.jsp" method="get">
        <input type="hidden" name="playerPK">
    </form>

    <script language="Javascript" type="text/javascript">
        function draft(customerId) {
            if (confirm("Do you really want to draft this player?")) {
                document.draftForm.playerPK.value = customerId;
                document.draftForm.submit();
            }
        }
    </script>
</div>
<jsp:include page="footer.jsp"/>
