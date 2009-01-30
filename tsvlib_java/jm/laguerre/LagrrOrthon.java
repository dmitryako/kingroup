package jm.laguerre;
import numeric.functor.Func;
import numeric.functor.FuncGamma;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.ApplyToArr;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 2, 2004, Time: 4:04:31 PM
 */
//  Orthonormal version: pp579-580 from Russian copy of Abramowitz
//                             (a)
//   LagrrBasis's polinomials   L  (y), n = 0,..., nmax
//                              n
//   oo   (a)    (a)    -y  a
//   I   L  (y) L  (y) e   y  dy = Gamma(a + n + 1) / n!
//   0     n     n'
//
//     y = lambda * r; dr = dy/lambda
//
//   oo   (a)             (a)             -y  a
//   I   L  (lambda * r) L  (lambda * r) e   y  dr = 1/lambda * Gamma(a + n + 1) / n!
//   0     n               n'
//                                                oo
//Set of orthonormal functions L_n(r), such that I L_n(r) L_n'(r) dr = delta_nn'
//                                                0
// NOTE: integration is dr  not  r**2 dr
public class LagrrOrthon extends LagrrArr {
  public LagrrOrthon(valarray x, int size, int alpha, double lambda) {
    super(x, size, alpha, lambda);
    ApplyToArr.mult(this, new ThisNormFunc());
    normalize();
  }
  private class ThisNormFunc implements Func {
    public double calc(double r) {
      double x = lambda * r;
      if (x == 0)
        return 0;
      return Math.exp(-0.5 * x + 0.5 * alpha * Math.log(x));
    }
  }
  private void normalize() {
    if (alpha <= 1e-16) {
      normalize_zero_alpha();
      return;
    }
    FuncGamma G = new FuncGamma();
    for (int n = 0; n < size(); n++) {
      double norm_n = 1.0 / Math.sqrt(G.apply(alpha + n + 1).div(G.apply(n + 1)).real() / lambda);
      get(n).scale(norm_n);
    }
  }
  private void normalize_zero_alpha() {
    double norm_n = Math.sqrt(lambda);
    for (int n = 0; n < size(); n++) {
      get(n).scale(norm_n);
    }
  }
}
