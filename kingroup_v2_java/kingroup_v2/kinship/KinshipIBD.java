package kingroup_v2.kinship;
import javax.utilx.pair.DoublePair;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 15:28:11
 */
public class KinshipIBD extends DoublePair
{
//  private float rm;  // identical by MATERNAL descent probability
//  private float rp;  // identical by paternal descent probability

  public KinshipIBD(double rm, double rp)
  {
    super(rm, rp);
  }
  public KinshipIBD()
  {
    super();
  }

  public void loadDefault() {
    setRm(0.0f);
    setRp(0.0f);
  }
  public void copyTo(KinshipIBD model)
  {
    model.setRm(getRm());
    model.setRp(getRp());
  }
  final public double getRm() {   return getA();  }
  final public void setRm(double s) {    setA(s);  }
  final public double getRp() {    return getB();  }
  final public void setRp(double s) {    setB(s);  }
  public boolean equals(KinshipIBD v)
  {
    if (this == v)
      return true;
    return ((float)a == (float)v.a && (float)b == (float)v.b);
  }

}
