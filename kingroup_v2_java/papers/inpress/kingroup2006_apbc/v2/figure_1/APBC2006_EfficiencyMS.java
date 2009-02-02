package papers.inpress.kingroup2006_apbc.v2.figure_1;
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
 * User: jc138691, Date: 11/01/2006, Time: 11:21:14
 */
public class APBC2006_EfficiencyMS extends Figure1
{
  private static ProjectLogger log = ProjectLogger.getLogger(APBC2006_EfficiencyMS.class.getName());
  private final MSAlgModel ALG_MODEL;
  public int WIND_SIZE_LIMIT = 2;
  public static void main(String[] args) {
    APBC2006_EfficiencyMS test = new APBC2006_EfficiencyMS();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public APBC2006_EfficiencyMS() {
    ALG_NAME = "MS";
    ALG_MODEL = new MSAlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
//    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusMin_WRONG());
//    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusMax_WRONG());
//    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusLog());

    N_TRIALS = 100; // 100-paper
    WIND_SIZE_LIMIT = 10; // 10
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    MSAlgV2 method = new MSAlgV2(sysPop, ALG_MODEL);
    return method.partition();
  }
}
