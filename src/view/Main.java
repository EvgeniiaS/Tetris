/*
 * TCSS 305
 * Assignment 6B - Tetris
 */

package view;

import com.sun.media.codec.audio.mp3.JavaDecoder;

import java.awt.EventQueue;

import javax.media.Codec;
import javax.media.PlugInManager;


/**
 * Runs Tetris game.
 * @author Evgeniia Shcherbinina
 * @version 12/9/2016
 */
public final class Main {
   
    /**
     * Private constructor prevents instantiation of this class.
     */
    private Main() {
        // do nothing
    }

    /**
     * The main method, invokes the Tetris GUI. Command line arguments are ignored.     
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().start();
            }
        });
        
        final Codec c = new JavaDecoder();
        PlugInManager.addPlugIn("com.sun.media.codec.audio.mp3.JavaDecoder",
                                c.getSupportedInputFormats(),
                                c.getSupportedOutputFormats(null),
                                PlugInManager.CODEC);
    }    

}
