
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.Match.*" %>
<%@page language="java" import="java.io.IOException" %>
<%@page language="java" import="org.jsoup.Jsoup" %>
<%@page language="java" import="org.jsoup.nodes.Document" %>
<%@page language="java" import="org.jsoup.nodes.Element" %>
<%@page language="java" import="org.jsoup.select.Elements" %>
<%@page language="java" import="java.io.IOException" %>


<%--<%@page language="java" import="nameofclass" %>--%>

<jsp:include page="header.jsp"/>
<div class="content">

    <!--<a href="insertCustomer.jsp"><h3>My League</h3></a>-->
    <title>ATP Results</title>
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
    

            StringData match = new StringData();

           
            String html = "http://www.atpworldtour.com/en/scores/current/barcelona/425/results";
            StringBuilder sb = new StringBuilder("");
            try {
                Document doc = Jsoup.connect(html).get();
                Elements tableElements = doc.select("table");

                Elements tableHeaderEles = tableElements.select("thead tr th");


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
                        if (j == 0) {

                            match.seedOne = rowItems.get(j).text();
                            //match.ranking = rowItems.get(j).text();
                            //System.out.println((rowItems.get(j).text()));
                        } else if (j == 2) {
                            match.playerOne = rowItems.get(j).text();
                            //match.name = rowItems.get(j).text();
                            //System.out.println(match.name);
                        } else if (j == 4) {
                            match.seedTwo = rowItems.get(j).text();
                            //match.age = rowItems.get(j).text();
                        } else if (j == 6) {
                            match.playerTwo = rowItems.get(j).text();
                            //match.rankingPoints = rowItems.get(j).text().replace(",", "");
                        }
                        
                    }

                    //sb.append("executed the query " + "<br/><br/>");
                    sb.append("<tr>");

                    sb.append(FormatUtils.formatStringTd(match.seedOne));
                    sb.append(FormatUtils.formatStringTd(match.playerOne));
                    sb.append(FormatUtils.formatStringTd("W"));
                    sb.append(FormatUtils.formatStringTd(match.seedTwo));
                    sb.append(FormatUtils.formatStringTd(match.playerTwo));
                    

                    sb.append("</tr>\n");
                    sb.toString();

                }
            }catch (IOException e) {
                sb.append("Could not show match results.");
                sb.toString();
            e.printStackTrace();
        }
            // PREVENT DB connection leaks:

    %>

    <h2 style="text-align:center">ATP Results</h2>
    <% out.print(sb);%>

</div>
<jsp:include page="footer.jsp"/>
