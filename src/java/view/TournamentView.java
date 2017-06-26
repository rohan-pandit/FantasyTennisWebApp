
package view;

import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author rohanpandit
 */
public class TournamentView {


    
    public static String listAllUsers(String cssTableClass, DbConn dbc) {

        // String type could have been used, but StringBuilder is more efficient 
        // in this case where we are just appending
        StringBuilder sb = new StringBuilder("");

        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            //String sql = "select web_user_id, user_email, user_password, membership_fee, birthday from web_user";
            String sql = "SELECT tourney_info, tourney_format, tourney_court_type, tourney_prize_money, tourney_winners\n" +
"FROM SP16_2308_tuf62047.tournament\n";
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table class='");
            sb.append(cssTableClass);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:left'>Info</th>");
            sb.append("<th style='text-align:left'>Format</th>");
            sb.append("<th style='text-align:left'>Court Type</th>");
            sb.append("<th style='text-align:left'>Prize Money</th>");
            sb.append("<th style='text-align:left'>Winners</th></tr>");         
            while (results.next()) {
                sb.append("<tr>");
                
                sb.append(FormatUtils.formatStringTd(results.getObject("tourney_info")));
                sb.append(FormatUtils.formatStringTd(results.getObject("tourney_format")));
                sb.append(FormatUtils.formatStringTd(results.getObject("tourney_court_type")));
                sb.append(FormatUtils.formatStringTd(results.getObject("tourney_prize_money")));
                sb.append(FormatUtils.formatStringTd(results.getObject("tourney_winners")));
   
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
