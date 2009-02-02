package papers.kingroup2006_cervus.accuracy;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import tsvlib.project.ProjectLogger;

import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/01/2006, Time: 14:51:01
 */
public class CervusFigure1_DR_mut    extends CervusFigure1_DR
{
  private static ProjectLogger log = ProjectLogger.getLogger(CervusFigure1_DR_mut.class.getName());
  public static final int ERROR_PERCENTAGE = 5;
  public static void main(String[] args) {
    CervusFigure1_DR_mut test = new CervusFigure1_DR_mut();
    test.runFigure1();
    System.exit(0);
  }
  public CervusFigure1_DR_mut() {
    DIR += File.separator + "mutation" + ERROR_PERCENTAGE;
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
//    log.info("sysPop=\n" + sysPop);
    SysPop newPop = SysPopFactory.makeWithAlleleError(sysPop, ERROR_PERCENTAGE);
//    log.info("newPop=\n" + newPop);
    return super.partitionSysPop(newPop);
  }
}
