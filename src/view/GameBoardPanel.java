/*
 * TCSS 305
 * Assignment 6B - Tetris
 */

package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import model.Board;
import model.MovableTetrisPiece;


/**
 * GUI representation of a game board. 
 * @author Evgeniia Shcherbinina
 * @version 12/9/2016
 */
public class GameBoardPanel extends JPanel implements Observer {    
    
    /** Generated serial version ID. */
    private static final long serialVersionUID = -1624320960169731509L; 

    /** Focus for an input map. */
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    
    /** A timer delay. */
    private static final int TIMER_DELAY = 700;

    /** A horizontal size (in squares) of a game board. */
    private static final int WIDTH_SQUARES = 10;
    
    /** A vertical size (in squares) of a game board. */
    private static final int HEIGHT_SQUARES = 20;
    
    /** A name for an action, which does nothing. */
    private static final String NOTHING_NAME = "Nothing";
    
    /** Property change name. */
    private static final String CHANGE_CLEARED_LINES = "Change cleared lines";
    
    /** Property change name. */
    private static final String CHANGE_SCORE = "Change score";
    
    /** Property change name. */
    private static final String NEW_PIECE = "New piece";
    
    /** Property change name. */
    private static final String END_GAME = "End game";
    
    /** Property change name. */
    private static final String CHANGE_LEVEL = "Change level";
    
    /** Game over string. */
    private static final String GAME_OVER = "GAME OVER";
    
    /** Pause string. */
    private static final String PAUSE = "PAUSE";
    
    /** Game Saved string. */
    private static final String GAME_SAVED = "GAME SAVED";
    
    /** Score 200 points. */
    private static final int POINTS_200 = 200;
    
    /** Score 500 points. */
    private static final int POINTS_500 = 500;
    
    /** Score 1000 points. */
    private static final int POINTS_1000 = 1000;
    
    /** Score 2000 points. */
    private static final int POINTS_2000 = 2000;
    
    /** Three cleared lines at once. */
    private static final int THREE_LINES = 3;
    
    /** Timer acceleration. */
    private static final double TIMER_ACCELERATION = 0.7;
    
    /** A list of music files. */
    private static final File[] MUSIC_FILES = {new File("sounds/rainbows.wav")};
    
    /** A sound when a line was cleared. */
    private static final File[] MUSIC_FILE_2 = {new File("sounds/bit-bubbles.wav")};
    
    /** A sound for game over. */
    private static final File[] MUSIC_FILE_3 = {new File("sounds/game-over-sound.wav")};
    
    /** Name of the saved file. */
    private static final String SAVED_FILE_NAME = "game.bin";

    
    
    /** A game board. */
    private Board myBoard;
    
    /** A string representation of a game board. */
    private String myStringGame;
    
    /** A timer. */
    private Timer myTimer;
    
    /** Game Over status. */
    private boolean myGameOver;
    
    /** Pause status. */
    private boolean myIsPaused;
    
    /** First game status. */
    private boolean myIsFirstGame;
    
    /** Number of cleared lines. */
    private int myClearedLines;
    
    /** A horizontal size (in squares) of a game board. */
    private int myWidth;
    
    /** A vertical size (in squares) of a game board. */
    private int myHeight; 
    
    /** A music player. */
    private MusicPlayer myPlayer1;
    
    /** A music player for short sounds. */
    private MusicPlayer myPlayer2;
    
    /** Muted status. */
    private boolean myIsMuted;
    
    /** Initial timer delay. */
    private int myInitialDelay;
    
    /** Next Tetris piece. */
    private String myNextPiece;
    
    /** Score of the game. */
    private int myScore;
    
    /** Game saved status. */
    private boolean myIsGameSaved;

    
    
    /**
     * Constructs this panel. 
     */
    public GameBoardPanel() {
        super();              
        setup();
    }
    
    /**
     * Sets up this panel.
     */
    private void setup() {
        myStringGame = "";
        myInitialDelay = TIMER_DELAY;
        myTimer = new Timer(myInitialDelay, new MyTimerActionListener());
        myWidth = WIDTH_SQUARES;
        myHeight = HEIGHT_SQUARES;
        myGameOver = true;
        myIsPaused = false;
        myIsFirstGame = true;
        myClearedLines = 0;        
        myPlayer1 = new MusicPlayer();
        myPlayer2 = new MusicPlayer();
        myIsMuted = false;
        myScore = 0;  
        myIsGameSaved = false;
    }
    
    /**
     * Starts a new game.
     */
    public void newGame() {
        if (myGameOver) {
            myBoard = new Board(myWidth, myHeight);
            myBoard.addObserver(this);
            myBoard.newGame();
            myClearedLines = 0;
            myScore = 0;
            setupGame();
        }
        myIsFirstGame = false;
        myIsGameSaved = false;
    }
    
    /**
     * Sets up the game.
     */
    private void setupGame() {        
        myTimer.start();
        myTimer.setDelay(myInitialDelay);
        myGameOver = false;
        for (int i = 0; i < GUI.ACTION_NAMES.length; i++) {
            getInputMap(IFW).put(KeyStroke.getKeyStroke(GUI.KEY_NAMES[i]), 
                                 GUI.ACTION_NAMES[i]);
            getActionMap().put(GUI.ACTION_NAMES[i], new KeyAction(myBoard, 
                                 GUI.ACTION_NAMES[i], this));
        }        
        firePropertyChange(CHANGE_CLEARED_LINES, null, myClearedLines);
        firePropertyChange(CHANGE_SCORE, null, 0);
        firePropertyChange("New game", null, 0);
        myPlayer1.newList(MUSIC_FILES);
        if (myPlayer2.isStarted()) {
            myPlayer2.stopPlay();
        }
        if (myIsMuted) {            
            myPlayer1.togglePause();
        }
    }
    
    /**
     * Ends the game.
     */
    public void endGame() {
        myTimer.stop();
        if (!myIsMuted) {
            myPlayer2.newList(MUSIC_FILE_3);
        }
        myGameOver = true;
        for (int i = 0; i < GUI.ACTION_NAMES.length; i++) {
            getActionMap().put(GUI.ACTION_NAMES[i], 
                               new KeyAction(myBoard, NOTHING_NAME, this));
        }
        myTimer.setDelay(myInitialDelay);
        repaint();
        myPlayer1.stopPlay();
        firePropertyChange(END_GAME, null, 0);
    }
    
    /**
     * Rebinds a control key.
     * @param theEvent the key event which causes this change.
     * @param theActionName the name of a new key.
     */
    public void rebindKey(final KeyEvent theEvent, final String theActionName) {
        final String keyText = KeyEvent.getKeyText(theEvent.getKeyCode());
        int i = 0;
        while (!theActionName.equals(GUI.ACTION_NAMES[i])) {
            i++;
        }        
        getInputMap(IFW).remove(KeyStroke.getKeyStroke(GUI.KEY_NAMES[i]));
        getInputMap(IFW).put(KeyStroke.getKeyStrokeForEvent(theEvent), theActionName);
        GUI.KEY_NAMES[i] = KeyStroke.getKeyStrokeForEvent(theEvent).toString();        
        
        firePropertyChange("Control key is changed", theActionName, 
                                keyText.toUpperCase(Locale.ENGLISH));    
    }      
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;        

        int x;
        int y;
        final int symbolsInLine = myWidth + 3;
        final int nonPaintedSymbols = symbolsInLine * 5;
        
        for (int i = 0; i < myStringGame.length(); i++) {            
            if (GUI.TETRIS_PIECE_SYMBOLS.contains(myStringGame.charAt(i))) {
                
                final int width = getWidth();
                final int height = getHeight();
                final int size;
                final int emptySpace;
                
                g2d.setPaint(GUI.COLORS.get(GUI.TETRIS_PIECE_SYMBOLS.indexOf
                                            (myStringGame.charAt(i))));
                x = (i - nonPaintedSymbols) % symbolsInLine;
                y = (i - nonPaintedSymbols) / symbolsInLine;
                
                if (width / myWidth > height / myHeight) {
                    size = height / myHeight;
                    emptySpace = (int) ((width - myWidth * size) / 2);
                    g2d.fillRect(x * size + emptySpace, y * size, size, size);
                    g2d.setPaint(Color.GRAY);
                    g2d.drawRect(x * size + emptySpace, y * size, size, size);
                    g2d.drawRect(emptySpace, 0, size * myWidth, size * myHeight);
                } else {
                    size = width / myWidth;
                    emptySpace = (int) ((height - myHeight * size));
                    g2d.fillRect(x * size, y * size + emptySpace, size, size);
                    g2d.setPaint(Color.GRAY);
                    g2d.drawRect(x * size, y * size + emptySpace, size, size);
                    g2d.drawRect(0, emptySpace, width, height);
                    
                }                
            }        
        }
        
        final int fontSize = getWidth() / myWidth;
        final Font font = new Font("Serif", Font.BOLD, fontSize);
        g2d.setPaint(Color.BLACK);            
        g2d.setFont(font);
        final FontMetrics fm = g2d.getFontMetrics();            
        y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();           
            
        if (myGameOver && !myIsFirstGame && !myIsGameSaved) {
            x = (getWidth() - fm.stringWidth(GAME_OVER)) / 2;
            g2d.drawString(GAME_OVER, x, y);
        } else if (myIsPaused) {
            x = (getWidth() - fm.stringWidth(PAUSE)) / 2;
            g2d.drawString(PAUSE, x, y);
        } else if (myIsGameSaved) {
            x = (getWidth() - fm.stringWidth(GAME_SAVED)) / 2;
            g2d.drawString(GAME_SAVED, x, y);     
        }        
    }
    
    
    /**
     * Pauses and resumes a game.
     */
    public void pauseGame() {
        myIsPaused = !myIsPaused;
        if (myIsPaused) {
            myTimer.stop();
            for (int i = 0; i < GUI.ACTION_NAMES.length - 1; i++) {
                getActionMap().put(GUI.ACTION_NAMES[i], new KeyAction(myBoard,
                                   NOTHING_NAME, this));
            }
        } else {
            myTimer.start();
            for (int i = 0; i < GUI.ACTION_NAMES.length; i++) {
                getActionMap().put(GUI.ACTION_NAMES[i], new KeyAction(myBoard, 
                                               GUI.ACTION_NAMES[i], this));
            }
        }
        if (!myIsMuted) {
            myPlayer1.togglePause();
        }
        repaint();
    }
    
    /**
     * Sets width of this panel (in squares).
     * @param theWidth the width to be set
     */
    public void setWidth(final int theWidth) {
        myWidth = theWidth;        
    }
    
    /**
     * Sets height of this panel (in squares).
     * @param theHeight the height to be set
     */
    public void setHeight(final int theHeight) {
        myHeight = theHeight;
    }
    
    /**
     * Sets the level of the game.
     * @param theLevel to be set
     */
    public void setLevel(final int theLevel) {
        int delay = TIMER_DELAY;
        for (int i = 1; i < theLevel; i++) {
            delay = (int) (delay * TIMER_ACCELERATION);            
        }
        myTimer.setDelay(delay);
        myInitialDelay = delay;
        firePropertyChange(CHANGE_LEVEL, null, theLevel);
    }
    
    /**
     * Returns whether the game is over or not.
     * @return game over status
     */
    public boolean isGameOver() {
        return myGameOver;
    }
    
    /**
     * Turns on or turns off all sounds in the game.
     */
    public void changeMuteSounds() {
        myIsMuted = !myIsMuted;
        myPlayer1.togglePause();
        myPlayer2.togglePause();
    }

    @Override
    public void update(final Observable theObject, final Object theArgument) {
        if (theArgument instanceof String) {
            myStringGame = (String) theArgument;
            myScore++;
            firePropertyChange(CHANGE_SCORE, null, myScore);
            repaint();
        } else if (theArgument instanceof Boolean) {
            myTimer.stop();
            myGameOver = true;
            firePropertyChange(END_GAME, null, 0);
            repaint();
            if (!myIsMuted) {
                myPlayer1.stopPlay();
                myPlayer2.newList(MUSIC_FILE_3);
            }    
        } else if (theArgument instanceof Integer[]) {
            final Integer[] i = (Integer[]) theArgument;
            if (i.length == 1) {
                myScore += POINTS_200;
            } else if (i.length == 2) {
                myScore += POINTS_500;
            } else if (i.length == THREE_LINES) {
                myScore += POINTS_1000;            
            } else {
                myScore += POINTS_2000;
            }
            firePropertyChange(CHANGE_SCORE, null, myScore);
            final int levelBefore = myClearedLines / 5;
            myClearedLines += i.length;
            final int levelAfter = myClearedLines / 5;
            if (levelBefore != levelAfter) {
                myInitialDelay = (int) (myTimer.getDelay() * TIMER_ACCELERATION);
                myTimer.setDelay(myInitialDelay);
            }
            firePropertyChange(CHANGE_CLEARED_LINES, null, myClearedLines);
            if (!myIsMuted) {
                myPlayer2.newList(MUSIC_FILE_2);
            }
        } else { //theArgument instanceof MovableTetrisPiece
            myNextPiece = ((MovableTetrisPiece) theArgument).toString();            
            firePropertyChange(NEW_PIECE, null, myNextPiece);
        }         
    } 
    
    
    /**
     * Saves the game.
     */
    public void saveGame() {
        myTimer.stop();
        myPlayer1.stopPlay();
        try (FileOutputStream fs = new FileOutputStream(SAVED_FILE_NAME)) {
            final ObjectOutputStream os = new ObjectOutputStream(fs);
            
            os.writeObject(myBoard);
            os.writeInt(myWidth);
            os.writeInt(myHeight);
            os.writeInt(myInitialDelay);
            os.writeInt(myClearedLines);
            os.writeUTF(myNextPiece);
            os.writeInt(myScore);
            
            os.close();
            myIsGameSaved = true;
            myGameOver = true;
            repaint();
            firePropertyChange(END_GAME, null, 0);
        }
        catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }        
    }
    
    
    /**
     * Starts saved game.
     */
    public void startSavedGame() {
        try (FileInputStream fi = new FileInputStream(SAVED_FILE_NAME)) {
            final ObjectInputStream os = new ObjectInputStream(fi);
            
            final Board board = (Board) os.readObject();
            final int width = os.readInt();
            final int height = os.readInt();
            final int initialDelay = os.readInt();
            final int clearedLines = os.readInt();
            final String nextPiece = (String) os.readUTF();
            final int score = os.readInt();
                            
            os.close();
            
            myBoard = board;
            myBoard.addObserver(this);
            myWidth = width;
            myHeight = height;
            myInitialDelay = initialDelay;
            myClearedLines = clearedLines;
            myNextPiece = nextPiece;
            myScore = score;
            myIsGameSaved = false;
          
            setupGame();
            firePropertyChange(NEW_PIECE, null, myNextPiece);
            
            int temp = initialDelay;
            int i = 0;
            for (temp = initialDelay; temp < TIMER_DELAY; 
                            temp = (int) (temp / TIMER_ACCELERATION)) {
                i++;
            } 
            if (i == 0) {
                firePropertyChange(CHANGE_LEVEL, null, 1);
            } else {
                firePropertyChange(CHANGE_LEVEL, null, i);
            }
            firePropertyChange(CHANGE_SCORE, null, myScore);
            repaint();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Represents an action listener for a timer.
     * @author Evgeniia Shcherbinina
     * @version 12/4/2016
     */
    private class MyTimerActionListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent theArg) {
            myBoard.down();
            if (!myPlayer1.isStarted() && !myGameOver && !myIsMuted) {
                myPlayer1.newList(MUSIC_FILES);
            }
        }        
    }

}
