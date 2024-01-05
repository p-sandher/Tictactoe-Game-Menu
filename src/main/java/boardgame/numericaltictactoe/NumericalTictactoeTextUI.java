package numericaltictactoe;
import java.util.Scanner;

public class NumericalTictactoeTextUI{
    private NumericalTictactoeGame game = new NumericalTictactoeGame(3,3);
    private Scanner userInput = new Scanner(System.in);
    private int across = 0;
    private int down = 0;
    private int gamePiece = 0;
    private int menuOption = 0;
    
     /**
     * @param gameChoice, number 1 is Tictactoe and 2 is NumTicTactoe 
     * sets gameboard and game
     */
    public NumericalTictactoeTextUI(int gameChoice){
        game = new NumericalTictactoeGame(3,3);
        game.setGrid(NumericalTictactoeGame.newGrid(gameChoice,3,3));
    }

    /**
     * gets row and col from user and prints exceptions 
     */
    private void getPiecePosition(){
        boolean validRow = false;
        boolean validCol= false;

        while(!validRow){
            sayMessage(game.positionRowMessage());
            try{
                across = userInput.nextInt();
                validRow = true;
            } catch (Exception e){
                sayMessage(game.positionErrorMessage());
                userInput.reset();
                userInput.next();
            }
        }
        while(!validCol){
            sayMessage(game.positionColMessage());
            try{
                down = userInput.nextInt();
                validCol = true;
            } catch (Exception e){
                sayMessage(game.positionErrorMessage());
                userInput.reset();
                userInput.next();
            }
        }
    }


    /**
     * gets game piece from user
     */
    public void gamePiece(){
        boolean validPiece = false;

        while(!validPiece){
            sayMessage(game.gamePieceMessage());
            try{
                gamePiece = userInput.nextInt();
                validPiece = true;
            } catch (Exception e){
                sayMessage(game.invalidGamePieceMessage());
                userInput.reset();
                userInput.next();
            }
        }
    }

    /**
     * @param message, prints string to terminal
     */
    public void sayMessage(String message){
        System.out.println(message);
    }
    
    /**
     * collects menuOption from user
     */
    private void menuOption(){
        menuOption = userInput.nextInt();
    }
    
    /**
     * method runs the game, gets position repeatedly 
     * until the game is done
     * prints exceptions as necessary
     */
    public void gameRunner(){
        sayMessage(game.getGameStateMessage());
        while(!game.isDone()){

            getPiecePosition();
            gamePiece();
            try{
                game.takeTurn(across,down,Integer.toString(gamePiece));
                sayMessage(game.toString());
                sayMessage(game.getGameStateMessage());
            }catch(RuntimeException e){
                sayMessage(game.getGameStateMessage());
            }
        }
        try{
            menuOption();
            game.gameMenu(menuOption);
            if(menuOption == 2){
                gameRunner();
            }
        }catch(RuntimeException e){
            sayMessage(game.getGameStateMessage());
        }
    }

    /**
     * @return String, returns welcome game message
     */
    @Override 
    public String toString(){
        return "Welcome to Numerical Tictactoe";
    }



    public static void main(String[] args){
        NumericalTictactoeTextUI numericalTictactoeUI = new NumericalTictactoeTextUI(1);
        numericalTictactoeUI.gameRunner();
    }
}
