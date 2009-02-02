package papers.inpress.kingroup2006_apbc.v2.figure_3;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import papers.inpress.kingroup2006_apbc.v2.figure_2.APBC2006_AccuracyMS2;

import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/01/2006, Time: 16:26:33
 */
public class APBC2006_MutationMS2  extends APBC2006_AccuracyMS2
{
  public static final int ERROR_PERCENTAGE = 2;
  public static void main(String[] args) {
    APBC2006_MutationMS2 test = new APBC2006_MutationMS2();
//    test.run_P50();
    test.run_Figure_4();
    System.exit(0);
  }
  public APBC2006_MutationMS2() {
    DIR += File.separator + "mutation" + ERROR_PERCENTAGE;
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    sysPop = SysPopFactory.mutatePerLocus(sysPop, ERROR_PERCENTAGE);
    return super.partitionSysPop(sysPop);
  }
}

