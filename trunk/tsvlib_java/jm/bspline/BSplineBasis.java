package jm.bspline;
import stlx.valarrayx.SymmetricMatrix;
import stlx.valarrayx.valarray;

import javax.vecmath.GMatrix;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.integration.Quadr;
import javax.vecmathx.integration.Quadr2;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/04/2005, Time: 11:08:33
 */
public class BSplineBasis extends BSplineArr {
  public BSplineBasis(valarray x, valarray t, int k) {
    super(x, t, k);
  }

//   public static valarray makeBSplineKnotsFromGrid(int basisSize, valarray r, int k) {
//      int knotsNum = calcKnotsNumFromBasisSize(basisSize, k); //
  public static valarray makeBSplineKnotsFromGrid(int knotsNum, valarray r, int k) {
    valarray t = new valarray(knotsNum);
//      t.set(0, r.first());// t[0]=r[0]
    t.set(knotsNum - 1, r.getLast());// t[last]=r[last]
    int idxStep = (r.size() - 1) / (knotsNum - 1);
    for (int i = 0; i < knotsNum - 1; i++) {
      t.set(i, r.get(i * idxStep));
    }
    return t;
  }
  public void makeOrthonorm(Quadr w) {
    Quadr2 iw = new Quadr2(w.getNormWeights());
    SymmetricMatrix BB = new SymmetricMatrix(size());
    for (int r = 0; r < size(); r++) {
      for (int c = 0; c <= r; c++) {
        BB.set(r, c, iw.calc(get(r), get(c)));
      }
    }
//      LOG.report(this, "BB=\n" + BB.toString());
    valarray[] CC = new valarray[size()];
    // F_0 = B_0
    int i = 0;
    CC[i] = new valarray(i + 1);
    CC[i].set(i, 1.);  // i:=0
    i++;

    // F_1 = B_1 + C_0 * B_0
    // <F_1 B_0> = BB_01 + C_0 BB_00 = 0
    CC[i] = new valarray(i + 1);
    CC[i].set(i, 1.);
    CC[i].set(i - 1, -BB.get(0, 1) / BB.get(0, 0));
    i++;

    // F_i = B_i + SUM(j<i) C_j * B_j
    // <F_i B_j'> = BB_ij' + SUM(j<i) C_j BB_jj'= 0
    // M * C = D
    // C = M^-1 * D
    for (; i < size(); i++) {
      int N = i;
//         LOG.report(this, "N=" + N);
//         LOG.report(this, "i=" + i);
      valarray D = new valarray(N);
      valarray C = new valarray(N);
      GMatrix M = new GMatrix(N, N);
      for (int j = 0; j < N; j++) {
        D.set(j, -BB.get(i, j));
        for (int J = 0; J < N; J++) {
          M.setElement(j, J, BB.get(j, J));
        }
      }
//         LOG.report(this, "M=\n" + M.toString());
//         LOG.report(this, "D=\n" + D.toString());
      M.invert();
      C.mul(M, D);
      CC[i] = new valarray(N + 1);
      CC[i].set(N, 1);
      for (int j = 0; j < N; j++) {
        CC[i].set(j, C.get(j));
      }
    }
    makeOrthog(CC);
    norm(iw);
  }
  private void makeOrthog(valarray[] C) {
    FuncArr res = new FuncArr(getX(), size());
    for (int ix = 0; ix < getX().size(); ix++) {
      for (int i = 0; i < size(); i++) {
        valarray Cj = C[i];
        double sum = 0;
        int N = Cj.size();
        for (int j = 0; j < N; j++) {
          sum += Cj.get(j) * get(j).get(ix);
        }
        res.get(i).set(ix, sum);
      }
    }
    setArr(res.getArray());
  }
  private void norm(Quadr2 iw) {
    for (int r = 0; r < size(); r++) {
      double N = iw.calc(get(r), get(r));
      N = 1. / Math.sqrt(N);
      get(r).scale(N);
    }
  }
}
