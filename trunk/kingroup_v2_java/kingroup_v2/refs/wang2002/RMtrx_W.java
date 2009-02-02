package kingroup_v2.refs.wang2002;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/09/2006, Time: 11:37:15
 */
public class RMtrx_W extends PairwiseRMtrx
{
  public RMtrx_W(SysPop pop, SysPop refPop)
  {
    super(pop);
    estimator = new REstimator_W(pop, refPop);
  }
}
