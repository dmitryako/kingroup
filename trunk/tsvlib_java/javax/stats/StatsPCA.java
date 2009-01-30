package javax.stats;
import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import javax.utilx.arrays.mtrx.Mtrx;
import java.util.logging.Logger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/12/2006, Time: 15:21:50
 */
public class StatsPCA
{
  protected static final Logger log = Logger.getAnonymousLogger();

  /* x_rc; r - variable/descriptor in rows; c - observations by columns
  */
  public static double[][] calc(double[][] aX) {
    Stats.makeRowsMeanZeroVarOne(aX);
//    log.info("renorm arr=\n" + DoubleArr.toString(aX));

    double[][] C = Stats.cov(aX);
//    log.info("cov= \n" + DoubleArr.toString(C));

    Matrix mA = new Matrix(C);
    EigenvalueDecomposition res = mA.eig();
//    log.info("eig_val =\n" + DoubleArr.toString(res.getRealEigenvalues()));
//    double[][] v = res.getV().getArray();
//    log.info("eig_vec =\n" + DoubleArr.toString(v));

    // TEST diagY = P' * A * P
    Matrix PT = res.getV().transpose();
    Matrix P = res.getV();
    Matrix diagY = PT.times( mA.times(P));
//    log.info("diagY = P' * A * P =\n" + DoubleArr.toString(diagY.getArray()));

    Matrix X = new Matrix(aX);
    Matrix Y = PT.times(X);
    double[][] aY = Y.getArray();
    aY = Mtrx.mirrorRows(aY);
    return aY;
  }
}
