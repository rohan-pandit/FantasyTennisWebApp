
package view;

import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author rohanpandit
 */
public class DraftView {

    /* This method returns a HTML table displaying all the records of the web_user table. 
     * cssClassForResultSetTable: the name of a CSS style that will be applied to the HTML table.
     *   (This style should be defined in the JSP page (header or style sheet referenced by the page).
     * dbc: an open database connection.
     */



    public static String listAllUsers(String cssTableClass, DbConn dbc) {

        // String type could have been used, but StringBuilder is more efficient 
        // in this case where we are just appending
        StringBuilder sb = new StringBuilder("");

        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            //String sql = "select web_user_id, user_email, user_password, membership_fee, birthday from web_user";
            // WHERE atp_player.fk_fantasy_user=fantasy_user.idfantasy_user 
            String sql = "SELECT ranking, name, age, ranking_points, tourneys_played, email\n"
                    + "FROM SP16_2308_tuf62047.atp_player left join fantasy_user\n ON atp_player.fk_fantasy_user=fantasy_user.idfantasy_user "
                    + "ORDER BY ranking, name, age, ranking_points, tourneys_played, email";
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table class='");
            sb.append(cssTableClass);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:left'>Draft</th>");
            sb.append("<th style='text-align:left'>Ranking</th>");
            sb.append("<th style='text-align:left'>Name</th>");
            sb.append("<th style='text-align:left'>Age</th>");
            sb.append("<th style='text-align:left'>Ranking Points</th>");
            sb.append("<th style='text-align:left'>Tournaments Played</th>");
            sb.append("<th style='text-align:left'>Owner</th></tr>");
            while (results.next()) {
                sb.append("<tr>");
                sb.append("<td style='background-color:transparent;border:none;'><a href=draft.jsp><img src=delete.png> </a> </td>");
                sb.append(FormatUtils.formatIntegerTd(results.getObject("ranking")));
                sb.append(FormatUtils.formatStringTd(results.getObject("name")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("age")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("ranking_points")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("tourneys_played")));
                sb.append(FormatUtils.formatStringTd(results.getObject("email")));
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

    public static String listDraft(String cssTableClass, DbConn dbc, String delFn, String delIcon) {

        StringBuilder sb = new StringBuilder("");
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {

            String sql = "SELECT idatp_player, ranking, name, age, ranking_points, tourneys_played, email\n"
                    + "FROM SP16_2308_tuf62047.atp_player left join fantasy_user\n ON atp_player.fk_fantasy_user=fantasy_user.idfantasy_user "
                    + "ORDER BY ranking, name, age, ranking_points, tourneys_played, email";

            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            String delStart = "<td style='border:none; text-align:center; background-color:transparent;'><a href='" + delFn + "(";
            String delEnd = ")'><img src='" + delIcon + "'></a></td>";

            sb.append("<table class='");
            sb.append(cssTableClass);
            sb.append("'>\n");
            sb.append("<tr>");
            /*if (deleteFn.length() > 0) {
                sb.append("<th style='background-color:transparent;border:none;'> </th>");
            }*/

            sb.append("<th style='text-align:left'>Draft</th>");
            sb.append("<th style='text-align:left'>Ranking</th>");
            sb.append("<th style='text-align:left'>Name</th>");
            sb.append("<th style='text-align:left'>Age</th>");
            sb.append("<th style='text-align:left'>Ranking Points</th>");
            sb.append("<th style='text-align:left'>Tournaments Played</th>");
            sb.append("<th style='text-align:right'>Owner</th></tr>");
            while (results.next()) {
                sb.append("<tr>");
                String custId = results.getString("idatp_player");
                String playerName = results.getString("name");
                System.out.println("Cust id: " + custId);
                System.out.println("Name: " + playerName);
                //String roleId = results.getString("user_role_id");
                /*if (deleteFn.length() > 0) {
                    sb.append("<td style='background-color:transparent;border:none;'><a href='javascript:" + deleteFn +
                            "("+ custId + ")'><img src='" + deleteIcon + "'></a> </td>");
                }*/

                Object primaryKeyObj = results.getObject(1);
                Integer primaryKeyInt = (Integer) primaryKeyObj;
                /*if (updateURL.length() > 0) {
                    sb.append("<td style='background-color:transparent;border:none;'><a href='updateCustomer.jsp?custId=" 
                            + custId + "&roleId=" + roleId + "'><img src='" + updateIcon + "'></a> </td>");
                    sb.append(delStart + primaryKeyInt.toString() + delEnd);
                }*/
                /*if (delFn.length() > 0) {
                    sb.append("<td style='background-color:transparent;border:none;'><a href='javascript:" + delFn
                            + "(" + custId + ", " + playerName + ")'><img src='" + delIcon + "'></a> </td>");

                }*/
                  if (delFn.length() > 0) {
                    sb.append("<td style='background-color:transparent;border:none;'><a href='javascript:" + delFn
                            + "(" + custId + ")'><img src='" + delIcon + "'></a> </td>");

                }
                //sb.append("<td style='text-align:right'>" + custId + "</td>");
                sb.append(FormatUtils.formatIntegerTd(results.getObject("ranking")));
                sb.append(FormatUtils.formatStringTd(results.getObject("name")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("age")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("ranking_points")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("tourneys_played")));
                sb.append(FormatUtils.formatStringTd(results.getObject("email")));
                sb.append("</tr>\n");
            }
            sb.append("</table>\n");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in CustomerView.CustomersByName(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }

    }

    
}
