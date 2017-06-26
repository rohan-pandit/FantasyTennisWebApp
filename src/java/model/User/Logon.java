package model.User;

import java.sql.DriverManager;
import java.sql.Connection;
import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Player.playerData;


public class Logon {

    public static playerData find(DbConn dbc, String emailAddress, String userPwd) {
        playerData foundUser = new playerData(); // default constructor sets all fields to "" (empty string)
        try {
            String sql = "SELECT idfantasy_user, first_name, last_name, email, password FROM SP16_2308_tuf62047.fantasy_user WHERE fantasy_user.email = ? AND fantasy_user.password = ?";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            // System.out.println("*** statement prepared- no sql compile errors");
            // this puts the user's input (from variable emailAddress) into the 1st question mark of the sql statement above.
            stmt.setString(1, emailAddress);
            // System.out.println("*** email address substituted into the sql");
            // this puts the user's input (from variable userPwd) into the 2nd question mark of the sql statement above.
            stmt.setString(2, userPwd);
            // System.out.println("*** pwd substituted into the sql");
            ResultSet results = stmt.executeQuery();
            // System.out.println("*** query executed");
            // since the email address is required (in database) to be unique, we don't need a while loop like we did
            // for the display data lab. An "if" statement is better for this purpose.
            if(results.next()) {
                //System.out.println("*** record selected");
                foundUser.userId = results.getObject("idfantasy_user").toString();
                foundUser.firstName = results.getObject("first_name").toString();
                foundUser.lastName = results.getObject("last_name").toString();
                foundUser.emailAddress = emailAddress; // we can take this from input parameter instead of db.
                //foundUser.pwd = userPwd;
                foundUser.password = results.getString("password");
                //System.out.println("*** 3 fields extracted from result set");
                return foundUser;
            } else {
                return null; // means customer not found with given credentials.
            }
        } catch (Exception e) {
            foundUser.errorMsg = "Exception thrown in Logon.find(): " + e.getMessage();
            return foundUser;
        }
    }
}
