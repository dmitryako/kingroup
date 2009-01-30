package javax.vecmathx.function.legendra;
import numeric.functor.FuncConst;
import numeric.functor.functor_legendra_1;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.function.FuncArr;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 10, 2004, Time: 10:36:59 AM
 */
public class ShiftedLegendraArray extends FuncArr {
  public ShiftedLegendraArray(final valarray x, final int size) {
    super(x, size);
    loadLegendraArray();
  }
  private void loadLegendraArray() {
    if (size() == 0)
      return;
    set(0, new FuncVec(getX(), new FuncConst(1.)));
    if (size() == 1)
      return;
    set(1, new FuncVec(getX(), new functor_legendra_1()));
    if (size() == 2)
      return;
    for (int i = 0; i < getX().size(); i++) {
      double x = getX().get(i);
      int n = 0;
      double L_n_2 = get(n++).get(i); // L_(n-2)
      double L_n_1 = get(n++).get(i); // L_(n-1)
      for (; n < size(); n++) { // n starts from 2
        // (n+1) L_(n+1) = ((-2n-1)+(4n+2)x) L_n   -  n    L_n_1  // from p588 of russian Abramowitz
        //     n L_n     = ((-2n+1)+(4n-2)x) L_n_1 - (n-1) L_n_2
        double L_n = (((-2.0 * n + 1) + (4 * n - 2) * x) * L_n_1 - (n - 1) * L_n_2) / n;
        get(n).set(i, L_n); // set (n)'th function
        L_n_2 = L_n_1;
        L_n_1 = L_n;
      }
    }
  }
}
