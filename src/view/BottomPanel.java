/*
 * TCSS 305
 * Assignment 6B - Tetris
 */

package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Represents a left bottom panel in a game window.
 * @author Evgeniia Shcherbinina
 * @version 12/9/2016
 */
public class BottomPanel extends JPanel {
    
    /** Generated serial version ID. */
    private static final long serialVersionUID = -2771786819105484087L;

    /** A background color of a panel. */
    private static final Color PANEL_COLOR = new Color(153, 229, 153);
    
    /** A number of raws on a panel. */
    private static final int RAWS = 6;
    
    /** The third element of a list of strings. */
    private static final int THIRD_ELEMENT = 3;
    
    /** The fourth element of a list of strings. */
    private static final int FOURTH_ELEMENT = 4;
    
    /** The fifth element of a list of strings. */
    private static final int FIFTH_ELEMENT = 5;
 
    
    /** A label which represents a key's name to move a Tetris piece to the left.*/
    private JLabel myLeft;
    
    /** A label which represents a key's name to move a Tetris piece to the right.*/
    private JLabel myRight;
    
    /** A label which represents a key's name to drop a Tetris piece down.*/
    private JLabel myDrop;
    
    /** A label which represents a key's name to move a Tetris piece down.*/
    private JLabel myDown;
    
    /** A label which represents a key's name to rotate a Tetris piece.*/
    private JLabel myRotate;
    
    /** A label which represents a key's name to pause a game.*/
    private JLabel myPause;
    

    /**
     * Constructs a left bottom panel of a game window.
     */
    public BottomPanel() {
        super();
        setup();       
    }

    /**
     * Sets up this object.
     */
    private void setup() {        
        setBackground(PANEL_COLOR);
        setVisible(true);
        setLayout(new GridLayout(RAWS, 2));
        
        final List<JLabel> labelList = new ArrayList<>();
        
        
        myLeft = new JLabel(GUI.KEY_NAMES[0]);
        myRight = new JLabel(GUI.KEY_NAMES[1]);
        myDrop = new JLabel(GUI.KEY_NAMES[2]);
        myDown = new JLabel(GUI.KEY_NAMES[THIRD_ELEMENT]);
        myRotate = new JLabel(GUI.KEY_NAMES[FOURTH_ELEMENT]);
        myPause = new JLabel(GUI.KEY_NAMES[FIFTH_ELEMENT]);       
        
        final JLabel left = new JLabel(GUI.ACTION_NAMES[0]);
        final JLabel right = new JLabel(GUI.ACTION_NAMES[1]);
        final JLabel drop = new JLabel(GUI.ACTION_NAMES[2]);
        final JLabel down = new JLabel(GUI.ACTION_NAMES[THIRD_ELEMENT]);
        final JLabel rotate = new JLabel(GUI.ACTION_NAMES[FOURTH_ELEMENT]);
        final JLabel pause = new JLabel(GUI.ACTION_NAMES[FIFTH_ELEMENT]);
        
        labelList.add(left);
        labelList.add(myLeft);
        labelList.add(right);
        labelList.add(myRight);
        labelList.add(drop);
        labelList.add(myDrop);
        labelList.add(down);
        labelList.add(myDown);
        labelList.add(rotate);
        labelList.add(myRotate);
        labelList.add(pause);
        labelList.add(myPause);
        
        for (int i = 0; i < labelList.size(); i++) {
            add(labelList.get(i));
            labelList.get(i).setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
    
    
    /**
     * Changes text on a label.
     * @param theName the name of action associated with the key
     * @param theValue the text to be set on label
     */
    public void changeLabel(final String theName, final String theValue) {
        if ("Left:".equals(theName)) {
            myLeft.setText(theValue);
        } else if ("Right:".equals(theName)) {
            myRight.setText(theValue);
        } else if ("Drop:".equals(theName)) {
            myDrop.setText(theValue);           
        } else if ("Down:".equals(theName)) {
            myDown.setText(theValue);
        } else if ("Rotate:".equals(theName)) {
            myRotate.setText(theValue);
        } else {
            myPause.setText(theValue);
        }
    }    

}
