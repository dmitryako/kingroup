package kingroup_v2.relatedness;
import kingroup_v2.kinship.KinshipRMaxLikeMtrx;
import kingroup_v2.kinship.like.MilliganREstimator;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Min rm, rp are ZERO
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/04/2006, Time: 10:22:57
 */
public class MilliganRMtrx extends KinshipRMaxLikeMtrx
{
  public MilliganRMtrx(SysPop pop)
  {
    super(pop);
    estimator = new MilliganREstimator(pop);
  }
}
