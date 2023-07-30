/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 95 "model.ump"
// line 165 "model.ump"
public class WeaponCard implements Card
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum WeaponName { Broom, Scissors, Knife, Shovel, IPad }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WeaponCard Attributes
  private WeaponName name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WeaponCard(WeaponName aName)
  {
    name = aName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(WeaponName aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public WeaponName getName()
  {
    return name;
  }

  public void delete()
  {}

  // line 100 "model.ump"
  public String getCardName(){
    return name.toString();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "name" + "=" + (getName() != null ? !getName().equals(this)  ? getName().toString().replaceAll("  ","    ") : "this" : "null");
  }
}