package jm.bspline;
import jm.JMatrixException;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.function.FuncArr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/04/2005, Time: 16:51:09
 */
public class BSplineArr extends FuncArr {
  final private valarray tt;  // true t-knots e.g. {0,0,0,1,.... 4,5,5}
  private int currK = 0;
  private final int MAX_K;
  public BSplineArr(final valarray x
    , final valarray t // knots
    , final int k // order
  ) {
    super(x, calcArraySize(t, k));
    MAX_K = k;
    tt = makeTrueKnots(t, k);
    loadBOne();
  }
  public static int calcArraySize(valarray g, int k) {
    return g.size() + k - 2;
  }
  // g must be g[i] < g[i+1]
  public static valarray makeTrueKnots(valarray g, int k) {
    valarray res = new valarray(g.size() + 2 * k - 2);
    for (int i = 0; i < g.size(); i++) {
      res.set(i + k - 1, g.get(i));
    }
    for (int i = 0; i < k - 1; i++) {
      res.set(i, g.getFirst());
      res.set(res.size() - 1 - i, g.getLast());
    }
//      LOG.report(null, "g=" + g.toString());
//      LOG.report(null, "res=" + res.toString());
    return res;
  }
  // reverse of calcArraySize
  public static int calcKnotsNum(int arraySize, int k) {
    return arraySize - k + 2;
  }
//  public int getOrder() {
//    return MAX_K;
//  }
  public void loadNextK() {
    currK++;
    if (currK >= MAX_K) {
      String mssg = "Trying to exceed maximum K";
      LOG.error(this, mssg, "");
      throw new JMatrixException(mssg);
    }
    valarray x = getX();
    for (int j = 0; j < x.size(); j++) {
      double xj = x.get(j);
//         if (xj > 2) {
//            int debug = 1;
//         }
      for (int i = 0; i < size(); i++) {
        double distL = tt.get(i + currK) - tt.get(i);
        double distR = tt.get(i + currK + 1) - tt.get(i + 1);
        if (distL != 0)
          distL = 1. / distL;
        if (distR != 0)
          distR = 1. / distR;
        double dL = xj - tt.get(i);//      deltal(j) = x - t(left+1-j)
        double dR = tt.get(i + currK + 1) - xj; //      deltar(j) = t(left+j) - x
        FuncVec Bk = get(i);
        double L = dL * Bk.get(j) * distL;
        if (i < size() - 1) {
          double R = dR * get(i + 1).get(j) * distR;
          Bk.set(j, L + R);
        } else {
          Bk.set(j, L);
        }
      }
    }
  }
  public void loadBSplineArr() {
    for (int i = 0; i < MAX_K - 1; i++) {
      loadNextK();
    }
  }
  private void loadBOne() {
    valarray x = getX();
    int i = 0;
    for (int j = 0; j < x.size() && i < size();) {
      double xj = x.get(j);
      double ti = tt.get(i);
      double ti1 = tt.get(i + 1);
//         LOG.report(this, IOx.eol);
//         LOG.report(this, "t[i]=[" + i + "]=" + ti);
//         LOG.report(this, "t[i+1]=[" + (i+1) + "]=" + ti1);
//         LOG.report(this, "x[j]=x[" + j + "]=" + xj);
//         LOG.report(this, "(ti <= xj && xj < ti1) = ("
//                 + ti + "<=" + xj + "&&" + xj + "<" + ti1
//                 + ")=" + (ti <= xj && xj < ti1));
      if (ti <= xj && xj < ti1) {
        get(i).set(j, 1);
        j++;
//            LOG.report(this, "getLine(i).set(j, 1)");
      } else {
        i++;
//            LOG.report(this, "i++");
      }
    }
  }
}