
package model.Player;

/**
 *
 * @author rohanpandit
 */
public class Validation {
    private StringData player = new StringData();

    // validation error messages, one per field to be validated
    private StringData fieldErrors = new StringData();

    private boolean valid = false; // true iff all fields validate ok.

    // default constructor is good for first rendering (all error messages are set to "", empty string).
    public Validation() {
    }

    // Other constructor does the actual field level validation
    public Validation(StringData player) {
        // validationUtils method validates each user input (String even if destined for other type) from WebUser object
        // side effect of validationUtils method puts validated, converted typed value into TypedData object
        this.player = player;

        // this is not needed for insert, but will be needed for update.
        if (player.playerId != null && player.playerId.length() != 0) {
            this.fieldErrors.playerId = ValidationUtils.integerValidationMsg(player.playerId, true);
        }
        this.fieldErrors.ranking = ValidationUtils.integerValidationMsg(player.ranking, true);
        this.fieldErrors.name = ValidationUtils.stringValidationMsg(player.name, 45, true);
        this.fieldErrors.age = ValidationUtils.integerValidationMsg(player.age, true);
        this.fieldErrors.rankingPoints = ValidationUtils.integerValidationMsg(player.rankingPoints, true);
        this.fieldErrors.tourneysPlayed = ValidationUtils.integerValidationMsg(player.tourneysPlayed, true);
  

        String allMessages = this.fieldErrors.getAllStrings();
        //System.out.println(allMessages);
        this.valid = (allMessages.length() == 0);
    }

    public StringData getStringData() {
        return this.player;
    }

    public StringData getFieldErrors() {
        return this.fieldErrors;
    }
    
    public boolean isValid() {
        return this.valid;
    }
}
