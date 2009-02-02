package kingroup_v2.kinship.like;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/11/2006, Time: 15:01:03
 */
public class MilliganREstimator extends KinshipRMaxLikeEstimator
{
  public MilliganREstimator(SysPop pop)
  {
    super(pop);
    setMin(0., 0.);
  }
}
