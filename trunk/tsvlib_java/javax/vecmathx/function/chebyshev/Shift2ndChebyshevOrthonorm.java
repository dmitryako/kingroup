package javax.vecmathx.function.chebyshev;
import numeric.functor.Func;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.ApplyToArr;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 10, 2004, Time: 1:06:37 PM
 */
public class Shift2ndChebyshevOrthonorm extends Shift2ndChebyshevArray {
  public Shift2ndChebyshevOrthonorm(valarray x, int size) {
    super(x, size);
    ApplyToArr.mult(this, new functor_norm());
    normalize();
  }
  private void normalize() {
    for (int n = 0; n < size(); n++) {
      get(n).scale(Math.sqrt(8. / Math.PI));
    }
  }
  private class functor_norm implements Func {
    public double calc(double x) {
      if (x <= 0 || x >= 1)
        return 0;
      return Math.pow(x - x * x, 0.25);
    }
  }
}

