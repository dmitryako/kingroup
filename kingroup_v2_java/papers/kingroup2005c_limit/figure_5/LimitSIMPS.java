package papers.kingroup2005c_limit.figure_5;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005c_limit.LimitCommon;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2005, Time: 08:14:53
 */
public class LimitSIMPS extends LimitCommon
{
  private static ProjectLogger log = ProjectLogger.getLogger(LimitSIMPS.class.getName());
  protected final SIMPS2AlgModel ALG_MODEL;
  public static void main(String[] args) {
    LimitSIMPS test = new LimitSIMPS();
    LOG.setTrace(true);
//    test.runGroupSizes();
    test.runGS5();
    test.runGS10();
    System.exit(0);
  }
  public LimitSIMPS() {
    ALG_NAME = "SIMPS";
    ALG_MODEL = new SIMPS2AlgModel();

    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 10;
    N_GROUPS_STEP = 2;

//    N_LOCI = 1;
//    N_TRIALS = 100; // L=1, 100-paper

    N_LOCI = 2;
    N_TRIALS = 400; // L=2, 400-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    int nIters = POP_SIZE * POP_SIZE * POP_SIZE;
    if (ALG_MODEL.getNumIters() != nIters) {
      log.info("nIters = " + nIters);
      ALG_MODEL.setNumIters(nIters);
    }
    ALG_MODEL.setLoopBreaker(100 * POP_SIZE);
    SIMPS2Alg method = new SIMPS2Alg(sysPop, ALG_MODEL);
    return method.partition();
  }
}