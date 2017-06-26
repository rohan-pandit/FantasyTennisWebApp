

<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.Player.*" %>
<%@page language="java" import="view.PlayerView"%>
<%@page language="java" import="java.io.IOException" %>
<%@page language="java" import="org.jsoup.Jsoup" %>
<%@page language="java" import="org.jsoup.nodes.Document" %>
<%@page language="java" import="org.jsoup.nodes.Element" %>
<%@page language="java" import="org.jsoup.select.Elements" %>
<%@page language="java" import="java.io.IOException" %>


<%--<%@page language="java" import="nameofclass" %>--%>

<jsp:include page="header.jsp"/>
<div class="content">
    

<title>List All Customer</title>
<style>
    
    .resultSetFormat {background-color:rgba(51, 51, 51, 0.8);}
    .resultSetFormat th {border: medium solid black; background-color:blue; opacity: 0.75; padding:5px; color: #E0FFFF}
    .resultSetFormat td {border: thin solid black; background-color:rgba(51, 51, 51, 0.8); padding:5px; color: #E0FFFF}
    table {
        margin:auto;
    }

</style>

<form name="deleteForm" action="customers.jsp" method="get">
    <input type="hidden" name="customerPK">
</form>
<%
    DbConn dbc = new DbConn();
    String msg = dbc.getErr(); // returns "" if connection is good, else error msg.
    if (msg.length() == 0) { // got open connection

        
        StringData player = new StringData();

        // Default constructor sets all error messages to "" - good for 1st rendering
        StringData fieldErrors = new StringData();

        //DbConn dbc = new DbConn();
        //String msg="";

        String html = "http://www.atpworldtour.com/en/rankings/singles";
        try {
            Document doc = Jsoup.connect(html).get();
            Elements tableElements = doc.select("table");

            Elements tableHeaderEles = tableElements.select("thead tr th");
            //System.out.println("headers");
            /*for (int i = 0; i < tableHeaderEles.size(); i++) {
                System.out.println(tableHeaderEles.get(i).text());
            }*/
            System.out.println();

            Elements tableRowElements = tableElements.select(":not(thead) tr");

            for (int i = 1; i < tableRowElements.size(); i++) {
                Element row = tableRowElements.get(i);
                //System.out.println("row");
                Elements rowItems = row.select("td");
                for (int j = 0; j < rowItems.size(); j++) {
                    //System.out.println(rowItems.get(j).text());
                    if (j == 0) {
                        //System.out.println("Ranking: " + rowItems.get(j).text());
                        player.ranking = rowItems.get(j).text();
                        //System.out.println((rowItems.get(j).text()));
                    } else if (j == 3) {
                        //System.out.println("Name: " + rowItems.get(j).text());
                        player.name = rowItems.get(j).text();
                        //System.out.println(player.name);
                    } else if (j == 4) {
                        //System.out.println("Age: " + rowItems.get(j).text());
                        player.age = rowItems.get(j).text();
                    } else if (j == 5) {
                        //System.out.println("Ranking Points: " + rowItems.get(j).text());
                        player.rankingPoints = rowItems.get(j).text().replace(",", "");
                    } else if (j == 6) {
                        //System.out.println("Tourneys Played: " + rowItems.get(j).text());
                        player.tourneysPlayed = rowItems.get(j).text();
                    }
                }
               
                
                msg = dbc.getErr();
                
                
                DbMods playerMod = new DbMods(dbc);
                playerMod.update(player);
            
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        msg = PlayerView.listAllPlayers("resultSetFormat", dbc);
    }
        // PREVENT DB connection leaks:
        dbc.close(); //    EVERY code path that opens a db connection, must also close it.
%>

<h2 style="text-align:center">ATP Players</h2>
<% out.print(msg);%>

</div>
<jsp:include page="footer.jsp"/>