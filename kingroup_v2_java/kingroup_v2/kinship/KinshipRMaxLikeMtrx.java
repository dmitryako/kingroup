package kingroup_v2.kinship;
import kingroup_v2.kinship.like.KinshipRMaxLikeEstimator;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/04/2006, Time: 10:51:01
 */
public class KinshipRMaxLikeMtrx   extends PairwiseRMtrx
{
  public KinshipRMaxLikeMtrx(SysPop pop)
  {
    super(pop);
    estimator = new KinshipRMaxLikeEstimator(pop);
  }
}
