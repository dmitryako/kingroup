package papers.kingroup2005c_limit.figure_6;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SIMPS2Alg;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/01/2006, Time: 12:49:28
 */
public class LimitEfficiencySIMPS  extends LimitFigure6
{
  private final SIMPS2AlgModel ALG_MODEL;
  public static void main(String[] args) {
    LimitEfficiencySIMPS test = new LimitEfficiencySIMPS();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public LimitEfficiencySIMPS() {
    ALG_NAME = "SIMPS";
    ALG_MODEL = new SIMPS2AlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());

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
