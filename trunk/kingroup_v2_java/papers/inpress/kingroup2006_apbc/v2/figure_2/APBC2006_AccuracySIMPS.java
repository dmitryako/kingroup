package papers.inpress.kingroup2006_apbc.v2.figure_2;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/01/2006, Time: 12:27:04
 */
public class APBC2006_AccuracySIMPS extends Figure2
{
  private static ProjectLogger log = ProjectLogger.getLogger(APBC2006_AccuracySIMPS.class.getName());
  private final SIMPS2AlgModel ALG_MODEL;
  public static void main(String[] args) {
    APBC2006_AccuracySIMPS test = new APBC2006_AccuracySIMPS();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }
  public APBC2006_AccuracySIMPS() {
    ALG_NAME = "SIMPS";
    ALG_MODEL = new SIMPS2AlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setNumIters(1000);

    POP_SIZE = 50;
    N_TRIALS = 100; // 100-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    ALG_MODEL.setNumIters(POP_SIZE * POP_SIZE * POP_SIZE);
    ALG_MODEL.setLoopBreaker(100 * POP_SIZE);
    SIMPS2Alg method = new SIMPS2Alg(sysPop, ALG_MODEL);
    return method.partition();
  }
}

