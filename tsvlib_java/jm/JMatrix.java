package jm;
import jm.atom.Energy;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.function.FuncArr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/03/2005, Time: 12:08:09
 */
public class JMatrix {
  private static final double IGNORE = 1e-32;
  public static boolean isZero(double v) {
    return Math.abs(v) < IGNORE;
  }
  public static boolean isZero(Energy e) {
    return isZero(e.kin + e.pot);
  }
  public static void trimTailSLOW(FuncVec f) {
    int newSize = f.size();
    for (int i = f.size() - 1; i >= 0; i--) {
      if (isZero(f.get(i)))
        newSize--;
      else
        break;
    }
    f.setSize(newSize);
  }
  public static void trimTailSLOW(FuncArr arr) {
    for (int i = 0; i < arr.size(); i++) {
      trimTailSLOW(arr.get(i));
    }
  }
}
