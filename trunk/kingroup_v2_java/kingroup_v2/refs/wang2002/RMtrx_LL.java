package kingroup_v2.refs.wang2002;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/09/2007, Time: 15:22:29
 */
public class RMtrx_LL  extends PairwiseRMtrx
{
  public RMtrx_LL(SysPop pop, SysPop refPop)
  {
    super(pop);
    estimator = new REstimator_LL(pop, refPop);
  }
}

