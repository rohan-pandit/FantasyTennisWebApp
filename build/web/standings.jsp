
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.Player.*" %>
<%@page language="java" import="model.Match.*" %>
<%@page language="java" import="view.PlayerView"%>
<%@page language="java" import="java.io.IOException" %>
<%@page language="java" import="org.jsoup.Jsoup" %>
<%@page language="java" import="org.jsoup.nodes.Document" %>
<%@page language="java" import="org.jsoup.nodes.Element" %>
<%@page language="java" import="org.jsoup.select.Elements" %>
<%@page language="java" import="java.io.IOException" %>
<%@page language="java" import="model.Standing.*" %>
<%@page language="java" import="model.User.*" %>
<%@page language="java" import="view.*" %>



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
        model.Standing.DbMods test = new model.Standing.DbMods(dbc);

        model.User.DbMods userNum = new model.User.DbMods(dbc);

        int x[] = userNum.getNumUsers();
        //System.out.println(x.length);
        for (int m = 0; m < x.length; m++) {
            //test.getPlayer("1");
            //System.out.println(x[m]);
            String playerNames = test.getPlayer(x[m]);

            String[] individualNames = playerNames.split(",");
            int numWins = 0;
            for (int k = 0; k < individualNames.length; k++) {
                String atpPlayer = individualNames[k];
                
                model.Match.StringData match = new model.Match.StringData();

                
                String html = "http://www.atpworldtour.com/en/scores/current/";
                StringBuilder sb = new StringBuilder("");
                try {
                    Document doc = Jsoup.connect(html).get();
                    Elements tableElements = doc.select("table");

                    Elements tableHeaderEles = tableElements.select("thead tr th");
                    //Elements tableHeaderEles = tableElements.select("tbody tr th");
                    //System.out.println("headers");
                    /*for (int i = 0; i < tableHeaderEles.size(); i++) {
                    System.out.println(tableHeaderEles.get(i).text());
                }*/
                    //System.out.println("Test");

                    Elements tableRowElements = tableElements.select(":not(thead) tr");

                    sb.append("<table class='");
                    sb.append("resultSetFormat");
                    sb.append("'>");
                    sb.append("<tr>");
                    sb.append("<th style='text-align:left'>Seed</th>");
                    sb.append("<th style='text-align:left'>Player One</th>");
                    sb.append("<th style='text-align:left'>Result</th>");
                    sb.append("<th style='text-align:left'>Seed</th>");
                    sb.append("<th style='text-align:left'>Player Two</th></tr>");
                    for (int i = 3; i < tableRowElements.size(); i++) {
                        Element row = tableRowElements.get(i);
                        //System.out.println(row);
                        Elements rowItems = row.select("td");
                        for (int j = 0; j < rowItems.size(); j++) {
                            //System.out.println(rowItems.get(j).text());
                            if (j == 2) {
                                match.playerOne = rowItems.get(j).text();
                                //match.name = rowItems.get(j).text();
                                //System.out.println(match.name);
                                if (atpPlayer.equals(match.playerOne)) {
                                    numWins++;
                                }
                            }

                        }

                    }
                } catch (IOException e) {
                    sb.append("Could not show match results.");
                    sb.toString();
                    e.printStackTrace();
                }
            }
            test.storeWins(numWins, x[m]);
        }
        
        
        String msg = StandingsView.listStandings("resultSetFormat", dbc);
        


    %>

    <h2 style="text-align:center">Standings</h2>
    <% out.print(msg);%>


</div>
<jsp:include page="footer.jsp"/>