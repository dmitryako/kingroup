package jm.laguerre;
import numeric.functor.FuncConst;
import numeric.functor.FuncLagrr_1;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.function.FuncArr;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 2, 2004, Time: 2:40:59 PM
 */
public class LagrrArr extends FuncArr {
  final protected double alpha;
  final protected double lambda;
  public LagrrArr(final valarray x, final int size
    , final double alpha, final double lambda) {
    super(x, size);
    this.alpha = alpha;
    this.lambda = lambda;
    load();
  }
  private void load() {
    if (size() == 0)
      return;
    set(0, new FuncVec(getX(), new FuncConst(1.)));
    if (size() == 1)
      return;
    set(1, new FuncVec(getX(), new FuncLagrr_1(alpha, lambda)));
    if (size() == 2)
      return;
    for (int i = 0; i < getX().size(); i++) {
      double x = lambda * getX().get(i);
      int n = 0;
      double L_n_2 = get(n++).get(i); // L_(n-2)
      double L_n_1 = get(n++).get(i); // L_(n-1)
      for (; n < size(); n++) { // n starts from 2
        // (n+1) L_(n+1) = ((2n+a+1)-x) L_n   - (n+a)   L_n_1  // from p588 of russian Abramowitz
        //     n L_n     = ((2n+a-1)-x) L_n_1 - (n-1+a) L_n_2
        double L_n = (((2.0 * n + alpha - 1) - x) * L_n_1 - (n - 1 + alpha) * L_n_2) / n;
        get(n).set(i, L_n); // set (n)'th function
        L_n_2 = L_n_1;
        L_n_1 = L_n;
      }
    }
  }
}
