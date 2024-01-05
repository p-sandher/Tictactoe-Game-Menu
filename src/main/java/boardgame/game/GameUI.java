package game;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import java.io.File;

import java.awt.Color;
import javax.swing.BorderFactory; 
import javax.swing.JComponent;

import tictactoe.TictactoeView;
import numericaltictactoe.NumericalTictactoeView;
import boardgame.Player;
import boardgame.FileHandler;

public class GameUI extends JFrame{

    private JPanel mainContainer;
    private JMenuBar fileMenuBar;
    private JLabel filePath;
    private JLabel fileName;
    private JTextArea outputArea;
    private JButton loadFileButton;
    private JMenuBar playerMenuBar;
    private JMenuBar directoryMenuBar;

    private FileHandler filer;
    private JFileChooser chooseFile;
    private Player playerOne;


    /**
     * GameUI constructor
     * set layout, add main container and preps GUI
     */
    public GameUI(){
        super();
        setTitle("Welcome to the Games Menu!");
        playerOne = new Player();

        mainContainer = new JPanel();
        chooseFile = new JFileChooser(); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(mainContainer, BorderLayout.CENTER);
        addMainPage();
        pack();
    }

    /**
     * adds main components of main page:
     * button directory
     * welcome title
     * menu bar
     */
    public void addMainPage(){
        mainContainer.removeAll();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.X_AXIS));
        mainContainer.add(welcomeTitle(),BorderLayout.NORTH);
        mainContainer.add(makeMainButtonDirectoryPanel(),BorderLayout.CENTER);
        mainContainer.setBorder(new EmptyBorder(100,100,100,100));
        playerMenu();
        setJMenuBar(playerMenuBar);
        setContainer();
    }

    /**
     * @return JPanel with main page title 
     */
    public JPanel welcomeTitle(){
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        JLabel title = new JLabel("Welcome!");
        titlePanel.add(title);
        return titlePanel;
    }

    /**
     * resets page changes to page in numerical tictactoe in NumericalTictactoeView
     */
    public void numericalTictactoe(){
        mainContainer.removeAll();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        gameDirectory();
        setJMenuBar(directoryMenuBar);
        mainContainer.add(new NumericalTictactoeView(this));

        setContainer();
    }

    /**
     * resets page changes to page in tictactoe in TictactoeView
     */
    public void tictactoe(){
        mainContainer.removeAll();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        gameDirectory();
        setJMenuBar(directoryMenuBar);
        mainContainer.add(new TictactoeView(this));

        setContainer();
    }

    /**
     * instructions necessary to set a container in GUI
     */
    public void setContainer(){
        getContentPane().repaint();
        getContentPane().revalidate();
        pack();
    }

    /**
     * @return JPanel with buttons to direct to each game
     */
    private JPanel makeMainButtonDirectoryPanel(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.red));

        buttonPanel.add(makeNumericalTictactoeButton());
        buttonPanel.add(makeTictactoeButton());
        buttonPanel.setBorder(new EmptyBorder(70,70,70,70));
        return buttonPanel;
    }


    /**
     * @return JButton for tictactoe that directs to the method that will then direct to the game
     */
    private JButton makeTictactoeButton(){
        JButton button = new JButton("Play Tictactoe!");
        button.addActionListener(e->tictactoe());

        return button;
    }

/**
     * @return JButton for numericalTictactoe that directs to the method that will then direct to the game
     */
    private JButton makeNumericalTictactoeButton(){
        JButton button = new JButton("Play Numberical Tictactoe!");
        button.addActionListener(e->numericalTictactoe());
        return button;
    }

    /**
     * Creates menu bar for loading and saving player stats
     */
    public void playerMenu(){
        playerMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Player Menu");
        
        JMenuItem playerLoadItem = new JMenuItem("Load Player Scores");
        JMenuItem playerSaveItem = new JMenuItem("Save Player Scores");
        JMenuItem playerScoreItem = new JMenuItem("Player Score Board");

        menu.add(playerLoadItem);
        menu.add(playerSaveItem);
        menu.add(playerScoreItem);

        playerMenuBar.add(menu);
        playerLoadItem.addActionListener(e->playerLoad());
        playerSaveItem.addActionListener(e->playerSave());
        playerScoreItem.addActionListener(e->playerScoreBoard());

    }

    /**
     * Prompts user to for player name and saves the states in given file 
     */
    private void playerSave(){
        fileName = new JLabel(); 
        filePath = new JLabel();
        // Player playerOne = new Player();
        String name = JOptionPane.showInputDialog("Please enter player name"); 

        if (chooseFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (chooseFile.getSelectedFile().canRead()) {
                try {
                    playerOne.setPlayerName(name);
                    playerOne.stringToSave();
                    filer.saveToFile(playerOne,chooseFile.getSelectedFile().getName(),
                            chooseFile.getSelectedFile().getAbsolutePath());

                    fileName.setText(chooseFile.getSelectedFile().getName());
                    filePath.setText(chooseFile.getSelectedFile().getAbsolutePath());
                    
                    if(playerOne.getExceptionFoundFlag()){
                        JOptionPane.showMessageDialog(null,playerOne.getExceptionErrorMessage());
                    } else {
                        JOptionPane.showMessageDialog(null,"File loading complete");   
                    }
                    
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null,playerOne.getExceptionErrorMessage());    
                }
            }
        }
        
    }

    /**
     * creates popup of all the player profiles in the game
     */
    private void playerScoreBoard(){
        JOptionPane.showMessageDialog(null,playerOne.playerScoreBoard());
   
    }

    /**
     * loads a given player profile file into the game 
     */
    private void playerLoad(){
        fileName = new JLabel(); 
        filePath = new JLabel();
        // Player playerOne = new Player();

        if (chooseFile.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (chooseFile.getSelectedFile().canRead()) {
                try {
                    filer.loadFromFile(playerOne,chooseFile.getSelectedFile().getName(),
                        chooseFile.getSelectedFile().getAbsolutePath());
                    fileName.setText(chooseFile.getSelectedFile().getName());
                    filePath.setText(chooseFile.getSelectedFile().getAbsolutePath());
                    if(playerOne.getExceptionFoundFlag()){
                        JOptionPane.showMessageDialog(null,playerOne.getExceptionErrorMessage());
                    } else {
                        JOptionPane.showMessageDialog(null,"File loading complete");   
                    }
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(null,playerOne.getExceptionErrorMessage());    
                }
            }
        }

    }


    /**
     * Game directory jmenu bar for switch between games and main page
     */
    public void gameDirectory(){
        directoryMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Game Directory");
        JMenuItem tictactoeItem = new JMenuItem("Play Tictactoe");
        JMenuItem numTictactoeItem = new JMenuItem("Play Numerical Tictactoe");
        JMenuItem homePageItem = new JMenuItem("Home Directory");

        menu.add(tictactoeItem);
        menu.add(numTictactoeItem);
        menu.add(homePageItem);
        directoryMenuBar.add(menu);

        tictactoeItem.addActionListener(e->tictactoe());
        numTictactoeItem.addActionListener(e->numericalTictactoe());
        homePageItem.addActionListener(e->addMainPage());
    }

    /**
     * @return string with welcome message
     */

    @Override 
    public String toString(){
        return "Welcome to Games Menu";
    }


    /**
     * main that starts game 
     */
    public static void main(String[] args) {
        GameUI ui = new GameUI();
        ui.setVisible(true);
    }

}
