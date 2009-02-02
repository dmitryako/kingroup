package qsar.bench.qsar.MLR;
import tsvlib.project.ProjectLogger;

import javax.stats.Stats;
import javax.utilx.arrays.vec.Vec;
import java.util.Arrays;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 17/06/2007, 08:51:03
 */
public class MlrRes extends Stats
{
  private static ProjectLogger log = ProjectLogger.getLogger(MlrRes.class);
  protected double[] yPred = null;
  protected double[] mlrCoeffs = null;
  protected double[][] z; // calibration

  public MlrRes() {

  }
  public MlrRes(double[] Y, double[] Yp, int p) {
    calcMLR(Y, Yp, p);
  }
  public void calcMLR(double[] y, double[] yp, int p)
  {
    int n = y.length;
    double[] e = new double[n];
    Stats sY = new Stats(y);
    double mY = sY.getMean();
    double SSR = 0;      //Sum of squares due to regression
    double SSE = 0;      //Sum of squares due to error
    double SST = 0;      //Total sum of squares
    double MAE = 0;
    for (int i = 0; i < n; i++) {
      double v = yp[i] - y[i];
      e[i] = v;
      SSE += v * v;
      MAE += Math.abs(v);

      v = yp[i] - mY;
      SSR += v * v;

      v = y[i] - mY;
      SST += v * v;
    }
    log.debug("e = ", new Vec(e));
    log.debug("SSE = ", SSE);
    log.debug("SSR = ", SSR);
    log.debug("SST = ", SST);

    calc(e);

    Vec.abs(e);
    Arrays.sort(e); // needed later!!!
    setMedAE(Vec.calcMedianFromSorted(e));            log.debug("medAbsErr = ", getMedAE());

    double robCorr = robCorr(y, yp);
    setRobCorr(robCorr);                      log.debug("robCorr = ", robCorr);

    double corr = corr(y, yp);
    setCorr(corr);                      log.debug("corr = ", corr);

    int nTrim = Math.max((n + p + 2) / 2, 1);
    setTmae(Vec.sum(e, nTrim) / nTrim);                    log.debug("trimmed meanAbsErr = ", getTmae());

    Vec.square(e);
    setTmse(Vec.sum(e, nTrim) / nTrim);                    log.debug("trimmed meanSqrErr = ", getTmse());

//    double r2 = SSR / SST; //WRONG!!!!!!!!!!!!
    double r2 = 1 - SSE / SST;
    setR2(r2);                      log.debug("R2 = ", r2);
    double MSE = SSE / (n - p - 1);    log.debug("s^2=MSE=SSE/(n-p-1) = ", MSE);
    setS2(MSE);                    log.debug("s = ", getS());
    double MSEP = SSE / n;             log.debug("MSEP=SSE/n = ", MSEP);
    setMse(MSEP);                 log.debug("RMSEP=sqrt(MSEP) = ", getRmse());
    double MSR = SSR / p;  //http://www-stat.stanford.edu/~jtaylo/courses/stats191/notes/multiple.pdf
    setMsr(MSR);                   log.debug("MSR = ", MSR);
    double f = MSR / MSE;     //http://www.unesco.org/webworld/idams/advguide/Chapt5_2.htm
    setF(f);                       log.debug("F = ", f);
    double MST = SST / (n - 1);
    setMst(MST);                   log.debug("MST = ", MST);
    double ra2 = 1. - MSE / MST;
    setRa2(ra2);                     log.debug("Ra2 = ", ra2);
    MAE /= n;
    setMae(MAE);                   log.debug("MAE = ", MAE);
  }


  public double[] getYPred()
  {
    return yPred;
  }
  public double[][] getCalibZ()
  {
    return z;
  }
  public double[] getMlrCoeffs()
  {
    return mlrCoeffs;
  }
  public void setMlrCoeffs(double[] a)
  {
    mlrCoeffs = a;
  }

  public void multMlrCoeffs(double d) {
    Vec.mult(d, mlrCoeffs);
  }

}
