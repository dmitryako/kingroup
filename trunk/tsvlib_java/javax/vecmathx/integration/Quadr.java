package javax.vecmathx.integration;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.function.FuncArr;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 4, 2004, Time: 6:42:33 PM
 */
public class Quadr extends FuncVec {
  public Quadr(valarray x) {
    super(x, x.size());
  }
  public valarray getNormWeights() {
    return this;
  }
  public double calcMaxOrthonErr(FuncArr arr) {
    double res = 0;
    for (int n = 0; n < arr.size(); n++) {
      for (int n2 = 0; n2 <= n; n2++) {
        double norm = FastLoop.dot(arr.get(n), arr.get(n2), getNormWeights());
        LOG.report(this, "norm error[" + n + ", " + n2 + "]=" + (float) norm);
        if (n == n2)
          norm = Math.abs(1. - norm);
        else
          norm = Math.abs(norm);
        if (res < norm) {
          res = norm;
        }
      }
    }
    return res;
  }
}
