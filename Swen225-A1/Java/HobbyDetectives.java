import java.util.*;

public class HobbyDetectives {
    private static Guess solution;
    private int playerCount;
    private String playerName;
    private PlayerName currentTurn;
    private GameState state;
    private Board board;
    private static ArrayList<Player> allPlayers = new ArrayList<>();
    private static ArrayList<Character> allCharacters = new ArrayList<>();
    private static ArrayList<Card> allCards = new ArrayList<>();
    private static ArrayList<Estate> allEstates = new ArrayList<>();
    private static ArrayList<Weapon> allWeapons = new ArrayList<>();
    private ArrayList<Card> tempDeck = new ArrayList<>();

    public static final Map<String, CharacterCard> characterMap = new HashMap<>();
    public static final Map<String, WeaponCard> weaponMap = new HashMap<>();
    public static final Map<String, EstateCard> estateMap = new HashMap<>();

    //COLOURS
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    public HobbyDetectives(){
       //initialize fields and perform necessary setup
        playerCount = 4;
        //players = new Player[]{PlayerName.Lucilla, PlayerName.Bert, PlayerName.Percy, PlayerName.Malina};
    }

    public enum PlayerName{
        Lucilla, Bert, Percy, Malina;
        public static Map<String, PlayerName> playerNameMap = Map.of("lucilla", Lucilla, "bert", Bert, "percy", Percy, "malina", Malina);
    }
    public enum WeaponName {
        Broom, Scissors, Knife, Shovel, iPad;
        public static Map<String, WeaponName> weaponNameMap = Map.of("broom", Broom, "scissors", Scissors, "knife", Knife, "shovel", Shovel, "ipad", iPad);
    }
    public enum EstateName {
        HauntedHouse, ManicManor, VisitationVilla, PerilPalace, CalamityCastle;
        public static Map<String, EstateName> estateNameMap = Map.of("haunted house", HauntedHouse, "manic manor", ManicManor, "visitation villa", VisitationVilla, "peril palace", PerilPalace, "calamity castle", CalamityCastle);
        @Override
        public String toString() {
            return switch (this) {
                case HauntedHouse -> "Haunted House";
                case ManicManor -> "Manic Manor";
                case VisitationVilla -> "Visitation Villa";
                case PerilPalace -> "Peril Palace";
                case CalamityCastle -> "Calamity Castle";
            };
        }
    }

    enum GameState{START, ONGOING, GUESS, WON, LOST}
    /**
     * Main method
     * initializes the game object
     * setups up the game
     * runs game loop
     * @param args
     * - Matt
     */
    public static void main(String[] args){
        HobbyDetectives game = new HobbyDetectives();
        game.setup();
        game.loop();
    }

    /** The setup for the game.
     * Shuffles the cards.
     * Distributes each card to each player
     * Allows player selection
     * Initializes all elements
     * - Matt
     */
    public void setup(){
        initializeEstates();
        initializeBoard();
        initializeDoors();
        initializeCards();
        initializeCharacters();
        initializePlayers();
        distributeCards();
        initializeWeapons();
        state = GameState.ONGOING;
    }

    /**
     * Create estates
     */
    private void initializeEstates(){
        allEstates.add(new Estate("Haunted House", 2, 2, 5, 5));
        allEstates.add(new Estate("Manic Manor", 17, 2, 5, 5));
        allEstates.add(new Estate("Calamity Castle", 2, 17, 5, 5));
        allEstates.add(new Estate("Peril Palace", 17, 17, 5, 5));
        allEstates.add(new Estate("Visitation Villa", 9, 10, 6, 4));
    }

    public void initializeBoard() {
        board = new Board();
        for (int x = 0; x < board.getLength(); x++) {
            for (int y = 0; y < board.getLength(); y++) {
                Square square = new Square(x, y);
                board.addSquare(square, x, y);
                for (Estate e : allEstates){
                    if (e.squarePartOfEstate(square) != null) {
                        square.setBlocked(true);
                        square.setEstate(e);
                    }
                }
            }
        }
    }

    private void initializeDoors() {
        // Haunted House doors
        Board.getSquare(6, 3).setBlocked(false);
        Board.getSquare(5,6).setBlocked(false);

        // Manic Manor doors
        Board.getSquare(17,5).setBlocked(false);
        Board.getSquare(20,6).setBlocked(false);

        // Visitation Villa doors
        Board.getSquare(12,10).setBlocked(false);
        Board.getSquare(14,11).setBlocked(false);
        Board.getSquare(11,13).setBlocked(false);
        Board.getSquare(9,12).setBlocked(false);

        // Calamity Castle doors
        Board.getSquare(3, 17).setBlocked(false);
        Board.getSquare(6, 18).setBlocked(false);

        // Peril Palace doors
        Board.getSquare(18, 17).setBlocked(false);
        Board.getSquare(17, 20).setBlocked(false);
    }

    /**
     * Create each card
     * Add cards to the solution
     *
     */
     private void initializeCards() {
         for (PlayerName p : PlayerName.values()) {
             CharacterCard c = new CharacterCard(p.toString());
             characterMap.put(p.toString(), c);
             allCards.add(c);
         }

         for (WeaponName w : WeaponName.values()) {
             WeaponCard c = new WeaponCard(w.toString());
             weaponMap.put(w.toString(), c);
             allCards.add(c);
         }

         for (EstateName e : EstateName.values()) {
             EstateCard c = new EstateCard(e.toString());
             estateMap.put(e.toString(), c);
             allCards.add(c);
         }
     }
    /**
     * Create character and place them at starting positions
     */
    private void initializeCharacters(){
        allCharacters.add(new Character(Board.getSquare(11, 1), PlayerName.Lucilla, RED_BOLD));
        allCharacters.add(new Character(Board.getSquare(1, 9), PlayerName.Bert, BLUE_BOLD));
        allCharacters.add(new Character(Board.getSquare(22, 14), PlayerName.Percy, PURPLE_BOLD));
        allCharacters.add(new Character(Board.getSquare(9, 22), PlayerName.Malina, GREEN_BOLD));
    }

    /**
     * Create players
     */
    private void initializePlayers(){
//        Collections.shuffle(allCharacters);
        for (int i = 0; i < playerCount; i++){
            allPlayers.add(new Player(allCharacters.get(i), board));
        }
    }

    /**
     * Distributes cards evenly between players
     */
    private void distributeCards(){
        Collections.shuffle(allCards);
        tempDeck = new ArrayList<>(allCards);
        solution = generateSolution();
        int playerIndex = 0;
        while(!tempDeck.isEmpty()){
            Player currentPlayer = allPlayers.get(playerIndex);
            Card c = tempDeck.get((int) (Math.random()* tempDeck.size()));
            currentPlayer.addCard(c);
            tempDeck.remove(c);
            playerIndex = (playerIndex + 1) % playerCount;
        }
        // for debugging
        for (Player p : getPlayers()){
            System.out.print(p.getName() + ": ");
            System.out.println(p.getHand().size());
            for (Card card : p.getHand()){
                System.out.println(card.getCardName());
            }
        }
    }


    /**
     * Create weapons and randomly add to estates
     * @return
     */
    private void initializeWeapons(){
        allWeapons.add(new Weapon("Broom"));
        allWeapons.add(new Weapon("Scissors"));
        allWeapons.add(new Weapon("Knife"));
        allWeapons.add(new Weapon("Shovel"));
        allWeapons.add(new Weapon("iPad"));

        Collections.shuffle(allEstates);

        for (int i = 0; i < allEstates.size(); i++){
            allEstates.get(i).addWeapon(allWeapons.get(i));
        }
    }

    /**
     * Create a guess object that is the solution for the game
     * @return
     */
    private Guess generateSolution(){
         WeaponCard weapon;
         EstateCard estate;
         CharacterCard murderer;

         int i = (int) (Math.random() * tempDeck.size());
         while(!(tempDeck.get(i) instanceof WeaponCard)){
             i = (int) (Math.random() * tempDeck.size());
         }

         weapon = (WeaponCard) tempDeck.get(i);
         tempDeck.remove(weapon);

         i = (int) (Math.random() * tempDeck.size());
         while(!(tempDeck.get(i) instanceof EstateCard)){
             i = (int) (Math.random() * tempDeck.size());

         }

         estate = (EstateCard) tempDeck.get(i);
         tempDeck.remove(estate);

        i = (int) (Math.random() * tempDeck.size());
        while(!(tempDeck.get(i) instanceof CharacterCard)){
            i = (int) (Math.random() * tempDeck.size());

        }

        murderer = (CharacterCard) tempDeck.get(i);
        tempDeck.remove(murderer);

        System.out.println("Solution: " + murderer.getCardName() + " with the " + weapon.getCardName() + " in " + estate.getCardName());

        return new Guess(murderer, weapon, estate);

    }

    /**
     * The game loop, runs the game logic,
     * and allows each player to play during their turn
     */
    public void loop(){
        int currentPlayerIndex = 0;
        while(state == GameState.ONGOING){ // condition for game loop to run
            Player currentPlayer = allPlayers.get(currentPlayerIndex);
            currentTurn = currentPlayer.getName();
            //Display the current game state
            board.drawToScreen();

            currentPlayer.doTurn();
            currentPlayerIndex = (currentPlayerIndex + 1) % playerCount;
        }
    }

    public static Guess getSolution(){
        return solution;
    }

    public static List<Player> getPlayers(){
        return allPlayers;
    }

    public static ArrayList<Card> getAllCards(){
        return allCards;
    }

}
