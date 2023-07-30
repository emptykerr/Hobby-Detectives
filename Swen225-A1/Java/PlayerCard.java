/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 86 "model.ump"
// line 160 "model.ump"
public class PlayerCard implements Card
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PlayerName { Lucilla, Bert, Malina, Percy }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayerCard Attributes
  private PlayerName name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayerCard(PlayerName aName)
  {
    name = aName;
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

  public void delete()
  {}

  // line 91 "model.ump"
  public String getCardName(){
    return name.toString();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "name" + "=" + (getName() != null ? !getName().equals(this)  ? getName().toString().replaceAll("  ","    ") : "this" : "null");
  }
}