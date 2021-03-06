package papers.kingroup2005b_simpson.v2.figure_2;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 12:50:52
 */
public class MSAccuracyMS   extends MSFigure2
{
  private static ProjectLogger log = ProjectLogger.getLogger(MSAccuracyMS_min.class.getName());
  protected final MSAlgModel ALG_MODEL;
  public int WIND_SIZE_LIMIT = 2;
  public MSAccuracyMS() {
    ALG_NAME = "MS";
    ALG_MODEL = new MSAlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
//    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());

    N_TRIALS = 100; // 100-paper
    WIND_SIZE_LIMIT = 10; // 10
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
//    MSAlgV2 method = new MSAlgV2(sysPop, ALG_MODEL);
    MSAlgV2 method = new MSAlgV2(sysPop, ALG_MODEL);
    return method.partition();
  }
}

