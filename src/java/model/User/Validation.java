
package model.User;

/**
 *
 * @author rohanpandit
 */
public class Validation {
        private model.User.StringData user = new model.User.StringData();

    // validation error messages, one per field to be validated
    private model.User.StringData fieldErrors = new model.User.StringData();

    private boolean valid = false; // true iff all fields validate ok.

    // default constructor is good for first rendering (all error messages are set to "", empty string).
    public Validation() {
    }

    // Other constructor does the actual field level validation
    public Validation(model.User.StringData user) {
        // validationUtils method validates each user input (String even if destined for other type) from WebUser object
        // side effect of validationUtils method puts validated, converted typed value into TypedData object
        this.user = user;

        // this is not needed for insert, but will be needed for update.
        if (user.fantasyUserId != null && user.fantasyUserId.length() != 0) {
            this.fieldErrors.fantasyUserId = model.User.ValidationUtils.integerValidationMsg(user.fantasyUserId, true);
        }
        this.fieldErrors.fname = model.User.ValidationUtils.stringValidationMsg(user.fname, 45, true);
        this.fieldErrors.lname = model.User.ValidationUtils.stringValidationMsg(user.lname, 45, true);
        this.fieldErrors.email = model.User.ValidationUtils.stringValidationMsg(user.email, 45, true);
        this.fieldErrors.password = model.User.ValidationUtils.stringValidationMsg(user.password, 45, true);
        this.fieldErrors.passwordConf = model.User.ValidationUtils.stringValidationMsg(user.passwordConf, 45, true);
        
          if (user.password.compareTo(user.passwordConf) != 0) {
            this.fieldErrors.passwordConf = "Both passwords must match.";
        }
        
        
        String allMessages = this.fieldErrors.getAllStrings();
        //System.out.println(allMessages);
        this.valid = (allMessages.length() == 0);
    }

    public model.User.StringData getStringData() {
        return this.user;
    }

    public model.User.StringData getFieldErrors() {
        return this.fieldErrors;
    }
    
    public boolean isValid() {
        return this.valid;
    }
}
