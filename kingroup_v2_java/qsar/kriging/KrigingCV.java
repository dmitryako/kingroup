package qsar.kriging;
import Jama.Matrix;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/01/2007, Time: 15:59:57
 */
public class KrigingCV implements LooAlgI
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  public double calc(int idx, double[][] z){
    int nR = z.length;
    int nC = z[0].length;
    double[][] C = new double[nR][nR];
    double[][] D = new double[nR][1];
    for (int r = 0; r < nR; r++) {
      if (r == idx)
        D[r][0] = 1;
      else {
        double d = Vec.dist(1, nC, z[r], z[idx]);
        D[r][0] = cov(d);
      }

      for (int c = 0; c <= r; c++) {
        if (r == idx && c == idx) {
            C[r][c] = 0;
        }
        else if (r == idx || c == idx) {
          C[r][c] = 1;
          if (r == c)
            C[r][c] = 0;
        }
        else {
          double d = Vec.dist(1, nC, z[r], z[c]);
          C[r][c] = cov(d);
        }
        C[c][r] = C[r][c]; // symmetric
      }
    }
//    log.info("C=\n" + DoubleArr.toString(C));
//    log.info("D=\n" + DoubleArr.toString(D));

    Matrix mC = new Matrix(C);
    Matrix mCI = mC.inverse();
    log.info("\ndet["+idx+"] = " + mC.det());

    Matrix mD = new Matrix(D);
    Matrix mW = mCI.times(mD);

    log.info("\ndet["+idx+"] = " + mC.det());
    log.info("mW=\n" + Mtrx.toString(mW.getArray()));
    log.info("mD=\n" + Mtrx.toString(mD.getArray()));

    double res = 0;
    for (int r = 0; r < nR; r++) {
      if (r != idx)
        res += z[r][0] * mW.get(r, 0);
    }
    return res;
  }

  private double cov(double d)
  {
//    return 0.1 * Math.exp(-0.2 * d);
    return Math.max(0, 1.5 - 0.17 * d);
//    return Math.max(0, 0.2 - 0.2 * d);
  }
}
