package papers.kingroup2005c_limit.figure_5;
import kingroup.population.OldPop;
import kingroup_v2.fsr.bound.FsrLBoundMonteCarlo;
import papers.kingroup2005b_simpson.v1.Efficiency;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/11/2005, Time: 14:41:45
 */
public class LimitMC extends LimitDist
{
  public static void main(String[] args) {
    LimitMC test = new LimitMC();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public LimitMC() {
    N_TRIALS = 1000;
    FILE = "LIMIT_MONTE_CARLO";
  }

  protected Efficiency calcEfficiency(OldPop popA) {
    FsrLBoundMonteCarlo emp = new FsrLBoundMonteCarlo(N_GROUPS,  N_ALLELES, N_LOCI);
    double dist = emp.runSimL(GROUP_SIZE).getNumErrors();
//    double dist = emp.runSimOLD(GROUP_SIZE).getNumErrors();

    return new Efficiency(dist, 0, 0);
  }
}