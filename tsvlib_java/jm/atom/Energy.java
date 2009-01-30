package jm.atom;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/03/2005, Time: 09:52:21
 */
public class Energy {
  public double kin = 0;
  public double pot = 0;
  public Energy(Energy from) {
    this.kin = from.kin;
    this.pot = from.pot;
  }
  public Energy() {
  }
  public Energy(double v, double v2) {
    kin = v;
    pot = v2;
  }
}
