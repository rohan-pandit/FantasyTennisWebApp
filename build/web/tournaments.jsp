
<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@page language="java" import="view.TournamentView" %>
<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.Tournament.*" %>
<%@page language="java" import="java.io.IOException" %>
<%@page language="java" import="org.jsoup.Jsoup" %>
<%@page language="java" import="org.jsoup.nodes.Document" %>
<%@page language="java" import="org.jsoup.nodes.Element" %>
<%@page language="java" import="org.jsoup.select.Elements" %>
<%@page language="java" import="java.io.IOException" %>


<%--<%@page language="java" import="nameofclass" %>--%>

<jsp:include page="header.jsp"/>
<div class="content">
    
<title>List All Tournament</title>
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
        /*String customerKey = request.getParameter("customerPK");
        if (customerKey != null && customerKey.length() != 0) {
            DbMods delTournament = new DbMods(dbc);
            delTournament.delete(customerKey);
            out.print(delTournament.getErrorMsg());
            // returns a string that contains a HTML table with the db data in it                      
        }*/
        
        StringData tournament = new StringData();

        // Default constructor sets all error messages to "" - good for 1st rendering
        StringData fieldErrors = new StringData();

        //DbConn dbc = new DbConn();
        //String msg="";

        String html = "http://www.atpworldtour.com/en/scores/results-archive";
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

            for (int i = 0; i < tableRowElements.size(); i++) {
                Element row = tableRowElements.get(i);
                //System.out.println("row");
                Elements rowItems = row.select("td");
                for (int j = 0; j < rowItems.size(); j++) {
                    //System.out.println(rowItems.get(j).text());
                    if (j == 2) {
                        //System.out.println("Ranking: " + rowItems.get(j).text());
                        tournament.info = rowItems.get(j).text();
                        //System.out.println((rowItems.get(j).text()));
                    } else if (j == 3) {
                        //System.out.println("Name: " + rowItems.get(j).text());
                        tournament.format = rowItems.get(j).text();
                        //System.out.println(player.name);
                    } else if (j == 4) {
                        //System.out.println("Name: " + rowItems.get(j).text());
                        tournament.courtType = rowItems.get(j).text();
                        //System.out.println(player.name);
                    } else if (j == 5) {
                        //System.out.println("Name: " + rowItems.get(j).text());
                        tournament.prizeMoney = rowItems.get(j).text();
                        //System.out.println(player.name);
                    } else if (j == 6) {
                        //System.out.println("Name: " + rowItems.get(j).text());
                        tournament.winners = rowItems.get(j).text();
                        //System.out.println(player.name);
                    }
                }
                System.out.println(tournament.info);
                
                msg = dbc.getErr();
                
                
                DbMods tourneyMod = new DbMods(dbc);
                //tourneyMod.insert(tournament);
                
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        msg = TournamentView.listAllUsers("resultSetFormat", dbc);
    }
        // PREVENT DB connection leaks:
        dbc.close(); //    EVERY code path that opens a db connection, must also close it.
%>

<h2 style="text-align:center">ATP Tournaments</h2>
<% out.print(msg);%>

<!--<script language="Javascript" type="text/javascript">
    function deleteTournament(customerId) {
        if (confirm("Do you really want to delete customer " + customerId + "?")) {
            document.deleteForm.customerPK.value = customerId;
            document.deleteForm.submit();
        }
    }
</script>-->
</div>
<jsp:include page="footer.jsp"/>
