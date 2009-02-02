package papers.kingroup2005c_limit.figure_4;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/07/2005, Time: 08:50:01
 */
public class AccuracySimps extends Figure4 {
  private final SIMPS2AlgModel ALG_MODEL;
  private int SIMPS_ITERATIONS = 1000;
  public static void main(String[] args) {
    AccuracySimps test = new AccuracySimps();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }
  public AccuracySimps()
  {
    ALG_NAME = "SIMPS";

    ALG_MODEL = new SIMPS2AlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setNumIters(SIMPS_ITERATIONS);

    POP_SIZE = 50;
    N_TRIALS = 200; // 100-paper
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
