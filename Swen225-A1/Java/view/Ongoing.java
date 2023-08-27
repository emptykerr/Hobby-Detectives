package view;
import controller.LoadSave;
import main.*;

import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;

public class Ongoing extends JFrame {

    public int backgroundWidth, backgroundHeight;

    public JFrame jframe;
    private JPanel mainPanel;
    private JLabel currentPlayerNameDisplay;
    private JTextArea displayMessage;
    private JPanel currentPlayerHand;
    private static int CARD_WIDTH = 150;
    private static int CARD_HEIGHT = 150;

    private JPanel toolbar = new JPanel();
    private JPanel playerPanel = new JPanel();
    private JPanel infoPanel = new JPanel();
    private JLabel infoText = new JLabel("Info");

    HobbyDetectives game;
    public Ongoing (HobbyDetectives game){
        super();
        this.game = game;

//        jframe = game.gameWindow.jframe;
        jframe = new JFrame();
        jframe.setTitle("Hobby Detectives - Playing");

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension size = new Dimension(1920, 1080);
        jframe.setPreferredSize(size);
        jframe.setResizable(true);
        jframe.pack();
        jframe.setLocationRelativeTo(null);

        jframe.setVisible(true);
        jframe.addWindowFocusListener(new WindowFocusListener() {

                                          @Override
                                          public void windowLostFocus(WindowEvent e) {
//                                              game.gamePanel.getGame().windowFocusLost();
                                          }

                                          @Override
                                          public void windowGainedFocus(WindowEvent e) {
                                          }
                                      }
        );

        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // default icon, custom title
                int n = JOptionPane.showConfirmDialog(new JFrame(), "Are you sure you want to exit the game?",
                        "Warning", JOptionPane.YES_NO_OPTION);
                // Yes
                if (n == 0)
                    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // No
                else {
                    jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });


        initUI();
    }

    private void initUI(){
        jframe.setLayout(new BorderLayout());

        infoPanel.add(infoText);


        JLabel playerName = new JLabel("Name");
        playerPanel.add(playerName);

        toolbar.setLayout(new FlowLayout());

//        JButton rollButton = new JButton("Roll Dice");
//        toolbar.add(rollButton);
//        JButton guessButton = new JButton("Guess");
//        toolbar.add(guessButton);
//        JButton solveButton = new JButton("Solve");
//        toolbar.add(solveButton);



         mainPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                drawGrid(this, g);
            }
        };

//        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        jframe.add(toolbar, BorderLayout.SOUTH);
        jframe.add(mainPanel, BorderLayout.CENTER);
        jframe.add(createPlayerPanel(), BorderLayout.WEST);
        jframe.add(infoPanel, BorderLayout.EAST);


    }

    public void printToUI(String message){
        JOptionPane.showConfirmDialog(null, message);
    }

    public boolean askSolveAttempt(main.Player player){
        int response = JOptionPane.showConfirmDialog(null, "Would you like to make a solve attempt " + player.getName() + "?", "Solve Attempt", JOptionPane.YES_NO_OPTION);
        if (response == 0 ) { return true; }
        else return false;
    }

    public boolean askLeaveEstate(main.Player player){
        int response = JOptionPane.showConfirmDialog(null, "Would you like to leave the estate " + player.getName() + "?", "Leave Estate", JOptionPane.YES_NO_OPTION);
        if (response == 0 ) { return true; }
        else return false;
    }

    public String askDoorDirection(main.Player player) {
        JPanel getDoorDirectionPanel = new JPanel();
        JPanel enterDirectionLabelPanel = new JPanel();
        JTextField doorDirection = new JTextField();

        enterDirectionLabelPanel.setLayout(new BorderLayout());
        enterDirectionLabelPanel.add(new JLabel("Enter door direction (U, D, L, or R): "), BorderLayout.WEST);
        getDoorDirectionPanel.add(enterDirectionLabelPanel);
        getDoorDirectionPanel.add(doorDirection);

        return doorDirection.getText();
    }


    private void drawGrid(JPanel panel, Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        //Color customColor = new Color(209, 192, 168);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println(pixelCoordToCoord(e.getX(), e.getY(), panel)[1]);
            }
        });

        int height = panel.getHeight();
        int width = panel.getWidth();
        System.out.println(width);
        System.out.println(height);

        int widthSize = (int) (width / (Board.getLength() + 6));
        int heightSize = (int) (height / (Board.getLength() + 6));
        
        int clampedValue = Math.max(0, Math.min(widthSize, heightSize));

        int distanceFromLeftBorder = width/2 - (clampedValue*Board.getLength()/2);
        int distanceFromTopBorder = height/2 - (clampedValue*Board.getLength()/2);

        System.out.println(clampedValue);

        for(int y = 0; y < Board.getLength(); y++){
            for(int x = 0; x < Board.getLength(); x++) {
                Square square = Board.getSquare(x, y);
                g2d.setColor(square.draw());
                if (square.getCharacter() != null) {
                    g2d.setColor(new Color(209, 192, 168));
                    g2d.fillRect( clampedValue * x + distanceFromLeftBorder, clampedValue * y + distanceFromTopBorder, clampedValue, clampedValue);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(clampedValue * x + distanceFromLeftBorder, clampedValue * y + distanceFromTopBorder, clampedValue, clampedValue);

                    g2d.setColor(square.draw());
                    g2d.fillOval( clampedValue * x + distanceFromLeftBorder, clampedValue * y + distanceFromTopBorder, clampedValue, clampedValue);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(clampedValue * x + distanceFromLeftBorder, clampedValue * y + distanceFromTopBorder, clampedValue, clampedValue);
                } else {
                    g2d.fillRect( clampedValue * x + distanceFromLeftBorder, clampedValue * y + distanceFromTopBorder, clampedValue, clampedValue);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(clampedValue * x + distanceFromLeftBorder, clampedValue * y + distanceFromTopBorder, clampedValue, clampedValue);
                }

            }
        }
    }

    /**
     * Converts game coordinates to coorosponding GUI pixel coordinates
     * @param panel main game panel
     * @return the corresponding pixel coordinates ([0] is x [1] is y)
     */
    public int[] coordToPixelCoord(int x, int y, JPanel panel){
        int height = panel.getHeight();
        int width = panel.getWidth();

        int size = (int) (height / (Board.getLength() + 6));
        int distanceFromLeftBorder = width/2 - (size*Board.getLength()/2);
        int distanceFromTopBorder = height/2 - (size*Board.getLength()/2);

        int pixelX = distanceFromLeftBorder + x*size;
        int pixelY = distanceFromTopBorder + y*size;

        int[] pixelCoords = new int[2];
        pixelCoords[0] = pixelX;
        pixelCoords[1] = pixelY;

        return pixelCoords;
    }

    /**
     * Converts GUI pixel coordinates to game coordinates
     * @param panel main game panel
     * @return the corosponding game coordinates ([0] is x [1] is y)
     */
    public int[] pixelCoordToCoord(int pixelX, int pixelY, JPanel panel){
        int height = panel.getHeight();
        int width = panel.getWidth();

        int size = (int) (height / (Board.getLength() + 6));
        int distanceFromLeftBorder = width/2 - (size*Board.getLength()/2);
        int distanceFromTopBorder = height/2 - (size*Board.getLength()/2);

        int x = (int) (pixelX - distanceFromLeftBorder)/size;
        int y =(int) (pixelY - distanceFromTopBorder)/size;

        int[] coords = new int[2];
        coords[0] = x ;
        coords[1] = y ;

        return coords;
    }


    private JPanel createPlayerPanel() {
        JPanel wholePanel = new JPanel(new BorderLayout());


        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel avatar = new JPanel(new GridBagLayout());

        avatar = new JPanel() {
            @Override
            public void paint(Graphics g) {
                drawAvatar(this, g);
            }
        };

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.weightx = 0.1;

        //player name display
        this.currentPlayerNameDisplay = new JLabel("Current Player " + game.currentTurn, JLabel.CENTER);
        this.currentPlayerNameDisplay.setFont(currentPlayerNameDisplay.getFont().deriveFont(15.0f));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.15;
        leftPanel.add(currentPlayerNameDisplay, constraints);




        //Current game state description
        this.displayMessage = new JTextArea("Game start");
        this.displayMessage.setEditable(false);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weighty = 0.35;
        leftPanel.add(this.displayMessage, constraints);

        //Player actions label
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 0.1;
        leftPanel.add(new JLabel("Player Actions"), constraints);

        //Player Cards
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridheight = 1;
        constraints.weighty = 1.0;
        leftPanel.add(new JPanel());
        constraints.gridx = 0;
        constraints.gridy = 4;
        leftPanel.add(createCardPanel(game), constraints);
        leftPanel.add(createCardPanel(game), constraints);
        avatar.add(createCardPanel(game), constraints);


        wholePanel.add(leftPanel, BorderLayout.SOUTH);
        wholePanel.add(avatar, BorderLayout.NORTH);


        this.repaint();
        return wholePanel;
    }


    public void drawAvatar(JPanel panel, Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        int cardSize = panel.getWidth() * (3/4);
//        if(game.currentTurn.name() != null) {
//            g2d.drawImage((LoadSave.getSpriteAtlas("/resources/" + game.currentTurn.name() + ".png")).getScaledInstance(cardSize, cardSize, Image.SCALE_SMOOTH), panel.getWidth() / 2 - cardSize / 2, 50, this);
//        }

    }


    private JPanel createCardPanel(HobbyDetectives game) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setPreferredSize(new Dimension(3 * CARD_WIDTH + 50, 3 * CARD_HEIGHT));

        JPanel labelPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Cards in hand:");
        label.setFont(label.getFont().deriveFont(20.0f));
        labelPanel.add(label);
        cardPanel.add(labelPanel, BorderLayout.NORTH);

        this.currentPlayerHand = new JPanel(new FlowLayout());
        cardPanel.add(currentPlayerHand, BorderLayout.CENTER);
        this.repaint();
        return cardPanel;
    }
}
