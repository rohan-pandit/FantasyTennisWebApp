
package model.Tournament;

/**
 *
 * @author rohanpandit
 */
public class StringData {
        public String tournamentId = "";
        public String info = "";
        public String format = "";
        public String courtType = "";
        public String prizeMoney = "";
        public String winners = "";
        public String errorMsg = "";
        
    public String getAllStrings() {
        return tournamentId + info + format + courtType + prizeMoney + winners;
    }

}
