/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 77 "model.ump"
// line 155 "model.ump"
public class EstateCard implements Card
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum EstateName { HauntedHouse, ManicManor, VisitationVilla, CalamityCastle, PerilPalace }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //EstateCard Attributes
  private EstateName name;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EstateCard(EstateName aName)
  {
    name = aName;
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

  public void delete()
  {}

  // line 82 "model.ump"
  public String getCardName(){
    return name.toString();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "name" + "=" + (getName() != null ? !getName().equals(this)  ? getName().toString().replaceAll("  ","    ") : "this" : "null");
  }
}