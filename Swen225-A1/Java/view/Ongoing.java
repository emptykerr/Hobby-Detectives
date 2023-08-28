package view;
import controller.ChecklistPanel;
import controller.LoadSave;
import main.*;

import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Ongoing extends JFrame {

    public JFrame jframe;
    private JPanel mainPanel;
    private JLabel currentPlayerNameDisplay;
    private JLabel displayMessage;
    private JPanel currentPlayerHand;
    private JLabel movesLeft = new JLabel();
    private JPanel playerPanel = new JPanel();
    private JPanel infoPanel = new JPanel();

    private ChecklistPanel currentPlayerChecklistPanel;
    HobbyDetectives game;
    public Ongoing (HobbyDetectives game){
        super();
        this.game = game;

        setJMenuBar(createMenuBar());

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
              }

              @Override
              public void windowGainedFocus(WindowEvent e) {
              }
        });

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

    /**
     * Creates and returns a custom menu bar for the game user interface.
     *
     * @return The JMenuBar instance containing File and Game menus.
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setToolTipText("Exit the game");
        exitMenuItem.addActionListener((event) -> System.exit(0));

        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartMenuItem = new JMenuItem("Restart");

        restartMenuItem.setToolTipText("Restart the game");
        gameMenu.add(restartMenuItem);
        menuBar.add(gameMenu);

        return menuBar;
    }

    /**
     * sets up the entire ongoing state UI, also has the keys to make characters move and puts
     * them into maps.
     */
    private void initUI() {
        jframe.setLayout(new BorderLayout());

        JPanel background = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.drawImage((LoadSave.getSpriteAtlas(LoadSave.ONGOING_WALLPAPER)).getScaledInstance(1920, 1080, Image.SCALE_SMOOTH),  0, 0, this);
                repaint();
            }
        };

        background.setPreferredSize(new Dimension(1920, 1080));

//         jframe.setContentPane(background);


        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Checklist"), BorderFactory.createEmptyBorder(0, 0, 50, 50)));
        infoPanel.add(game.getCurrentPlayer().getChecklist());


        JLabel playerName = new JLabel("Name");
        playerPanel.add(playerName);


        mainPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                drawGrid(this, g);
            }
        };

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                int[] coords = pixelCoordToCoord(x, y, mainPanel);
                int squareX = coords[0];
                int squareY = coords[1];
                if (squareX >= 0 && squareX < Board.getLength() && squareY >=0 && squareY < Board.getLength()){
                    printToUI(Board.getSquare(squareX, squareY).toString());
                }
            }
        });

        InputMap inputMap = mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = mainPanel.getActionMap();

    // Arrow keys
        KeyStroke upKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        inputMap.put(upKey, "up");
        actionMap.put("up", new MoveAction('w'));

        KeyStroke downKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        inputMap.put(downKey, "down");
        actionMap.put("down", new MoveAction('s'));

        KeyStroke leftKey = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
        inputMap.put(leftKey, "left");
        actionMap.put("left", new MoveAction('a'));

        KeyStroke rightKey = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
        inputMap.put(rightKey, "right");
        actionMap.put("right", new MoveAction('d'));

    // WASD keys
        KeyStroke wKey = KeyStroke.getKeyStroke(KeyEvent.VK_W, 0);
        inputMap.put(wKey, "w");
        actionMap.put("w", new MoveAction('w'));

        KeyStroke sKey = KeyStroke.getKeyStroke(KeyEvent.VK_S, 0);
        inputMap.put(sKey, "s");
        actionMap.put("s", new MoveAction('s'));

        KeyStroke aKey = KeyStroke.getKeyStroke(KeyEvent.VK_A, 0);
        inputMap.put(aKey, "a");
        actionMap.put("a", new MoveAction('a'));

        KeyStroke dKey = KeyStroke.getKeyStroke(KeyEvent.VK_D, 0);
        inputMap.put(dKey, "d");
        actionMap.put("d", new MoveAction('d'));


        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        jframe.add(mainPanel, BorderLayout.CENTER);
        jframe.add(createPlayerPanel(), BorderLayout.WEST);
        jframe.add(infoPanel, BorderLayout.EAST);
    }

    /**
     * A class representing a move action in a game.
     * This action is used to handle player movement based on the specified direction.
     * The direction can be any valid character representing a movement direction.
     */
    class MoveAction extends AbstractAction {
        private char direction;

        MoveAction(char direction) {
            this.direction = java.lang.Character.toUpperCase(direction);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Player currentPlayer = game.getCurrentPlayer();
            if (currentPlayer.getCharacter().getSquare().getEstate() != null) {
                currentPlayer.moveOutOfEstate(direction);
            } else {
                currentPlayer.doBoardMove(direction);
            }

            displayMessage.setText(game.getCurrentPlayer().getActionPerformed());
            movesLeft.setText("Moves left: " + game.getCurrentPlayer().getMoves());
            repaint();

        }
    }

    /**
     * Given a message, prints this to a dialouge box
     * @param message to print
     */
    public void printToUI(String message){
        JOptionPane.showMessageDialog(null, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Makes a dropdown menu for user to chose from selection of cards
     * @param options the cards to be chosen from
     * @return the chosen card
     */
    public String askCardDropdown(Object[] options){
        JComboBox cb = new JComboBox(options);

        int input;
        input = JOptionPane.showConfirmDialog(this, cb, "Select card", JOptionPane.DEFAULT_OPTION);

        if(input == JOptionPane.OK_OPTION){
            String result = (String) cb.getSelectedItem();
            return result;
        }
        return null;
    }

    /**
     * draws the grid with the estates and players in the correct places
     * @param panel the panel which the grid is drawn in
     * @param g graphics to draw onto the UI
     */
    private void drawGrid(JPanel panel, Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        int height = panel.getHeight();
        int width = panel.getWidth();

        int widthSize = (int) (width / (Board.getLength() + 6));
        int heightSize = (int) (height / (Board.getLength() + 6));
        
        int clampedValue = Math.max(0, Math.min(widthSize, heightSize));

        int distanceFromLeftBorder = width/2 - (clampedValue*Board.getLength()/2);
        int distanceFromTopBorder = height/2 - (clampedValue*Board.getLength()/2);


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
                if (square.getWeapon() != null) {
                   drawWeapon(mainPanel,g, clampedValue,  square.getWeapon().getName(), square.getEstate(),  x * clampedValue + distanceFromLeftBorder, y * clampedValue + distanceFromTopBorder);
                }
            }
        }
        g2d.setStroke(new BasicStroke(10));
        g2d.drawRect(-5 + distanceFromLeftBorder, -5 + distanceFromTopBorder, (Board.getLength() * clampedValue) + 5, (Board.getLength() * clampedValue) + 5);
        repaint();
    }

//    /**
//     * Converts game coordinates to corresponding GUI pixel coordinates
//     * @param panel main game panel
//     * @return the corresponding pixel coordinates ([0] is x [1] is y)
//     */
//    public int[] coordToPixelCoord(int x, int y, JPanel panel){
//        int height = panel.getHeight();
//        int width = panel.getWidth();
//
//        int size = (int) (height / (Board.getLength() + 6));
//        int distanceFromLeftBorder = width/2 - (size*Board.getLength()/2);
//        int distanceFromTopBorder = height/2 - (size*Board.getLength()/2);
//
//        int pixelX = distanceFromLeftBorder + x*size;
//        int pixelY = distanceFromTopBorder + y*size;
//
//        int[] pixelCoords = new int[2];
//        pixelCoords[0] = pixelX;
//        pixelCoords[1] = pixelY;
//
//        return pixelCoords;
//    }

    /**
     * Converts GUI pixel coordinates to game coordinates
     * @param panel main game panel
     * @return the corresponding game coordinates ([0] is x [1] is y)
     */
    public int[] pixelCoordToCoord(int pixelX, int pixelY, JPanel panel){
        int height = panel.getHeight();
        int width = panel.getWidth();

        int widthSize = (int) (width / (Board.getLength() + 6));
        int heightSize = (int) (height / (Board.getLength() + 6));

        int size = Math.max(0, Math.min(widthSize, heightSize));

        int distanceFromLeftBorder = width/2 - (size*Board.getLength()/2);
        int distanceFromTopBorder = height/2 - (size*Board.getLength()/2);

        int x = (int) (pixelX - distanceFromLeftBorder)/size;
        int y =(int) (pixelY - distanceFromTopBorder)/size;

        int[] coords = new int[2];
        coords[0] = x ;
        coords[1] = y ;

        return coords;
    }

    /**
     * Creates a panel representing the current player's information and actions.
     *
     * @return A JPanel containing information about the current player and their actions.
     */
    private JPanel createPlayerPanel() {
        JPanel wholePanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        updateChecklistPanelItems(game.getCurrentPlayer());

        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.weightx = 0.15;

        //player name display
        this.currentPlayerNameDisplay = new JLabel("Current Player: " + game.getCurrentPlayer().getName(), JLabel.CENTER);
        this.currentPlayerNameDisplay.setFont(currentPlayerNameDisplay.getFont().deriveFont(20.0f));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.20;
        leftPanel.add(currentPlayerNameDisplay, constraints);

        //Current game state description
        this.displayMessage = new JLabel(game.getCurrentPlayer().getActionPerformed());
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.35;
        leftPanel.add(this.displayMessage, constraints);

        //Player actions label
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.weighty = 0.1;
        leftPanel.add(new JLabel("Use W, A, S and D to move (note: you need to have rolled the dice first!)"), constraints);
        JPanel playerActions = new JPanel(new FlowLayout());

        JButton endTurn = new JButton("End Turn");
        endTurn.addActionListener(l -> {
            game.doEndTurn();
            currentPlayerNameDisplay.setText("Current Player: " + game.getCurrentPlayer().getName());
            movesLeft.setText("Moves left: " + game.getCurrentPlayer().getMoves());
            displayMessage.setText("");
            updateChecklistPanelItems(game.getCurrentPlayer());
            infoPanel.updateUI();

            wholePanel.repaint();
        });
        JButton rollButton = new JButton("Roll Dice");
        rollButton.addActionListener(l -> {
            game.getCurrentPlayer().doDieRoll();
            movesLeft.setText("Moves left: " + game.getCurrentPlayer().getMoves());
            repaint();
        });
        JButton guessButton = new JButton("Guess");
        guessButton.addActionListener(l -> game.getCurrentPlayer().doGuess());
        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(l -> game.getCurrentPlayer().doSolveAttempt());

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.weighty = 0.2;
        playerActions.add(endTurn);
        playerActions.add(rollButton);
        playerActions.add(guessButton);
        playerActions.add(solveButton);

        leftPanel.add(playerActions, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.weighty = 0.2;
        movesLeft = new JLabel(("Moves left: " + game.getCurrentPlayer().getMoves()));
        leftPanel.add(movesLeft);

        //Player Cards
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridheight = 1;
        constraints.weighty = 1.0;
        leftPanel.add(new JPanel());
        constraints.gridx = 0;
        constraints.gridy = 5;
        leftPanel.add(createCardPanel(game), constraints);

        JPanel avatar = new JPanel() {
            @Override
            public void paint(Graphics g) {
                drawImages(this, leftPanel, g);
            }
        };

        avatar.setPreferredSize(new Dimension(420, 420));

        wholePanel.add(leftPanel, BorderLayout.SOUTH);
        wholePanel.add(avatar, BorderLayout.NORTH);

        this.repaint();
        return wholePanel;
    }


    /**
     * Draws images on the specified panels using the provided Graphics object.
     *
     * @param avatarPanel The panel where the avatar image will be drawn.
     * @param cardPanel The panel where the card image will be drawn.
     * @param g The Graphics object used for drawing.
     */
    public void drawImages(JPanel avatarPanel, JPanel cardPanel, Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        int avatarSize = (int)(cardPanel.getWidth() * (3.0/4.0));

        g2d.drawImage((LoadSave.getSpriteAtlas(game.currentTurn.name()+".png")).getScaledInstance(avatarSize, avatarSize, Image.SCALE_SMOOTH), (cardPanel.getWidth() / 2 )- (avatarSize / 2), jframe.getHeight()/20, this);
        repaint();
    }

    public void drawWeapon(JPanel panel, Graphics g, int size, String weapon, Estate e, int x, int y){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        int weaponSize = size;

        g2d.drawImage((LoadSave.getSpriteAtlas(weapon +".png")).getScaledInstance(weaponSize, weaponSize, Image.SCALE_SMOOTH),
                x ,
                y ,  this);
        repaint();
    }
    /**
     * Draws current players cards in their hand on the screen
     * @param panel to draw cards in
     * @param g
     */
    public void drawCards(JPanel panel, Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        Player currentPlayer = HobbyDetectives.getPlayers().get(game.getCurrentPlayerIndex());
        ArrayList<Card> cards = (ArrayList<Card>) currentPlayer.getHand();

        int cardSize = (int) (panel.getWidth() * (1.0/(cards.size() +1)));
        int cardGap = (int) (panel.getWidth() * ((1.0/(cards.size() + 1))/(cards.size() )));

        for(int i = 0; i < cards.size(); i++){
            g2d.drawImage(LoadSave.getSpriteAtlas((cards.get(i).getCardName().toLowerCase() + ".png")).getScaledInstance(cardSize, cardSize, Image.SCALE_SMOOTH),
                    panel.getX() + ((panel.getWidth()/4) * i) + ((cardGap+1)*(i+1)), panel.getY(), this);

            String cardName = cards.get(i).getCardName();
            g2d.setFont(new Font("Arial", Font.BOLD, 15));
            g2d.drawString(cardName, panel.getX() + ((panel.getWidth() / 4) * i) + ((cardGap + 1) * (i + 1)), panel.getY() + cardSize + 15);
        }
    }

    /**
     * Updates the grid so that any changes can be seen
     */
    public void updateGrid() {
        drawGrid(mainPanel, mainPanel.getGraphics());
    }

    /**
     * Creates a panel displaying the cards in the current player's hand.
     *
     * @param game The instance of the HobbyDetectives game.
     * @return A JPanel displaying the cards in the current player's hand.
     */
    private JPanel createCardPanel(HobbyDetectives game) {

        JPanel cardPanel = new JPanel(new BorderLayout()) {
            @Override
            public void paint(Graphics g) {
                drawCards(this, g);
            }
        };
        cardPanel.setPreferredSize(new Dimension(jframe.getWidth()/4, jframe.getHeight()/4));


        JPanel labelPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Cards in hand:");
        label.setFont(label.getFont().deriveFont(20.0f));
        labelPanel.add(label);
        cardPanel.add(labelPanel, BorderLayout.NORTH);

        this.currentPlayerHand = new JPanel(new FlowLayout());
        cardPanel.add(currentPlayerHand, BorderLayout.SOUTH);
        repaint();
        return cardPanel;
    }

    private void updateChecklistPanelItems(Player currentPlayer){
        if(currentPlayerChecklistPanel !=null) {
            infoPanel.remove(currentPlayerChecklistPanel);
        }
        currentPlayerChecklistPanel = (currentPlayer.getChecklist());
        infoPanel.add(currentPlayerChecklistPanel);
        infoPanel.revalidate();
        infoPanel.repaint();
    }
}
