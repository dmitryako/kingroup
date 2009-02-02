package qsar.bench.qsar.MLR;
import Jama.Matrix;
import Jama.QRDecomposition;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/02/2007, Time: 14:05:50
 */
public class MlrAlg extends MlrAlgOld
{
  private static ProjectLogger log = ProjectLogger.getLogger(MlrAlg.class);

  public boolean calc(double[][] Z) {
//    return calcViaQR(Z);
    return calcViaXX(Z);
  }
  private boolean calcViaQR(double[][] z) {
    int nC = z[0].length;
    BitSet checkA = Mtrx.getNonConstCols(z);
    if (checkA.cardinality() != nC) {
      String error = "Redundant data: Const cols";
      log.trace(error);
      z = Mtrx.getCols(checkA, z);
      log.trace("new z =\n", new Mtrx(z));
    }
//    if (findSameXRows(z)) {
////      z = removeConstCols(checkA, z);
//      String error = "Redundant data: identical X-rows";
//      log.debug(error);
//    }

    double[] yt = Mtrx.getCol(0, z); // store y's
    double[][] Y = Mtrx.getCols(0, 1, z); // [nRx1]
    Mtrx.setCol(0, 1., z);
//    log.info("\nz=\n" + Vec.toString(z));

    Matrix mY = new Matrix(Y);
    Matrix mX = new Matrix(z);

    QRDecomposition qr = mX.qr();
    Matrix mQ = qr.getQ();
    log.trace("mQ =\n", new Mtrx(mQ.getArray()));

    Matrix mQt = mQ.transpose();
    Matrix mR = qr.getR();
    log.trace("mR =\n", new Mtrx(mR.getArray()));

    log.debug("mR.det = ", mR.det());
    Matrix invR = mR.inverse();
    log.trace("invR =\n", new Mtrx(invR.getArray()));

    Matrix mB = mQt.times(mY);
    mB = invR.times(mB);
    mlrCoeffs = Mtrx.getCol(0, mB.getArray());
    log.trace("a =", new Vec(mlrCoeffs));

    Matrix eY = mX.times(mB); // estimated
    yPred = Mtrx.getCol(0, eY.getArray());
    log.trace("eY =", new Vec(yPred));

    if (checkA.cardinality() != nC) {
////      log.info("\na=\n" + Vec.toString(a));
      mlrCoeffs = insertConstCols(nC, checkA, mlrCoeffs);
////      log.info("\na=\n" + Vec.toString(a));
    }
    Mtrx.setCol(0, yt, z); // restore
    return true;
  }
}
