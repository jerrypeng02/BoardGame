package edu.cmu.cs.cs214.hw4;

import edu.cmu.cs.cs214.hw4.gui.CarcassonneClient;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * The main program runs the whole game
 */
public class Main {
    private static final String GAME = "Carcassonne";

    /**
     * The main method runs the whole program
     *
     * @param args  program input argument
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(GAME);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            frame.add(new CarcassonneClient(frame));

            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
