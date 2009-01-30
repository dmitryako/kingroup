package javax.stats;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 18, 2004, Time: 9:08:15 AM
 */
public class Stats extends StatsRes {
  private static ProjectLogger log = ProjectLogger.getLogger(Stats.class);

  public Stats(){}
  public Stats(double[] y)
  {
    calc(y);
  }

  public void calc(double[] from) {
    if (from == null || from.length == 0)
      return ;
    if (from.length == 1) {
      setMean(from[0]);
      return ;
    }
    final int n = from.length;
    double m = Vec.avr(from);
    double sum = 0;
    for (int i = 0; i < n; i++) {
      double v = from[i] - m;
      sum += v * v;
    }
    setMean(m);
    double var = sum / (double)(n - 1);
    setS2(var);
  }

  public static StatsRes toMeanZeroVarOne(double[] arr)
  {
    StatsRes res = new Stats(arr);
    double m = res.getMean();
    double d = 1.;
    if (res.getS() != 0.0)
      d = 1. / res.getS();
    for (int i = 0; i < arr.length; i++) {
      double v = arr[i];
      arr[i] = (v - m) * d;
    }
    return res;
  }


  public static double[][] makeColsMeanZeroVarOne(double[][] z)
  {
    double[][] zt = Mtrx.trans(z);
    zt = Stats.makeRowsMeanZeroVarOne(zt);
    zt = Mtrx.trans(zt);
    log.debug("res = \n", new Mtrx(zt));
    return zt;
  }
  public static double[][] makeRowsMeanZeroVarOne(double[][] arr)
  {
    for (int i = 0; i < arr.length; i++) {
      StatsRes st = Stats.toMeanZeroVarOne(arr[i]);
//      log.info("stats = \n" + st);
//      st = new Stats(arr[i]);
//      log.info("stats after renorm= \n" + st);
    }
    return arr;
  }

  public static double[] corr(double[][] arr, int rowIdx)
  {
//    log.info("\narr= \n" + Vec.toString(arr));
    int nR = arr.length;
    double[] res = new double[nR];
    double normY = 1. / Math.sqrt(cov(arr[rowIdx], arr[rowIdx]));
    for (int r = 0; r < nR; r++) {
      double normX = 1. / Math.sqrt(cov(arr[r], arr[r]));
      double v = cov(arr[r], arr[rowIdx]) * normX * normY;
      res[r] = v;
    }
    return res;
  }

  public static double[][] corr(double[][] arr)
  {
    double[][] A = cov(arr);
    int n = arr.length;
    double[][] res = new double[n][n];
    for (int r = 0; r < res.length; r++) {
      double normR = 1. / Math.sqrt(A[r][r]);
      for (int c = 0; c <= r; c++) {
        double normC = 1. / Math.sqrt(A[c][c]);
        double v = A[r][c] * normR * normC;
        res[r][c] = v;
        res[c][r] = v;
      }
    }
    return res;
  }

  public static double[][] cov(double[][] arr)
  {
    int n = arr.length;
    double[][] res = new double[n][n];
    for (int r = 0; r < res.length; r++) {
      for (int c = 0; c <= r; c++) {
        double v = cov(arr[r], arr[c]);
        res[r][c] = v;
        res[c][r] = v;
      }
    }
    return res;
  }

  public static double corr(double[] A, double[] B) {
    double ab = cov(A, B);
    return  ab / Math.sqrt(cov(A, A) * cov(B, B));
  }
  public static double[] robNorm(double[] x) {
    double[] xx = Vec.copy(x);
    double medX = Vec.medianSLOW(x);   // MED(x)
    Vec.add(-medX, xx);                // x - MED(x)
    double var = robVar(x);
    if (var > 0) {
      Vec.mult(1./Math.sqrt(var), xx);                // (x - MED(x))/sqrt(var)
    }
    return xx;
  }
  public static double robVar(double[] x) {
    double[] xx = Vec.copy(x);
    double medX = Vec.medianSLOW(x);   // MED(x)
    Vec.add(-medX, xx);                // x - MED(x)
    Vec.square(xx);
    double res = Vec.medianSLOW(xx);  // var(x)=MED [x - MED(x)]^2
    return res;
  }
  public static double[] robNorm2(double[] x) {
    double[] xx = Vec.copy(x);
    double medX = Vec.medianSLOW(x);   // MED(x)
    Vec.add(-medX, xx);                // x - MED(x)
    double[] absXX = Vec.copy(xx);
    Vec.abs(absXX);                    // |x - MED(x)|
    double medAbsXX = Vec.medianSLOW(absXX);  // MAD(x)=MED|x - MED(x)|
    Vec.mult(1./medAbsXX, xx);                // (x - MED(x))/MAD(x)
    return xx;
  }

  public static double robMad(double[] x) {
    double[] xx = Vec.copy(x);
    double medX = Vec.medianSLOW(x);   // MED(x)
    Vec.add(-medX, xx);                // x - MED(x)
    Vec.abs(xx);                    // |x - MED(x)|
    double res = Vec.medianSLOW(xx);  // MAD(x)=MED|x - MED(x)|
    return res;
  }

  public static double robCorr2(double[] x, double[] y) {
    double[] xx = robNorm2(x);
    double[] yy = robNorm2(y);
    double[] v = Vec.sum(1., xx, -1., yy);
    double[] u = Vec.sum(1., xx, 1., yy);
    Vec.abs(v);
    Vec.abs(u);
    double varV = Vec.medianSLOW(v);
    double varU = Vec.medianSLOW(u);
    varU *= varU;
    varV *= varV;
    double top = varU - varV;
    double bot = varU + varV;
    if (bot == 0) {
      return 1;
    }
    return  top / bot;
  }

  public static double robCorr(double[] x, double[] y) {
    double[] xx = robNorm(x);
    double[] yy = robNorm(y);
    double[] v = Vec.sum(1., xx, -1., yy);
    double[] u = Vec.sum(1., xx, 1., yy);
    double varV = robVar(v);
    double varU = robVar(u);
    double top = varU - varV;
    double bot = varU + varV;
    if (bot == 0) {
      return 1;
    }
    return  top / bot;
  }

  public static double r2(double[] A, double[] B) {
    double ab = corr(A, B);
    return  ab * ab;
  }
  public static double robustR2(double[] A, double[] B) {
    double ab = robCorr(A, B);
    return  ab * ab;
  }
  public static double cov(double[] A, double[] B)
  {
    if (A.length != B.length)
      throw new RuntimeException("A.length != B.length");
    double a = Vec.avr(A);
    double b = Vec.avr(B);

    double res = 0;
    for (int i = 0; i < A.length; i++) {
      res += (A[i] - a) * (B[i] - b);
    }
    res /= (A.length - 1);
    return res;
  }

  public static double[][] calcGaussianKernel(double[][] arr, double q)
  {
    int n = arr.length;
    double[][] res = new double[n][n];
    for (int r = 0; r < res.length; r++) {
      for (int c = 0; c <= r; c++) {
        double v = calcGaussianKernel(arr[r], arr[c], q);
        res[r][c] = v;
        res[c][r] = v;
      }
    }
    return res;
  }

  private static double calcGaussianKernel(double[] A, double[] B, double q)
  {
    double dist = Vec.distL2(A, B);
    return Math.exp(-q * dist);
  }

  public static StatsRes selectColsByCorr(double[][] z, int nCols, double minR2)
  {
    z = Mtrx.trans(z);
    StatsRes res = Stats.selectRowsByCorr(z, nCols, minR2); //
    z = res.getArr();
    res.setArr(Mtrx.trans(z));
    return res;
  }
  public static StatsRes selectRowsByCorr(double[][] x, int nKeptRows, double maxR2)
  {
    double[] pc = Stats.corr(x, 0); // Pearson correlation coefficient
    log.info("\npc= \n" + Vec.toString(pc));

    int[] idxOrder = Vec.sortIdxByAbs(pc, false);
    log.info("idxOrder= \n" + IntVec.toString(idxOrder));

    double[] s = Vec.order(pc, idxOrder); // sorted
    log.info("testOrd= \n" + Vec.toString(s));
    log.info("testOrd[nKeptRows]= \n" + (float)s[nKeptRows -1]);

//    x = Vec.selectRows(x, idxOrder, nKeptRows); // retain first N_VARS descriptors
    StatsRes res = selectRows(x, idxOrder, nKeptRows, maxR2); // retain first N_VARS descriptors
    x = res.getArr();
    log.info("\nres = \n" + Mtrx.toString(x));
    return res;
  }

  public static StatsRes selectRows(double[][] from, int[] oldOrder, int nRows, double maxR2)
  {
//    log.info("from= \n" + Vec.toString(from));
    StatsRes res = new StatsRes();
    int n = from.length;
    double[][] newArr = new double[nRows][0];
    int[] newOrder = new int[oldOrder.length];
    int idx = 0;
    for (int i = 0; i < n && idx < nRows; i++) {
      double[] v = from[oldOrder[i]];
      boolean ignore = false;
      for (int j = 1; j < idx; j++) { // NOTE!!! j=1 do not test against Y
        double r2 = Stats.r2(newArr[j], v);
//        log.info("\nres[j]= \n" + Vec.toString(newArr[j])
//          + "\nv=\n" + Vec.toString(v)
//          + "\nr2=" + r2);
        if (r2 > maxR2) {
          ignore = true;
          break;
        }
      }
      if (ignore)
        continue;
      newOrder[idx] = oldOrder[i];
      newArr[idx++] = v;
    }
    res.setArr(newArr);
    res.setIdxArr(newOrder);
//    log.info("newArr= \n" + Vec.toString(newArr));
    log.info("oldOrder= \n" + IntVec.toString(oldOrder));
    log.info("newOrder= \n" + IntVec.toString(newOrder));
    return res;
  }


  public static double rmse(double[] dy)
  {
    double res = 0;
    for (int i = 0; i < dy.length; i++) {
      double v = dy[i];
      res += v * v;
    }
    return Math.sqrt(res / (double)dy.length);
  }
  public static double mae(double[] dy)
  {
    double res = 0;
    for (int i = 0; i < dy.length; i++) {
      double v = dy[i];
      res += Math.abs(v);
    }
    return res / (double)dy.length;
  }

}
