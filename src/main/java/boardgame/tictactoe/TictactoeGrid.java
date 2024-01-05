package tictactoe;
import boardgame.Grid;
import java.util.Iterator;

public class TictactoeGrid extends boardgame.Grid{
    
    private static String[] playerPieces = {"X","O"};
    private String gridAsCSV = "";

    /**
     * @param width, height -game baord dimensions
     * TictactoeGrid constructor, sets up grid
     */
    public TictactoeGrid(int width, int height){
        super(width,height);
        emptyGrid();

    }

    /**
     * @param across, down 
     * checks if the position to place game piece is available
     */
    public void validatePosition(int across, int down) throws Exception {

        String piece = getValue(across,down);
        for(String gamePiece: playerPieces){
            if(piece.equals(gamePiece)){
                throw new Exception("This position on the game board is already taken!");
            }
        }
    }

    /**
     * @param input
     * checks if the game piece to be placed is valid
     */
    public void validateGamePiece(String input) throws Exception {

        for(String gamePiece: playerPieces){
            if(input.equals(gamePiece)){
                return;
            }
        }
        throw new Exception("This is not a valid game piece!");
    }

    /**
     * @return boolean if vertical win found
     * 
     */
    public boolean verticalWin(){
        for(int i = 1; i <= getWidth(); i++){
            if(!getValue(i,1).equals(" ")){
                if(getValue(i,1).equals(getValue(i,2)) && getValue(i,2).equals(getValue(i,3))){
                    return true;
                }
            } 
        }
        return false;
    }

    /**
     * @return boolean if horizontal win found
     * 
     */
    public boolean horizontalWin(){
        for(int i = 1; i <= getHeight(); i++){
            if(!getValue(1,i).equals(" ")){
                if(getValue(1,i).equals(getValue(2,i)) && getValue(2,i).equals(getValue(3,i))){
                    return true;
                }
            } 
        }
        return false;
    }

    /**
     * @return boolean if diagonal win found
     * 
     */
    public boolean diagonalWin(){
        if(!getValue(1,1).equals(" ")){
            if(getValue(1,1).equals(getValue(2,2)) && getValue(2,2).equals(getValue(3,3))){
                return true;
            }
        } 

        if(!getValue(1,3).equals(" ")){
            if(getValue(1,3).equals(getValue(2,2)) && getValue(2,2).equals(getValue(3,1))){
                return true;
            }
        }
        return false;
    }

    /**
     * @return boolean if tie found
     * 
     */
    public boolean isBoardFull(){ 
        Iterator<String> iter = iterator();
        int pieceCounter = getHeight()*getWidth();
        String piece;
        for(int i = 1; i <= getHeight(); i++){
            for(int j = 1; j <= getWidth(); j++){
                piece = getValue(i,j);
                if((piece.equals("X")) || (piece.equals("O"))){
                    pieceCounter--;
                }
            }
        }
        
        if(pieceCounter == 0){
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
                validatePiece(j);
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

    /**
     * Throws exceptions if it is not a valid
     * piece in the game board 
     * can only be 0,1 or 2
     * @param piece piece in the file
     */
    private void validatePiece(String piece) throws Exception{
        if((!piece.equals("")) && (!piece.equals("X")) && (!piece.equals("O"))){
            throw new Exception("Incorrect piece found <"+piece+">.");
        }
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
        String piece = "";

        for(int i = 1; i <= getHeight()*getWidth(); i++){

            if(col > 3){
                row++;
                col = 1;
            }
            piece = getValue(row,col);
            if(piece.equals("X")){
                player1++;
            }
            if(piece.equals("O")){
                player2++;
            }
            col++;
        }
        if(player1 > player2){
            return 2;
        } else {
            return 1;
        }
        
    }

    /**
     * @return String of gmaeboard string
     */
    @Override
    public String toString(){
        return getFormattedGrid();
    }

    
}
