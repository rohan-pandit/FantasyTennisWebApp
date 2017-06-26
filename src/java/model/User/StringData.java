
package model.User;

/**
 *
 * @author rohanpandit
 */
public class StringData {
        public String fantasyUserId = "";
        public String fname = "";
        public String lname = "";
        public String email = "";
        public String password = "";
        public String passwordConf = "";
        public String errorMsg = "";
        
    public String getAllStrings() {
        return fantasyUserId + fname + lname + email + password + passwordConf;
    }
}
