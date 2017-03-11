/*
 * TCSS 305
 * Assignment 6B - Tetris
 */

package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import model.Board;

/**
 * Represents an action associated with a control key.
 * @author Evgeniia Shcherbinina
 * @version 12/9/2016 
 */
public class KeyAction extends AbstractAction {
    
    /** Generated serial version ID. */
    private static final long serialVersionUID = 6109429373641952864L;
    
    /** A Tetris board. */
    private final Board myBoard;
    
    /** A string representation on an action. */
    private final String myName;
    
    /** GUI of a game board. */
    private final GameBoardPanel myGUIBoard;

    /**
     * Constructs an action associated with a control key.
     * @param theBoard the Tetris board
     * @param theName the name of the action
     * @param theGUIBoard the GUI of the game board
     */
    public KeyAction(final Board theBoard, final String theName, 
                     final GameBoardPanel theGUIBoard) {
        super();
        myBoard = theBoard;
        myName = theName;
        myGUIBoard = theGUIBoard;
    }

    
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        if ("Left:".equals(myName)) {
            myBoard.left();
        } else if ("Right:".equals(myName)) {
            myBoard.right();
        } else if ("Down:".equals(myName)) {
            myBoard.down();
        } else if ("Drop:".equals(myName)) {
            myBoard.drop();
        } else if ("Rotate:".equals(myName)) {    
            myBoard.rotate();
        } else if ("Pause:".equals(myName)) {
            myGUIBoard.pauseGame();    
        } else { // do nothing action
            System.out.println("");
        }
    }

}
