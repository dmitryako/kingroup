package qsar.bench.qsar.MLR.robust.clts;
import qsar.bench.qsar.MLR.MlrAlg;
import qsar.bench.qsar.MLR.robust.lta.LtaMlrAlg;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import javax.utilx.bitset.CompBitSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.TreeSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/03/2008, Time: 14:53:48
 */
public class CltsMlrAlg extends MlrAlg
{
  private static ProjectLogger log = ProjectLogger.getLogger(CltsMlrAlg.class);
  private int K;
  private int n;
  private int p;

  public CltsMlrAlg()
  {
//    log.setDebug(false);
//    log.setDebug(true);
  }

  public boolean calc(double[][] z) {    //log.setDebug(false);
    n = z.length;
    p = z[0].length;
    K = 500;
    double[] y = Mtrx.getCol(0, z);
    int nTrim = (n + p + 1) / 2;
    ArrayList<MlrAlg> esArr = LtaMlrAlg.makeElementalSets(K, z);

    int N_ITER = 20; // k - concentration steps
    MlrAlg bestAlg = null;
    BitSet ignore = new BitSet();            // 'ignore' indicator
    double[] lastErr = new double[esArr.size()];
    double bestErr = -1.;                        // global least trimed squares
//    boolean exitCLoop = false;
//    for (int m = 0; m < N_ITER && !exitCLoop; m++) {
    for (int m = 0; m < N_ITER; m++) {
      TreeSet<CompBitSet> tree = new TreeSet<CompBitSet>();  // check for redundancy
//      int count = 0;
      for (int i = 0; i < esArr.size(); i++) {
        if (ignore.get(i)) { // problem or converged data points
          continue;
        }
//        System.out.println("m = " + m + ", count=" + (count++));
        MlrAlg alg = esArr.get(i);
        if (alg == null) {
          ignore.set(i);
          continue;
        }
        double[] r = calcErr2(alg, y, z);
        int[] order = Vec.sortIdx(r, true);      log.debug("leastErrOrder = ", new IntVec(order)); // TODO: NOTE! could be done in O(n)
        r = Vec.get(r, order, nTrim);            log.debug("r = ", new Vec(r));

        double err = Vec.sum(r, nTrim);          log.debug("TSE = ", err); // trimmed sum of squared errors
        if (bestErr < 0 || bestErr > err) {
          bestErr = err;                             log.debug("LTS = ", bestErr);
          bestAlg = alg;
//          if (exitCLoop) {
//             System.out.println("found better m="+ m + ", err=" + (float)err);
//          }
        }
        if (m == N_ITER - 1) { // calc only the TSE during the last iteration
          continue;
        }

        // ignore redundant attractors
        CompBitSet nextSet = makeNextSet(order, nTrim);
        if (!tree.add(nextSet)) {
          ignore.set(i);                         log.debug("redundant", nextSet);
        }

        double[][] nextIter = Mtrx.getRows(z, order, nTrim);
        if (!alg.calc(nextIter)) {
          ignore.set(i);  // ignore this attractor due to errors
          continue;
        }
//        System.out.println("lastErr["+ i + "]="+ (float)lastErr[i] + ", err=" + (float)err);
        if ((float)lastErr[i] == (float)err) {  log.debug("converged lastErr[i]=err=", err);
          ignore.set(i); // converged, no need to continue with this attractor
//          if ((float)err == (float)bestErr) {
//            System.out.println("m="+ m + ", err=" + (float)err);
//            exitCLoop = true;  // NOTE!!!  This need to be proven
//          }
          continue;
        }
        lastErr[i] = err;
      }
    }
    if (bestAlg == null) {
      return false;
    }
    return super.calc(bestAlg.getCalibZ());
  }

  public static CompBitSet makeNextSet(int[] order, int nTrim)
  {
    CompBitSet res = new CompBitSet();
    for (int i = 0; i < nTrim; i++) {
      res.set(order[i]);
    }
    return res;
  }

  public static double[] calcErr2(MlrAlg alg, double[] y, double[][] z)
  {
    double[] yp = alg.calcYFromXZ(z);   log.debug("yp = ", new Vec(yp));
    Vec.add(yp, -1., y);                log.debug("yp-y = ", new Vec(yp));
    Vec.abs(yp);                        log.debug("abs(yp) = ", new Vec(yp));
    Vec.square(yp);                        log.debug("square(yp) = ", new Vec(yp)); //NOTE!!!
    return yp;
  }

}

