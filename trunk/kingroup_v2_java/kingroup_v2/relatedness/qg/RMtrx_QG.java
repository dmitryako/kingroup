package kingroup_v2.relatedness.qg;
import kingroup_v2.kinship.KinshipREstimator;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/12/2006, Time: 11:15:56
 */
public class RMtrx_QG extends PairwiseRMtrx
{
  public RMtrx_QG(SysPop pop)
  {
    super(pop);
    setEstimator(new KinshipREstimator(pop));
  }
}
