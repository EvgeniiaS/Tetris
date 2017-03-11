/*
 * TCSS 305
 * Assignment 6B - Tetris
 */

package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Represents a field with a label which provides an ability to change a control key.
 * @author Evgeniia Shcherbinina
 * @version 12/9/2016
 */
public class KeyChangeField extends JPanel {
    
    /** Generated serial version ID. */
    private static final long serialVersionUID = -2237725292371097326L;
    
    /** A name of an action associated with a given control key. */
    private final String myName;
    
    /** A graphical representation of a game board. */
    private final GameBoardPanel myGamePanel;    

    /**
     * Constructs this object.
     * @param theName the name of the label.
     * @param thePanel the graphical representation of the game board.
     */
    public KeyChangeField(final String theName, final GameBoardPanel thePanel) {
        super();
        myName = theName;
        myGamePanel = thePanel; 
        setup();
    }

    /**
     * Sets up this object.
     */
    private void setup() {
        final JLabel l = new JLabel(myName);
        final JTextField f = new JTextField(1);
        add(l);
        add(f);
        setVisible(true);
        f.addKeyListener(new KeyHandler());
    }
    
    /**
     * Represents a key listener associated with KeyChangeField object. 
     * @author Evgeniia Shcherbinina
     * @version 12/9/2016
     */
    private class KeyHandler extends KeyAdapter { 
        
        @Override        
        public void keyPressed(final KeyEvent theEvent) {            
            myGamePanel.rebindKey(theEvent, myName);           
        }
    }
}
