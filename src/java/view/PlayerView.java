package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// classes in my project
import dbUtils.*;
import java.math.BigDecimal;

public class PlayerView {

    
    
    public static String listAllPlayers(String cssTableClass, DbConn dbc) {

        // String type could have been used, but StringBuilder is more efficient 
        // in this case where we are just appending
        StringBuilder sb = new StringBuilder("");

        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            //String sql = "select web_user_id, user_email, user_password, membership_fee, birthday from web_user";
            String sql = "SELECT ranking, name, age, ranking_points, tourneys_played\n" +
"FROM SP16_2308_tuf62047.atp_player\n" +
"ORDER BY ranking, name, age, ranking_points, tourneys_played";
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table class='");
            sb.append(cssTableClass);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:left'>Ranking</th>");
            sb.append("<th style='text-align:left'>Name</th>");
            sb.append("<th style='text-align:left'>Age</th>");
            sb.append("<th style='text-align:left'>Ranking Points</th>");
            sb.append("<th style='text-align:left'>Tournaments Played</th></tr>");         
            while (results.next()) {
                sb.append("<tr>");
                sb.append(FormatUtils.formatIntegerTd(results.getObject("ranking")));
                sb.append(FormatUtils.formatStringTd(results.getObject("name")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("age")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("ranking_points")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("tourneys_played")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in WebUserSql.listAllUsers(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }
    
    
    
}