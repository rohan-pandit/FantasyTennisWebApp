
package model.Tournament;

import dbUtils.DbConn;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Tournament.Validation;
import model.Tournament.ValidationUtils;
import model.Tournament.StringData;
import java.sql.ResultSet;

/**
 *
 * @author rohanpandit
 */
public class DbMods {

    private DbConn dbc;  // Open, live database connection
    private model.Tournament.StringData fieldErrors = new model.Tournament.StringData();
    private String errorMsg = "";

    // all methods of this class require an open database connection.
    public DbMods(DbConn dbc) {
        this.dbc = dbc;
    }

    public model.Tournament.StringData getFieldErrors() {
        return this.fieldErrors;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getAllErrors() {
        return this.errorMsg; // empty string means there was no error.
    }

    public String insert(model.Tournament.StringData tourney) {

        this.errorMsg = "";// empty error message means it worked.

        Validation v = new Validation(tourney);
        // dont even try to insert if the user data didnt pass validation.
        if (!v.isValid()) {
            this.errorMsg = "Validation errors. Please try again.";
            this.fieldErrors = v.getFieldErrors(); // this is the field level error messages from the validation
            return this.errorMsg;
        }

        String sql = "INSERT INTO tournament (tourney_info, tourney_format, tourney_court_type, tourney_prize_money, tourney_winners"
                + ") VALUES (?,?,?,?,?)";

        try {
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, tourney.info);
            stmt.setString(2, tourney.format);
            stmt.setString(3, tourney.courtType);
            stmt.setString(4, tourney.prizeMoney);
            stmt.setString(5, tourney.winners);

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
                    this.errorMsg = "Cannot insert: a record with that ID already exists.";
                } else if (e.getMessage().toLowerCase().contains("duplicate entry")) {
                    this.errorMsg = "User Friendly Error: That email already exists!."; // for example a unique key constraint.
                } else if (e.getMessage().toLowerCase().contains("foreign key")) {
                    this.errorMsg = "Cannot insert: User Role does not exist."; // for example a unique key constraint.
                } else {
                    this.errorMsg = "WebUserMods.insert: SQL Exception while attempting insert. "
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
    }

    public String ifDrafted(String userId, DbConn dbc) {

        this.errorMsg = "";
        String sql = "SELECT fk_fantasy_user FROM atp_player"
                + " WHERE idatp_player=?";
        ResultSet results = null;
        int fk;

        try {
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setInt(1, ValidationUtils.integerConversion(userId));
            results = stmt.executeQuery();
            results.next();
            fk = results.getInt("fk_fantasy_user");
            
            

            if (this.errorMsg.length() != 0) {
                System.out.println(this.errorMsg);
                return this.errorMsg;
            }

            //System.out.println("************* got past encoding");
            try{
                // extract the real java.sql.PreparedStatement from Sally's wrapper class.
                //int numRows = stmt.executeUpdate();
                System.out.println("Test inside second try.");
                
                
                if (fk == 0) {
                    return ""; // all is GOOD, one record inserted is what we expect
                } else {
                    this.errorMsg = "This player has already been drafted, please choose another."; // probably never get here, would be bulk sql insert
                    return this.errorMsg;
                }
            
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

        
    }

    public String draft(String draftedPlayer, String user) {

        this.errorMsg = "";// empty error message means it worked.

        //Validation v = new Validation(player);
        // dont even try to insert if the user data didnt pass validation.
        /*if (!v.isValid()) {
            this.errorMsg = "Validation errors. Please try again.";
            this.fieldErrors = v.getFieldErrors(); // this is the field level error messages from the validation
            return this.errorMsg;
        }*/
        String sql = "UPDATE atp_player SET fk_fantasy_user=?"
                + " WHERE idatp_player=?";

        try {
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setInt(1, ValidationUtils.integerConversion(user));
            stmt.setInt(2, ValidationUtils.integerConversion(draftedPlayer));

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
                    this.errorMsg = "Cannot insert: a record with that ID already exists.";
                } else if (e.getMessage().toLowerCase().contains("duplicate entry")) {
                    this.errorMsg = "User Friendly Error: That email already exists!."; // for example a unique key constraint.
                } else if (e.getMessage().toLowerCase().contains("foreign key")) {
                    this.errorMsg = "Cannot insert: User Role does not exist."; // for example a unique key constraint.
                } else {
                    this.errorMsg = "WebUserMods.insert: SQL Exception while attempting insert. "
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
