package tictactoe;
import java.util.Scanner;

public class TictactoeTextUI { 
    private TictactoeGame game = new TictactoeGame(3,3);
    private Scanner userInput = new Scanner(System.in);
    private int across = 0;
    private int down = 0;
    private int menuOption = 0;

    /**
     * @param gameChoice, number 1 is Tictactoe and 2 is NumTicTactoe 
     * sets gameboard and game
     */
    public TictactoeTextUI(int gameChoice){
        game = new TictactoeGame(3,3);
        game.setGrid(TictactoeGame.newGrid(gameChoice,3,3));
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
                sayMessage(game.inputErrorMessage());
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
                sayMessage(game.inputErrorMessage());
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
        boolean fileSaveCheck = false;
        while(!game.isDone()){
            getPiecePosition();
            fileSaveCheck = game.fileHandlerCheck(across);
                try{
                    game.takeTurn(across,down,game.getPlayerString());
                    sayMessage(game.toString());
                    sayMessage(game.getGameStateMessage());
                }catch(RuntimeException e){
                    sayMessage(game.getGameStateMessage());
                }
        }
        try{
            menuOption();
            game.gameMenu(menuOption);
            sayMessage(game.getGameStateMessage());
            if(menuOption == 2){
                gameRunner();
            }
        }catch(RuntimeException e){
            sayMessage(game.getGameStateMessage());
        }
    }

    /**
     * collects filename input from user
     * @return String with filename
     */
    public String filenameInput(){
        boolean correct = false;
        String fileName = "";
        while(!correct){
            sayMessage(game.getGameStateMessage()); 
            try{
                fileName = userInput.next();
                correct = true;
                return fileName;
            } catch (Exception e){
                sayMessage(game.inputErrorMessage()); 
            }
        }
        return fileName;
    }

    /**
     * @return String, returns welcome game message
     */
    @Override 
    public String toString(){
        return "Welcome to Tictactoe";
    }

    /**
     * main that creates constructor of TictactoeUI
     * and starts game
     */
    public static void main(String[] args){
        TictactoeTextUI tictactoeUI = new TictactoeTextUI(1);
        tictactoeUI.gameRunner();
    }
}
