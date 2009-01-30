package javax.utilx.triplet;

import javax.utilx.pair.DoublePair;

/**
 * Copyright KinGroup Team.
 * User: eng-nbb, Date: 11/04/2006, Time: 12:50:35
 */
public class DoubleTriplet extends DoublePair {
  public double c;

  public DoubleTriplet(double a, double b, double c) {
    super(a, b);
    this.c = c;
  }

  public String toString() {
    return "(" + a + ", " + b + ", " + c + ")";
  }
}
