package papers.kingroup2005b_simpson.v2.figure_2;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 11:52:27
 */
public class MSAccuracySIMPS extends MSFigure2
{
  private static ProjectLogger log = ProjectLogger.getLogger(MSAccuracySIMPS.class.getName());
  private final SIMPS2AlgModel ALG_MODEL;
  private int SIMPS_ITERATIONS = 1000;
  public static void main(String[] args) {
    MSAccuracySIMPS test = new MSAccuracySIMPS();
    LOG.setTrace(true);
    test.runFigure2();
    System.exit(0);
  }
  public MSAccuracySIMPS() {
    ALG_NAME = "SIMPS";
    ALG_MODEL = new SIMPS2AlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setNumIters(SIMPS_ITERATIONS);

    N_TRIALS = 100; // 100-paper
    SIMPS_ITERATIONS = 100000;

    ALG_MODEL.setNumIters(SIMPS_ITERATIONS);
    ALG_MODEL.setLoopBreaker(100 * POP_SIZE);
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    SIMPS2Alg method = new SIMPS2Alg(sysPop, ALG_MODEL);
    return method.partition();
  }
}