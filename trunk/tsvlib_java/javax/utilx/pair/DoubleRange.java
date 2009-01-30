package javax.utilx.pair;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/12/2006, Time: 10:36:16
 */
public class DoubleRange extends DoublePair
{
  public DoubleRange(double minV, double maxV) {
    super(minV, maxV);
  }
  public double getMin() { return a;}
  public void setMin(double v) {a = v;}
  public double getMax() { return b;}
  public void setMax(double v) {b = v;}
  public double getRange() {return b - a;}
  public boolean isFloatConst() {return (float)getRange() == 0f; }
  public boolean isDoubleConst() {return getRange() == 0; }
}
