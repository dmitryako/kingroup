package papers.inpress.kingroup2006_apbc.v2.talk;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import papers.inpress.kingroup2006_apbc.v2.APBC2006_Common;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.utilx.RandomSeed;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/02/2006, Time: 09:58:00
 */
public class APBC2006_Talk  extends APBC2006_Common
{
  private final MSAlgModel ALG_MODEL;
  private static ProjectLogger log = ProjectLogger.getLogger(APBC2006_Talk.class.getName());
  public static void main(String[] args) {
    APBC2006_Talk test = new APBC2006_Talk();
    LOG.setTrace(true);
    test.run_Talk();
    System.exit(0);
  }
  public APBC2006_Talk()
  {
    ALG_NAME = "MS";
    ALG_MODEL = new MSAlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
    int WIND_SIZE_LIMIT = 10; // 10
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);

    N_ALLELES = 5;
    MIN_N_LOCI = 1; // 1-paper
    MAX_N_LOCI = 10;//15-paper

    N_TRIALS = 2; // 100-paper

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
  }
  public void run_Talk()
  {
    String fileName;
    POP_SIZE = 6;
    RandomSeed.getInstance().setSeed(POP_SIZE);
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(2);
    BUILDER_MODEL.setGroupSize(3);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    MSAlgV2 method = new MSAlgV2(sysPop, ALG_MODEL);
    return method.partition();
  }
}
