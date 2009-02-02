package papers.kingroup2005c_limit.figure_4;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/07/2005, Time: 09:26:53
 */
public class AccuracyMS extends Figure4
{
  private static ProjectLogger log = ProjectLogger.getLogger(AccuracyMS.class.getName());
  private final MSAlgModel ALG_MODEL;
  public int WIND_SIZE_LIMIT = 2;
  public static void main(String[] args) {
    AccuracyMS test = new AccuracyMS();
    LOG.setTrace(true);
    test.run_P50();
    System.exit(0);
  }
  public AccuracyMS() {
    ALG_NAME = "MS";

    ALG_MODEL = new MSAlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
//    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusMin_WRONG());
//    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusMax_WRONG());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
//    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusLog());

    N_TRIALS = 200; // 200-paper
    WIND_SIZE_LIMIT = 10;
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    MSAlgV2 method = new MSAlgV2(sysPop, ALG_MODEL);
    return method.partition();
  }
}
