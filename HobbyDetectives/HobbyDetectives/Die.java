/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/



// line 104 "model.ump"
// line 170 "model.ump"
public class Die
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Die Attributes
  private int value;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Die(int aValue)
  {
    value = aValue;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setValue(int aValue)
  {
    boolean wasSet = false;
    value = aValue;
    wasSet = true;
    return wasSet;
  }

  public int getValue()
  {
    return value;
  }

  public void delete()
  {}

  // line 107 "model.ump"
  public int roll(){
    value = (int) (Math.random() * 6) + 1;
    return value;
  }


  public String toString()
  {
    return super.toString() + "["+
            "value" + ":" + getValue()+ "]";
  }
}