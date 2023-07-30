/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.util.*;

// line 52 "model.ump"
// line 139 "model.ump"
public class Estate
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum EstateName { HauntedHouse, ManicManor, VisitationVilla, CalamityCastle, PerilPalace }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Estate Attributes
  private EstateName name;

  //Estate Associations
  private List<Square> squares;
  private Weapon weapon;
  private List<Player> players;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Estate(EstateName aName)
  {
    name = aName;
    squares = new ArrayList<Square>();
    players = new ArrayList<Player>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(EstateName aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public EstateName getName()
  {
    return name;
  }
  /* Code from template association_GetMany */
  public Square getSquare(int index)
  {
    Square aSquare = squares.get(index);
    return aSquare;
  }

  public List<Square> getSquares()
  {
    List<Square> newSquares = Collections.unmodifiableList(squares);
    return newSquares;
  }

  public int numberOfSquares()
  {
    int number = squares.size();
    return number;
  }

  public boolean hasSquares()
  {
    boolean has = squares.size() > 0;
    return has;
  }

  public int indexOfSquare(Square aSquare)
  {
    int index = squares.indexOf(aSquare);
    return index;
  }
  /* Code from template association_GetOne */
  public Weapon getWeapon()
  {
    return weapon;
  }

  public boolean hasWeapon()
  {
    boolean has = weapon != null;
    return has;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSquares()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addSquare(Square aSquare)
  {
    boolean wasAdded = false;
    if (squares.contains(aSquare)) { return false; }
    if (squares.contains(aSquare)) { return false; }
    squares.add(aSquare);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSquare(Square aSquare)
  {
    boolean wasRemoved = false;
    if (squares.contains(aSquare))
    {
      squares.remove(aSquare);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSquareAt(Square aSquare, int index)
  {  
    boolean wasAdded = false;
    if(addSquare(aSquare))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSquares()) { index = numberOfSquares() - 1; }
      squares.remove(aSquare);
      squares.add(index, aSquare);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSquareAt(Square aSquare, int index)
  {
    boolean wasAdded = false;
    if(squares.contains(aSquare))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSquares()) { index = numberOfSquares() - 1; }
      squares.remove(aSquare);
      squares.add(index, aSquare);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSquareAt(aSquare, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setWeapon(Weapon aNewWeapon)
  {
    boolean wasSet = false;
    weapon = aNewWeapon;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 4;
  }
  /* Code from template association_AddUnidirectionalOptionalN */
  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() < maximumNumberOfPlayers())
    {
      players.add(aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    if (players.contains(aPlayer))
    {
      players.remove(aPlayer);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalOptionalN */
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

    if (verifiedPlayers.size() != newPlayers.length || verifiedPlayers.size() > maximumNumberOfPlayers())
    {
      return wasSet;
    }

    players.clear();
    players.addAll(verifiedPlayers);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    squares.clear();
    weapon = null;
    players.clear();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "name" + "=" + (getName() != null ? !getName().equals(this)  ? getName().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "weapon = "+(getWeapon()!=null?Integer.toHexString(System.identityHashCode(getWeapon())):"null");
  }
}