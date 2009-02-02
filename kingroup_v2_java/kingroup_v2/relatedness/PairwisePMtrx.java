package kingroup_v2.relatedness;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopNullHypothesisFactory;
import kingroup_v2.ucm.relatedness.PairwiseRMtrxFactory;
import tsvlib.project.ProjectLogger;

import javax.stats.HypothesisTesting;
import java.util.Arrays;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/12/2006, Time: 17:23:12
 */
public class PairwisePMtrx //extends PairwiseRMtrx
{
  public static final int NULL_DISTR_SIZE = 17000;
  private static ProjectLogger log = ProjectLogger.getLogger(PairwisePMtrx.class.getName());
  private PairwiseRMtrxFactory factory;

  public PairwisePMtrx(PairwiseRMtrxFactory factory) {
    this.factory = factory;
  }

  public PairwiseRMtrx makePMtrx(SysPop pop) {
    PairwiseRMtrx rm = factory.makeRMtrx(pop, pop);
    double[] nullDistr = calcNullDistr(pop, NULL_DISTR_SIZE);
    PairwiseRMtrx pm = convertToP(nullDistr, rm);
    return pm;
  }

  public double[] calcNullDistr(SysPop sysPop) {
    return calcNullDistr(sysPop, NULL_DISTR_SIZE);
  }
  public double[] calcNullDistr(SysPop sysPop, int n) {
    double[] res = new double[n];
    int currIdx = 0;
    while (currIdx < n) {
      SysPop nullPop = SysPopNullHypothesisFactory.makeNullHypothesis(sysPop);
      PairwiseRMtrx rm = factory.makeRMtrx(nullPop, nullPop);
      currIdx = loadDistr(currIdx, res, rm);
    }
    Arrays.sort(res);
    return res;
  }

  public static int loadDistr(int currIdx, double[] nullDistr, PairwiseRMtrx m)
  {
    int idx = currIdx;
    for (int r = 0; r < m.size(); r++) {
      for (int c = 0; c < r; c++) {
        if (idx >= nullDistr.length)
          return nullDistr.length;
        nullDistr[idx++] = m.get(r, c);
      }
    }
    return idx;
  }

  public static PairwiseRMtrx convertToP(double[] nullDistr, PairwiseRMtrx m)
  {
    for (int r = 0; r < m.size(); r++) {
      for (int c = 0; c < r; c++) {
        double v = m.get(r, c);
        double p = HypothesisTesting.calcGETypeI(v, nullDistr);
        m.set(r, c, p) ;
      }
    }
    return m;
  }
}