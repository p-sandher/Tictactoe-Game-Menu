package numericaltictactoe;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;

import javax.swing.border.EmptyBorder;

import boardgame.ui.PositionAwareButton;

import game.GameUI;

import boardgame.FileHandler;
import javax.swing.JFileChooser;
import java.io.File;

public class NumericalTictactoeView extends JPanel {
    private JLabel messageLabel;
    private NumericalTictactoeGame game;
    private PositionAwareButton[][] buttons;
        
    private NumericalTictactoeGame controller;
    private GameUI parent = null;

    private JFileChooser chooseFile;
    private FileHandler filer;


    private JLabel filePath;
    private JLabel fileName;

    /**
     * @param GameUI root 
     * NumericalTictactoeView constructor, sets GUI view and starts the game 
     */
    public NumericalTictactoeView(GameUI root) {
        super();
        setLayout(new BorderLayout());
        parent = root;
        controller = new NumericalTictactoeGame(3, 3);
        controller.newGame();
        chooseFile = new JFileChooser(); 
        add(makeTitlePanel(), BorderLayout.NORTH);
        add(makeGameGrid(), BorderLayout.CENTER);
        add(gameButtonPanel(), BorderLayout.WEST);

        
    }



    /**
     * @return JPanel with game grid in the form of buttons 
     */
    private JPanel makeGameGrid(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));

        buttons = new PositionAwareButton[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                //Create buttons and link each button back to a coordinate on the grid
                buttons[y][x] = new PositionAwareButton();
                buttons[y][x].setAcross(x + 1); //made the choice to be 1-based
                buttons[y][x].setDown(y + 1);
                buttons[y][x].addActionListener(e -> {
                    addGamePiece(e);

                });
                panel.add(buttons[y][x]);
            }
        }
        return panel;
    }

    /**
     * @param ActionEvent e, gets game piece selcted from user
     * checks if is a valid piece and state of the game (win or tie)
     * pop up occurs if an error occurs 
     */
    public void addGamePiece(ActionEvent e) {
        
        String num = JOptionPane.showInputDialog("Please input a value"); 
        int option = 0;

        if(option == JOptionPane.YES_OPTION){
            PositionAwareButton clicked = ((PositionAwareButton) (e.getSource()));
            try{
                controller.takeTurn(clicked.getAcross(), clicked.getDown(), num);
                clicked.setText(controller.getCell(clicked.getAcross(), clicked.getDown()));
                    if (controller.isDone()) {
                        if(controller.getGameTieResult()){
                            JOptionPane.showMessageDialog(null, controller.tieMessage());
                        } else {
                            JOptionPane.showMessageDialog(null, controller.winMessage());
                        }
                        endOfGame();
                    }
                
                
            } catch (Exception error) {                
                JOptionPane.showMessageDialog(null, controller.getGameStateMessage());
            }
        }
        
    }

     /**
     * end of game resets game  with prompt
     */
    public void endOfGame(){
        int option = 0;
        
        option = gameOverPanel();
        if(option == JOptionPane.YES_OPTION){
            parent.numericalTictactoe();
            controller.gameMenu(2);
        }
    }
    /**
     * resets gridand game
     */
    public void resetGame(){
        parent.numericalTictactoe();
        controller.gameMenu(2);
    }

    /**
     * @return int of what user decides to end game or replay
     */
    public int gameOverPanel(){
        int option = 0;
        JOptionPane gameOverPanel = new JOptionPane();
        option = gameOverPanel.showConfirmDialog(null,controller.gameRestartInquiry(),"", JOptionPane.YES_NO_OPTION);
        return option;
    }

    /**
     * @return JButton that will save current game board
     */
    private JButton makeSaveGameButton(){
        JButton button = new JButton("Save Game");
        button.addActionListener(e->saveGame());
        return button;
    }

    /**
     * @return JButton that will load game board from file
     */
    private JButton makeLoadGameButton(){
        JButton button = new JButton("Load Game");
        button.addActionListener(e->loadGame());
        return button;
    }

     /**
     * @return JButton that will reset game board
     */
    private JButton makeResetGameButton(){
        JButton button = new JButton("Reset Game");
        button.addActionListener(e->resetGame());
        return button;
    }

    /**
     * @return JPanel with load,save,reset buttons
     */
    private JPanel gameButtonPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(makeLoadGameButton());
        buttonPanel.add(makeSaveGameButton());
        buttonPanel.add(makeResetGameButton());

        return buttonPanel;
    }

    /**
     * @return JPanel with title 
     */
    private JPanel makeTitlePanel(){
        JPanel title = new JPanel();
        title.add(new JLabel("Numerical Tictactoe"));
        title.setBorder(new EmptyBorder(15,15,15,15));
        return title;

    }
    /**
     * loads game from textfile and calls methods in Tictactoe game to upload game
     * and uses Saveable interface to do 
     * pop appears if theres an error in the file
     */
    private void loadGame(){
        fileName = new JLabel();
        filePath = new JLabel();
        if (chooseFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (chooseFile.getSelectedFile().canRead()) {
                try {
                    filer.loadFromFile(controller,chooseFile.getSelectedFile().getName(),
                        chooseFile.getSelectedFile().getAbsolutePath());
                    updateView();
                    fileName.setText(chooseFile.getSelectedFile().getName());
                    filePath.setText(chooseFile.getSelectedFile().getAbsolutePath());
                    if(controller.getExceptionFoundFlag()){
                        JOptionPane.showMessageDialog(null,controller.getExceptionErrorMessage());
                    } else {
                        JOptionPane.showMessageDialog(null,"File loading complete");   
                    }
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null,controller.getExceptionErrorMessage());    
                }
            }
        }
    }


    /**
     * saves game from textfile and calls methods in Tictactoe game to save game
     * and uses Saveable interface to do 
     * pop appears if theres an error in the file
     */
    private void saveGame(){
        fileName = new JLabel();
        filePath = new JLabel();

        if (chooseFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (chooseFile.getSelectedFile().canRead()) {
                try {
                    controller.saveToFileString();
                    filer.saveToFile(controller,chooseFile.getSelectedFile().getName(),
                        chooseFile.getSelectedFile().getAbsolutePath());
        
                    fileName.setText(chooseFile.getSelectedFile().getName());
                    filePath.setText(chooseFile.getSelectedFile().getAbsolutePath());
                    JOptionPane.showMessageDialog(null,"File saving complete");    

                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null,error.getMessage());    
                }
            }
        }
    }

    /**
     * Updates all the buttons in the game grid to be up to date
     */
    protected void updateView(){
        for(int y = 0; y < 3;y++){
            for(int x = 0; x < 3;x++){
                buttons[y][x].setText(controller.getCell(buttons[y][x].getAcross(),buttons[y][x].getDown())); 

            }
        }
    }

    /**
     * @return string with gameboard grid
     */
    @Override 
    public String toString(){
        return controller.toString();
    }
    
}