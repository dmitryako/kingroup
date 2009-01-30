package jm.laguerre;
import jm.JMatrix;
import numeric.functor.FuncPow;

import javax.vecmathx.function.FuncVec;

// Froese-Fisher basis
// NOTE!!! In the following transformation all radial extremes have been removed, eg z/r or L(L+1)/r2
//                                                oo
//Set of orthonormal functions R_nl(r), such that I R_nl(r) R_n'l(r) r^2 dr = delta_nn'
//                                                0
// After  Fn(r) = sqrt(r)*Rn(r)
// and r = exp(x);   dr = r dx
//                        2              2
//                     1 d        (L+1/2)
// r^2 * Hamiltonian = - - --   + -------- + r*Z = r^2 * H(X)
//                     2 dx^2        2
//  oo
//  |  r^2 dx Fn'(exp(x)) H Fn(exp(x)) = delta_n'n
// -oo
// w are for 'x' integral
public class LaguerreLogR extends LagrrOrthon {
  public LaguerreLogR(FuncVec xToR, int size, int alpha, double lambda) {
    super(xToR, size, alpha, lambda); // NOTE!!! y is passed not x
    loadLogRBasis();
    changeGrid(xToR.x); // set to X-x !!!!!******
    JMatrix.trimTailSLOW(this);
  }
  //   Rn(r) = Ln(r) / r; since Ln is interable with dr not r^2dr
  //   Fn(x) = sqrt(r) L_n(y) / r = L_n(y) / sqrt(r);  with rdr  or  r^2dx   for the integral
  private void loadLogRBasis() {
    final FuncVec scale = new FuncVec(getX(), new FuncPow(1., -0.5));
    for (int n = 0; n < size(); n++) {
      get(n).mult(scale);
    }
  }
}
