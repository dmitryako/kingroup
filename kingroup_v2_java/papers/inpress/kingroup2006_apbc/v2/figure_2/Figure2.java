package papers.inpress.kingroup2006_apbc.v2.figure_2;
import kingroup_v2.pop.sample.PopBuilderModel;
import papers.inpress.kingroup2006_apbc.v2.APBC2006_Common;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/01/2006, Time: 12:06:40
 */
public class Figure2 extends  APBC2006_Common
{
  public Figure2()
  {
    N_ALLELES = 10;
    MIN_N_LOCI = 1; // 1-paper
    MAX_N_LOCI = 10;//15-paper

    N_TRIALS = 10; // 100-paper

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
  }
  public void run_P50() {
    String fileName;

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(10);
    BUILDER_MODEL.setGroupSize(5);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(5);
    BUILDER_MODEL.setGroupSize(10);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(2);
    BUILDER_MODEL.setGroupSize(25);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    BUILDER_MODEL.setNumGroups(1);
    BUILDER_MODEL.setGroupSize(50);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);

//    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModelOLD.GROUPS_BUILDER_UNIFORM);
//    BUILDER_MODEL.setNumGroups(50);
//    BUILDER_MODEL.setGroupSize(1);
//    fileName = makeFileNameByL(ALG_NAME);
//    calcAccuracyByLoci(DIR, fileName);
//
//    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModelOLD.GROUPS_BUILDER_UNIFORM);
//    BUILDER_MODEL.setNumGroups(25);
//    BUILDER_MODEL.setGroupSize(2);
//    fileName = makeFileNameByL(ALG_NAME);
//    calcAccuracyByLoci(DIR, fileName);

  }
  public void run_Figure_4() {
    String fileName;

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.MANUAL_GROUPS);
    BUILDER_MODEL.setGroupSizes(new int[] {2, 6, 10, 14, 18});
    BUILDER_MODEL.setNumGroups(5);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.MANUAL_GROUPS);
    BUILDER_MODEL.setGroupSizes(new int[] {20, 5, 5, 5, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1});
    BUILDER_MODEL.setNumGroups(14);
    fileName = makeFileNameByL(ALG_NAME);
    calcAccuracyByLoci(DIR, fileName);
  }
}