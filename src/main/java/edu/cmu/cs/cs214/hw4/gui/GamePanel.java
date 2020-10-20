package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.GameChangeListener;
import edu.cmu.cs.cs214.hw4.core.Coordinate;
import edu.cmu.cs.cs214.hw4.core.Tiles;
import edu.cmu.cs.cs214.hw4.core.PositionOnTile;
import edu.cmu.cs.cs214.hw4.core.CarcassonneInterface;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Carcassonne;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.Image;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Thte main game panel for the players to interact with
 */
public class GamePanel extends JPanel implements GameChangeListener {
    private BufferedImage image;
    private BufferedImage currentImageOnStackTop;
    private Image white;
    private List<BufferedImage> imageList;
    private Map<Coordinate, BufferedImage> imageMap;
    private Coordinate lastPlacedTileCoordinate;
    private Tiles tileOnStackTop;
    private Tiles lastPlacedTile;
    private PositionOnTile position;
    private CarcassonneInterface carcassonne;
    private JLabel imageLabel;
    private JComboBox segmentOnTile;
    private JButton confirmButton;
    private int roundNum;
    private int playerNum;
    private List<Player> playerList;
    private List<JLabel> playerScore;
    private List<JLabel> playerMeeple;
    private JPanel label;
    private final JButton[][] buttons;
    private static final String TILES_IMAGE = "src/main/resources/Carcassonne.png";
    private static final String ROTATE_IMAGE = "src/main/resources/rotate.png";
    private static final String CROSS_IMAGE = "src/main/resources/cross.png";
    private static final String CHECK_IMAGE = "src/main/resources/checked.png";
    private static final String EMPTY_IMAGE = "src/main/resources/whiteSquare.png";
    private static final int ROW = 6;
    private static final int IMAGE_SIZE = 90;
    private static final int BUTTON_SIZE = 20;
    private static final int MEEPLE_RADIUS = 5;
    private static final String[] POSITION_ON_TILE = {"-", "Center", "Left", "Right", "Up", "Down"};
    private static final Color[] PLAYER_MEEPLE_COLOR = {Color.BLACK, Color.CYAN, Color.PINK, Color.RED, Color.BLUE};

    /**
     * The constructor for the game panel
     *
     * @param playerNum     the number of player that user just entered
     * @throws IOException  throw exception if cannot find the image
     */
    public GamePanel(int playerNum) throws IOException {
        this.playerNum = playerNum;
        this.imageList = new ArrayList<>();
        this.imageMap = new HashMap<>();
        this.carcassonne = new Carcassonne(playerNum);
        this.buttons = new JButton[BUTTON_SIZE][BUTTON_SIZE];
        this.image = ImageIO.read(new File(TILES_IMAGE));
        this.imageLabel = new JLabel();
        this.segmentOnTile = new JComboBox(POSITION_ON_TILE);
        this.lastPlacedTileCoordinate = null;
        this.lastPlacedTile = null;
        this.carcassonne.addGameChangeListener(this);
        this.tileOnStackTop = carcassonne.getTile();
        this.currentImageOnStackTop = getImageFromTileStack(tileOnStackTop.getTileType());
        this.roundNum = 0;
        this.playerList = carcassonne.getPlayerList();
        this.confirmButton = new JButton();
        this.playerScore = new ArrayList<>();
        this.playerMeeple = new ArrayList<>();
        this.label = new JPanel();
        try {
            this.white = ImageIO.read(new File(EMPTY_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        confirmButton.setEnabled(false);
        segmentOnTile.setEnabled(false);

        setLayout(new BorderLayout());
        add(createBoardPanel(), BorderLayout.WEST);
        add(createInformationPanel(), BorderLayout.EAST);
    }

    @Override
    public void tilePlaced(Tiles tile, Coordinate coordinate) {
        JButton button = buttons[coordinate.getY()][coordinate.getX()];
        ImageIcon icon = new ImageIcon(currentImageOnStackTop);
        button.setIcon(icon);
        segmentOnTile.setEnabled(true);
        imageList.add(currentImageOnStackTop);
        imageMap.put(coordinate, currentImageOnStackTop);
        lastPlacedTileCoordinate = coordinate;
        lastPlacedTile = tileOnStackTop;

        if(carcassonne.getRestTilesNum() > 0) {
            tileOnStackTop = carcassonne.getTile();
            currentImageOnStackTop = getImageFromTileStack(tileOnStackTop.getTileType());
            imageLabel.setIcon(new ImageIcon(currentImageOnStackTop));
        } else {
            Image whiteImage = white.getScaledInstance(90, 90, Image.SCALE_DEFAULT);
            ImageIcon whiteIcon = new ImageIcon(whiteImage);
            imageLabel.setIcon(whiteIcon);
        }
        confirmButton.setEnabled(true);
    }

    @Override
    public void tileRotated() {
        currentImageOnStackTop = rotateClockwise(currentImageOnStackTop);
        imageLabel.setIcon(new ImageIcon(currentImageOnStackTop));
    }

    @Override
    public void skip() {
        tileOnStackTop = carcassonne.getTile();
        currentImageOnStackTop = getImageFromTileStack(tileOnStackTop.getTileType());
        imageLabel.setIcon(new ImageIcon(currentImageOnStackTop));
    }

    @Override
    public void nextTurn() {
        roundNum++;
    }

    @Override
    public void returnMeeple(List<Coordinate> returnCoordinate) {
        for(Coordinate c : returnCoordinate) {
            BufferedImage returnImage = imageMap.get(c);
            JButton button = buttons[c.getY()][c.getX()];
            ImageIcon icon = new ImageIcon(returnImage);
            button.setIcon(icon);
        }
    }

    /**
     * Create a scrollable panel as the board
     *
     * @return  return a scroll pane as the board
     */
    public JScrollPane createBoardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(BUTTON_SIZE, BUTTON_SIZE));
        for(int i = 0; i < BUTTON_SIZE; i++) {
            for(int j = 0; j < BUTTON_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(IMAGE_SIZE, IMAGE_SIZE));
                int r = i;
                int c = j;
                buttons[i][j].addActionListener(e -> {
                    if(buttons[r][c].getIcon() == null) {
                        Coordinate coordinate = new Coordinate(c, r);
                        carcassonne.placeTile(tileOnStackTop, coordinate);
                    }
                });

                panel.add(buttons[i][j]);
            }
        }
        JButton centerButton = buttons[BUTTON_SIZE / 2][BUTTON_SIZE / 2];
        Tiles centerTile = carcassonne.getCenterTile();
        BufferedImage centerImage = getImageFromTileStack(centerTile.getTileType());
        centerButton.setIcon(new ImageIcon(centerImage));
        imageList.add(centerImage);
        imageMap.put(new Coordinate(BUTTON_SIZE / 2, BUTTON_SIZE / 2), centerImage);
        final JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(800, 800));
        scrollPane.setVisible(true);
        return scrollPane;
    }

    /**
     * Create the information panel for user to interact with
     *
     * @return              return a JPanel to consist game panel
     * @throws IOException  throw exception if cannot find image
     */
    public JPanel createInformationPanel() throws IOException {
        JPanel panel = new JPanel();
        ImageIcon icon = new ImageIcon(currentImageOnStackTop);
        imageLabel.setIcon(icon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);

        for(int i = 0; i < playerNum; i++) {
            JLabel score = new JLabel("Player " + i + " Points: " + playerList.get(i).getCurrentScore());
            JLabel meeple = new JLabel("Player " + i + " Meeples: " + playerList.get(i).getMeepleNumber());
            playerScore.add(score);
            playerMeeple.add(meeple);
        }
        label.setLayout(new BoxLayout(label, BoxLayout.Y_AXIS));
        for(int i = 0; i < playerScore.size(); i++) {
            label.add(playerScore.get(i));
            label.add(playerMeeple.get(i));
        }

        segmentOnTile.setSelectedIndex(0);
        segmentOnTile.addActionListener(e-> {
            if(lastPlacedTileCoordinate != null) {
                BufferedImage replaceImage = imageList.get(imageList.size() - 1);
                int row = lastPlacedTileCoordinate.getY();
                int col = lastPlacedTileCoordinate.getX();
                Player currentPlayer = playerList.get(roundNum % playerNum);
                Color currentColor = PLAYER_MEEPLE_COLOR[currentPlayer.getPlayerID()];
                switch (segmentOnTile.getSelectedIndex()) {
                    case 1:
                        position = PositionOnTile.CENTER;
                        if(lastPlacedTile.checkIfContainsMeeple(PositionOnTile.CENTER)
                                && currentPlayer.getMeepleNumber() > 0) {
                            replaceImage = withCircle(replaceImage, currentColor, 45, 45);
                        }
                        break;
                    case 2:
                        position = PositionOnTile.LEFT;
                        if(lastPlacedTile.checkIfContainsMeeple(PositionOnTile.LEFT)
                                && currentPlayer.getMeepleNumber() > 0) {
                            replaceImage = withCircle(replaceImage, currentColor, 10, 45);
                        }
                        break;
                    case 3:
                        position = PositionOnTile.RIGHT;
                        if(lastPlacedTile.checkIfContainsMeeple(PositionOnTile.RIGHT)
                                && currentPlayer.getMeepleNumber() > 0) {
                            replaceImage = withCircle(replaceImage, currentColor, 80, 45);
                        }
                        break;
                    case 4:
                        position = PositionOnTile.UP;
                        if(lastPlacedTile.checkIfContainsMeeple(PositionOnTile.UP)
                                && currentPlayer.getMeepleNumber() > 0) {
                            replaceImage = withCircle(replaceImage, currentColor, 45, 10);
                        }
                        break;
                    case 5:
                        position = PositionOnTile.DOWN;
                        if(lastPlacedTile.checkIfContainsMeeple(PositionOnTile.DOWN)
                                && currentPlayer.getMeepleNumber() > 0) {
                            replaceImage = withCircle(replaceImage, currentColor, 45, 80);
                        }
                        break;
                    default:
                        position = null;
                }
                buttons[row][col].setIcon(new ImageIcon(replaceImage));
            }
        });
        label.add(segmentOnTile);


        JButton rotateButton = new JButton();
        Image rotate = ImageIO.read(new File(ROTATE_IMAGE));
        Image rotateImage = rotate.getScaledInstance(90, 90, Image.SCALE_DEFAULT);
        ImageIcon rotateIcon = new ImageIcon(rotateImage);
        rotateButton.setIcon(rotateIcon);
        rotateButton.addActionListener(e -> carcassonne.rotateTile(tileOnStackTop));

        JButton abandonButton = new JButton();
        Image cross = ImageIO.read(new File(CROSS_IMAGE));
        Image crossImage = cross.getScaledInstance(90, 90, Image.SCALE_DEFAULT);
        ImageIcon crossIcon = new ImageIcon(crossImage);
        abandonButton.setIcon(crossIcon);
        abandonButton.addActionListener(e -> carcassonne.skip());

        Image check = ImageIO.read(new File(CHECK_IMAGE));
        Image checkImage = check.getScaledInstance(90, 90, Image.SCALE_DEFAULT);
        ImageIcon checkIcon = new ImageIcon(checkImage);
        confirmButton.setIcon(checkIcon);
        confirmButton.addActionListener(e -> {
            if(position != null && lastPlacedTile.checkIfContainsMeeple(position)) {
                carcassonne.placeMeeple(lastPlacedTile, position);
            }
            carcassonne.scoringCompletion(lastPlacedTile);
            update();
            segmentOnTile.setEnabled(false);
            confirmButton.setEnabled(false);
            if(carcassonne.getRestTilesNum() > 0) {
                carcassonne.nextTurn();
            } else {
                carcassonne.scoringFinal();
                int maxScore = Integer.MIN_VALUE;
                int winnerIndex = -1;
                for(Player player : playerList) {
                    if(player.getCurrentScore() > maxScore) {
                        maxScore = player.getCurrentScore();
                        winnerIndex = player.getPlayerID();
                    }
                }
                JFrame frame = (JFrame) SwingUtilities.getRoot(this);
                showDialog(frame, "Player " + winnerIndex + " won the game for score of " + maxScore + " points");
            }

        });

        JPanel userButtons = new JPanel();
        userButtons.setLayout(new BorderLayout());
        userButtons.add(confirmButton, BorderLayout.NORTH);
        userButtons.add(rotateButton, BorderLayout.CENTER);
        userButtons.add(abandonButton, BorderLayout.SOUTH);

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panel.add(label, BorderLayout.NORTH);
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(userButtons, BorderLayout.SOUTH);
        panel.setVisible(true);
        return panel;
    }

    private void showDialog(Component component, String message) {
        JOptionPane.showMessageDialog(component, message, "Winner!", JOptionPane.INFORMATION_MESSAGE);
    }

    private void update() {
        for(Player player : playerList) {
            int index = player.getPlayerID();
            playerScore.get(index).setText("Player " + index + " Points: " +
                    this.playerList.get(index).getCurrentScore());
            playerMeeple.get(index).setText("Player " + index + " Meeples: " +
                    this.playerList.get(index).getMeepleNumber());
        }
        label.setLayout(new BoxLayout(label, BoxLayout.Y_AXIS));
        for(int i = 0; i < playerNum; i++) {
            label.add(playerScore.get(i));
            label.add(playerMeeple.get(i));
        }
        label.add(segmentOnTile);
    }

    private BufferedImage rotateClockwise(BufferedImage src) {
        int weight = src.getWidth();
        int height = src.getHeight();

        AffineTransform at = AffineTransform.getQuadrantRotateInstance(1, weight / 2.0, height / 2.0);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage dest = new BufferedImage(weight, height, src.getType());
        op.filter(src, dest);
        return dest;
    }

    private BufferedImage getImageFromTileStack(int tileType) {
        int x = tileType % ROW;
        int y = tileType / ROW;
        return image.getSubimage(x * IMAGE_SIZE, y * IMAGE_SIZE, IMAGE_SIZE, IMAGE_SIZE);
    }

    private BufferedImage withCircle(BufferedImage src, Color color, int x, int y) {
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());

        Graphics2D g = (Graphics2D) dest.getGraphics();
        g.drawImage(src, 0, 0, null);
        g.setColor(color);
        g.fillOval(x - GamePanel.MEEPLE_RADIUS, y - GamePanel.MEEPLE_RADIUS, 2 * GamePanel.MEEPLE_RADIUS, 2 * GamePanel.MEEPLE_RADIUS);
        g.dispose();

        return dest;
    }
}
