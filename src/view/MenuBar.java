/*
 * TCSS 305
 * Assignment 6B - Tetris
 */

package view;

import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;


/**
 * Represents a menu bar in a game window.
 * @author Evgeniia Shcherbinina
 * @version 12/9/2016
 */
public class MenuBar extends JMenuBar implements PropertyChangeListener {
    
    /** Generated serial version ID. */
    private static final long serialVersionUID = -7128494878211941996L;    

    /** A name of the option to change control keys. */
    private static final String MENU_NAME = "Change Control Keys...";
    
    /** A number of raws on the control keys panel. */
    private static final int RAWS = 6;
    
    /** A number of raws on the grid panel. */
    private static final int RAWS_GRID = 4;
    
    /** Major tick spacing for the width slider. */
    private static final int WIDTH_MAJOR_TICK = 5;
    
    /** Major tick spacing for the height slider. */
    private static final int HEIGHT_MAJOR_TICK = 10;
    
    /** A name of an image file. */
    private static final ImageIcon TETRIS_IMAGE = new ImageIcon("images/tetris_40.png");
    
    
    /** JFrame that contains all elements of GUI. */
    private final JFrame myFrame;
    
    /** GUI of a game board. */
    private final GameBoardPanel myGamePanel;
    
    /** Options menu item. */
    private final JMenu myOptions;
    
    /** New game menu item. */
    private final JMenuItem myNewGame;
    
    /** End game menu item. */
    private final JMenuItem myEndGame;
    
    /** Save game menu item. */
    private final JMenuItem mySaveGame;
    
    /** Play saved game menu item. */
    private final JMenuItem myPlaySavedGame;


    /**
     * Constructs this menu bar.
     * @param theFrame JFrame that contains all elements of GUI
     * @param thePanel the panel which represents GUI of a game board
     */
    public MenuBar(final JFrame theFrame, final GameBoardPanel thePanel) {
        super();
        myFrame = theFrame;
        myGamePanel = thePanel;
        myOptions = new JMenu("Options");
        myNewGame = new JMenuItem(new NewGameAction(myGamePanel));
        myEndGame = new JMenuItem(new EndGameAction(myGamePanel));
        mySaveGame = new JMenuItem("Save Game", 'S');
        myPlaySavedGame = new JMenuItem("Play Saved Game", 'P');
        setup();
    }
    
    /**
     * Sets up the menu bar.
     */
    private void setup() {
        setupFileMenu();
        setupOptionsMenu();
        setupHelpMenu();
    }
    
    /**
     * Sets up this File menu. 
     */
    private void setupFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
        add(fileMenu);
        
        myNewGame.setMnemonic('N');
        fileMenu.add(myNewGame);
        fileMenu.add(myEndGame);
        myEndGame.setMnemonic('E');
        myEndGame.setEnabled(false);
        
        fileMenu.addSeparator();
        myGamePanel.addPropertyChangeListener(this);
        
        fileMenu.add(mySaveGame);
        mySaveGame.setEnabled(false);
        mySaveGame.addActionListener(theEvent -> {
            myGamePanel.saveGame();
        });
        
        fileMenu.add(myPlaySavedGame);
        myPlaySavedGame.addActionListener(theEvent -> {
            myGamePanel.startSavedGame();
        });
        
        fileMenu.addSeparator();
        
        final JMenuItem quit = new JMenuItem("Quit", 'Q');
        fileMenu.add(quit);
        quit.addActionListener(theEvent -> {
            myFrame.dispatchEvent(new WindowEvent(myFrame, WindowEvent.WINDOW_CLOSING));
        });
    }
    
    /**
     * Sets up Options menu.
     */
    private void setupOptionsMenu() {
        myOptions.setMnemonic('O');
        add(myOptions);
        
        setupOptionsControlKeysMenu();
        myOptions.addSeparator();
        setupOptionsGridMenu();
        setupOptionsLevelMenu();
        myOptions.addSeparator();
        
        final JCheckBoxMenuItem muteSounds = new JCheckBoxMenuItem("Mute Sounds");
        muteSounds.addActionListener(theEvent -> {
            myGamePanel.changeMuteSounds();
        });
        myOptions.add(muteSounds);
    }
    
    /**
     * Sets up Options -> Control Keys menu.
     */
    private void setupOptionsControlKeysMenu() {        
        final JMenuItem controlKeys = new JMenuItem(MENU_NAME, 'C');
        myOptions.add(controlKeys);        

        final JPanel controlKeysPanel = new JPanel();
        controlKeysPanel.setLayout(new GridLayout(RAWS, 2));      
            
        for (int i = 0; i < GUI.ACTION_NAMES.length; i++) {
            final KeyChangeField k = new KeyChangeField(GUI.ACTION_NAMES[i], myGamePanel);
            controlKeysPanel.add(k);                
        }
            
        controlKeys.addActionListener(theEvent -> {
            JOptionPane.showMessageDialog(myFrame, controlKeysPanel, MENU_NAME, 
                                          JOptionPane.PLAIN_MESSAGE, TETRIS_IMAGE);            
        });
    }    
        

    /**
     * Sets up Options -> Choose Grid menu.
     */
    private void setupOptionsGridMenu() {
        final JMenuItem chooseGrid = new JMenuItem("Choose Grid...", 'G');
        myOptions.add(chooseGrid);
        
        final JPanel sliders = new JPanel();   
        sliders.setLayout(new GridLayout(RAWS_GRID, 1));
        
        final JSlider sliderWidth = new JSlider(5, 20, 10);
        sliderWidth.setMajorTickSpacing(WIDTH_MAJOR_TICK);
        sliderWidth.setMinorTickSpacing(1);
        sliderWidth.setPaintTicks(true);
        sliderWidth.setPaintLabels(true);  
        sliderWidth.addChangeListener(theEvent -> {
            if (!myGamePanel.isGameOver()) {
                myGamePanel.endGame();
            }
            myGamePanel.setWidth(sliderWidth.getValue());
        });
        
        final JSlider sliderHeight = new JSlider(10, 40, 20);
        sliderHeight.setMajorTickSpacing(HEIGHT_MAJOR_TICK);
        sliderHeight.setMinorTickSpacing(1);
        sliderHeight.setPaintTicks(true);
        sliderHeight.setPaintLabels(true);
        sliderHeight.addChangeListener(theEvent -> {
            myGamePanel.setHeight(sliderHeight.getValue());
        });
        
        final JLabel widthLabel = new JLabel("Choose width");
        final JLabel heightLabel = new JLabel("Choose height");
        
        sliders.add(widthLabel);
        sliders.add(sliderWidth);
        sliders.add(heightLabel);
        sliders.add(sliderHeight);
        
        chooseGrid.addActionListener(theEvent -> {
            JOptionPane.showMessageDialog(myFrame, sliders, "Choose Grid", 
                                          JOptionPane.PLAIN_MESSAGE, TETRIS_IMAGE);            
        });
    }
    
    
    /**
     * Sets up Options -> Choose Level menu.
     */
    private void setupOptionsLevelMenu() {
        
        final JMenuItem chooseLevel = new JMenuItem("Choose Level...", 'L');
        myOptions.add(chooseLevel);
        
        final JPanel levelPanel = new JPanel();           
        final JSlider levelSlider = new JSlider(1, 11, 1);
        levelSlider.setMajorTickSpacing(2);
        levelSlider.setMinorTickSpacing(1);
        levelSlider.setPaintTicks(true);
        levelSlider.setPaintLabels(true);  
        levelSlider.addChangeListener(theEvent -> {
            if (!myGamePanel.isGameOver()) {
                myGamePanel.endGame();
            }
            myGamePanel.setLevel(levelSlider.getValue());
        });
        
        levelPanel.add(levelSlider);
        
        chooseLevel.addActionListener(theEvent -> {
            JOptionPane.showMessageDialog(myFrame, levelPanel, "Choose Level", 
                                          JOptionPane.PLAIN_MESSAGE, TETRIS_IMAGE);            
        });
    }
    
    /**
     * Sets up Help menu.
     */
    private void setupHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');
        add(helpMenu);
        
        final JMenuItem scoring = new JMenuItem("Scoring...", 'S');
        scoring.addActionListener(theEvent -> {
            JOptionPane.showMessageDialog(myFrame,
                              "A game starts at level 1 and increments the level"
                            + " for each cumulative five lines cleared.\n"
                            + "Every time as a tetris piece moves down, a score increases"
                            + " by one point.\n"
                            + "You can earn:\n"
                            + "- 200 points for clearing a single line\n"
                            + "- 500 points for clearing two lines at once\n"
                            + "- 1000 points for clearing three lines at once\n"
                            + "- 2000 points for clearing four lines at once",
                            "Scoring", JOptionPane.PLAIN_MESSAGE, TETRIS_IMAGE);
        });
        helpMenu.add(scoring);
        
        final JMenuItem about = new JMenuItem("About...", 'A');
        about.addActionListener(theEvent -> {
            JOptionPane.showMessageDialog(myFrame, "TCSS 305 Tetris\nAutumn 2016\n"
                            + "Evgeniia Shcherbinina\n\n"
                            + "Used music and sounds:\n\n"
                            + "\"Rainbows\" by Kevin MacLeod (incompetech.com)\n"
                            + "Licensed under Creative Commons: By Attribution 3.0 License.\n"
                            + "http://creativecommons.org/licenses/by/3.0/\n\n"
                            + "\"Game Over Sound\" by TheZero (freesound.org)\n"
                            + "\"Bit Bubbles\" by Rurbital (freesound.org)\n"
                            + "Licensed under the Creative Commons 0 License.", "About",
                            JOptionPane.PLAIN_MESSAGE, TETRIS_IMAGE);
        });
        helpMenu.add(about);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theArg) {
        if ("New game".equals(theArg.getPropertyName())) {
            myNewGame.setEnabled(false);
            myEndGame.setEnabled(true);
            mySaveGame.setEnabled(true); 
            myPlaySavedGame.setEnabled(false);
        } else if ("End game".equals(theArg.getPropertyName())) {
            myNewGame.setEnabled(true);
            myEndGame.setEnabled(false);
            mySaveGame.setEnabled(false);
            myPlaySavedGame.setEnabled(true);
        }
        
    }
    
}
