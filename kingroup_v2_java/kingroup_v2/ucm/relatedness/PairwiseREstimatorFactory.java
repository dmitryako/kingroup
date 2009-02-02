package kingroup_v2.ucm.relatedness;
import kingroup_v2.Kingroup;
import kingroup_v2.relatedness.identix.IdentixREstimator;
import kingroup_v2.kinship.CalculatorI;
import kingroup_v2.kinship.KHDistSqrEstimator;
import kingroup_v2.kinship.KingroupREstimV2;
import kingroup_v2.kinship.KinshipREstimator;
import kingroup_v2.kinship.like.KinshipRMaxLikeEstimator;
import kingroup_v2.kinship.like.MilliganREstimator;
import kingroup_v2.lynch.REstimator_LR;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/12/2006, Time: 09:53:59
 */
public class PairwiseREstimatorFactory
{
  private int type;
  public PairwiseREstimatorFactory(int type) {
    this.type = type;
  }

  public CalculatorI makeREstimator(SysPop sysPop) {
    return makeREstimator(sysPop, type);
  }

  public static CalculatorI makeREstimator(SysPop sysPop, int type) {
    if (type == Kingroup.PAIRWISE_R_KINSHIP_ML)
      return new KinshipRMaxLikeEstimator(sysPop);

    if (type == Kingroup.PAIRWISE_R_MILLIGAN)
      return new MilliganREstimator(sysPop);

    if (type == Kingroup.PAIRWISE_R_LR)
      return new REstimator_LR(sysPop);

    if (type == Kingroup.PAIRWISE_R_WANG)
      return null;
    if (type == Kingroup.PAIRWISE_R_LL)
      return null;

    if (type == Kingroup.PAIRWISE_R_IDENTIX)
      return new IdentixREstimator(sysPop);

    if (type == Kingroup.PAIRWISE_R_TEST)
      return new KingroupREstimV2(sysPop);

    if (type == Kingroup.PAIRWISE_R_KH_OUTBRED)
      return null; //todo
//    return new TEstimator(sysPop);

    if (type == Kingroup.PAIRWISE_DIST_SQR)
      return new KHDistSqrEstimator(sysPop);

    return new KinshipREstimator(sysPop);
  }

}
