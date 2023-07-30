/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.util.*;

// line 32 "model.ump"
// line 123 "model.ump"
public class Player
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PlayerName { Lucilla, Bert, Malina, Percy }
  public enum State { PLAYING, ELIMINATED }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private PlayerName name;

  //Player Associations
  private Square square;
  private Estate estate;
  private List<Card> hand;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(PlayerName aName, Square aSquare)
  {
    name = aName;
    if (!setSquare(aSquare))
    {
      throw new RuntimeException("Unable to create Player due to aSquare. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    hand = new ArrayList<Card>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(PlayerName aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public PlayerName getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public Square getSquare()
  {
    return square;
  }
  /* Code from template association_GetOne */
  public Estate getEstate()
  {
    return estate;
  }

  public boolean hasEstate()
  {
    boolean has = estate != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Card getHand(int index)
  {
    Card aHand = hand.get(index);
    return aHand;
  }

  public List<Card> getHand()
  {
    List<Card> newHand = Collections.unmodifiableList(hand);
    return newHand;
  }

  public int numberOfHand()
  {
    int number = hand.size();
    return number;
  }

  public boolean hasHand()
  {
    boolean has = hand.size() > 0;
    return has;
  }

  public int indexOfHand(Card aHand)
  {
    int index = hand.indexOf(aHand);
    return index;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setSquare(Square aNewSquare)
  {
    boolean wasSet = false;
    if (aNewSquare != null)
    {
      square = aNewSquare;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setEstate(Estate aNewEstate)
  {
    boolean wasSet = false;
    estate = aNewEstate;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHand()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addHand(Card aHand)
  {
    boolean wasAdded = false;
    if (hand.contains(aHand)) { return false; }
    hand.add(aHand);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHand(Card aHand)
  {
    boolean wasRemoved = false;
    if (hand.contains(aHand))
    {
      hand.remove(aHand);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHandAt(Card aHand, int index)
  {  
    boolean wasAdded = false;
    if(addHand(aHand))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHand()) { index = numberOfHand() - 1; }
      hand.remove(aHand);
      hand.add(index, aHand);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHandAt(Card aHand, int index)
  {
    boolean wasAdded = false;
    if(hand.contains(aHand))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHand()) { index = numberOfHand() - 1; }
      hand.remove(aHand);
      hand.add(index, aHand);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHandAt(aHand, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    square = null;
    estate = null;
    hand.clear();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "name" + "=" + (getName() != null ? !getName().equals(this)  ? getName().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "square = "+(getSquare()!=null?Integer.toHexString(System.identityHashCode(getSquare())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "estate = "+(getEstate()!=null?Integer.toHexString(System.identityHashCode(getEstate())):"null");
  }
}