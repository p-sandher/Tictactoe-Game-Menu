package boardgame;
// import java.lang.String;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player implements boardgame.Saveable{
    

    private HashMap<String, Integer> playerProfile;
    private String exceptionMessage;
    private boolean exceptionFound = false;
    private String player;

    public Player(){
        playerProfile = new HashMap<String,Integer>();
    }

    /**
     * @return string that will be saved to a file using Saveable interface
     */
    @Override
    public String getStringToSave(){
        
        return getPlayerStats();
    }

    /**
     * @param string with file contents of player that is uploaded
     */
    @Override
    public void loadSavedString(String toLoad){
        setExceptionFoundFlag(false);

        try{
            parseLoadedString(toLoad);
        } catch (Exception e){
            setExceptionFoundFlag(true);
            setExceptionErrorMessage(e.getMessage());
        }

    }

    /**
     * finds the key to save and check if it exists 
     */
    public void stringToSave(){
        setExceptionFoundFlag(false);
        try{
            isKeyPresent();

        }catch (Exception e){
            setExceptionFoundFlag(true);
            setExceptionErrorMessage(e.getMessage());
        }
    }

    /**
     * @param string with key (player name)
     */
    public void setPlayerName(String name){
        player = name;
    }

    /**
     * @return string with playe name (key)
     */
    public String getPlayerName(){
        return player;
    }

    /**
     * @return string in text format to save 
     */
    public String getPlayerStats(){
        String save = getPlayerName();


        save = save +","+playerProfile.get(getPlayerName());
        return save;
    }

    /**
     * checks if key is in hashmap, throws error if not
     */
    public void isKeyPresent() throws Exception{
        if(playerProfile.get(player) == null){
            throw new Exception("Player not found in database");
        }

    }

    /**
     * @param playerScore, string from text file
     * checks if valid file 
     * uploads player stats to hashmap if it is
     */
    public void parseLoadedString(String playerScore) throws Exception{
        int won;
        int lost;
        validateNumberOfColumns(playerScore);

        String[] contents = playerScore.split(",");
        if(contents.length != 2){
            throw new Exception("Incorrect File format. Must have Player and Games won");
        }
        won = convertScoreToInt(contents[1]);
        setPlayerProfile(contents[0], won);
    }

    /**
     * @return board, a string with the player score board 
     */
    public String playerScoreBoard(){
        String board = "Player Score Board\n\nPlayer            Games Won\n\n";
        for(String key: playerProfile.keySet()){
            board = board + key + "            " + playerProfile.get(key)+"\n";
        }
        return board;
    }

    /**
     * @param str, string with file to load, checks if there are appropriate number of columns
     */
    private void validateNumberOfColumns(String str) throws Exception{
        int commaCounter = 0;
        char c;
        for(int i = 0; i < str.length(); i++){ 
            c = str.charAt(i);
            if(c == ','){
                commaCounter++;
            }
        }
        if(commaCounter > 1){
            throw new Exception("Incorrect file format. There are too many columns in file");
        }
        if(commaCounter < 1){
            throw new Exception("Incorrect file format. There are not enough columns in file");
        }
    }

    /**
     * @param score, number of wins found in in file
     * @return num, conversion string to int
     */
    private int convertScoreToInt(String score) throws Exception{
        int num;
        try{
            num = Integer.parseInt(score);
            return num;
        } catch (NumberFormatException nfe){
            throw new Exception("Incorrect score. It is not a number.");
        }
    }

    /**
     * @param name, won 
     * Sets file contents to hasmap
     */
    public void setPlayerProfile(String name, int won){
        playerProfile.put(name, won); //check
    }

    /**
     * @return boolean, of exceptionFound
     */
    public boolean getExceptionFoundFlag(){
        return exceptionFound;
    }

    /**
     * @param value, boolean of if exception thrown or not
     */
    public void setExceptionFoundFlag(boolean value){
        if(value){
            exceptionFound = true;
        } else {
            exceptionFound= false;
        }
    }

    /**
     * @param message of exception
     */
    public void setExceptionErrorMessage(String message){
        exceptionMessage = message;
    }

    /**
     * @return message of exception
     */
    public String getExceptionErrorMessage(){
        return exceptionMessage;
    }

    @Override 
    public String toString(){
        return "Player Score Board";
    }


}
