package kingroup_v2.ucm.relatedness;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopNullHypothesisFactory;
import kingroup_v2.relatedness.PairwisePMtrx;
import kingroup_v2.relatedness.PairwiseRMtrx;
import tsvlib.project.ProjectLogger;

import java.util.Arrays;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2006, Time: 14:19:03
 */
public class PairwiseRMtrxPValues extends PairwiseRMtrxCalculator
{
  private static ProjectLogger log = ProjectLogger.getLogger(PairwiseRMtrxPValues.class.getName());
  private double[] nullDistr;

  public PairwiseRMtrxPValues(SysPop sysPop) {
    nullDistr = new double[PairwisePMtrx.NULL_DISTR_SIZE];
    int currIdx = 0;
    while (currIdx < PairwisePMtrx.NULL_DISTR_SIZE) {
      SysPop nullPop = SysPopNullHypothesisFactory.makeNullHypothesis(sysPop);
      PairwiseRMtrx m = super.makeRMtrx(nullPop, sysPop);
      currIdx = PairwisePMtrx.loadDistr(currIdx, nullDistr, m);
    }
    Arrays.sort(nullDistr);
  }


  protected PairwiseRMtrx makeBiasGroup(SysPop group, SysPop refPop, Kinship kinship) {
    PairwiseRMtrx m = super.makeBiasGroup(group, refPop, kinship, -1);
    m = PairwisePMtrx.convertToP(nullDistr, m);
    return m;
  }

  protected PairwiseRMtrx makeBiasGroupArr(SysPop[] groups, SysPop pop, Kinship kinship) {
    PairwiseRMtrx m = super.makeBiasGroupArr(groups, pop, kinship);
    m = PairwisePMtrx.convertToP(nullDistr, m);
    return m;
  }

  public PairwiseRMtrx makeRMtrx(SysPop group, SysPop refPop) {
    PairwiseRMtrx m = super.makeRMtrx(group, refPop);
    m = PairwisePMtrx.convertToP(nullDistr, m);
    return m;
  }

  protected PairwiseRMtrx makeBiasPair(SysPop group, SysPop refPop, Kinship kinship) {
    PairwiseRMtrx m = super.makeBiasPair(group, refPop, kinship, -1);
    m = PairwisePMtrx.convertToP(nullDistr, m);
    return m;
  }

  public PairwiseRMtrx convertToP(PairwiseRMtrx m) {
    return PairwisePMtrx.convertToP(nullDistr, m);
  }

}
