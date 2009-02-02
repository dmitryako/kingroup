package papers.kingroup2005c_limit.figure_4;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.smc.MCSAlg;
import kingroup_v2.partition.smc.MCSAlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/12/2005, Time: 09:08:23
 */
public class AccuracyMCS extends Figure4
{
  private static ProjectLogger log = ProjectLogger.getLogger(AccuracyMCS.class.getName());
  protected final MCSAlgModel ALG_MODEL;
  public static void main(String[] args) {
    AccuracyMCS test = new AccuracyMCS();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }
  public AccuracyMCS() {
    ALG_NAME = "MCS";

    POP_SIZE = 50;
    ALG_MODEL = new MCSAlgModel();
    ALG_MODEL.setSimpsonAlg(false);
    ALG_MODEL.setNumIters(100000);
    ALG_MODEL.setLoopBreaker(100 * POP_SIZE);

    N_TRIALS = 200; // 200-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    MCSAlg method = new MCSAlg(sysPop, ALG_MODEL);
    return method.partition();
  }
}

