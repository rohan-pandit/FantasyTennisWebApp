
package view;

import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Player.ValidationUtils;

/**
 *
 * @author rohanpandit
 */
public class StandingsView {
    public static String listStandings(String cssTableClass, DbConn dbc) {

        // String type could have been used, but StringBuilder is more efficient 
        // in this case where we are just appending
        StringBuilder sb = new StringBuilder("");

        PreparedStatement stmt = null;
        ResultSet results = null;
        int rank = 1;

        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            //String sql = "select web_user_id, user_email, user_password, membership_fee, birthday from web_user";
            String sql = "SELECT first_name, last_name, wins FROM fantasy_user"
                    + " ORDER BY wins DESC";
            stmt = dbc.getConn().prepareStatement(sql);
            
            results = stmt.executeQuery();

            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table class='");
            sb.append(cssTableClass);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:left'>Ranking</th>");
            sb.append("<th style='text-align:left'>First Name</th>");
            sb.append("<th style='text-align:left'>Last Name</th>");
             sb.append("<th style='text-align:left'>Wins</th></tr>");
            while (results.next()) {
                sb.append("<tr>");
             
                sb.append(FormatUtils.formatIntegerTd(rank));
                sb.append(FormatUtils.formatStringTd(results.getObject("first_name")));
                sb.append(FormatUtils.formatStringTd(results.getObject("last_name")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("wins")));
                sb.append("</tr>\n");
                rank++;
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
