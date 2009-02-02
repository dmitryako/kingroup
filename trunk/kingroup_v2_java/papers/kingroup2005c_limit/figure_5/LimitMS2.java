package papers.kingroup2005c_limit.figure_5;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.ms2.MS2AlgModel;
import kingroup_v2.partition.ms2.MS2AlgV2;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.kingroup2005c_limit.LimitCommon;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/11/2005, Time: 10:47:49
 */
public class LimitMS2 extends LimitCommon
{
  private static ProjectLogger log = ProjectLogger.getLogger(LimitMS2.class.getName());
  private final MS2AlgModel ALG_MODEL;
  public static void main(String[] args) {
    LimitMS2 test = new LimitMS2();
    LOG.setTrace(true);
    test.runGroupSizes();
    System.exit(0);
  }
  public LimitMS2() {
    ALG_NAME = "MS2";

    ALG_MODEL = new MS2AlgModel();
//    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusMin_WRONG());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());

    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 20;
    N_GROUPS_STEP = 2;
    N_LOCI = 1;

    N_TRIALS = 10; // 200-paper
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    MS2AlgV2 method = new MS2AlgV2(sysPop, ALG_MODEL);
    return method.partition();
  }
}