package jm.harmonic;
import jm.laguerre.LagrrArr;
import numeric.functor.Func;
import numeric.functor.FuncGamma;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.ApplyToArr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/04/2005, Time: 08:22:32
 */
/*
Vanroose etal PhysRevLett 88, p010404-1

P(nL;r)=(-1)NORM(nL) (r/b)^L exp(-1/2*(r/b)^2) L^(L+1/2)_n((r/b)^2)
NORM(nL)=b^-(3/2) sqrt(2n!/Gamma(n+l+3/2))
*/
public class HarmonicBasis extends LagrrArr {
  private final int L_;
  // NOTE!!!  r^2 is given
  // alpha = L + 1/2
  // lambda = 1/(B*B)
  public HarmonicBasis(valarray r2, int size, int L, double B) {
    super(r2, size, 0.5 + L, 1. / (B * B));
    L_ = L;
    ApplyToArr.mult(this, new functor_norm());
    normalize();
  }
  private class functor_norm implements Func {
    public double calc(double r2) {
      double x = lambda * r2;
      if (x == 0)
        return 0;
      return Math.exp(-0.5 * x + 0.5 * (L_ + 1) * Math.log(x));
    }
  }
  private void normalize() {
    FuncGamma G = new FuncGamma();
    double b = Math.sqrt(lambda);
    for (int n = 0; n < size(); n++) {
      double norm_n = Math.sqrt(2. * b * G.apply(n + 1).div(G.apply(alpha + n + 1)).real());
      get(n).scale(norm_n);
    }
  }
}
