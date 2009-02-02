package papers.kingroup2005c_limit.by_L;
import kingroup.population.OldPop;
import kingroup_v2.fsr.bound.FsrLBoundMonteCarlo;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/11/2005, Time: 14:38:34
 */
public class LLimitMC extends LLimitDist
{
  public static void main(String[] args) {
    LLimitMC test = new LLimitMC();
    LOG.setTrace(true);
    test.run_rxg();
    System.exit(0);
  }
  public LLimitMC() {
    N_TRIALS = 10000;
    FILE = "LIMIT_MONTE_CARLO";
  }

  protected double calcOneDistance(OldPop popA) {
    FsrLBoundMonteCarlo emp = new FsrLBoundMonteCarlo(N_GROUPS,  N_ALLELES, N_LOCI);
    double dist = emp.runSimL(GROUP_SIZE).getAccuracyError() * POP_SIZE;
    return dist;
  }
}
