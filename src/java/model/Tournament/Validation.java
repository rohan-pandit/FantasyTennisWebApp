
package model.Tournament;

/**
 *
 * @author rohanpandit
 */
public class Validation {
    private model.Tournament.StringData tourney = new model.Tournament.StringData();

    // validation error messages, one per field to be validated
    private model.Tournament.StringData fieldErrors = new model.Tournament.StringData();

    private boolean valid = false; // true iff all fields validate ok.

    // default constructor is good for first rendering (all error messages are set to "", empty string).
    public Validation() {
    }

    // Other constructor does the actual field level validation
    public Validation(model.Tournament.StringData tourney) {
        // validationUtils method validates each user input (String even if destined for other type) from WebUser object
        // side effect of validationUtils method puts validated, converted typed value into TypedData object
        this.tourney = tourney;

        // this is not needed for insert, but will be needed for update.
        if (tourney.tournamentId != null && tourney.tournamentId.length() != 0) {
            this.fieldErrors.tournamentId = model.Tournament.ValidationUtils.integerValidationMsg(tourney.tournamentId, true);
        }
        this.fieldErrors.info = model.Tournament.ValidationUtils.stringValidationMsg(tourney.info, 100, true);
        this.fieldErrors.format = model.Tournament.ValidationUtils.stringValidationMsg(tourney.format, 100, true);
        this.fieldErrors.courtType = model.Tournament.ValidationUtils.stringValidationMsg(tourney.courtType, 100, true);
        this.fieldErrors.prizeMoney = model.Tournament.ValidationUtils.stringValidationMsg(tourney.prizeMoney, 100, true);
        this.fieldErrors.winners = model.Tournament.ValidationUtils.stringValidationMsg(tourney.winners, 100, true);
        
       

        String allMessages = this.fieldErrors.getAllStrings();
        //System.out.println(allMessages);
        this.valid = (allMessages.length() == 0);
    }

    public model.Tournament.StringData getStringData() {
        return this.tourney;
    }

    public model.Tournament.StringData getFieldErrors() {
        return this.fieldErrors;
    }
    
    public boolean isValid() {
        return this.valid;
    }
}
