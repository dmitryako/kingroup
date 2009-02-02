package kingroup_v2.kinship;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/05/2006, Time: 11:41:45
 */
public class KinshipR2Mtrx extends PairwiseRMtrx
{
  public KinshipR2Mtrx(SysPop pop)
  {
    super(pop);
    estimator = new KinshipR2Estimator(pop);
  }
}
