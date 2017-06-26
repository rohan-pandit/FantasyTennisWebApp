package dbUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author rohanpandit
 */
public class MakeSelectTag {

    public static String makeSelect(DbConn dbc, String select_name, String sqlStm, String id, String val, String select_val) {
        String sql = sqlStm;
        String tagName = select_name;
        String option = "";
        String option_name = select_name;
        String out = "\n\n<select name = '" + tagName + "'>\n";
        
        if (select_val.equals("")) {
            option = "";
            out += " <option value='" + option + "' selected='selected" + "'>";
            out += "Select " + option_name + "</option>\n";
        }else{
            out += " <option value='" + option + "'>";
            out += "Select " + option_name + "</option>\n";
        }
        
        try {
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                if (select_val.equals(Integer.toString(results.getInt(id)))) {
                    out += " <option value='" + results.getInt(id) + "' selected='selected" + "'>";
                    out += results.getString(val) + "</option>\n";
                } else {
                    out += " <option value='" + results.getInt(id) + "'>";
                    out += results.getString(val) + "</option>\n";
                }

            }
            out += "</select>\n\n";
            return out;
        } catch (Exception e) {
            return "Exception in MakeSelectTag.makeSelect(). Partial output: " + out
                    + ". Error: " + e.getMessage();
        }
    }
}
