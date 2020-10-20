package edu.cmu.cs.cs214.hw4.gui;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.io.IOException;

/**
 *  The Carcassonne client interface start with enabling user to enter the number of players to the game
 */
public class CarcassonneClient extends JPanel {
    private JFrame parentFrame;

    /**
     * The constructor for entering the number of players for the game
     *
     * @param frame the frame to start with the panel
     */
    public CarcassonneClient(JFrame frame) {
        this.parentFrame = frame;

        JLabel participantNumberLabel = new JLabel("Number of player (2 to 5): ");

        final JTextField numberText = new JTextField(10);

        JButton startButton = new JButton("Start Carcassonne!");
        JPanel addPlayerPanel = new JPanel();
        addPlayerPanel.setLayout(new BorderLayout());
        addPlayerPanel.add(participantNumberLabel, BorderLayout.WEST);
        addPlayerPanel.add(numberText, BorderLayout.CENTER);
        addPlayerPanel.add(startButton, BorderLayout.EAST);

        startButton.addActionListener(e -> {
            String input = numberText.getText().trim();
            if(!input.isEmpty()) {
                int playerNum = Integer.parseInt(numberText.getText().trim());
                if(playerNum >= 2 && playerNum <= 5) {
                    try {
                        startGame(playerNum);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        setLayout(new BorderLayout());
        add(addPlayerPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    private void startGame(int playerNum) throws IOException {
        parentFrame.dispose();
        parentFrame = null;

        JFrame frame = new JFrame("Game Board");
        GamePanel gamePanel = new GamePanel(playerNum);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(gamePanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
