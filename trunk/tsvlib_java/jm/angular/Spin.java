package jm.angular;
import javax.iox.LOG;
import javax.vecmathx.VecMathException;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 3:39:23 PM
 */
public class Spin {
  final public static Spin ELECTRON = new Spin(1, 2);
  final public static Spin SINGLET = new Spin(0, 1);
  final public static Spin TRIPLET = new Spin(1, 1);
  final public int s2_1;
  final public int numer;
  final public int denom;
  // The top number is called the numerator. The bottom number is called the denominator
  public Spin(int numer, int denom) {
    s2_1 = (2 * numer + denom) / denom;
    this.denom = denom;
    this.numer = numer;
  }
  final public boolean equals(Spin obj) {
    if (obj == this)
      return true;
    return (s2_1 == obj.s2_1);
  }
  final public int getInt() {
    if (denom != 1) {
      String mssg = "int value is not available for " + numer + "/" + denom;
      LOG.error(this, mssg, "");
      throw new VecMathException(mssg);
    }
    return numer;
  }
}
