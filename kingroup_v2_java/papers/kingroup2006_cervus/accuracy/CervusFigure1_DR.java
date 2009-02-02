package papers.kingroup2006_cervus.accuracy;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.dr.DRAlg;
import kingroup_v2.partition.dr.DRAlgModel;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/01/2006, Time: 14:25:59
 */
public class CervusFigure1_DR   extends CervusInput
{
  private static ProjectLogger log = ProjectLogger.getLogger(CervusFigure1_DR.class.getName());
  protected DRAlgModel ALG_MODEL;
  public static void main(String[] args) {
    CervusFigure1_DR test = new CervusFigure1_DR();
    LOG.setTrace(true);
//    test.runFigure1();
    test.makeCervusInput();
    System.exit(0);
  }
  public CervusFigure1_DR()
  {
    ALG_NAME = "DR";
    ALG_MODEL = new DRAlgModel();

    N_TRIALS = 10; // 100-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    DRAlg method = new DRAlg(sysPop, ALG_MODEL);
    return method.partition();
  }
}

