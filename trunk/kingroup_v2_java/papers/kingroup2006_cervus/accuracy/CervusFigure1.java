package papers.kingroup2006_cervus.accuracy;
import kingroup_v2.pop.sample.PopBuilderModel;
import papers.kingroup2006_cervus.CervusCommon;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 23/01/2006, Time: 14:23:32
 */
public class CervusFigure1 extends  CervusCommon
{
  private static ProjectLogger log = ProjectLogger.getLogger(CervusFigure1.class.getName());
  public CervusFigure1()
  {
    N_ALLELES = 10;
    MIN_N_LOCI = 1; // 1-paper
    MAX_N_LOCI = 20;//10-paper

    N_TRIALS = 10; // 100-paper

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
  }
  public void runFigure1() {
    String fileName;

//    POP_SIZE = 200;
//    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModelOLD.GROUPS_BUILDER_UNIFORM);
//    BUILDER_MODEL.setNumGroups(20);
//    BUILDER_MODEL.setGroupSize(10);
//    fileName = makeFileNameByL(ALG_NAME);
//    calcAccuracyByLoci(DIR, fileName);

    POP_SIZE = 500;
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(20);
    BUILDER_MODEL.setGroupSize(25);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);

    POP_SIZE = 1000;
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(20);
    BUILDER_MODEL.setGroupSize(50);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);
  }

}

