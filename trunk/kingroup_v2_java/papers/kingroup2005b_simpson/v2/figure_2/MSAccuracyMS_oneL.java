package papers.kingroup2005b_simpson.v2.figure_2;
import kingroup.algorithm.window.AlgWinAccessOrder;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.partition.ms.MSAlgAccessViaGroups;
import kingroup_v2.partition.ms.MSAlgModel;
import kingroup_v2.partition.ms.MSAlgV2;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 13:03:48
 */
public class MSAccuracyMS_oneL    extends MSFigure2
{
  private static ProjectLogger log = ProjectLogger.getLogger(MSAccuracyMS_oneL.class.getName());
  protected final MSAlgModel ALG_MODEL;
  public int WIND_SIZE_LIMIT = 2;
  public static void main(String[] args) {
    MSAccuracyMS_oneL test = new MSAccuracyMS_oneL();
    LOG.setTrace(true);
    test.runFigure2();
    System.exit(0);
  }
  public MSAccuracyMS_oneL() {
    ALG_NAME = "MS_L";
    ALG_MODEL = new MSAlgModel();
    ALG_MODEL.setSibshipAlg(new DiploidSibship());
    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusAvr());
//    ALG_MODEL.setGenotypeDistAlg(new GenotypeDistLocusMin_WRONG());

    MIN_N_LOCI = 3;
    MAX_N_LOCI = 3;

    N_TRIALS = 1;
    WIND_SIZE_LIMIT = 10; // 10
    ALG_MODEL.setWindSize(WIND_SIZE_LIMIT);
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
//    MSAlgGroups tr = new MSAlgGroups(sysPop
//      , ALG_MODEL.getGenotypeDistAlg()
//      , ALG_MODEL.getSibshipAlg());

//    AlgWinAccessOrder order = new MSAlgAccessViaPairs(sysPop, ALG_MODEL.getGenotypeDistAlg());
    AlgWinAccessOrder order = new MSAlgAccessViaGroups(sysPop
      , ALG_MODEL.getGenotypeDistAlg()
      , ALG_MODEL.getSibshipAlg());
    StringBuffer buff = new StringBuffer();
    while (order.hasNext())
    {
      int idIdx = order.nextIdx();
      buff.append(sysPop.toString(idIdx));
      buff.append(SysProp.EOL);
    }
    log.info(SysProp.EOL +buff.toString());

//    MSAlgV2 method = new MSAlgV2(sysPop, ALG_MODEL);
    MSAlgV2 method = new MSAlgV2(sysPop, ALG_MODEL);
    return method.partition();
  }
}
