/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 66 "model.ump"
// line 149 "model.ump"
public class Weapon
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum WeaponName { Broom, Scissors, Knife, Shovel, IPad }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Weapon Attributes
  private WeaponName name;

  //Weapon Associations
  private Estate estate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Weapon(WeaponName aName, Estate aEstate)
  {
    name = aName;
    if (!setEstate(aEstate))
    {
      throw new RuntimeException("Unable to create Weapon due to aEstate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetOne */
  public Estate getEstate()
  {
    return estate;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setEstate(Estate aNewEstate)
  {
    boolean wasSet = false;
    if (aNewEstate != null)
    {
      estate = aNewEstate;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {
    estate = null;
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "name" + "=" + (getName() != null ? !getName().equals(this)  ? getName().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "estate = "+(getEstate()!=null?Integer.toHexString(System.identityHashCode(getEstate())):"null");
  }
}