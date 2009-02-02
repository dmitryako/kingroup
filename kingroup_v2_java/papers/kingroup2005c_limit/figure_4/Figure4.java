package papers.kingroup2005c_limit.figure_4;
import kingroup_v2.pop.sample.PopBuilderModel;
import papers.kingroup2005c_limit.LimitCommon;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/11/2005, Time: 14:33:50
 */
public abstract class Figure4 extends  LimitCommon
{
  public Figure4()
  {
    N_ALLELES = 8;
    MIN_N_LOCI = 1;
    MAX_N_LOCI = 12;

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
  }
  public void run_P50() {
    String fileName;

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(5);
    BUILDER_MODEL.setGroupSize(10);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.MANUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(5);
    BUILDER_MODEL.setGroupSizes(new int[] {20, 10, 10, 5, 5});
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.MANUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(5);
    BUILDER_MODEL.setGroupSizes(new int[] {30, 5, 5, 5, 5});
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.MANUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(5);
    BUILDER_MODEL.setGroupSizes(new int[] {40, 5, 2, 2, 1});
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);


    // less interesting last
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(50);
    BUILDER_MODEL.setGroupSize(1);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(25);
    BUILDER_MODEL.setGroupSize(2);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);
  }
}
