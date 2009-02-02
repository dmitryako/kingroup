package kingroup_v2.lynch;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/04/2006, Time: 12:34:56
 */
public class RMtrx_LR extends PairwiseRMtrx
{
  public RMtrx_LR(SysPop pop)
  {
    super(pop);
    estimator = new REstimator_LR(pop);
  }
}
