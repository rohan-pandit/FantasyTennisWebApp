
package model.User;

/**
 *
 * @author rohanpandit
 */
import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User.Validation;
import model.User.ValidationUtils;

/**
 *
 * @author rohanpandit
 */
public class DbMods {

    private DbConn dbc;  // Open, live database connection
    private model.User.StringData fieldErrors = new model.User.StringData();
    private String errorMsg = "";

    // all methods of this class require an open database connection.
    public DbMods(DbConn dbc) {
        this.dbc = dbc;
    }

    public model.User.StringData getFieldErrors() {
        return this.fieldErrors;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getAllErrors() {
        return this.errorMsg; // empty string means there was no error.
    }

    public int[] getNumUsers() {
        PreparedStatement stmt = null;
        ResultSet results = null;
        int temp[] = new int[0];
        
        int i = 0;

        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            //String sql = "select web_user_id, user_email, user_password, membership_fee, birthday from web_user";
            String sql = "SELECT idfantasy_user FROM fantasy_user ORDER BY idfantasy_user ASC";
            stmt = dbc.getConn().prepareStatement(sql);

            results = stmt.executeQuery();

            int size = 0;
            if (results != null) {
                results.beforeFirst();
                results.last();
                size = results.getRow();
            }
            
            int numUsers[] = new int[size];
            results.beforeFirst();
            
            while (results.next()) {
                numUsers[i] = results.getInt("idfantasy_user");
                //System.out.println(numUsers[i]);
                i++;
            }

            results.close();
            stmt.close();
            return numUsers;
        } catch (Exception e) {
            return temp;
        }

    }

    public String insert(model.User.StringData user) {

        this.errorMsg = "";// empty error message means it worked.

        Validation v = new Validation(user);
        // dont even try to insert if the user data didnt pass validation.
        if (!v.isValid()) {
            this.errorMsg = "Validation errors. Please try again.";
            this.fieldErrors = v.getFieldErrors(); // this is the field level error messages from the validation
            return this.errorMsg;
        }

        String sql = "INSERT INTO fantasy_user (first_name, last_name, email, password"
                + ") VALUES (?,?,?,?)";

        try {
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, user.fname);
            stmt.setString(2, user.lname);
            stmt.setString(3, user.email);
            stmt.setString(4, user.password);

            if (this.errorMsg.length() != 0) {
                System.out.println(this.errorMsg);
                return this.errorMsg;
            }

            //System.out.println("************* got past encoding");
            try {
                // extract the real java.sql.PreparedStatement from Sally's wrapper class.
                int numRows = stmt.executeUpdate();
                if (numRows == 1) {
                    return ""; // all is GOOD, one record inserted is what we expect
                } else {
                    this.errorMsg = "Error: " + new Integer(numRows).toString()
                            + " records were inserted where only 1 expected."; // probably never get here, would be bulk sql insert
                    return this.errorMsg;
                }
            } // try execute the statement
            catch (SQLException e) {
                if (e.getSQLState().equalsIgnoreCase("S1000")) {
                    // this error would only be possible for a non-auto-increment primary key.
                    this.errorMsg = "Cannot Register: This user already exists.";
                } else if (e.getMessage().toLowerCase().contains("duplicate entry")) {
                    this.errorMsg = "That email already exists."; // for example a unique key constraint.
                } else if (e.getMessage().toLowerCase().contains("foreign key")) {
                    this.errorMsg = "Cannot insert: User Role does not exist."; // for example a unique key constraint.
                } else {
                    this.errorMsg = "User.insert: SQL Exception while attempting insert. "
                            + "SQLState:" + e.getSQLState()
                            + ", Error message: " + e.getMessage();
                    // this message would show up in the NetBeans log window (below the editor)
                    System.out.println("************* " + this.errorMsg);
                }
                return this.errorMsg;
            } // catch
            catch (Exception e) {
                // this message would show up in the NetBeans log window (below the editor)
                this.errorMsg = "WebUserMods.insert: General Error while attempting the insert. " + e.getMessage();
                System.out.println("****************** " + this.errorMsg);
                return this.errorMsg;
            } // catch
        } // trying to prepare the statement
        catch (Exception e) {
            this.errorMsg = "WebUserMods.insert: General Error while trying to prepare the SQL INSERT statement. " + e.getMessage();
            System.out.println("****************** " + this.errorMsg);
            return this.errorMsg;
        }
    }// method

    
}
