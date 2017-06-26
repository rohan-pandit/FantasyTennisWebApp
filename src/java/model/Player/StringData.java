
package model.Player;

/**
 *
 * @author rohanpandit
 */
public class StringData {
        public String playerId = "";
        public String ranking = "";
        public String name = "";
        public String age = "";
        public String rankingPoints = "";
        public String tourneysPlayed = "";
        public String errorMsg = "";
        
    public String getAllStrings() {
        return playerId + ranking + name + age + rankingPoints + tourneysPlayed;
    }

}
