/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Match;

/**
 *
 * @author rohanpandit
 */
public class StringData {
            public String matchId = "";
        public String seedOne = "";
        public String seedTwo = "";
        public String playerOne = "";
        public String playerTwo = "";
        
        public String errorMsg = "";
        
    public String getAllStrings() {
        return matchId + seedOne + seedTwo + playerOne + playerTwo;
    }
}
