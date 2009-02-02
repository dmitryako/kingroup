package kingroup_v2.kinship.like;
import kingroup.genetics.Like;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipDiploidParentCalculator;
import kingroup_v2.kinship.KinshipHaplodipCalculator;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 08:31:57
 */
public class KinshipLikeCalculator implements KinshipLikeCalculatorI {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipLikeCalculator.class.getName());
  private final KinshipDiploidParentCalculator diploid;
  private final KinshipHaplodipCalculator haplodip;
  private final Kinship kinship;
  private final SysPop pop;
  public KinshipLikeCalculator(SysPop pop, KinshipIBD model, Kinship kinship) {
    this.pop = pop;
    this.kinship = kinship;

    diploid = new KinshipDiploidParentCalculator(model.getRm(), model.getRp());
    haplodip = new KinshipHaplodipCalculator(model, kinship);

    float error = kinship.getAlleleErrorRate();
    if (error != 0f) {
      KinshipLikeErrorIBD like = new KinshipLikeErrorIBD(error);
      diploid.setLikeIBD(like);
      haplodip.setLikeIBD(like);
    }
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

//      SysAlleleFreq sysFreq = KinGroupV2MainUI.getInstance().getSysAlleleFreq();
//      if ((float)fx == 0.0f)
//        fx = sysFreq.getFreq(L, x);
//      if ((float)fx2 == 0.0f)
//        fx2 = sysFreq.getFreq(L, x2);
//      if ((float)fy == 0.0f)
//        fy = sysFreq.getFreq(L, y);
//      if ((float)fy2 == 0.0f)
//        fy2 = sysFreq.getFreq(L, y2);

      diploid.setFx(fx);
      diploid.setFx2(fx2);
      diploid.setFy(fy);
      diploid.setFy2(fy2);

//      diploid.setFreqX(freq.getFreq(L, x));
//      diploid.setFreqX2(freq.getFreq(L, x2));
//      diploid.setFreqY(freq.getFreq(L, y));
//      diploid.setFreqY2(freq.getFreq(L, y2));

      prob = diploid.calcLocusLogMatPat(i, k, L, pop);
//      log.info("\nKinship calcProb()=" + (float)prob);
    }
    else if (kinship.getTreatHaploid() == Kinship.HAPLOID_IGNORE)
    {
      return Like.IGNORE;
    }
    else if (kinship.getTreatHaploid() == Kinship.HAPLOID_EXCLUDE)
    {
      return Like.MIN_LOG;
    }
    else if ((x == -1 || x2 == -1)
      && (y != -1 && y2 != -1))
    {
      if (x == -1)
        x = x2;
      double fx = pop.getFreq().getFreq(L, x);
      double fy = pop.getFreq().getFreq(L, y);
      double fy2 = pop.getFreq().getFreq(L, y2);
      prob = haplodip.calcProb(x, fx, y, fy, y2, fy2);
    } else if ((y == -1 || y2 == -1)
      && (x != -1 && x2 != -1)) {
      if (y == -1)
        y = y2;
      double fy = pop.getFreq().getFreq(L, y);
      double fx = pop.getFreq().getFreq(L, x);
      double fx2 = pop.getFreq().getFreq(L, x2);
      prob = haplodip.calcProb(y, fy, x, fx, x2, fx2);
    } else if ((x == -1 || x2 == -1)
      && (y == -1 || y2 == -1)) {
      if (x == -1)
        x = x2;
      if (y == -1)
        y = y2;
      double fx = pop.getFreq().getFreq(L, x);
      double fy = pop.getFreq().getFreq(L, y);
      prob = haplodip.calcProb(x, fx, y, fy);
    }
    else
    {
      return Like.MIN_LOG;
    }

//    // log.info("prob=" + prob);
    prob = Like.probToLog(prob);
//    // log.info("ln(prob)=" + prob);
    return prob;
  }
}
