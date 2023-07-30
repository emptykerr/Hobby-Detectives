/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 60 "model.ump"
// line 144 "model.ump"
public class Wall
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Direction { North, East, South, West }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Wall Attributes
  private Direction direction;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Wall(Direction aDirection)
  {
    direction = aDirection;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDirection(Direction aDirection)
  {
    boolean wasSet = false;
    direction = aDirection;
    wasSet = true;
    return wasSet;
  }

  public Direction getDirection()
  {
    return direction;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "direction" + "=" + (getDirection() != null ? !getDirection().equals(this)  ? getDirection().toString().replaceAll("  ","    ") : "this" : "null");
  }
}