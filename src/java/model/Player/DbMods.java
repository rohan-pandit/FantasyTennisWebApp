/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Player;

import dbUtils.DbConn;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 *
 * @author rohanpandit
 */
public class DbMods {

    private DbConn dbc;  // Open, live database connection
    private StringData fieldErrors = new StringData();
    private String errorMsg = "";

    // all methods of this class require an open database connection.
    public DbMods(DbConn dbc) {
        this.dbc = dbc;
    }

    public StringData getFieldErrors() {
        return this.fieldErrors;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getAllErrors() {
        return this.errorMsg; // empty string means there was no error.
    }

    public String insert(StringData player) {

        this.errorMsg = "";// empty error message means it worked.

        Validation v = new Validation(player);
        // dont even try to insert if the user data didnt pass validation.
        if (!v.isValid()) {
            this.errorMsg = "Validation errors. Please try again.";
            this.fieldErrors = v.getFieldErrors(); // this is the field level error messages from the validation
            return this.errorMsg;
        }

        String sql = "INSERT INTO atp_player (ranking, name, age, ranking_points, tourneys_played"
                + ") VALUES (?,?,?,?,?)";

        try {
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setInt(1, ValidationUtils.integerConversion(player.ranking));
            stmt.setString(2, player.name);
            stmt.setInt(3, ValidationUtils.integerConversion(player.age));
            stmt.setInt(4, ValidationUtils.integerConversion(player.rankingPoints));
            stmt.setInt(5, ValidationUtils.integerConversion(player.tourneysPlayed));
            
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
                    this.errorMsg = "Error: " + new Integer(numRows).toString(); // probably never get here, would be bulk sql insert
                    return this.errorMsg;
                }
            } // try execute the statement
            catch (SQLException e) {
                if (e.getSQLState().equalsIgnoreCase("S1000")) {
                    // this error would only be possible for a non-auto-increment primary key.
                    this.errorMsg = "Cannot insert: that player already exists.";
                } else if (e.getMessage().toLowerCase().contains("duplicate entry")) {
                    this.errorMsg = "That player already exists!."; // for example a unique key constraint.
                } else if (e.getMessage().toLowerCase().contains("foreign key")) {
                    this.errorMsg = ""; // for example a unique key constraint.
                } else {
                    this.errorMsg = "DbMods.insert: SQL Exception while attempting insert. "
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

    public String update(StringData player) {
        
        this.errorMsg = "";// empty error message means it worked.

        Validation v = new Validation(player);
        // dont even try to insert if the user data didnt pass validation.
        if (!v.isValid()) {
            this.errorMsg = "Validation errors. Please try again.";
            this.fieldErrors = v.getFieldErrors(); // this is the field level error messages from the validation
            return this.errorMsg;
        }

        String sql = "UPDATE atp_player SET ranking=?, name=?, age=?, ranking_points=?, tourneys_played=?"
                + " WHERE idatp_player=?";
      
        
        try {
            //System.out.println("test");
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);

            //System.out.println("test");
            stmt.setInt(6, ValidationUtils.integerConversion(player.ranking));
            stmt.setInt(1, ValidationUtils.integerConversion(player.ranking));
            stmt.setString(2, player.name);
            stmt.setInt(3, ValidationUtils.integerConversion(player.age));
            stmt.setInt(4, ValidationUtils.integerConversion(player.rankingPoints));
            stmt.setInt(5, ValidationUtils.integerConversion(player.tourneysPlayed));
            //stmt.setInt(6, ValidationUtils.integerConversion(customer.ranking));
            if (this.errorMsg.length() != 0) {
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
                            + " records were updated where only 1 expected."; // probably never get here, would be bulk sql insert
                    return this.errorMsg;
                }
            } // try execute the statement
            catch (SQLException e) {
                if (e.getSQLState().equalsIgnoreCase("S1000")) {
                    // this error would only be possible for a non-auto-increment primary key.
                    this.errorMsg = "Cannot update: a record with that ID already exists.";
                } else if (e.getMessage().toLowerCase().contains("duplicate entry")) {
                    this.errorMsg = "User Friendly Error: That email already exists!."; // for example a unique key constraint.
                } else if (e.getMessage().toLowerCase().contains("foreign key")) {
                    this.errorMsg = "Cannot insert: User Role does not exist."; // for example a unique key constraint.
                } else {
                    this.errorMsg = "WebUserMods.insert: SQL Exception while attempting update. "
                            + "SQLState:" + e.getSQLState()
                            + ", Error message: " + e.getMessage();
                    // this message would show up in the NetBeans log window (below the editor)
                    System.out.println("************* " + this.errorMsg);
                }
                return this.errorMsg;
            } // catch
            catch (Exception e) {
                // this message would show up in the NetBeans log window (below the editor)
                this.errorMsg = "WebUserMods.insert: General Error while attempting the update. " + e.getMessage();
                System.out.println("****************** " + this.errorMsg);
                return this.errorMsg;
            } // catch
        } // trying to prepare the statement
        catch (Exception e) {
            this.errorMsg = "WebUserMods.insert: General Error while trying to prepare the SQL UPDATE statement. " + e.getMessage();
            System.out.println("****************** " + this.errorMsg);
            return this.errorMsg;
        }
    }// method
    
    
}

