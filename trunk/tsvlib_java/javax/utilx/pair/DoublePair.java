package javax.utilx.pair;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/04/2005, Time: 12:38:48
 */
public class DoublePair {
  public double a;
  public double b;
  public DoublePair(double a, double b) {
    this.a = a;
    this.b = b;
  }
  public DoublePair() {
  }
  public double getA()  {    return a;  }
  public double getB()  {    return b;  }
  public void setA(double s) {    a = s;  }
  public void setB(double s) {    b = s;  }
}
