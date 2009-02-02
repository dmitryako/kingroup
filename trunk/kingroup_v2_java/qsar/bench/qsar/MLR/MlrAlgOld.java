package qsar.bench.qsar.MLR;
import Jama.Matrix;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 29/04/2007, 08:51:01
 */
public class MlrAlgOld extends MlrRes
{
  private static ProjectLogger log = ProjectLogger.getLogger(MlrAlgOld.class);

  public boolean calc(double[][] Z) {
    return calcViaXX(Z);
  }

  protected boolean calcViaXX(double[][] Z) {
    z = Z;
    log.trace("calcViaXX(\n", new Mtrx(Z));
    int nC = Z[0].length;

    // [080414] commented out
//        BitSet checkA = Mtrx.getNonConstCols(Z);
//    if (checkA.cardinality() != nC) {
//      String error = "Redundant data: Const cols";      log.trace(error);
//      Z = Mtrx.getCols(checkA, Z);     log.trace("new z =\n", new Mtrx(Z));
//    }

//    if (findSameXRows(z)) {
////      z = removeConstCols(checkA, z);
//      String error = "Redundant data: identical X-rows";
//      log.debug(error);
//    }

    double[][] Y = Mtrx.getCols(0, 1, Z); // [nRx1]
    Matrix mY = new Matrix(Y);
    Matrix mX = makeCopyX(Z);
    Matrix mXt = mX.transpose();
    Matrix B = mXt.times(mX);  // X'X
    try {
      B = B.inverse();          //  (X'X)^-1
    } catch(RuntimeException e) {
      log.trace("unable to invert mX=\t", mX);
//      log.setAll();
//      log.debug("mX =\n", new Mtrx(mX.getArray()));
//      log.debug("Unable to invert B=Xt*X =\n", new Mtrx(B.getArray()));
//      log.debug("B.det = ", B.det());
//      log.error("Unable to invert", e);
      return false;
    }
    B = B.times(mXt);      //   (X'X)^-1 X'
    B = B.times(mY);       //   (X'X)^-1 X'Y
    mlrCoeffs = Mtrx.getCol(0, B.getArray());                 log.trace("a = ", new Vec(mlrCoeffs));

    Matrix eY = mX.times(B); // estimated
    yPred = Mtrx.getCol(0, eY.getArray());               log.trace("estimated Y, ey = ", new Vec(yPred));

    // [080414] commented out
//    if (checkA.cardinality() != nC) {
//      log.trace("before restoring columns, a = ", new Vec(mlrCoeffs));
//      mlrCoeffs = insertConstCols(nC, checkA, mlrCoeffs);               log.trace("restored a = ", new Vec(mlrCoeffs));
//    }
    return true;
  }

  public Matrix makeCopyX(double[][] Z) {
    Matrix mX = new Matrix(Z);                              // make tmp COPY of X
    double[][] cX = mX.getArrayCopy(); // copy of Z
    Mtrx.setCol(0, 1., cX);     log.trace("(after setting first col to 1s) cX\n", new Mtrx(cX));
    return new Matrix(cX);
  }


  protected double[] insertConstCols(int nC, BitSet set, double[] a)
  {
    double[] res = new double[nC];
    int idx = 0;
    for (int i = 0; i < nC; i++) {
      if (set.get(i))
        res[i] = a[idx++];
      else
        res[i] = 0;
    }
    return res;
  }

  public static boolean findSameXRows(double[][] z)
  {
    int nR = z.length;
    int nC = z[0].length;
    for (int i = 0; i < nR; i++) {
      for (int j = 0; j < i; j++) {
        double v = Vec.dist(1, nC, z[i], z[j]);
        if ((float)v == 0f) {
          log.info("\nz[i]=\n" + Vec.toString(z[i])
            + "\nz[j]=\n" + Vec.toString(z[j])
            + "\ni=" + i + ", j=" + j);
        }
      }
    }
    double[] pd = Mtrx.pDistByRows(1, nC, z); // NOTE!!! First column is y's
    int idx = Vec.findFirstIdx(pd, 0);
    return (idx != -1);
  }

  public double calcYFromXZ(double[] x)
  {
    int idxA = mlrCoeffs.length -1;
    int idxX = x.length -1;
    double res = mlrCoeffs[0];
    // NOTE!!!!
    // x maybe X=(x1,x2,...,x_n) or Z=(1,x1,x2,...,x_n), but in both cases they alined
    for (int i = 1; i < mlrCoeffs.length; i++) { // NOTE: i=1
      res += x[idxX--] * mlrCoeffs[idxA--];
    }
    return res;
  }

  public static double[] calcY(double[][] z, double[] coeff)
  {
    double[] yt = Mtrx.getCol(0, z); // store y's
    Mtrx.setCol(0, 1., z);
//    log.info("\nz=\n" + Vec.toString(z));
    Matrix X = new Matrix(z);
    double[][] a = new double[1][0];
    a[0] = coeff;
    a = Mtrx.trans(a);
//    log.info("\nat=\n" + Vec.toString(a));
    Matrix A = new Matrix(a);
    Matrix eY = X.times(A); // estimated
    return Mtrx.getCol(0, eY.getArray());
  }

  public double[] calcYFromXZ(double[][] x) {  log.trace("calcYFromX");
    int n = x.length;
    double[] res = new double[n];
    for (int i = 0; i < res.length; i++) {
      res[i] = calcYFromXZ(x[i]);
    }
    log.trace("calcYFromX = ", new Vec(res));
    return res;
  }
}
