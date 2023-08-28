package view;

import main.HobbyDetectives;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

public class Menu extends JFrame {
    public static final Color BUTTON_COLOUR = new Color(231,175,95);
    
    private HobbyDetectives game;
    private final String[] okOption = {"Okay"};

    private boolean charactersChosen = false;
    private int chosenAmountOfPlayers = 4;

    public Menu(){
        setup();
    }
    /**
     * Sets up the Hobby Detectives game by creating a new instance and initializing the user interface.
     */
    public void setup(){
        this.game = new HobbyDetectives(this);
        initUI(game);
        game.setup();
    }

    /**
     * Initializes the user interface for the Hobby Detectives game.
     *
     * @param game The instance of the HobbyDetectives game.
     */
    private void initUI(HobbyDetectives game){
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        setJMenuBar(createMenuBar());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave the game?", "Closing Hobby Detectives", JOptionPane.YES_NO_OPTION);
                if (response == 0) { System.exit(0); }
            }
        });

        JPanel titlePanel = new JPanel();
        JPanel text = new JPanel();
        JLabel welcome = new JLabel("Welcome to");
        JLabel title = new JLabel("HOBBY DETECTIVES");
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        title.setFont(title.getFont().deriveFont(50.0f));
        welcome.setFont(welcome.getFont().deriveFont(30.0f));
        text.add(welcome);
        text.add(title);

        titlePanel.add(text);
        add(titlePanel, BorderLayout.NORTH);

        JPanel playerCount = new JPanel();
        JLabel statement = new JLabel("Choose the amount of players: ");
        JRadioButton numOfPlayers3 = new JRadioButton("3");
        JRadioButton numOfPlayers4 = new JRadioButton("4");


        ButtonGroup playerCountOptions = new ButtonGroup();


        playerCountOptions.add(numOfPlayers3);
        playerCountOptions.add(numOfPlayers4);
        numOfPlayers4.setSelected(true);

        playerCount.add(statement);
        playerCount.add(numOfPlayers3);
        playerCount.add(numOfPlayers4);

        add(playerCount, BorderLayout.CENTER);



        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());

        JButton playButton = new JButton("Play");
        JButton quitButton = new JButton("Quit");

        playButton.setBackground(BUTTON_COLOUR);
        playButton.addActionListener(l -> {
            chosenAmountOfPlayers = numOfPlayers4.isSelected() ? 4 : 3;
            selectCharacters();
        });

        quitButton.setBackground(BUTTON_COLOUR);
        quitButton.addActionListener(l -> {
            game.state = HobbyDetectives.GameState.QUIT;
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave the game?", "Closing Hobby Detectives", JOptionPane.YES_NO_OPTION);
            if (response == 0) { System.exit(0); }
        });

        buttons.add(playButton);
        buttons.add(quitButton);

        add(buttons, BorderLayout.SOUTH);

        setResizable(true);
        pack();
        setVisible(true);
    }

    /**
     * sets up the box to choose which characters are wanting to play and lets you
     * give them a name for the real players
     */
    private void selectCharacters() {

        JPanel playerDetailsPanel = new JPanel();
        JPanel selectCharLabelPanel = new JPanel();
        JPanel enterNameLabelPanel = new JPanel();
        JPanel characterPanel = new JPanel();
        JTextField playerName = new JTextField();

        ButtonGroup playerSelect = new ButtonGroup();
        Set<JRadioButton> characterSet = new HashSet<JRadioButton>();

        //set up the character selection popup
        playerDetailsPanel.setLayout(new BoxLayout(playerDetailsPanel, BoxLayout.PAGE_AXIS));
        selectCharLabelPanel.setLayout(new BorderLayout());
        selectCharLabelPanel.add(new JLabel("Select a character to play: "), BorderLayout.WEST);
        playerDetailsPanel.add(selectCharLabelPanel);

        for (HobbyDetectives.PlayerName p : HobbyDetectives.PlayerName.values()) {
            JRadioButton characterRadioButton = new JRadioButton(p.name());
            playerSelect.add(characterRadioButton);
            characterSet.add(characterRadioButton);
            characterPanel.add(characterRadioButton);
        }

        playerDetailsPanel.add(characterPanel);
        enterNameLabelPanel.setLayout(new BorderLayout());
        enterNameLabelPanel.add(new JLabel("Enter your name: "), BorderLayout.WEST);
        playerDetailsPanel.add(enterNameLabelPanel);
        playerDetailsPanel.add(playerName);

        //ask the players for their name and the character they pick
        String name;
        String characterName;
        boolean success = false;

        for (int i = 1; i < chosenAmountOfPlayers + 1; i++) {
            setCharactersChosen(false);
            JRadioButton select = null;

            while (!isCharactersChosen()) {
                name = null;
                characterName = null;
                playerName.setText("");
                playerSelect.clearSelection();

                while ((playerName == null || characterName == null)) {
                    JOptionPane.showOptionDialog(null, playerDetailsPanel, "Player " + i + " Character Selection", JOptionPane.PLAIN_MESSAGE, JOptionPane.QUESTION_MESSAGE, null, okOption, okOption[0]);
                    name = playerName.getText();
                    for (JRadioButton button : characterSet) {
                        if (button.isSelected() && name.length() > 0) {
                            characterName = button.getText();
                            select = button;

                        }
                    }
                }

                game.addPlayer(name, characterName);
                setCharactersChosen(true);
            }
            select.setEnabled(false);
        }

        if (game.getPlayerCount() == chosenAmountOfPlayers) {
            setCharactersChosen(true);
            game.setPlayerCount(chosenAmountOfPlayers);
            game.distributeCards();
            game.state = HobbyDetectives.GameState.ONGOING;
            dispose();
            game.initialTurnSetup();
            Ongoing ongoingView = new Ongoing(game);
            game.setOngoingView(ongoingView);
        }
    }

    /**
     * Sets the flag indicating whether characters have been chosen in the game.
     *
     * @param b {@code true} if characters have been chosen, {@code false} otherwise.
     */
    public void setCharactersChosen(boolean b){
        charactersChosen = b;
    }

    /**
     * Checks whether characters have been chosen in the game.
     *
     * @return {@code true} if characters have been chosen, {@code false} otherwise.
     */
    public boolean isCharactersChosen(){
        return charactersChosen;
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
}
