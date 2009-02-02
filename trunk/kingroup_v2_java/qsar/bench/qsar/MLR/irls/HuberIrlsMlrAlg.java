package qsar.bench.qsar.MLR.irls;
import Jama.Matrix;
import qsar.bench.qsar.MLR.MlrAlg;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2007, Time: 12:54:43
 */
public class HuberIrlsMlrAlg extends MlrAlg
{
  private static ProjectLogger log = ProjectLogger.getLogger(HuberIrlsMlrAlg.class);
  private IrlsNorm norm;
  private double[] err;  // residual errors
  private double[] W; // IRLS weights
  private static final double ERR = 0.0001;

  public HuberIrlsMlrAlg(IrlsNorm norm)
  {
    this.norm = norm;
  }

  public boolean calc(double[][] Z) {
    W = new double[Z.length];
    boolean ok = super.calc(Z);
    if (!ok)
      return false;

    int MAX_N_ITER = 10;
    double oldMAE = calcMAE(Z);   // mean absolute error
    for (int i = 0; i < MAX_N_ITER; i++) {
      W = calcW(oldMAE);
      if (!calcViaIrls(Z, W))
        return false;
      double newMAE = calcMAE(Z);
      if (calcDiff(oldMAE, newMAE) < ERR)   {
        log.debug("break");
        break;
      }
      oldMAE = newMAE;
    }
    return true;
  }

  private double calcDiff(double v, double v2) { log.debug("v=", v);  log.debug("v2=", v2);
    double base = 0.5 * (Math.abs(v) + Math.abs(v2));  log.debug("base=", base);
    if (base == 0)
      return 0;
    double res = Math.abs(v - v2) / base;      log.debug("calcDiff()=", res);
    return res;
  }

  private double[] calcW(double mae)
  {
    double saved = norm.getParam();
    norm.setParam(saved * mae);
    for (int i = 0; i < err.length; i++) {
      W[i] = norm.calcW(err[i]);
    }
    norm.setParam(saved);
    log.debug("W=", new Vec(W));
    return W;
  }

  private double calcMAE(double[][] Z) { // mean absolute error
    double[] yt = Mtrx.getCol(0, Z); // copy
    Vec.add(yt, -1, yPred); // result is in yt
    err = yt;           log.debug("err=", new Vec(err)); // residual errors
    Vec.abs(err);       log.debug("abs(err)=", new Vec(err));
    double MAE = Vec.avr(err);  log.debug("MAE=", MAE);// mean absolute error
    return MAE;
  }

  protected boolean calcViaIrls(double[][] Z, double[] W) {
    log.trace("calcViaXX(\n", new Mtrx(Z));
    int nC = Z[0].length;
    BitSet checkA = Mtrx.getNonConstCols(Z);
    if (checkA.cardinality() != nC) {
      String error = "Redundant data: Const cols";      log.trace(error);
      Z = Mtrx.getCols(checkA, Z);     log.trace("new z =\n", new Mtrx(Z));
      W = Vec.get(checkA, W);
    }

    double[][] Y = Mtrx.getCols(0, 1, Z); // [nRx1]          // make tmp COPY of Y
    Matrix mY = new Matrix(Y);
    Matrix mX = makeCopyX(Z);
    Matrix mXt = mX.transpose();   log.trace("X'=\n", new Mtrx(mXt.getArray()));  // X'

    Mtrx.times(W, mX.getArray());  log.trace("WX=\n", new Mtrx(mX.getArray()));   // WX
    Matrix B = mXt.times(mX);  mX = null; log.trace("X'WX=\n", new Mtrx(B.getArray()));  // X'WX
    try {
      B = B.inverse();  log.trace("(X'WX)^-1=\n", new Mtrx(B.getArray()));    // (X'WX)^-1
    } catch(RuntimeException e) {
//      log.setAll();
//      log.debug("mX =\n", new Mtrx(mX.getArray()));
//      log.debug("Unable to invert B=Xt*X =\n", new Mtrx(B.getArray()));
//      log.debug("B.det = ", B.det());
//      log.error("Unable to invert", e);
      return false;
    }
    B = B.times(mXt);  log.trace("(X'WX)^-1 X'=\n", new Mtrx(B.getArray()));  // (X'WX)^-1 X'
    Mtrx.times(W, Y);  log.trace("WY=\n", new Mtrx(Y));          // WY
    mY = new Matrix(Y);
    B = B.times(mY);   log.trace("(X'WX)^-1 X'WY=\n", new Mtrx(B.getArray())); //  (X'WX)^-1 X'WY
    mlrCoeffs = Mtrx.getCol(0, B.getArray());    log.trace("B=\n", new Vec(mlrCoeffs));

    mX = makeCopyX(Z);
    Matrix eY = mX.times(B); // estimated
    yPred = Mtrx.getCol(0, eY.getArray());               log.trace("estimated Y, ey = ", new Vec(yPred));

    if (checkA.cardinality() != nC) {                      log.trace("mlrCoeffs= ", new Vec(mlrCoeffs));
      mlrCoeffs = insertConstCols(nC, checkA, mlrCoeffs);  log.trace("restored mlrCoeffs= ", new Vec(mlrCoeffs));
    }
    return true;
  }

}
