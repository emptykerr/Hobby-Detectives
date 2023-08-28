package main;
import view.Menu;
import view.Ongoing;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A HobbyDetectives instance represents a running game of Hobby Detectives.
 * It takes care of handling the commands from GUI, initialising the game components
 * and running the main game loop
 */
public class HobbyDetectives {
	private static Guess solution;
	private int playerCount;
	private Player currentPlayer;
	public PlayerName currentTurn;
	public GameState state = GameState.MENU;
	private Ongoing ongoingView;

	//Lists that contain all game objects
	private static ArrayList<Player> allPlayers = new ArrayList<>();
	private static ArrayList<Character> allCharacters = new ArrayList<>();
	private static ArrayList<Card> allCards = new ArrayList<>();
	private static ArrayList<Estate> allEstates = new ArrayList<>();
	private static ArrayList<Weapon> allWeapons = new ArrayList<>();

	private ArrayList<Card> tempDeck = new ArrayList<>();

	private static Menu gui;

	// Maps that connect the names if game objects to their corresponding cards
	public static final Map<String, CharacterCard> characterMap = new HashMap<>();
	public static final Map<String, WeaponCard> weaponMap = new HashMap<>();
	public static final Map<String, EstateCard> estateMap = new HashMap<>();

	// Colours
	public static final Color RESET = Color.DARK_GRAY;
	public static final Color RED_BOLD = Color.RED;
	public static final Color GREEN_BOLD = Color.GREEN;
	public static final Color YELLOW_BOLD = Color.YELLOW;
	public static final Color BLUE_BOLD = Color.BLUE;
	public static final Color CYAN_BOLD = Color.CYAN;
	public static final Color BROWN = new Color(153, 102, 51); // BROWN

	private int currentPlayerIndex;

	public HobbyDetectives(Menu gui) {
		// initialize fields and perform necessary setup
		this.gui = gui;
		playerCount = 0;
	}

	/**
	 * Enum representing player names.
	 */
	public enum PlayerName {
		Lucilla, Bert, Malina, Percy;

		/**
		 * A map to convert lowercase player names to the corresponding enum value.
		 */
		public static Map<String, PlayerName> playerNameMap = Map.of("lucilla", Lucilla, "bert", Bert, "malina", Malina,
				"percy", Percy);
	}

	/**
	 * Enum representing weapon names.
	 */
	public enum WeaponName {
		Broom, Scissors, Knife, Shovel, iPad;

		/**
		 * A map to convert lowercase weapon names to the corresponding enum value.
		 */
		public static Map<String, WeaponName> weaponNameMap = Map.of("broom", Broom, "scissors", Scissors, "knife",
				Knife, "shovel", Shovel, "ipad", iPad);
	}

	/**
	 * Enum representing estate names.
	 */
	public enum EstateName {
		HauntedHouse, ManicManor, VisitationVilla, PerilPalace, CalamityCastle;

		/**
		 * A map to convert lowercase estate names to the corresponding enum value.
		 */
		public static Map<String, EstateName> estateNameMap = Map.of("haunted house", HauntedHouse, "manic manor",
				ManicManor, "visitation villa", VisitationVilla, "peril palace", PerilPalace, "calamity castle",
				CalamityCastle);

		/**
		 * Overrides the default toString method to provide a formatted version of the
		 * enum values.
		 *
		 * @return The formatted string representation of the enum value.
		 */
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

	/**
	 * Enum representing the game state.
	 */
	public enum GameState {
		ONGOING, WON, LOST, MENU, QUIT;
	}

	/**
	 * Main method initializes the game object setups up the game, runs game loop and calls GUI
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		gui = new Menu();
	}

	/**
	 * The setup for the game. Shuffles the cards. Distributes each card to each
	 * player Allows player selection Initializes all elements - Matt
	 */
	public void setup() {
		initializeEstates();
		initializeBoard();
		initializeDoors();
		initializeUnreachableSquares();
		initializeCards();
		initializeCharacters();
		initializeWeapons();
	}

	/**
	 * Create estates Hardcoded values
	 */
	private void initializeEstates() {
		allEstates.add(new Estate("Haunted House", 2, 2, 5, 5));
		allEstates.add(new Estate("Manic Manor", 17, 2, 5, 5));
		allEstates.add(new Estate("Visitation Villa", 9, 10, 6, 4));
		allEstates.add(new Estate("Calamity Castle", 2, 17, 5, 5));
		allEstates.add(new Estate("Peril Palace", 17, 17, 5, 5));
	}

	/**
	 * Creates the unreachable squares on the map
	 *
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void createUnreachableSquares(int x, int y, int width, int height) {
		x = x - 1;
		y = y - 1;
		for (int row = x; row < x + width; row++) {
			for (int col = y; col < y + height; col++) {
				Square square = new Square(row, col);
				Board.addSquare(square, row, col);

				square.setBlocked(true);
			}
		}
	}

	/**
	 * The ranges the unreachable squares start and end between
	 */
	private void initializeUnreachableSquares() {
		createUnreachableSquares(12, 6, 2, 2);
		createUnreachableSquares(6, 12, 2, 2);
		createUnreachableSquares(18, 12, 2, 2);
		createUnreachableSquares(12, 18, 2, 2);
	}

	/**
	 * creates the squares and estates on the board
	 */
	public void initializeBoard() {
		for (int x = 0; x < Board.getLength(); x++) {
			for (int y = 0; y < Board.getLength(); y++) {
				Square square = new Square(x, y);
				Board.addSquare(square, x, y);
				for (Estate e : allEstates) {
					if (e.squarePartOfEstate(square) != null) { // check if current square is part of estate
						square.setBlocked(true); // add estate to square
						square.setEstate(e);
					}
				}
			}
		}
	}

	/**
	 * Adds the doors to each estate Hardcoded for each estate
	 */
	private void initializeDoors() {
		// Haunted House doors
		allEstates.get(0).addDoor(6, 3, "Right");
		allEstates.get(0).addDoor(5, 6, "Bottom");
		allEstates.get(1).addDoor(17, 5, "Left");
		allEstates.get(1).addDoor(20, 6, "Bottom");

		// Visitation Villa doors
		allEstates.get(2).addDoor(12, 10, "Top");
		allEstates.get(2).addDoor(14, 11, "Right");
		allEstates.get(2).addDoor(11, 13, "Left");
		allEstates.get(2).addDoor(9, 12, "Bottom");

		// Calamity Castle doors
		allEstates.get(3).addDoor(3, 17, "Top");
		allEstates.get(3).addDoor(6, 18, "Right");

		// Peril Palace doors
		allEstates.get(4).addDoor(18, 17, "Top");
		allEstates.get(4).addDoor(17, 20, "Left");
	}

	/**
	 * Create each card Add cards to the solution
	 */
	private void initializeCards() {
		// Creates player cards
		for (PlayerName p : PlayerName.values()) {
			CharacterCard c = new CharacterCard(p.toString());
			characterMap.put(p.toString(), c);
			allCards.add(c);
		}

		// Creates weapon cards
		for (WeaponName w : WeaponName.values()) {
			WeaponCard c = new WeaponCard(w.toString());
			weaponMap.put(w.toString(), c);
			allCards.add(c);
		}

		// Creates estate cards
		for (EstateName e : EstateName.values()) {
			EstateCard c = new EstateCard(e.toString());
			estateMap.put(e.toString(), c);
			allCards.add(c);
		}
	}

	/**
	 * Create character and place them at starting positions
	 */
	private void initializeCharacters() {
		allCharacters.add(new Character(Board.getSquare(11, 1), PlayerName.Lucilla, GREEN_BOLD));
		allCharacters.add(new Character(Board.getSquare(1, 9), PlayerName.Bert, YELLOW_BOLD));
		allCharacters.add(new Character(Board.getSquare(9, 22), PlayerName.Malina, BLUE_BOLD));
		allCharacters.add(new Character(Board.getSquare(22, 14), PlayerName.Percy, RED_BOLD));
	}

	/**
	 * Add a new player to the game
	 * @param name the real name of the player
	 * @param character the name of the character that the player represents on the board
	 */
	public void addPlayer(String name, String character) {
		Character ch = allCharacters.stream().filter(c -> c.getName().toString().equals(character)).findFirst().get();
		allPlayers.add(new Player(name, ch, this));
		playerCount++;
	}

	/**
	 * Distributes cards evenly between players
	 */
	public void distributeCards() {
		if (state.equals(GameState.MENU)) {
			Collections.shuffle(allCards);
			tempDeck = new ArrayList<>(allCards);
			solution = generateSolution();
			int playerIndex = 0;
			while (!tempDeck.isEmpty()) {
				Player currentPlayer = allPlayers.get(playerIndex);
				Card c = tempDeck.get((int) (Math.random() * tempDeck.size()));
				currentPlayer.addCard(c);
				tempDeck.remove(c);
				playerIndex = (playerIndex + 1) % playerCount;
			}
		}
		printCheatAnswers();
	}

	/**
	 * For debugging purposes, display the solution. Also display each character,
	 * and their decks.
	 */
	private void printCheatAnswers() {
		System.out.println("\n--------------- CHEAT ANSWERS ---------------");
		System.out.println("Solution: " + solution.getCharacter().getCardName() + " with the "
				+ solution.getWeapon().getCardName() + " in " + solution.getEstate().getCardName());
		for (Player p : getPlayers()) {
			System.out.print(p.getName() + ": ");
			System.out.println(p.getHand().size());
			for (Card card : p.getHand()) {
				System.out.println(card.getCardName());
			}
		}
		System.out.println("--------------- CHEAT ANSWERS ---------------\n");
	}

	/**
	 * Create weapons and randomly add to estates
	 */
	private void initializeWeapons() {
		allWeapons.add(new Weapon("Broom"));
		allWeapons.add(new Weapon("Scissors"));
		allWeapons.add(new Weapon("Knife"));
		allWeapons.add(new Weapon("Shovel"));
		allWeapons.add(new Weapon("iPad"));

		Collections.shuffle(allEstates);

		for (int i = 0; i < allEstates.size(); i++) {
			allEstates.get(i).setWeapon(allWeapons.get(i));
			allEstates.get(i).getWeaponSquare().setWeapon(allWeapons.get(i));
		}

		for (Estate e : allEstates) {
			e.getWeapon().setEstate(e);
		}
	}

	/**
	 * Create a guess object that is the solution for the game
	 *
	 * @return Guess the generated solution
	 */
	private Guess generateSolution() {
		WeaponCard weapon;
		EstateCard estate;
		CharacterCard murderer;

		int i = (int) (Math.random() * tempDeck.size());
		while (!(tempDeck.get(i) instanceof WeaponCard)) {
			i = (int) (Math.random() * tempDeck.size());
		}

		weapon = (WeaponCard) tempDeck.get(i);
		tempDeck.remove(weapon);

		i = (int) (Math.random() * tempDeck.size());
		while (!(tempDeck.get(i) instanceof EstateCard)) {
			i = (int) (Math.random() * tempDeck.size());

		}

		estate = (EstateCard) tempDeck.get(i);
		tempDeck.remove(estate);

		i = (int) (Math.random() * tempDeck.size());
		while (!(tempDeck.get(i) instanceof CharacterCard)) {
			i = (int) (Math.random() * tempDeck.size());

		}

		murderer = (CharacterCard) tempDeck.get(i);
		tempDeck.remove(murderer);

		return new Guess(murderer, weapon, estate);
	}

	/**
	 * sets up the start of the next players turn
	 */
	public void initialTurnSetup() {
		currentPlayerIndex = (int) (Math.random() * playerCount); // index of next player
		changeTurn();
	}

	/**
	 * changes the turn to the next player
	 */
	public void changeTurn() {
		currentPlayer = allPlayers.get(currentPlayerIndex);
		currentTurn = currentPlayer.getCharacter().getName();
		currentPlayer.getCharacter().startNewRound();
	}

	/**
	 * when the players turn has finished checks wether the player has won or if they have lost,
	 * if neither of these then resets the roll and changes to the next player
	 */
	public void doEndTurn() {
		if (currentPlayer.checkGamestate()) {
			state = GameState.WON;
		}
		else if (allPlayers.stream().allMatch(Player::checkEliminated)) {
			state = GameState.LOST;
		} else {
			currentPlayer.reset();

			currentPlayerIndex = (currentPlayerIndex + 1) % playerCount;
			changeTurn();

			ongoingView.printToUI("It is now " + currentPlayer.getName() + "'s turn");
		}
	}

	/**
	 * Returns the murder solution
	 *
	 * @return
	 */
	public static Guess getSolution() {
		return solution;
	}

	/**
	 * Returns all the players in the game
	 *
	 * @return
	 */
	public static List<Player> getPlayers() {
		return allPlayers;
	}

	/**
	 * Returns all the weapons in the game
	 *
	 * @return
	 */
	public static List<Weapon> getWeapons() {
		return allWeapons;
	}

	/**
	 * @return the player index
	 */
	public int getCurrentPlayerIndex(){
		return currentPlayerIndex;
	}

	public int getPlayerCount(){
		return this.playerCount;
	}

	/**
	 * Returns a list of the players, in order from the current player.
	 *
	 * @param playerOrder
	 * @param currentPlayer
	 * @return list of players in order
	 */
	public static List<Player> getOrderedPlayers(List<Player> playerOrder, Player currentPlayer) {
		List<Player> orderedPlayerList = new ArrayList<>();

		int currentPlayerIndex = playerOrder.indexOf(currentPlayer);
		for (int i = 1; i <= playerOrder.size(); i++) {
			orderedPlayerList.add(playerOrder.get((currentPlayerIndex + i) % playerOrder.size()));
		}
		return orderedPlayerList;
	}

	/**
	 * Sets the player count
	 *
	 * @param playerCount
	 */
	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	/**
	 * Returns the gamestate
	 *
	 * @return
	 */
	public GameState getGamestate(){
		return state;
	}

	/**
	 * Sets the gamestate of the game
	 *
	 * @param state
	 */
	public void setGamestate(GameState state){
		this.state = state;
	}

	/**
	 * Set ongoingview
	 *
	 * @param og the Ongoing instance
	 */
	public void setOngoingView(Ongoing og){
		ongoingView = og;
		Player.setOngoingView(og);
	}

	/**
	 * Get the current player
	 * @return current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Returns the ongoing view
	 *
	 * @return the ongoing view
	 */
	public Ongoing getOngoingView(){
		return ongoingView;
	}

	/**
	 * Returns all cards
	 *
	 * @return all cards
	 */
	public List<Card> getAllCards(){ return allCards; }

	public void end() {
		System.exit(0);
	}
}
