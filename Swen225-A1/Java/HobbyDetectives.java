/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.util.*;

// line 6 "model.ump"
// line 116 "model.ump"
public class HobbyDetectives
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PlayerName { Lucilla, Bert, Malina, Percy }
  public enum GameState { START, ONGOING, GUESS, WON, LOST }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //HobbyDetectives Attributes
  private int playerCount;
  private PlayerName currentTurn;
  private GameState state;

  //HobbyDetectives Associations
  private List<Square> board;
  private List<Card> solution;
  private List<Player> players;
  private List<Die> dice;

  //------------------------
  // CONSTRUCTOR
  //------------------------


  public HobbyDetectives()
  {

    playerCount = 4;
    currentTurn = PlayerName.Lucilla;
    state = GameState.ONGOING;
    board = new ArrayList<Square>();
    solution = new ArrayList<Card>();
  }

  public HobbyDetectives(int aPlayerCount, PlayerName aCurrentTurn, GameState aState, Card[] allSolution, Player[] allPlayers, Die[] allDice)
  {

    playerCount = aPlayerCount;
    currentTurn = aCurrentTurn;
    state = aState;
    board = new ArrayList<Square>();
    solution = new ArrayList<Card>();
    boolean didAddSolution = setSolution(allSolution);
    if (!didAddSolution)
    {
      throw new RuntimeException("Unable to create HobbyDetectives, must have 3 solution. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    players = new ArrayList<Player>();
    boolean didAddPlayers = setPlayers(allPlayers);
    if (!didAddPlayers)
    {
      throw new RuntimeException("Unable to create HobbyDetectives, must have 4 players. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    dice = new ArrayList<Die>();
    boolean didAddDice = setDice(allDice);
    if (!didAddDice)
    {
      throw new RuntimeException("Unable to create HobbyDetectives, must have 2 dice. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }


  public static void main(String[] args) {
    HobbyDetectives game = new HobbyDetectives();
     game.setup();
      game.createBoard();
      System.out.println("Hello World!");
  }




  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPlayerCount(int aPlayerCount)
  {
    boolean wasSet = false;
    playerCount = aPlayerCount;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentTurn(PlayerName aCurrentTurn)
  {
    boolean wasSet = false;
    currentTurn = aCurrentTurn;
    wasSet = true;
    return wasSet;
  }

  public boolean setState(GameState aState)
  {
    boolean wasSet = false;
    state = aState;
    wasSet = true;
    return wasSet;
  }

  public int getPlayerCount()
  {
    return playerCount;
  }

  public PlayerName getCurrentTurn()
  {
    return currentTurn;
  }

  public GameState getState()
  {
    return state;
  }
  /* Code from template association_GetMany */
  public Square getBoard(int index)
  {
    Square aBoard = board.get(index);
    return aBoard;
  }

  public List<Square> getBoard()
  {
    List<Square> newBoard = Collections.unmodifiableList(board);
    return newBoard;
  }

  public int numberOfBoard()
  {
    int number = board.size();
    return number;
  }

  public boolean hasBoard()
  {
    boolean has = board.size() > 0;
    return has;
  }

  public int indexOfBoard(Square aBoard)
  {
    int index = board.indexOf(aBoard);
    return index;
  }
  /* Code from template association_GetMany */
  public Card getSolution(int index)
  {
    Card aSolution = solution.get(index);
    return aSolution;
  }

  public List<Card> getSolution()
  {
    List<Card> newSolution = Collections.unmodifiableList(solution);
    return newSolution;
  }

  public int numberOfSolution()
  {
    int number = solution.size();
    return number;
  }

  public boolean hasSolution()
  {
    boolean has = solution.size() > 0;
    return has;
  }

  public int indexOfSolution(Card aSolution)
  {
    int index = solution.indexOf(aSolution);
    return index;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }
  /* Code from template association_GetMany */
  public Die getDice(int index)
  {
    Die aDice = dice.get(index);
    return aDice;
  }

  public List<Die> getDice()
  {
    List<Die> newDice = Collections.unmodifiableList(dice);
    return newDice;
  }

  public int numberOfDice()
  {
    int number = dice.size();
    return number;
  }

  public boolean hasDice()
  {
    boolean has = dice.size() > 0;
    return has;
  }

  public int indexOfDice(Die aDice)
  {
    int index = dice.indexOf(aDice);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBoard()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addBoard(Square aBoard)
  {
    boolean wasAdded = false;
    if (board.contains(aBoard)) { return false; }
    board.add(aBoard);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBoard(Square aBoard)
  {
    boolean wasRemoved = false;
    if (board.contains(aBoard))
    {
      board.remove(aBoard);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBoardAt(Square aBoard, int index)
  {  
    boolean wasAdded = false;
    if(addBoard(aBoard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBoard()) { index = numberOfBoard() - 1; }
      board.remove(aBoard);
      board.add(index, aBoard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBoardAt(Square aBoard, int index)
  {
    boolean wasAdded = false;
    if(board.contains(aBoard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBoard()) { index = numberOfBoard() - 1; }
      board.remove(aBoard);
      board.add(index, aBoard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBoardAt(aBoard, index);
    }
    return wasAdded;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfSolution()
  {
    return 3;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSolution()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfSolution()
  {
    return 3;
  }
  /* Code from template association_SetUnidirectionalN */
  public boolean setSolution(Card... newSolution)
  {
    boolean wasSet = false;
    ArrayList<Card> verifiedSolution = new ArrayList<Card>();
    for (Card aSolution : newSolution)
    {
      if (verifiedSolution.contains(aSolution))
      {
        continue;
      }
      verifiedSolution.add(aSolution);
    }

    if (verifiedSolution.size() != newSolution.length || verifiedSolution.size() != requiredNumberOfSolution())
    {
      return wasSet;
    }

    solution.clear();
    solution.addAll(verifiedSolution);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfPlayers()
  {
    return 4;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 4;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 4;
  }
  /* Code from template association_SetUnidirectionalN */
  public boolean setPlayers(Player... newPlayers)
  {
    boolean wasSet = false;
    ArrayList<Player> verifiedPlayers = new ArrayList<Player>();
    for (Player aPlayer : newPlayers)
    {
      if (verifiedPlayers.contains(aPlayer))
      {
        continue;
      }
      verifiedPlayers.add(aPlayer);
    }

    if (verifiedPlayers.size() != newPlayers.length || verifiedPlayers.size() != requiredNumberOfPlayers())
    {
      return wasSet;
    }

    players.clear();
    players.addAll(verifiedPlayers);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfDice()
  {
    return 2;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDice()
  {
    return 2;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfDice()
  {
    return 2;
  }
  /* Code from template association_SetUnidirectionalN */
  public boolean setDice(Die... newDice)
  {
    boolean wasSet = false;
    ArrayList<Die> verifiedDice = new ArrayList<Die>();
    for (Die aDice : newDice)
    {
      if (verifiedDice.contains(aDice))
      {
        continue;
      }
      verifiedDice.add(aDice);
    }

    if (verifiedDice.size() != newDice.length || verifiedDice.size() != requiredNumberOfDice())
    {
      return wasSet;
    }

    dice.clear();
    dice.addAll(verifiedDice);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    board.clear();
    solution.clear();
    players.clear();
    dice.clear();
  }

  // line 17 "model.ump"
  //setup the game, create the board, deal cards, etc.
  public void setup(){

    createBoard();
    initializePlayers();
    state = GameState.ONGOING;
  }

  public void initializePlayers() {
    Player[] allPlayers = new Player[playerCount];

    for (int i = 0; i < playerCount; i++) {
      Player.PlayerName playerName = Player.PlayerName.values()[i];
      allPlayers[i] = new Player(playerName, new Square(null));
    }
    setPlayers(allPlayers);
    currentTurn = PlayerName.Lucilla;
  }

  private void dealCards() {
    // Implement the logic to shuffle and deal the Cluedo cards to players
    // For example:
    List<Card> allCards = new ArrayList<>();
    // Assuming you have a method to get all the cards for the Cluedo game
    // allCards.addAll(getAllRoomCards());
    // allCards.addAll(getAllWeaponCards());
    // allCards.addAll(getAllCharacterCards());
    Collections.shuffle(allCards);

    int cardsPerPlayer = allCards.size() / playerCount;
    for (int i = 0; i < playerCount; i++) {
        int startIndex = i * cardsPerPlayer;
        int endIndex = startIndex + cardsPerPlayer;
        Card[] playerCards = allCards.subList(startIndex, endIndex).toArray(new Card[0]);
        // getPlayer(i).setCards(playerCards);
    }

    // The remaining cards (if any) can be shown as "solutions" in the game
    Card[] solutionCards = allCards.subList(playerCount * cardsPerPlayer, allCards.size()).toArray(new Card[0]);
    setSolution(solutionCards);
}

  // line 18 "model.ump"
  public void createBoard(){
    //implement logic to create the cluedo game board here
    //create squares, rooms, and blocks
    //setup their connections in initial state.
  }

  // line 19 "model.ump"
  public void loop(){
    
  }

  // line 20 "model.ump"
  public void solveAttempt(){
    
  }

  

  // line 22 "model.ump"
  public void playerTurn(){
    int moves = 0;
    for (Die d : dice) {
      moves += d.roll();
    }
    while (moves > 0) {
      moves--;
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "playerCount" + ":" + getPlayerCount()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "currentTurn" + "=" + (getCurrentTurn() != null ? !getCurrentTurn().equals(this)  ? getCurrentTurn().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "state" + "=" + (getState() != null ? !getState().equals(this)  ? getState().toString().replaceAll("  ","    ") : "this" : "null");
  }
}