package papers.kingroup2005b_simpson.v2.figure_2;
import kingroup_v2.pop.sample.PopBuilderModel;
import papers.kingroup2005b_simpson.v2.MSCommon;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 11:47:55
 */
public class MSFigure2 extends  MSCommon
{
  public MSFigure2()
  {
    N_ALLELES = 10;
    MIN_N_LOCI = 1; // 1-paper
    MAX_N_LOCI = 8;//8-paper

    N_TRIALS = 10; // 100-paper
    POP_SIZE = 25;

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
  }
  public void runFigure2() {
    String fileName;
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(5);
    BUILDER_MODEL.setGroupSize(5);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);
  }
}
