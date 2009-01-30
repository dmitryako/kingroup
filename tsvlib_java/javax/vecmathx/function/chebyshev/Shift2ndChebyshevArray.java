package javax.vecmathx.function.chebyshev;
import numeric.functor.FuncConst;
import numeric.functor.functor_chebyshev_kind2_shifted_1;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.function.FuncArr;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 10, 2004, Time: 1:06:02 PM
 */
/* U_n(cos(theta)) = sin((n + 1)theta)/sin(theta)
*/
public class Shift2ndChebyshevArray extends FuncArr {
  public Shift2ndChebyshevArray(final valarray x, final int size) {
    super(x, size);
    loadChebyshevArray();
  }
  // U*(x) = U(2x-1), p584
  private void loadChebyshevArray() {
    if (size() == 0)
      return;
    set(0, new FuncVec(getX(), new FuncConst(1.)));
    if (size() == 1)
      return;
    set(1, new FuncVec(getX(), new functor_chebyshev_kind2_shifted_1()));
    if (size() == 2)
      return;
    for (int i = 0; i < getX().size(); i++) {
      double x = getX().get(i);
      int n = 0;
      double L_n_2 = get(n++).get(i); // L_(n-2)
      double L_n_1 = get(n++).get(i); // L_(n-1)
      for (; n < size(); n++) { // n starts from 2
        //  L_(n+1) = (-2+4x) L_n + L_n_1  // from p588 of russian Abramowitz
        double L_n = (-2. + 4 * x) * L_n_1 - L_n_2;
        get(n).set(i, L_n); // set (n)'th function
        L_n_2 = L_n_1;
        L_n_1 = L_n;
      }
    }
  }
}
