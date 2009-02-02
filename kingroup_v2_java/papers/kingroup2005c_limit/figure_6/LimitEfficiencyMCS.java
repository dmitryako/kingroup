package papers.kingroup2005c_limit.figure_6;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.smc.MCSAlg;
import kingroup_v2.partition.smc.MCSAlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/01/2006, Time: 14:12:15
 */
public class LimitEfficiencyMCS extends LimitFigure6
{
  private static ProjectLogger log = ProjectLogger.getLogger(LimitEfficiencyMCS.class.getName());
  private final MCSAlgModel ALG_MODEL;
  public static void main(String[] args) {
    LimitEfficiencyMCS test = new LimitEfficiencyMCS();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public LimitEfficiencyMCS() {
    ALG_NAME = "MCS";
    ALG_MODEL = new MCSAlgModel();
    ALG_MODEL.setSimpsonAlg(false);
    ALG_MODEL.setNumIters(100000);

    N_TRIALS = 200; // 100-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    ALG_MODEL.setNumIters(POP_SIZE * POP_SIZE * POP_SIZE);
    ALG_MODEL.setLoopBreaker(100 * POP_SIZE);
    MCSAlg method = new MCSAlg(sysPop, ALG_MODEL);
    return method.partition();
  }
}