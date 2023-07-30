/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/


import java.util.*;

// line 46 "model.ump"
// line 133 "model.ump"
public class Square
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Square Associations
  private List<Wall> walls;
  private Estate estate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Square(Wall... allWalls)
  {
    walls = new ArrayList<Wall>();
    boolean didAddWalls = setWalls(allWalls);
    if (!didAddWalls)
    {
      throw new RuntimeException("Unable to create Square, must have 4 walls. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Wall getWall(int index)
  {
    Wall aWall = walls.get(index);
    return aWall;
  }

  public List<Wall> getWalls()
  {
    List<Wall> newWalls = Collections.unmodifiableList(walls);
    return newWalls;
  }

  public int numberOfWalls()
  {
    int number = walls.size();
    return number;
  }

  public boolean hasWalls()
  {
    boolean has = walls.size() > 0;
    return has;
  }

  public int indexOfWall(Wall aWall)
  {
    int index = walls.indexOf(aWall);
    return index;
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
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfWalls()
  {
    return 4;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWalls()
  {
    return 4;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfWalls()
  {
    return 4;
  }
  /* Code from template association_SetUnidirectionalN */
  public boolean setWalls(Wall... newWalls)
  {
    boolean wasSet = false;
    ArrayList<Wall> verifiedWalls = new ArrayList<Wall>();
    for (Wall aWall : newWalls)
    {
      if (verifiedWalls.contains(aWall))
      {
        continue;
      }
      verifiedWalls.add(aWall);
    }

    if (verifiedWalls.size() != newWalls.length || verifiedWalls.size() != requiredNumberOfWalls())
    {
      return wasSet;
    }

    walls.clear();
    walls.addAll(verifiedWalls);
    wasSet = true;
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

  public void delete()
  {
    walls.clear();
    estate = null;
  }

}