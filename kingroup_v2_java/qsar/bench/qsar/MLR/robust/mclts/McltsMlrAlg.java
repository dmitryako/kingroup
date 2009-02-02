package qsar.bench.qsar.MLR.robust.mclts;
import qsar.bench.qsar.MLR.MlrAlg;
import qsar.bench.qsar.MLR.robust.clts.CltsMlrAlg;
import tsvlib.project.ProjectLogger;

import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import javax.utilx.bitset.CompBitSet;
import java.util.BitSet;
import java.util.TreeSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/05/2008, Time: 14:11:30
 */
public class McltsMlrAlg  extends MlrAlg
{
  private static ProjectLogger log = ProjectLogger.getLogger(McltsMlrAlg.class);
  private static final double BIAS = 0.99;
  private int K;
  private int n;
  private int p;
  private static final int MIN_K = 24;  // i.e. 3*2^3

  public McltsMlrAlg()
  {
//    log.setDebug(false);
//    log.setDebug(true);
  }

  public boolean calc(double[][] z) {    //log.setDebug(false);
    n = z.length;
    p = z[0].length;
//    K = (int)Math.round(3. * MathX.pow(2., p)); // default formula
//    K = Math.max(K, MIN_K);
    K = 100;
    double[] y = Mtrx.getCol(0, z);
    int nTrim = (n + p) / 2;
//    int nTrim = Math.max(2 * n / 3, 1);
    MlrAlg[] attrArr = makeStarts(z);

    int N_ITER = 10; // k - concentration steps
    MlrAlg bestAlg = null;
    BitSet ignore = new BitSet();            // 'ignore' indicator
    double LTS = -1.;                        // global least trimed squares
    for (int m = 0; m < N_ITER; m++) {
      TreeSet<CompBitSet> tree = new TreeSet<CompBitSet>();  // check for redundancy
      for (int i = 0; i < attrArr.length; i++) {
        if (ignore.get(i)) { // problem data points
          continue;
        }
        MlrAlg alg = attrArr[i];
        if (alg == null) {
          ignore.set(i);
          continue;
        }
        double[] r = CltsMlrAlg.calcErr2(alg, y, z);
        int[] order = Vec.sortIdx(r, true);      log.debug("leastErrOrder = ", new IntVec(order)); // TODO: NOTE! could be done in O(n)
        r = Vec.get(r, order, nTrim);            log.debug("r = ", new Vec(r));

        double TSE = Vec.sum(r, nTrim);          log.debug("TSE = ", TSE); // trimmed sum of squared errors
        if (LTS < 0 || LTS > TSE) {
          LTS = TSE;                             log.debug("LTS = ", LTS);
          bestAlg = alg;
        }
        if (m == N_ITER - 1) { // calc only the TSE during the last iteration
          continue;
        }

        // ignore redundant attractors
        CompBitSet nextSet = CltsMlrAlg.makeNextSet(order, nTrim);
        if (i > K) {
          nextSet.set(n);  // use the (n+1)'th bit to record BIAS
        }
        if (!tree.add(nextSet)) {
          ignore.set(i);                         log.debug("redundant", nextSet);
        }

        double[][] nextIter = Mtrx.getRows(z, order, nTrim);
        if (!alg.calc(nextIter)) {
          ignore.set(i);  // ignore this attractor due to errors
          continue;
        }
        if (i > K) {    // add 0.99 attractor
          alg.multMlrCoeffs(BIAS);
        }
      }
    }
    if (bestAlg == null) {
      return false;
    }
    return super.calc(bestAlg.getCalibZ());
  }

  private MlrAlg[] makeStarts(double[][] Z) {
//    log.setDebug();
    int nES = 2 * K + 1;  // num of elemental sets
    MlrAlg[] attrArr = new MlrAlg[nES]; // attractors

    // STEP 1. Build K-starts
    int[] pool = IntVec.makeRandIdxArr(n);   log.debug("pool=\n", new IntVec(pool));  // sequence of random idx up to n-1
    int currIdx = 0;
    for (int i = 0; i < nES; i++) {
      if (currIdx > n-p) { // need at least p points
        pool = IntVec.makeRandIdxArr(n);
        currIdx = 0;
      }

      // build one start
      BitSet start = new BitSet(n);
      if (i == K) {
        start.set(0, n);   // include OLS of the whole sample
      }
      else {
        for (int j = 0; j < p; j++) {  // 'starts' are p-long
          start.set(pool[currIdx++]);
        }
      }                                                       log.debug("start=\n", start);
      double[][] startSet = Mtrx.getRows(start, Z);           log.debug("setZT=\n", new Mtrx(startSet));

      MlrAlg mlrAlg = new MlrAlg();
      if (mlrAlg.calc(startSet)) {
        attrArr[i] = mlrAlg;
        if (i > K) {     // add 0.99 attractor
          mlrAlg.multMlrCoeffs(BIAS);
        }
      }
    }
    return attrArr;
  }
}

