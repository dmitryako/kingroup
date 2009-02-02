package papers.inpress.kingroup2006_apbc.v2.figure_1;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/02/2006, Time: 10:51:05
 */
public class APBC2006_EfficiencySIMPS extends Figure1
{
  private static ProjectLogger log = ProjectLogger.getLogger(APBC2006_EfficiencySIMPS.class.getName());
  private final SIMPS2AlgModel ALG_MODEL;
  public int WIND_SIZE_LIMIT = 2;
  public static void main(String[] args) {
    APBC2006_EfficiencySIMPS test = new APBC2006_EfficiencySIMPS();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public APBC2006_EfficiencySIMPS() {
    ALG_NAME = "SIMPS";
    ALG_MODEL = new SIMPS2AlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setNumIters(1000);

    GROUP_SIZE = 5;
    MIN_N_GROUPS = 5;
    MAX_N_GROUPS = 30;  // 100
    N_GROUPS_STEP = 5;

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
