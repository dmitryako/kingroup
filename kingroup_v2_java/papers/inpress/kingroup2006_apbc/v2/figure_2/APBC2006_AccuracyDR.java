package papers.inpress.kingroup2006_apbc.v2.figure_2;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.dr.DRAlg;
import kingroup_v2.partition.dr.DRAlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/01/2006, Time: 12:26:38
 */
public class APBC2006_AccuracyDR  extends Figure2
{
  private static ProjectLogger log = ProjectLogger.getLogger(APBC2006_AccuracyDR.class.getName());
  protected DRAlgModel ALG_MODEL;
  public static void main(String[] args) {
    APBC2006_AccuracyDR test = new APBC2006_AccuracyDR();
    LOG.setTrace(true);
//    test.run_P50();
    test.run_Figure_4();
    System.exit(0);
  }
  public APBC2006_AccuracyDR()
  {
    ALG_NAME = "DR";
//    ALG_NAME = "DR2";
    ALG_MODEL = new DRAlgModel();

    POP_SIZE = 50;
    N_TRIALS = 200; // 100-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
//    log.info("sysFreq = \n" + sysPop.getFreq());
//    log.info("sysPop = \n" + sysPop);
//    SysAlleleFreq newFreq = SysAlleleFreqFactory.calcFrom(sysPop);
//    sysPop.setFreq(newFreq);
//    log.info("newFreq = \n" + newFreq);

    DRAlg method = new DRAlg(sysPop, ALG_MODEL);
//    DRAlg method = new SDRAlgV2(sysPop, ALG_MODEL);

    return method.partition();
  }
}

