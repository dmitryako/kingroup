package kingroup_v2.like.milligan;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 15:19:09
 */
public class Milligan
{
  public static final String REFERENCE = "Milligan (2003) Genetics 163 p1153";
  private MilliganIBD primIBD;
  private MilliganIBD nullIBD;
  private MilliganIBD[]  nullArr;

  public Milligan() {
    init();
  }
  private void init() {
    primIBD = new MilliganIBD();
    nullIBD = new MilliganIBD();
    nullArr = new MilliganIBD[1];
    nullArr[0] = nullIBD;
  }
  public void loadDefault() {
    primIBD.loadPrimDefault();
    nullIBD.loadNullDefault();
  }

  public MilliganIBD getPrimIBD()  {    return primIBD;  }
  public void setPrimIBD(MilliganIBD primIBD)  {    this.primIBD = primIBD;  }
  public MilliganIBD getNullIBD()  {    return nullIBD;  }
  public void setNullIBD(MilliganIBD nullIBD)  {    this.nullIBD = nullIBD;  }
  public MilliganIBD[] getNullArr()
  {
    return nullArr;
  }

  public void setNullArr(MilliganIBD[] nullArr)
  {
    this.nullArr = nullArr;
  }
}
