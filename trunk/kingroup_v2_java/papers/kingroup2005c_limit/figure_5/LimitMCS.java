package papers.kingroup2005c_limit.figure_5;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.smc.MCSAlg;
import kingroup_v2.partition.smc.MCSAlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005c_limit.LimitCommon;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2005, Time: 08:28:50
 */
public class LimitMCS extends LimitCommon
{
  private static ProjectLogger log = ProjectLogger.getLogger(LimitMCS.class.getName());
  protected final MCSAlgModel ALG_MODEL;
  public static void main(String[] args) {
    LimitMCS test = new LimitMCS();
    LOG.setTrace(true);
//    test.runGroupSizes();
//    test.runGS5();
//    test.runGS10();
    test.runGS20();
    System.exit(0);
  }
  public LimitMCS() {
    ALG_NAME = "MCS";

    ALG_MODEL = new MCSAlgModel();
    ALG_MODEL.setSimpsonAlg(false);

    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 10;
    N_GROUPS_STEP = 1;

    N_LOCI = 1;
    N_TRIALS = 100; // L=1, 100-paper

//    N_LOCI = 2;
//    N_TRIALS = 400; // L=2, 200-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    int nIters = POP_SIZE * POP_SIZE * POP_SIZE;
    if (ALG_MODEL.getNumIters() != nIters) {
      log.info("nIters = " + nIters);
      ALG_MODEL.setNumIters(nIters);
    }
    ALG_MODEL.setLoopBreaker(100 * POP_SIZE);
    MCSAlg method = new MCSAlg(sysPop, ALG_MODEL);
    return method.partition();
  }
}
