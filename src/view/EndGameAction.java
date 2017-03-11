/*
 * TCSS 305
 * Assignment 6B - Tetris
 */

package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


/**
 * Represents the end game action.
 * @author Evgeniia Shcherbinina
 * @version 12/9/2016
 */
public class EndGameAction extends AbstractAction {

    /** Generated serial version ID. */
    private static final long serialVersionUID = -7130729467675643020L;
    
    /** GUI of a game board. */
    private final GameBoardPanel myBoardPanel;

    
    /**
     * Constructs a new game action.
     * @param theBoardPanel GUI of the game board
     */
    public EndGameAction(final GameBoardPanel theBoardPanel) {
        super("End Game");
        myBoardPanel = theBoardPanel;
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        myBoardPanel.endGame();        
    }

}
