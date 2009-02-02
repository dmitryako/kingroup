package kingroup_v2.like.milligan;
import kingroup.genetics.Like;
import kingroup_v2.kinship.like.KinshipLikeCalculatorI;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/03/2006, Time: 17:35:24
 */
public class MilliganLikeCalculator implements KinshipLikeCalculatorI
{
//  private static ProjectLogger log = ProjectLogger.getLogger(KinshipLikeCalculator.class.getName());
  private final MilliganDiploidLikeCalculator diploid;
  private final SysPop pop;
  public MilliganLikeCalculator(SysPop pop, MilliganIBD ibd) {
    this.pop = pop;
//    this.kinship = kinship;

    diploid = new MilliganDiploidLikeCalculator(ibd);

//    float error = kinship.getAlleleErrorRate();
//    if (error != 0f) {
//      KinshipLikeErrorIBD like = new KinshipLikeErrorIBD(error);
//      diploid.setLikeIBD(like);
//    }
  }
  public double calcLocusLog(int i, int k, int L)
  {
    SysAlleleFreq freq = pop.getFreq();
    int x = pop.getAllele(i, L, 0);
    int x2 = pop.getAllele(i, L, 1);
    int y = pop.getAllele(k, L, 0);
    int y2 = pop.getAllele(k, L, 1);
    if (x == -1 && x2 == -1)
      return Like.IGNORE;
    if (y == -1 && y2 == -1)
      return Like.IGNORE;
    double prob = Like.MIN_LOG;
    if (x != -1 && x2 != -1 && y != -1 && y2 != -1) {
      diploid.setX(x);
      diploid.setX2(x2);
      diploid.setY(y);
      diploid.setY2(y2);

      double fx = freq.getFreq(L, x);
      double fx2 = freq.getFreq(L, x2);
      double fy = freq.getFreq(L, y);
      double fy2 = freq.getFreq(L, y2);

      diploid.setFx(fx);
      diploid.setFx2(fx2);
      diploid.setFy(fy);
      diploid.setFy2(fy2);

//      prob = diploid.calcLocusLogMatPat(i, k, L, pop);
      prob = diploid.calcProb();
    }
    else
    {
      return Like.IGNORE;
    }

//    // log.info("prob=" + prob);
    prob = Like.probToLog(prob);
//    // log.info("ln(prob)=" + prob);
    return prob;
  }
}
