package papers.kingroup2006_isbe;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/05/2006, Time: 12:35:39
 */
public class ISBEFreqErrorByL  extends ISBERelatednessByL
{
  private int FREQ_ERROR = 50;
  public ISBEFreqErrorByL() {
    N_ALLELES = 10;
    N_TRIALS = 10000;  // 10,000 paper
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
  }
  protected void changeFreqAfterBuild(SysAlleleFreq freq)
  {
    SysAlleleFreqFactory.applyError(freq, FREQ_ERROR);
  }
  public void testKinship() {
//    N_TRIALS = 10000;
    R_TYPE = Kingroup.PAIRWISE_R_QG;
    calc(0.5, KinshipIBDFactory.makeFullSib()
      , "kinship_FS_RAND_NA" + N_ALLELES + "_FREQ_ERR");
    calc(0.5, KinshipIBDFactory.makeParentOffspring()
      , "kinship_PO_RAND_NA" + N_ALLELES  + "_FREQ_ERR");
    calc(0.0, KinshipIBDFactory.makeUnrelated()
      , "kinship_UN_RAND_NA" + N_ALLELES  + "_FREQ_ERR");
    calc(0.25, KinshipIBDFactory.makeHalfSib()
      , "kinship_HS_RAND_NA" + N_ALLELES  + "_FREQ_ERR");
  }

  public void testKinshipML() {
    N_TRIALS = 10000;
    R_TYPE = Kingroup.PAIRWISE_R_KINSHIP_ML;
    calc(0.5, KinshipIBDFactory.makeFullSib()
      , "kinshipML_FS_RAND_NA" + N_ALLELES + "_FREQ_ERR");
    calc(0.5, KinshipIBDFactory.makeParentOffspring()
      , "kinshipML_PO_RAND_NA" + N_ALLELES  + "_FREQ_ERR");
    calc(0.0, KinshipIBDFactory.makeUnrelated()
      , "kinshipML_UN_RAND_NA" + N_ALLELES  + "_FREQ_ERR");
    calc(0.25, KinshipIBDFactory.makeHalfSib()
      , "kinshipML_HS_RAND_NA" + N_ALLELES  + "_FREQ_ERR");
  }

  public void testLynch() {
//    N_TRIALS = 10000;
    R_TYPE = Kingroup.PAIRWISE_R_LR;
    calc(0.5, KinshipIBDFactory.makeFullSib()
      , "lynch_FS_RAND_NA" + N_ALLELES + "_FREQ_ERR");
    calc(0.5, KinshipIBDFactory.makeParentOffspring()
      , "lynch_PO_RAND_NA" + N_ALLELES  + "_FREQ_ERR");
    calc(0.0, KinshipIBDFactory.makeUnrelated()
      , "lynch_UN_RAND_NA" + N_ALLELES  + "_FREQ_ERR");
    calc(0.25, KinshipIBDFactory.makeHalfSib()
      , "lynch_HS_RAND_NA" + N_ALLELES  + "_FREQ_ERR");
  }

}

