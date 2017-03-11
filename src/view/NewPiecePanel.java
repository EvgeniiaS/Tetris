/*
 * TCSS 305
 * Assignment 6B - Tetris
 */


package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


/**
 * Represents a panel which contains a next Tetris piece.
 * @author Evgeniia Shcherbinina
 * @version 12/9/2016
 */
public class NewPiecePanel extends JPanel implements PropertyChangeListener { 
    
    /** Generated serial version ID. */
    private static final long serialVersionUID = 7734240927035895955L;

    /** A background color of this panel. */
    private static final Color PANEL_COLOR = new Color(247, 255, 242);
    
    /** A number of symbols in a line of a string representation of a Tetris piece. */
    private static final int SYMBOLS_IN_LINE = 5;
    
    /** A horizontal size (in squares) of a game board. */
    private static final int WIDTH_SQUARES = 10;
    
    /** A vertical size (in squares) of a game board. */
    private static final int HEIGHT_SQUARES = 20;
    
    /** A third part of a panel. */
    private static final int THIRD_PART_OF_PANEL = 3;
    
    /** A fourth part of a panel. */
    private static final int FORTH_PART_OF_PANEL = 4;
    
    
    /** A game board. */
    private final GameBoardPanel myBoard;
    
    /** A string representation of a Tetris piece. */
    private String myString;
    
    /** A label that contains an image. */
    private final JLabel myLabel;
    
    /** Whether a panel contains a label or not. */
    private boolean myIsLabel;
    

    /**
     * Constructs this panel.
     * @param theBoard the game board
     */
    public NewPiecePanel(final GameBoardPanel theBoard) {
        super();
        myBoard = theBoard;
        myString = "empty";        
        myLabel = new JLabel();
        myIsLabel = true;
        setup();        
    }
    
    /**
     * Sets up this panel.
     */
    private void setup() {
        setBackground(PANEL_COLOR);
        setVisible(true);
        setLayout(new BorderLayout());
        
        final Border borderCenter = BorderFactory.createMatteBorder(2, 0, 2, 0,
                                                                    Color.LIGHT_GRAY);
        setBorder(borderCenter);
        
        myBoard.addPropertyChangeListener(this);
        
        myLabel.setIcon(new ImageIcon("images/tetris_60.png"));       
        add(myLabel, BorderLayout.CENTER);
        myLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }    
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
    
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;       
        
        for (int i = 0; i < myString.length(); i++) {             
            
            if (GUI.TETRIS_PIECE_SYMBOLS.contains(myString.charAt(i))) {                
                
                final int x;
                final int y;
                final int width;
                final int height;
                final int size;
                final int widthEmptySpace;
                final int heightEmptySpace;
                
                if (myIsLabel) {
                    remove(myLabel);
                    myIsLabel = false;
                }
                
                g2d.setPaint(GUI.COLORS.get(GUI.TETRIS_PIECE_SYMBOLS.indexOf
                                            (myString.charAt(i))));
                
                x = i % SYMBOLS_IN_LINE;
                y = i / SYMBOLS_IN_LINE;
                width = getWidth();
                height = getHeight();
                
                if (width / WIDTH_SQUARES > height * THIRD_PART_OF_PANEL / HEIGHT_SQUARES) {
                    size = height * THIRD_PART_OF_PANEL / HEIGHT_SQUARES;                    
                } else {
                    size = width / WIDTH_SQUARES;                    
                } 
                widthEmptySpace = (int) ((width - FORTH_PART_OF_PANEL * size) / 2);
                heightEmptySpace = (int) ((height - FORTH_PART_OF_PANEL * size) / 2);
                g2d.fillRect(x * size + widthEmptySpace, y * size + heightEmptySpace,
                             size, size);
                g2d.setPaint(Color.GRAY);
                g2d.drawRect(x * size + widthEmptySpace, y * size + heightEmptySpace,
                             size, size);
            }
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if ("New piece".equals(theEvent.getPropertyName())) {
            myString = (String) theEvent.getNewValue();
            repaint();
        }        
    }
    
}
