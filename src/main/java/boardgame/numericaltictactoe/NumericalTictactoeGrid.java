package numericaltictactoe;
import java.util.Iterator;

public class NumericalTictactoeGrid extends boardgame.Grid{
    
    private int oddNumCounter = 0;
    private int evenNumCounter = 0;
    private String gridAsCSV = "";

    /**
     * @param width, height -game baord dimensions
     * NumericalTictactoeGrid constructor, sets up grid
     */
    public NumericalTictactoeGrid(int width, int height){
        super(width,height);
        emptyGrid();
    }

    /**
     * @return boolean if tie found
     * 
     */
    public boolean isBoardFull(){ 
        Iterator<String> iter = iterator();
        int freeSpaceCounter = 0;
        while(iter.hasNext()){
            if(!iter.next().equals(" ")){
                freeSpaceCounter++;
            }
        }
        if(freeSpaceCounter == getWidth()*getHeight()){
            return true;
        }
        return false;
    }
    /**
     * @return string of gameboard esque version of game grid
     */  
    public String getFormattedGrid(){
        String toPrint ="";
        Iterator<String> iter = iterator();
        int row = 1;
        int col  = 1;
        for(int i = 1; i <= getWidth()*getHeight(); i++){
            if(col > 3){
                toPrint = toPrint + "\n - - - \n";
                row++;
                col = 1;
            }
            toPrint = toPrint + "|"+getValue(row,col);
            col++;
        }
        return toPrint;
    }

    /**
     * @return boolean if diagonal win found
     * 
     */
    public boolean diagonalWin(){

        if(rightDiagonalCheck() || leftDiagonalCheck()){
            return true;
        } 
        return false;
 
    }

    /**
     * @return boolean if right diagonal win found
     * 
     */
    public boolean rightDiagonalCheck(){
        oddNumCounter = 0;
        evenNumCounter = 0;
        boolean winCheck = false;
        int[] pieceArray = new int[999];
        int gamePiece = 0;

        for(int i = 1; i <= getWidth();i++){
            gamePiece = convertGamePieceToInt(getValue(i,i)); 
            pieceArray[i]=gamePiece;

        }
        if(checkForThreeGamePieces(pieceArray)){
            winCheck = isSum15(pieceArray);
        }

        return winCheck;
    }

    /**
     * @return boolean if left diagonal win found
     * 
     */
    public boolean leftDiagonalCheck(){
        oddNumCounter = 0;
        evenNumCounter = 0;
        boolean winCheck = false;
        int acrossIndex = 3;
        int[] pieceArray = new int[999];
        int gamePiece = 0;

        for(int i = 1; i <= getWidth();i++){
            gamePiece = convertGamePieceToInt(getValue(acrossIndex,i)); 
            pieceArray[i] = gamePiece;
            acrossIndex--;
        }
        if(checkForThreeGamePieces(pieceArray)){
            winCheck = isSum15(pieceArray);
        }
        
        return winCheck;
    }

    public boolean checkForThreeGamePieces(int[] pieceArray){
        for(int i = 0; i < 3; i++){
            System.out.println(pieceArray[i]+"\n");
            if(pieceArray[i] == -1){
                return false;
            }
        }
        return true;
    }

    /**
     * @return boolean if horizontal win found
     * 
     */
    public boolean horizontalWin(){
        oddNumCounter = 0;
        evenNumCounter = 0;
        boolean winCheck = false;
        int[] pieceArray = new int[999];

        for(int i = 1; i <= getWidth(); i++){

                for(int k = 1; k <= getWidth(); k++){
                    pieceArray[k] = convertGamePieceToInt(getValue(i,k)); 
                }
                if(checkForThreeGamePieces(pieceArray)){
                    winCheck = isSum15(pieceArray);
                }
                

            if(winCheck){
                return winCheck;
            }
            oddNumCounter = 0;
            evenNumCounter = 0;
        }
        return winCheck;
    }


    /**
     * @return boolean if vertical win found
     * 
     */
    public boolean verticalWin(){
        oddNumCounter = 0;
        evenNumCounter = 0;
        boolean winCheck = false;
        int[] pieceArray = new int[999];

        for(int i = 1; i <= getWidth(); i++){

                for(int k = 1; k <= getWidth(); k++){
                    pieceArray[k] = convertGamePieceToInt(getValue(k,i)); 
                }
                if(checkForThreeGamePieces(pieceArray)){
                    winCheck = isSum15(pieceArray);
                }

            if(winCheck){
                return winCheck;
            }
            oddNumCounter = 0;
            evenNumCounter = 0;
        }
        return winCheck;
    }

    /**
     * @return num converts game piece to an int
     */
    private int convertGamePieceToInt(String piece){
        int num;
        if(piece.equals(" ")){
            num = -1;
        }
        if(piece.equals("")){
            num = -1;
        }
        try {
            num = Integer.parseInt(piece);
        } catch (NumberFormatException nfe){
            return -1; 
        }
        return num;
    }

    /**
     * @param pieceArray, 3 pieces on the board
     * checks if these 3 pieces equal ot 15
     * @return boolean true or false if the sum of pieceArray is 15
     */
    private boolean isSum15(int[] pieceArray){
        
        int sum = 0;
        for(int i = 0; i < pieceArray.length; i++){
            sum+= pieceArray[i];
        }
        if(sum == 15){
            return true;
        } 
        return false;
        
    }

    /**
     * @param across, down 
     * checks if the position to place game piece is available
     */
    public void validatePosition(int across, int down) throws Exception {
        String piece = getValue(across,down);
        if(!piece.equals(" ")){
            throw new Exception("This position on the game board is already taken!");
        }
    }

    /**
     * @param input
     * checks if the game piece to be placed is valid
     */
    public void validateGamePiece(String input) throws Exception { 
        int inputInt = convertGamePieceToInt(input);
        if(inputInt > 9){
            throw new Exception("Incorrect input! All numbers are less than or equal to 9!");
        }
        if(inputInt < 0){
            throw new Exception("Incorrect input! All numbers are positive!");
        }
        
        uniqueInput(input);
    }

    /**
     * @param input, checks if game piece entered already exists on board
     */
    private void uniqueInput(String input) throws Exception{
        Iterator<String> iter = iterator();
        while(iter.hasNext()){
            if(iter.next().equals(input)){
                throw new Exception("Incorrect input! You can't use the same number more than once!");
            }
        }
    }

    /**
     * creates string of gameboard in a a CSV format
     * 
     */ 
    public void setGridAsCSV(){
        gridAsCSV ="";
        Iterator<String> iter = iterator();
        int row = 1;
        int col  = 1;
        String piece = "";
        for(int i = 1; i <= getWidth()*getHeight(); i++){
            
            if(col > 3){
                gridAsCSV = gridAsCSV + "\n";
                row++;
                col = 1;
            }
            piece = getValue(col,row);
            if(piece.equals(" ")){
                piece = "";
            }
            if(col == 1){
                gridAsCSV = gridAsCSV+piece;
            } else {
                gridAsCSV = gridAsCSV + ","+piece;
            }
            
            col++;
        }
    }

    /**
     * @return string of gameboard in a a CSV format
     */  
    public String getGridAsCSV(){
        return gridAsCSV;
    }

    /**
     * Checks which player has the next turn from a loaded game file
     * @return next player's turn 
     */
    public int playerPieceCounter(){
        int player1 = 0;
        int player2 = 0;
        int row = 1;
        int col = 1;
        int piece;
        for(int i = 1; i <= getHeight()*getWidth(); i++){

            if(col > 3){
                row++;
                col = 1;
            }
            piece = convertGamePieceToInt(getValue(row,col));
            if(piece%2 == 1){
                player1++;
            }
            if(piece%2 == 0){
                player2++;
            }
            col++;
        }

        System.out.println("\nOdd: "+player1+" Even: "+player2);
        if(player1 > player2){
            return 2;
        } else {
            return 1;
        }
        
    }

    /**
     * @param pieces --> values of each posiition on the game board 
     * from the uploaded game file 
     * sets each piece onto the board
     */
    public void importPiecesFromFileToBoard(String[] pieces){
        String piece;
        int pieceIndex = 0;
        int row = 1;
        int col = 1;
        for(int i = 0; i < getHeight()*getWidth(); i++){
            if(col > 3){
                row++;
                col = 1;
            }
            setValue(col,row,pieces[i]);
            col++;
        }
    }

    
    /**
     * @param game, string of file uploaded csv of game grid 
     * splits file to check if each game piece is valid and sets in string array
     * @return string list of each piece of the uploaded game board
     */
    public String[] readUploadedGame(String game) throws Exception{ 
        String fileContent = game;
        String[] pieces = new String[1000];
        String[] newline = fileContent.split("\n");
        int columnCounter = 0;
        int rowCounter = 0;
        int gridIndex = 0;

        for(String i:newline){
            validateNumberOfColumns(i);
            rowCounter++;
        }
        validateNumberOfRows(rowCounter);

        for(String i: newline){
            for(String j: i.split(",",getHeight())){
                validateGamePiece(j);
                if(j.equals("")){
                    pieces[gridIndex]  = " ";
                } else {
                    pieces[gridIndex]  = j;
                }
                gridIndex++;
            }
        }
        return pieces;
    }

    /**
     * @return String of gmaeboard string
     */
    @Override
    public String toString(){
        return getFormattedGrid();
    }

    /**
     * Throws exceptions if there are too many
     * or not enough columns in the file
     * @param columnCounter number of columns in the file
     */
    private void validateNumberOfColumns(String row) throws Exception{
        String piece;
        int commaCounter = 0;
        for(int i = 0; i < row.length(); i++){
            piece = row.substring(i,i+1);
            if(piece.equals(",")){
                commaCounter++;
            }
        }
        if(commaCounter < 2){
            throw new Exception("Not enough columns in file.");
        } 
        if(commaCounter > 2){
            throw new Exception("Too many columns in file.");

        }
    }

    /**
     * Throws exceptions if there are too many
     * or not enough rows in the file
     * @param rowCounter number of rows in the file
     */
    private void validateNumberOfRows(int rowCounter) throws Exception{
        if(rowCounter > getWidth()){
            throw new Exception("Too many rows in file.");
        }
        if(rowCounter < getWidth()){
            throw new Exception("Not enough rows in file.");
        }
    }




}
