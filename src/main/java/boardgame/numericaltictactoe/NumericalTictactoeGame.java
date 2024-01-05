package numericaltictactoe;


public class NumericalTictactoeGame extends boardgame.BoardGame implements boardgame.Saveable { 
    
    private boolean gameDone = false;
    private boolean gameWon = false;
    private boolean gameTie = false;
    private int player = 1;
    private String gameMessage;
    private int winner;
    private String exceptionMessage;
    private boolean exceptionFound = false;

    
    /**
     * NumericalTictactoeGame constructor, preps game play and sets grid
     */
    public NumericalTictactoeGame(int width, int height){
        super(width,height);
        setGameStateMessage(welcomeMessage());
        setGrid(new NumericalTictactoeGrid(width,height));
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
     * @param across,down,input (number)
     * across and down are location where to put game piece
     * Validates game piece location, implements on board and checks if the game is over
     * @return boolean false if a player turn can't be complete, if its against game rule
     */
    @Override
    public boolean takeTurn(int across, int down, String input){
        try{
            validateRowInput(across);
            validateColInput(down);
            checkUserNumber(input);
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
     * @param input, a number that user entered to be placed on board
     * checks if appropiate user entered even or odd
     */
    public void checkUserNumber(String input) throws Exception{
        int num;
        try {
            num = Integer.parseInt(input);
        } catch (NumberFormatException nfe){
            throw new Exception("Incorrect input. Must be a number");
        }


        if(getPlayer() == 1){
            if(num % 2 == 0){
                throw new Exception("Incorrect input. Player 1 only uses odd numbers");
            }
        }

        if (getPlayer() == 2){
            if(num % 2 == 1){
                throw new Exception("Incorrect input. Player 2 only uses even numbers");
            }
        }
    }

    /**
     * @return string to prompt user game piece
     */
    public String gamePieceMessage(){
        String message = "Enter game piece: ";
        return message;
    }

    /**
     * @return string to error message for user game piece
     */
    public String invalidGamePieceMessage(){
        String message = "Invalid game piece. Please enter an integer: ";
        return message;
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
     * @param col, desired col to place game piece
     * checks col is in bounds of the board, throws exception if not
     */
    public void validateColInput(int col) throws Exception{
        if(col == 0){
            setGameStateMessage(endProgramMessage());
            System.exit(0);
        }
        if(col > getHeight() || col < 0){
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
        String message = currentPlayerString() + " it is your turn! Enter 0 in row to end game";
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

        String message = "\nWelcome to Numerical Tictactoe\n"+playerMessage();
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
            if(option == 2){
                switchPlayer(1);
                setGameStateMessage(welcomeMessage());

                gameDone = false;
                gameWon = false;
                gameTie = false;
                newGame();
            }
        } catch (Exception e){
            setGameStateMessage(e.getMessage());
        }
    }

    /**
     * @param newPlayer, resets player, needed when loading a file or restarting
     */
    private void switchPlayer(int newPlayer){
        player = newPlayer;
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
     * @return string with position error message 
     */
    public String positionErrorMessage(){
        String message = "Incorrect position must be a number between 1-3";
        return message;
    }

    /**
     * @return NumericalTictactoeGrid's grid 
     */
    private NumericalTictactoeGrid gameGrid(){
        return (NumericalTictactoeGrid) getGrid();
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
     * @return NumericalTictactoeGrid new grid 
     */
    public static NumericalTictactoeGrid newGrid(int gameChoice, int width, int height){
        return new NumericalTictactoeGrid(width,height);
    }

    /**
     * @return string that is sent to be saved into a file 
     */
    public String stringToSave(){
        String game = getPlayer()+"\n"+gameGrid().getGridAsCSV();
        return game;
    }

    /**
     * sets grid as CSV used for saving
     */
    public void saveToFileString(){
        gameGrid().setGridAsCSV();
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
            // playerStringToInt(grid[0]);
            pieces = gameGrid().readUploadedGame(grid[1]);
            gameGrid().importPiecesFromFileToBoard(pieces);
            if(isDone()){
                gameOver();
            } 
            playerStringToInt(grid[0]);

        } catch (Exception e){
            setExceptionFoundFlag(true);
            setExceptionErrorMessage(e.getMessage());
        }
    }

    /**
     * @return string string to save to a file
     */
    @Override
    public String getStringToSave(){
        
        return stringToSave();
    }

    /**
     * @param currentPlayer, has the current player in XO format and converts to int
     */
    public void playerStringToInt(String currentPlayer) throws Exception{
        if(currentPlayer.equals("1")){
            switchPlayer(1);
        } else if(currentPlayer.equals("2")){
            switchPlayer(2);
        } else {
            throw new Exception("Invalid player in file");
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
     * @return boolean of if the game has tied or not
     */
    public boolean getGameTieResult(){
        return gameTie;
    }

    /**
     * @return string with replay game message 
     */
    public String gameRestartInquiry(){
        String message = "Do you want to play again?";
        return message;
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

}

