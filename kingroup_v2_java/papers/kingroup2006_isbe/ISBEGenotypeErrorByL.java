package papers.kingroup2006_isbe;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/05/2006, Time: 15:29:01
 */
public class ISBEGenotypeErrorByL extends ISBERelatednessByL
{
  private int CLASS_I_ERROR = 5;
  public ISBEGenotypeErrorByL() {
    N_ALLELES = 10;
    N_TRIALS = 10000;  // 10,000 paper
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
  }
  protected void changePairAfterBuild(SysPop pair)
  {
//    log.info("\nBEFORE pair=\n" + pair);
    SysPopFactory.applyErrorClassI(pair, CLASS_I_ERROR);
//    log.info("\nAFTER pair=\n" + pair);
  }
  public void testKinship() {
//    N_TRIALS = 10000;
    R_TYPE = Kingroup.PAIRWISE_R_QG;
    calc(0.5, KinshipIBDFactory.makeFullSib()
      , "kinship_FS_RAND_NA" + N_ALLELES + "_CLASS_I");
    calc(0.5, KinshipIBDFactory.makeParentOffspring()
      , "kinship_PO_RAND_NA" + N_ALLELES  + "_CLASS_I");
    calc(0.0, KinshipIBDFactory.makeUnrelated()
      , "kinship_UN_RAND_NA" + N_ALLELES  + "_CLASS_I");
    calc(0.25, KinshipIBDFactory.makeHalfSib()
      , "kinship_HS_RAND_NA" + N_ALLELES  + "_CLASS_I");
  }

  public void testKinshipML() {
    N_TRIALS = 1000;
    R_TYPE = Kingroup.PAIRWISE_R_KINSHIP_ML;
    calc(0.5, KinshipIBDFactory.makeFullSib()
      , "kinshipML_FS_RAND_NA" + N_ALLELES + "_CLASS_I");
    calc(0.5, KinshipIBDFactory.makeParentOffspring()
      , "kinshipML_PO_RAND_NA" + N_ALLELES  + "_CLASS_I");
    calc(0.0, KinshipIBDFactory.makeUnrelated()
      , "kinshipML_UN_RAND_NA" + N_ALLELES  + "_CLASS_I");
    calc(0.25, KinshipIBDFactory.makeHalfSib()
      , "kinshipML_HS_RAND_NA" + N_ALLELES  + "_CLASS_I");
  }

  public void testLynch() {
//    N_TRIALS = 10000;
    R_TYPE = Kingroup.PAIRWISE_R_LR;
    calc(0.5, KinshipIBDFactory.makeFullSib()
      , "lynch_FS_RAND_NA" + N_ALLELES + "_CLASS_I");
    calc(0.5, KinshipIBDFactory.makeParentOffspring()
      , "lynch_PO_RAND_NA" + N_ALLELES  + "_CLASS_I");
    calc(0.0, KinshipIBDFactory.makeUnrelated()
      , "lynch_UN_RAND_NA" + N_ALLELES  + "_CLASS_I");
    calc(0.25, KinshipIBDFactory.makeHalfSib()
      , "lynch_HS_RAND_NA" + N_ALLELES  + "_CLASS_I");
  }

}

