package kingroup_v2.relatedness.identix;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/04/2006, Time: 12:35:57
 */
public class IdentixRMtrx   extends PairwiseRMtrx
{
  public IdentixRMtrx(SysPop pop)
  {
    super(pop);
    estimator = new IdentixREstimator(pop);
  }
}

