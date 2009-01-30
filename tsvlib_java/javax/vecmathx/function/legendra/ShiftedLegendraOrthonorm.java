package javax.vecmathx.function.legendra;
import stlx.valarrayx.valarray;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 10, 2004, Time: 10:23:04 AM
 */
//  Orthonormal version: pp579-580 from Russian copy of Abramowitz
//   Shifted Legendra's polinomials   P_n(y), n = 0,..., nmax
//   oo
//   I dy P_n(y) P_n'(y) = 1/(2n+1)
//   0
public class ShiftedLegendraOrthonorm extends ShiftedLegendraArray {
  public ShiftedLegendraOrthonorm(valarray x, int size) {
    super(x, size);
    normalize();
  }
  private void normalize() {
    for (int n = 0; n < size(); n++) {
      get(n).scale(Math.sqrt(2 * n + 1));
    }
  }
}
