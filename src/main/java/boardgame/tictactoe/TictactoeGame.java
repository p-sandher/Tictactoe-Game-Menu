package tictactoe;


public class TictactoeGame extends boardgame.BoardGame implements boardgame.Saveable{ 
    
    private boolean gameDone = false;
    private boolean gameWon = false;
    private boolean gameTie = false;
    private int player = 1;
    private String gameMessage;
    private int winner;
    private String exceptionMessage;
    private boolean exceptionFound = false;

    /**
     * TictacctoeGame constructor, preps game play and sets grid
     */

    public TictactoeGame(int width, int height){
        super(width,height);
        setGameStateMessage(welcomeMessage());
        setGrid(new TictactoeGrid(width,height));
    }

    /**
     * @return the next message to be printed for the user 
     */

    @Override
    public String getGameStateMessage(){
        return gameMessage; 
    }

    /**
     * @param string with the next message to print to user
     */

    private void setGameStateMessage(String message){
        gameMessage = message;
    }


    /**
     * @param across,down,input (player piece X or O)
     * across and down are location where to put game piece
     * Validates game piece location, implements on board and checks if the game is over
     * @return boolean false if a player turn can't be complete, if its against game rule
     */

    @Override
    public boolean takeTurn(int across, int down, String input){
        try{
            validateRowInput(across);
            validateColInput(down);
            gameGrid().validatePosition(across,down);
            gameGrid().validateGamePiece(input);
            setValue(across,down,input);
            if(!isDone()){
                switchPlayer();
                setGameStateMessage(playerMessage());
            } else {
                gameOver();
            }
        } catch (Exception e){
            setGameStateMessage(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return false;        
    }

    /**
     * @param row, this is from the user and if its -1, file handling is wanted
     * @return boolean if the user has prompted for file handling to occur
     */

    public boolean fileHandlerCheck(int row){
        if(row == -1){
            setGameStateMessage(collectFilenameMessage());
            return true;
        }
        return false;
    }


    @Override
    public boolean takeTurn(int across, int down, int input){
        return false;
    }

    /**
     * checks if game has been won or tied
     * @return boolean returns true if the game is complete
     */
    @Override
    public boolean isDone(){

        if(gameGrid().horizontalWin() || gameGrid().verticalWin() || gameGrid().diagonalWin()){
            gameWon = true;
            gameDone = true;
            winner = getPlayer();
        }
        
        if(gameGrid().isBoardFull()){
            gameTie = true;
            gameDone = true;
            winner = 0;
        }
        return gameDone;
    }

    /**
     * @return boolean of if the game has tied or not
     */
    public boolean getGameTieResult(){
        return gameTie;
    }

    /**
     * @return player num of of winner
     */

    @Override
    public int getWinner(){
        return winner;
    }

    /**
     * @return the current player
     */
    public int getPlayer(){
        return player;
    }


/**
 * @return returns string of current player in X O format
 */
    public String getPlayerString(){ 
        if(getPlayer() == 1){
            return "X";
        }
        return "O";
    }

    /**
     * @param row, desired row to place game piece
     * checks row is in bounds of the board, throws exception if not
     */
    public void validateRowInput(int row) throws Exception{
        if(row == 0){
            setGameStateMessage(endProgramMessage());
            System.exit(0); 
        }
        
        if(row > getWidth() || row < 0){
            throw new Exception("Out of bounds row input");
        }
    }

    /**
     * @return string with file input message
     */

    public String collectFilenameMessage(){
        String message = "Enter filename: ";
        return message;
    }

    /**
     * @param col, desired col to place game piece
     * checks col is in bounds of the board, throws exception if not
     */

    public void validateColInput(int col) throws Exception{
        if(col == 0){
            setGameStateMessage(endProgramMessage());
            System.exit(0);
        }
        if(col > getHeight() || col < -1){
            throw new Exception("Out of bounds column input");
        }
    }

    /**
     * changes player turn
     */
    private void switchPlayer(){
        if(getPlayer() == 1){
            player = 2;
        } else {
            player = 1;
        }
    }

    /**
     * @param newPlayer, resets player, needed when loading a file or restarting
     */
    private void switchPlayer(int newPlayer){
        player = newPlayer;
    }

    /**
     * @param currentPlayer, has the current player in XO format and converts to int
     */
    public void playerStringToInt(String currentPlayer) throws Exception{
        if(currentPlayer.equals("X")){
            switchPlayer(1);
        } else if(currentPlayer.equals("O")){
            switchPlayer(2);
        } else {
            throw new Exception("Invalid player in file");
        }
        
    }

    /**
     * @return string with win message
     */
    public String winMessage(){
        return("GAME OVER. You won "+currentPlayerString());
    }

    /**
     * @return string with tie message
     */
    public String tieMessage(){
        return("Congrats to both players! It's a tie!");
    }

    /**
     * @return string with player turn message
     */
    private String playerMessage(){
        String message = currentPlayerString() + " it is your turn!\nEnter 0 in row and column to end game.";
        return message;
    }

    /**
     * @return string with current player in string format
     */
    private String currentPlayerString(){
        String currentPlayer = "Player 1";
        
        if(getPlayer() == 2){
            currentPlayer = "Player 2";
        }
        return currentPlayer;
    }

     /**
     * @return string with welcome message and player messagefor TextUI
     */
    private String welcomeMessage(){
        String message = "\nWelcome to Tictactoe\n"+playerMessage();
        return message;
    }

    /**
     * @return string with welcome message for GUI
     */
    public String nameOfGame(){
        String message = "Welcome to Tictactoe";
        return message;
    }

    /**
     * @return string with game menu message 
     */
    private String gameMenuMessage(){
        String message = "\nMenu:\nEnter 1 to end game\nEnter 2 to restart\n";
        return message;
    }


    /**
     * @return string with replay game message 
     */
    public String gameRestartInquiry(){
        String message = "Do you want to play again?";
        return message;
    }


    /**
     * sets gameStateMessage based on if game has won or tied
     */
    public void gameOver(){
        if(gameWon){
            setGameStateMessage(winMessage().concat(gameMenuMessage()));
        } else {
            setGameStateMessage(tieMessage());
        }
    }

    /**
     * @param option, what user has decided to restart or end game
     * validates user input and restarts game if desired
     */
    public void gameMenu(int option){
        try{
            validateOption(option);
            if(option == 1){
                setGameStateMessage(endProgramMessage());
            } else {
                setGameStateMessage(restartGameMessage());
                gameDone = false;
                gameWon = false;
                gameTie = false;
                newGame();
                setGrid(new TictactoeGrid(getWidth(),getHeight()));
                switchPlayer(1);
            }
        } catch (Exception e){
            
            setGameStateMessage(e.getMessage());
        }
    }

    /**
     * @param boolean of if an exception has been found 
     */
    public boolean getExceptionFoundFlag(){
        return exceptionFound;
    }

    /**
     * @param boolean of if an exception has been found or not, and sets exceptionFound
     */
    public void setExceptionFoundFlag(boolean value){
        if(value){
            exceptionFound = true;
        } else {
            exceptionFound= false;
        }
    }
    
    /**
     * @param option, int value of game menu and validates input
     */
    public void validateOption(int option) throws Exception{
        if(option != 1 && option != 2){
            throw new Exception("Incorrect menu option. Enter 1 or 2.");
        }
    }

    /**
     * @return string with end of game message 
     */
    public String endProgramMessage(){
        String message = "Thank you for playing. Goodbye!";
        return message;
    }

    /**
     * @return string with restart game message 
     */
    public String restartGameMessage(){
        String message = "Thank you for playing. Let's play again!";
        return message;
    }

    /**
     * @return TictactoeGrid's grid 
     */
    private TictactoeGrid gameGrid(){
        return (TictactoeGrid) getGrid();
    }

    /**
     * @return string with input row game message 
     */
    public String positionRowMessage(){
        String message = "Enter the row number (1-3): ";
        return message;
    }

    /**
     * @return string with input col game message 
     */
    public String positionColMessage(){
        String message = "Enter the column number (1-3): ";
        return message;
    }

    /**
     * @return string with input error game message 
     */
    public String inputErrorMessage(){
        String message = "Incorrect input. Try again.";
        return message;
    }
    /**
     * @return string with formatted gameboard grid
     */
    public String uiGameGrid(){
        return gameGrid().getFormattedGrid();
    }

    /**
     * @return TictactoeGrid new grid 
     */
    public static TictactoeGrid newGrid(int gameChoice, int width, int height){
        return new TictactoeGrid(width,height);
    }

    /**
     * @param boardgame.Grid and sets grid
     */
    @Override
    public void setGrid(boardgame.Grid grid){
        super.setGrid(grid);
    }

    /**
     * @return string with formatted gameboard grid
     */
    @Override 
    public String toString(){
        return gameGrid().getFormattedGrid(); 
    }

    /**
     * @param string of file contents to be loaded to grid
     * loading is done with a series of validating methods, that throw exception
     * game is laoded and checked if its a completed game and handles it accordingly
     * 
     */
    @Override
    public void loadSavedString(String toLoad){
        String[] pieces = new String[20];
        String[] grid = toLoad.split("\n", 2);
        setExceptionFoundFlag(false);
        try{
            playerStringToInt(grid[0]);
            pieces = gameGrid().readUploadedGame(grid[1]);
            gameGrid().importPiecesFromFileToBoard(pieces);
            if(isDone()){
                gameOver();
            } else {
                switchPlayer(gameGrid().playerPieceCounter());
            }
        } catch (Exception e){
            setExceptionFoundFlag(true);
            setExceptionErrorMessage(e.getMessage());
        }
    }

    /**
     * @param string with error message from exception
     */
    public void setExceptionErrorMessage(String message){
        exceptionMessage = message;
    }

    /**
     * @return string with error message from exception
     */
    public String getExceptionErrorMessage(){
        return exceptionMessage;
    }

    /**
     * @return string string to save to a file
     */
    @Override
    public String getStringToSave(){
        
        return stringToSave();
    }

    /**
     * sets grid as CSV used for saving
     */
    public void saveToFileString(){
        gameGrid().setGridAsCSV();
    }


    /**
     * @return string that is sent to be saved into a file 
     */
    public String stringToSave(){
        String game = getPlayerString()+"\n"+gameGrid().getGridAsCSV();
        return game;
    }

}

