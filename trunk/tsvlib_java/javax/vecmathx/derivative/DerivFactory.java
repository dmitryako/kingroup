package javax.vecmathx.derivative;
import javax.vecmathx.function.FuncVec;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 3:56:40 PM
 */
public class DerivFactory {
  public static FuncVec makeDeriv(FuncVec f) {
    FuncVec res = null;
    if (f.size() >= 9) {
      res = new DerivPts9(f);
    } else if (f.size() >= 5) {
      res = new DerivPts5(f);
    } else if (f.size() >= 3) {
      res = new DerivPts3(f);
    }
    return res;
  }
}
