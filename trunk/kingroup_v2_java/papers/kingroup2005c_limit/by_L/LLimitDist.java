package papers.kingroup2005c_limit.by_L;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.population.OldPop;
import kingroup_v2.fsr.bound.FsrLBoundApproxGSLarge;
import papers.kingroup2005c_limit.figure_5.LimitDist;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/10/2005, Time: 11:23:49
 */
public class LLimitDist extends LimitDist
{
  public static void main(String[] args) {
    LLimitDist test = new LLimitDist();
    LOG.setTrace(true);
    test.run_rxg();
    System.exit(0);
  }

  public LLimitDist() {
    MIN_NUM_LOCI = 1;
    MAX_NUM_LOCI = 3;

    N_TRIALS = 2;
    FILE = "LIMIT_MCCONNELL";
    FILE = "LIMIT_BOXES";
  }

  protected double calcOneDistance(OldPop popA) {
//    FsrLBoundApproxGSOne bound = new FsrLBoundApproxGSOne(N_GROUPS, N_ALLELES, N_LOCI);
////    double dist = bound.calcMcConnellError(N_LOCI);

    FsrLBoundApproxGSLarge bound = new FsrLBoundApproxGSLarge(N_GROUPS, N_ALLELES, N_LOCI);
    double dist = bound.calc(GROUP_SIZE).getAccuracyError() * POP_SIZE;

    return dist;
  }


  public void run_rxg() {
//    N_GROUPS = 50;
//    calcAccuracyByLoci(DIR, name3+"_50x1S_NA"+N_ALLELES +".csv"
//      , ButlerFamilyData.BUTLER_50x1, N_ALLELES);
//

    POP_SIZE = 50;
    POP_MODEL.setSize(POP_SIZE);
    String name3 = (FILE + "_U" + POP_SIZE);
    N_GROUPS = 10;
    GROUP_SIZE = 5;
    calcAccuracyByLoci(DIR, name3+"_10x5S_NA"+N_ALLELES +".csv"
      , ButlerFamilyData.BUTLER_10x5, N_ALLELES);

    POP_SIZE = 100;
    POP_MODEL.setSize(POP_SIZE);
    name3 = (FILE + "_U" + POP_SIZE);
    N_GROUPS = 10;
    GROUP_SIZE = 10;
    calcAccuracyByLoci(DIR, name3+"_10x10S_NA"+N_ALLELES +".csv"
      , ButlerFamilyData.BUTLER_10x10, N_ALLELES);

    POP_SIZE = 200;
    POP_MODEL.setSize(POP_SIZE);
    name3 = (FILE + "_U" + POP_SIZE);
    N_GROUPS = 10;
    GROUP_SIZE = 20;
    calcAccuracyByLoci(DIR, name3+"_10x20S_NA"+N_ALLELES +".csv"
      , ButlerFamilyData.BUTLER_10x20, N_ALLELES);

    POP_SIZE = 500;
    POP_MODEL.setSize(POP_SIZE);
    name3 = (FILE + "_U" + POP_SIZE);
    N_GROUPS = 10;
    GROUP_SIZE = 50;
    calcAccuracyByLoci(DIR, name3+"_10x50S_NA"+N_ALLELES +".csv"
      , ButlerFamilyData.BUTLER_10x50, N_ALLELES);
  }
}