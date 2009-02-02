package papers.kingroup2005c_limit.figure_5;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005c_limit.LimitCommon;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2005, Time: 08:25:16
 */
public class LimitMS  extends LimitCommon
{
  private static ProjectLogger log = ProjectLogger.getLogger(LimitMS.class.getName());
  private final MSAlgModel ALG_MODEL;
  public static void main(String[] args) {
    LimitMS test = new LimitMS();
    LOG.setTrace(true);
//    test.runGroupSizes();
    test.runGS5();
    test.runGS10();
    System.exit(0);
  }
  public LimitMS() {
    ALG_NAME = "MS";

    ALG_MODEL = new MSAlgModel();
//    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusMin_WRONG());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
    ALG_MODEL.setWindSize(10);

    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 10;
    N_GROUPS_STEP = 1;
    N_LOCI = 2;

    N_TRIALS = 200; // 200-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    MSAlgV2 method = new MSAlgV2(sysPop, ALG_MODEL);
    return method.partition();
  }
}
