package papers.kingroup2005c_limit.figure_4;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.dr.DRAlg;
import kingroup_v2.partition.dr.DRAlgModel;
import kingroup_v2.partition.sdr.SDRAlgV2;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/07/2005, Time: 08:51:33
 */
public class AccuracyDR extends Figure4
{
  private static ProjectLogger log = ProjectLogger.getLogger(AccuracyDR.class.getName());
  private DRAlgModel ALG_MODEL;
  public AccuracyDR()
  {
//    ALG_NAME = "DR2";
    ALG_NAME = "DR2";
    ALG_MODEL = new DRAlgModel();

    POP_SIZE = 50;
    N_TRIALS = 100; // 200-paper
  }
  public static void main(String[] args) {
    AccuracyDR test = new AccuracyDR();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }


  public Partition partitionSysPop(SysPop sysPop)
  {
//    SysAlleleFreq newFreq = SysAlleleFreqFactory.calcFrom(sysPop);
//    sysPop.setFreq(newFreq);
//    DRAlg method = new DRAlg(sysPop, ALG_MODEL);
//    DRAlg method = new DRAlg(sysPop, ALG_MODEL);
    DRAlg method = new SDRAlgV2(sysPop, ALG_MODEL);
    return method.partition();
  }
}