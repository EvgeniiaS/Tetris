/*
 * TCSS 305
 * Assignment 6B - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;



/**
 * GUI representation of a Tetris game.
 * @author Evgeniia Shcherbinina
 * @version 12/9/2016
 */
public class GUI implements PropertyChangeListener {
    
    /** Names of actions. */
    protected static final String[] ACTION_NAMES = {"Left:", "Right:", "Drop:", "Down:",
                                                    "Rotate:", "Pause:"};
    
    /** A list of key names. */
    protected static final String[] KEY_NAMES = {"LEFT", "RIGHT", "SPACE", "DOWN",
                                                 "UP", "P"};
    
    /** A list of characters that represent Tetris pieces. */
    protected static final List<Character> TETRIS_PIECE_SYMBOLS = Arrays.asList('I', 'J',
                                                         'L', 'O', 'S', 'T', 'Z');
    
    /** A list of colors of Tetris pieces. */
    protected static final List<Color> COLORS = Arrays.asList(new Color(232, 120, 16), 
                                                            new Color(214, 58, 23), 
                                                            new Color(16, 181, 35), 
                                                            new Color(23, 99, 181), 
                                                            new Color(124, 50, 173), 
                                                            new Color(198, 29, 100), 
                                                            new Color(232, 228, 37));
        
    /** A frame dimension. */
    private static final Dimension FRAME_DIMENSION = new Dimension(400, 400);
    
    /** A panel dimension. */
    private static final Dimension PANEL_DIMENSION = new Dimension(200, 400);
    
    /** A minimum size of a frame. */
    private static final Dimension MIN_DIMENSION = new Dimension(300, 315);
    
    /** A background color of a panel. */
    private static final Color PANEL_COLOR = new Color(247, 255, 242);
    
    /** A name of a property change listener. */
    private static final String NAME_LISTENER = "Control key is changed";
    
    /** A layout of the left panel. */
    private static final GridLayout LEFT_PANEL_GRID_LAYOUT = new GridLayout(3, 1);
    
    
       
    
    /** JFrame that contains all elements. */
    private final JFrame myFrame;
    
    /** A bottom left panel. */
    private final BottomPanel myBottomPanel; 
    
    

    /**
     * Constructs this object.
     */
    public GUI() {
        super();
        myFrame = new JFrame("Tetris");
        myBottomPanel = new BottomPanel();        
    }    
    
    /**
     * Sets up this object.
     */
    public void start() {        
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setSize(FRAME_DIMENSION);
        myFrame.setMinimumSize(MIN_DIMENSION);
        myFrame.setLayout(new GridLayout(1, 2));
        
        final ImageIcon img = new ImageIcon("images/tetris_icon.png");
        myFrame.setIconImage(img.getImage());       
        
        final Border empty = BorderFactory.createMatteBorder(2, 2, 2, 2, 
                                                             Color.LIGHT_GRAY); 
        
        final GameBoardPanel gamePanel = new GameBoardPanel();
        gamePanel.setPreferredSize(PANEL_DIMENSION);
        gamePanel.setBackground(PANEL_COLOR);
        gamePanel.setVisible(true);
        gamePanel.setBorder(empty);
        
        final JPanel leftPanel = new JPanel();        
        leftPanel.setPreferredSize(PANEL_DIMENSION);
        leftPanel.setVisible(true);
        leftPanel.setLayout(LEFT_PANEL_GRID_LAYOUT);
        
        final MenuBar menuBar = new MenuBar(myFrame, gamePanel);
        myFrame.setJMenuBar(menuBar);
        
        final InfoPanel infoPanel = new InfoPanel(gamePanel);
        final NewPiecePanel piecePanel = new NewPiecePanel(gamePanel);

        leftPanel.add(infoPanel);
        leftPanel.add(piecePanel);
        leftPanel.add(myBottomPanel);
        final Border borderCenter = BorderFactory.createMatteBorder(2, 0, 2, 0,
                                                                    Color.LIGHT_GRAY);
        leftPanel.setBorder(borderCenter);                
         
        myFrame.add(leftPanel);
        myFrame.add(gamePanel);
        
        gamePanel.addPropertyChangeListener(NAME_LISTENER, this);        
        
        final Toolkit kit = Toolkit.getDefaultToolkit();        
        myFrame.setLocation(
            (int) (kit.getScreenSize().getWidth() / 2 - myFrame.getWidth() / 2),
            (int) (kit.getScreenSize().getHeight() / 2 - myFrame.getHeight() / 2));
        
        myFrame.pack();
        myFrame.setVisible(true); 
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (NAME_LISTENER.equals(theEvent.getPropertyName())) {             
            myBottomPanel.changeLabel((String) theEvent.getOldValue(),
                                      (String) theEvent.getNewValue());
        }         
    }
    
}
