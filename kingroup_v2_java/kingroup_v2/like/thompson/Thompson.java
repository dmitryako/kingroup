package kingroup_v2.like.thompson;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 10:35:40
 */
public class Thompson
{
  public static final String REFERENCE = "Thompson (1975) Ann.Hum.Genet.,Lond 39 p173";
  private ThompsonIBD primIBD;
  private ThompsonIBD nullIBD;
  private ThompsonIBD[]  nullArr;

  public Thompson() {
    init();
  }
  private void init() {
    primIBD = new ThompsonIBD();
    nullIBD = new ThompsonIBD();
    nullArr = new ThompsonIBD[1];
    nullArr[0] = nullIBD;
  }
  public void loadDefault() {
    primIBD.loadPrimDefault();
    nullIBD.loadNullDefault();
  }

  public ThompsonIBD getPrimIBD()  {    return primIBD;  }
  public void setPrimIBD(ThompsonIBD primIBD)  {    this.primIBD = primIBD;  }
  public ThompsonIBD getNullIBD()  {    return nullIBD;  }
  public void setNullIBD(ThompsonIBD nullIBD)  {    this.nullIBD = nullIBD;  }
  public ThompsonIBD[] getNullArr()
  {
    return nullArr;
  }

  public void setNullArr(ThompsonIBD[] nullArr)
  {
    this.nullArr = nullArr;
  }
}
