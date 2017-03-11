/*
 * TCSS 305
 * Assignment 6B - Tetris
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * Represents an information panel in a game window.
 * @author Evgeniia Shcherbinina
 * @version 12/9/2016
 */
public class InfoPanel extends JPanel implements PropertyChangeListener { 
    
    /** Generated serial version ID. */
    private static final long serialVersionUID = -6991134082341852880L;

    /** A background color of the panel. */
    private static final Color COLOR = new Color(153, 229, 153);
    
    /** Initial value for a label. */
    private static final String INITIAL_VALUE = "0";
    
    /** A number of raws on a panel. */
    private static final int RAWS = 4;
    
    /** A number lines to clear to reach the next level. */
    private static final int NUMBER_LINES = 5;
    
    
    /** GUI of a game board. */
    private final GameBoardPanel myBoardPanel;
    
    /** Number of cleared lines. */
    private int myClearedLines;
    
    /** Start level. */
    private int myStartLevel;
    
    /** Level label. */
    private final JLabel myLevel;
    
    /** Score label. */
    private final JLabel myScore;
    
    /** Cleared lives label. */
    private final JLabel myLines;
    
    /** Lines to clear until the next level label. */
    private final JLabel myLinesNext;
    
    /** New game button. */
    private JButton myNewGameButton;
    
    /** End game button. */
    private JButton myEndGameButton;
   
    /**
     * Constructs this object.
     * @param theBoardPanel GUI of the game board
     */
    public InfoPanel(final GameBoardPanel theBoardPanel) {
        super();
        myBoardPanel = theBoardPanel;
        myClearedLines = 0;
        myStartLevel = 1;
        myLevel = new JLabel("1");
        myScore = new JLabel(INITIAL_VALUE);
        myLines = new JLabel(INITIAL_VALUE);
        myLinesNext = new JLabel("5");
        setup();
    }
    
    /**
     * Sets up this object.
     */
    private void setup() {        
        setBackground(COLOR);        
        setVisible(true);
        setLayout(new BorderLayout());
        
        myBoardPanel.addPropertyChangeListener(this);
       
        final JPanel buttonPanel = new JPanel();
        
        myNewGameButton = new JButton(new NewGameAction(myBoardPanel));
        myNewGameButton.setBackground(COLOR); 
        myNewGameButton.setFocusable(false);
        myEndGameButton = new JButton(new EndGameAction(myBoardPanel));
        myEndGameButton.setBackground(COLOR);
        myEndGameButton.setFocusable(false);
        myEndGameButton.setEnabled(false);
        
        buttonPanel.add(myNewGameButton);
        buttonPanel.add(myEndGameButton);
        buttonPanel.setBackground(COLOR);        
        buttonPanel.setVisible(true);
        add(buttonPanel, BorderLayout.NORTH);
        
        myLevel.setHorizontalAlignment(SwingConstants.CENTER);
        myScore.setHorizontalAlignment(SwingConstants.CENTER);
        myLines.setHorizontalAlignment(SwingConstants.CENTER);
        myLinesNext.setHorizontalAlignment(SwingConstants.CENTER);
        
        final JPanel statPanel = new JPanel();
        statPanel.setLayout(new GridLayout(RAWS, 2));
        statPanel.setBackground(COLOR);
        statPanel.setVisible(true);
        add(statPanel, BorderLayout.CENTER);        

        final JLabel level = new JLabel("Level");        
        final JLabel score = new JLabel("Score");
        final JLabel numberClearedLines = new JLabel("Cleared Lines");
        final JLabel linesNextLevel = new JLabel("Lines to Clear");
        
        level.setHorizontalAlignment(SwingConstants.CENTER);
        score.setHorizontalAlignment(SwingConstants.CENTER);
        numberClearedLines.setHorizontalAlignment(SwingConstants.CENTER);
        linesNextLevel.setHorizontalAlignment(SwingConstants.CENTER);
           
        statPanel.add(level);
        statPanel.add(myLevel);
        statPanel.add(score);
        statPanel.add(myScore);
        statPanel.add(numberClearedLines);
        statPanel.add(myLines);
        statPanel.add(linesNextLevel);
        statPanel.add(myLinesNext);
    }
    
    /**
     * Updates info on the panel.
     */
    public void updateInfo() {
        final int level = myClearedLines / 5 + myStartLevel;        
        myLevel.setText(String.valueOf(level));
        //myScore.setText(String.valueOf(myCurrentScore)); 
        myLines.setText(String.valueOf(myClearedLines));
        myLinesNext.setText(String.valueOf(NUMBER_LINES - myClearedLines % NUMBER_LINES));
    }

   
    @Override
    public void propertyChange(final PropertyChangeEvent theArg) {
        if ("Change score".equals(theArg.getPropertyName())) {
            final int newScore = (int) theArg.getNewValue();
            myScore.setText(String.valueOf(newScore));
        } else if ("Change cleared lines".equals(theArg.getPropertyName())) {
            myClearedLines = (int) theArg.getNewValue();
            updateInfo();
        } else if ("Change level".equals(theArg.getPropertyName())) { 
            myStartLevel = (int) theArg.getNewValue();
            myLevel.setText(String.valueOf(myStartLevel));
        } else if ("New game".equals(theArg.getPropertyName())) {
            myNewGameButton.setEnabled(false);
            myEndGameButton.setEnabled(true);
        } else if ("End game".equals(theArg.getPropertyName())) {
            myNewGameButton.setEnabled(true);
            myEndGameButton.setEnabled(false);
        }
    }
    
}
