package qsar.bench.qsar.MLR.robust.lta;
import qsar.bench.qsar.MLR.MlrAlg;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/04/2008, Time: 12:36:58
 */
public class LtaMlrAlg  extends MlrAlg
{
  private static ProjectLogger log = ProjectLogger.getLogger(LtaMlrAlg.class);
  private int K;
  private int n;
  private int p;
  private int nTrim;

  public boolean calc(double[][] z) {    //log.setDebug(false);
    n = z.length;
    p = z[0].length;
    K = 1000;           // todo: get from model
    double[] y = Mtrx.getCol(0, z);
    nTrim = (n + p + 1) / 2;
    ArrayList<MlrAlg> attrArr = makeElementalSets(K, z);

    MlrAlg bestAlg = null;
    double bestErr = -1.;
    for (MlrAlg alg : attrArr) {
      if (alg == null) {
        continue;
      }
      double err = calcErr(alg, y, z);
      if (bestErr < 0 || bestErr > err) {
        bestErr = err;                             log.debug("LTA = ", bestErr);
        bestAlg = alg;
      }
    }
    if (bestAlg == null) {
      return false;
    }
    return super.calc(bestAlg.getCalibZ());
  }

  protected double calcErr(MlrAlg alg, double[] y, double[][] z)
  {
    double[] yp = alg.calcYFromXZ(z);   log.debug("yp = ", new Vec(yp));
    Vec.add(yp, -1., y);                log.debug("yp-y = ", new Vec(yp));
    Vec.abs(yp);                        log.debug("abs(yp) = ", new Vec(yp));
    Arrays.sort(yp);
    double err = Vec.sum(yp, nTrim);          log.debug("err = ", err); // trimmed sum of squared errors
    return err;
  }

  //    nES - num of elemental sets
  public static ArrayList<MlrAlg> makeElementalSets(int nES, double[][] Z) {
//    log.setDebug();
    int n = Z.length;
    int p = Z[0].length;
    ArrayList<MlrAlg> attrArr = new ArrayList<MlrAlg>();

    // STEP 1. Build K-starts
    int[] pool = IntVec.makeRandIdxArr(n);   log.debug("pool=\n", new IntVec(pool));  // sequence of random idx up to n-1
    int currIdx = 0;
    for (int i = 0; i < nES; i++) { // the last is for OLS
      if (currIdx > n-p) { // need at least p points
        pool = IntVec.makeRandIdxArr(n);
        currIdx = 0;
      }

      // build one start
      BitSet start = new BitSet(n);
      for (int j = 0; j < p; j++) {  // 'starts' are p-long
        start.set(pool[currIdx++]);
      }
      double[][] elSet;
      if (i < nES - 1) {
        elSet = Mtrx.getRows(start, Z);
      }
      else {
        elSet = Z;  //OLS
      }
      log.debug("setZT=\n", new Mtrx(elSet));

      MlrAlg mlrAlg = new MlrAlg();
      if (mlrAlg.calc(elSet)) {
        attrArr.add(mlrAlg);
      }
    }
    return attrArr;
  }
}

