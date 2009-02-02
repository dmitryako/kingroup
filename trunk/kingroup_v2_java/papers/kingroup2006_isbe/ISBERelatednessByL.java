package papers.kingroup2006_isbe;
import kingroup_v2.Kingroup;
import kingroup_v2.relatedness.identix.IdentixRMtrx;
import kingroup_v2.kinship.*;
import kingroup_v2.lynch.RMtrx_LR;
import kingroup_v2.lynch.RMtrx_LR_NN;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.MilliganRMtrx;
import kingroup_v2.relatedness.PairwiseRMtrx;
import kingroup_v2.relatedness.qg.RMtrx_QG;
import kingroup_v2.relatedness.qg.RMtrx_QG_NN;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.stats.Stats;
import javax.stats.StatsRes;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/04/2006, Time: 14:26:43
 */
public class ISBERelatednessByL extends ISBECommon
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  protected final double EPS = 1e-5;

  protected int N_TRIALS = 100;
  protected int MIN_N_LOCI = 1;
  protected int MAX_N_LOCI = 20;
  protected int MIN_N_ALLELES = 3;
  protected int MAX_N_ALLELES = 20;
  protected int N_ALLELES = 10;
  protected int N_LOCI = 10;
  protected PopBuilderModel POP_BUILDER_MODEL = new PopBuilderModel();
  protected int R_TYPE = Kingroup.PAIRWISE_R_QG;

  public ISBERelatednessByL() {
    N_TRIALS = 10000; // paper 10,000
//    N_TRIALS = 1000; // paper 10,000
    N_ALLELES = 5;
  }
  public void testKinship() {
    R_TYPE = Kingroup.PAIRWISE_R_QG;
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinship_FS_EQ_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinship_PO_EQ_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinship_UN_EQ_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinship_HS_EQ_NA" + N_ALLELES);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinship_FS_RAND_NA" + N_ALLELES);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinship_PO_RAND_NA" + N_ALLELES);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinship_UN_RAND_NA" + N_ALLELES);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinship_HS_RAND_NA" + N_ALLELES);

//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinship_FS_TR_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinship_PO_TR_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinship_UN_TR_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinship_HS_TR_NA" + N_ALLELES);
  }

  public void testKinshipPosit() {
    R_TYPE = Kingroup.PAIRWISE_R_QG_NN;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipPOSIT_FS_RAND_NA" + N_ALLELES);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipPOSIT_PO_RAND_NA" + N_ALLELES);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipPOSIT_UN_RAND_NA" + N_ALLELES);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipPOSIT_HS_RAND_NA" + N_ALLELES);
  }

//  public void testIdentix() {
//    R_TYPE = Kingroup.PAIRWISE_R_IDENTIX;
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "identix_FS_EQ_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "identix_PO_EQ_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "identix_UN_EQ_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "identix_HS_EQ_NA" + N_ALLELES);
//
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "identix_FS_RAND_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "identix_PO_RAND_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "identix_UN_RAND_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "identix_HS_RAND_NA" + N_ALLELES);
//
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "identix_FS_TR_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "identix_PO_TR_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "identix_UN_TR_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "identix_HS_TR_NA" + N_ALLELES);
//  }

  public void testLynch() {
    R_TYPE = Kingroup.PAIRWISE_R_LR;
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "lynch_FS_EQ_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "lynch_PO_EQ_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "lynch_UN_EQ_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "lynch_HS_EQ_NA" + N_ALLELES);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "lynch_FS_RAND_NA" + N_ALLELES);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "lynch_PO_RAND_NA" + N_ALLELES);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "lynch_UN_RAND_NA" + N_ALLELES);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "lynch_HS_RAND_NA" + N_ALLELES);

//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "lynch_FS_TR_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "lynch_PO_TR_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "lynch_UN_TR_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "lynch_HS_TR_NA" + N_ALLELES);
  }
  public void testLynchPosit() {
    R_TYPE = Kingroup.PAIRWISE_R_LR_NN;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "lynchPOSIT_FS_RAND_NA" + N_ALLELES);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "lynchPOSIT_PO_RAND_NA" + N_ALLELES);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "lynchPOSIT_UN_RAND_NA" + N_ALLELES);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "lynchPOSIT_HS_RAND_NA" + N_ALLELES);
  }
  public void testWang() {
    R_TYPE = Kingroup.PAIRWISE_R_WANG;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "W_FS_RAND_NA" + N_ALLELES);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "W_PO_RAND_NA" + N_ALLELES);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "W_UN_RAND_NA" + N_ALLELES);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "W_HS_RAND_NA" + N_ALLELES);
  }
  public void testKinshipML() {
    N_TRIALS = 10000;
    R_TYPE = Kingroup.PAIRWISE_R_KINSHIP_ML;
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipML_FS_EQ_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipML_PO_EQ_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipML_UN_EQ_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipML_HS_EQ_NA" + N_ALLELES);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipML_FS_RAND_NA" + N_ALLELES);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipML_PO_RAND_NA" + N_ALLELES);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipML_UN_RAND_NA" + N_ALLELES);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipML_HS_RAND_NA" + N_ALLELES);

//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipML_FS_TR_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipML_PO_TR_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipML_UN_TR_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipML_HS_TR_NA" + N_ALLELES);
  }
//  public void testKinshipML_DEBUG() {
//    N_TRIALS = 10;
//    MIN_N_LOCI = 10;
//    MAX_N_LOCI = 10;
//    R_TYPE = Kingroup.PAIRWISE_R_KINSHIP_ML;
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "debug_PO_EQ_NA" + N_ALLELES);
//  }
  public void testKinshipMIN() {
    N_TRIALS = 10000;
    R_TYPE = Kingroup.PAIRWISE_R_MILLIGAN;
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipMIN_FS_EQ_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipMIN_PO_EQ_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipMIN_UN_EQ_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipMIN_HS_EQ_NA" + N_ALLELES);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipMIN_FS_RAND_NA" + N_ALLELES);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipMIN_PO_RAND_NA" + N_ALLELES);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipMIN_UN_RAND_NA" + N_ALLELES);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipMIN_HS_RAND_NA" + N_ALLELES);

//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipMIN_FS_TR_NA" + N_ALLELES);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipMIN_PO_TR_NA" + N_ALLELES);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipMIN_UN_TR_NA" + N_ALLELES);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipMIN_HS_TR_NA" + N_ALLELES);
  }


  public void calc(double trueR, KinshipIBD pairIBD
    , String tag
  ) {
    int ARR_SIZE = MAX_N_LOCI - MIN_N_LOCI + 1;
    double[] locId = new double[ARR_SIZE];
    double[] varArr = new double[ARR_SIZE];
    double[] biasArr = new double[ARR_SIZE];
    for (N_LOCI = MIN_N_LOCI; N_LOCI <= MAX_N_LOCI; N_LOCI++) {
      double[] arr = new double[N_TRIALS];
      POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
      POP_BUILDER_MODEL.setNumLoci(N_LOCI);
      for (int i = 0; i < N_TRIALS; i++) {
        SysAlleleFreq freq = SysAlleleFreqFactory.makeSysAlleleFreq(POP_BUILDER_MODEL, null);
        SysPop pair = KinshipSysPopFactory.makePair(pairIBD, freq);

        changeFreqAfterBuild(freq);
        if (i == 0 && N_LOCI == 3) {
          log.info("\nfreq["+tag+"]=\n" + freq);
          log.info("\npair=\n" + pair);
        }
        changePairAfterBuild(pair);

        PairwiseRMtrx mtrx = makeRMtrx(pair);

        mtrx.calc();

//        boolean DEBUG = true;
//        if (DEBUG) {
//          mtrx.calc();
//        }
        arr[i] = mtrx.get(0, 1);
      }
//      log.info("\narr=\n" + DoubleArr.toString(arr));
      StatsRes stats = new Stats(arr);
      int idx = N_LOCI - MIN_N_LOCI;
      biasArr[idx] = stats.getMean() - trueR;
      varArr[idx] = stats.getS2();
      locId[idx] = N_LOCI;
//      log.info("\nnLoci="+nLoci+", bias, stddev=" + (float)biasArr[locIdx] + ", " + (float)devArr[locIdx]);
    }
    LOG.saveToFile(locId, biasArr, DIR, "bias_"+tag+".csv");
    LOG.saveToFile(locId, varArr, DIR, "var_"+tag+".csv");
  }

  protected void changePairAfterBuild(SysPop pair)
  {
  }

  protected void changeFreqAfterBuild(SysAlleleFreq freq)
  {
  }

  protected PairwiseRMtrx makeRMtrx(SysPop pair)
  {
    if (R_TYPE == Kingroup.PAIRWISE_R_QG)
      return new RMtrx_QG(pair);

    if (R_TYPE == Kingroup.PAIRWISE_R_QG_NN)
      return new RMtrx_QG_NN(pair);

    if (R_TYPE == Kingroup.PAIRWISE_R_KINSHIP_2)
      return new KinshipR2Mtrx(pair);

    if (R_TYPE == Kingroup.PAIRWISE_R_KINSHIP_ML)
      return new KinshipRMaxLikeMtrx(pair);

    if (R_TYPE == Kingroup.PAIRWISE_R_MILLIGAN)
      return new MilliganRMtrx(pair);

    if (R_TYPE == Kingroup.PAIRWISE_R_IDENTIX)
      return new IdentixRMtrx(pair);

    if (R_TYPE == Kingroup.PAIRWISE_R_LR)
      return new RMtrx_LR(pair);
    if (R_TYPE == Kingroup.PAIRWISE_R_LR_NN)
      return new RMtrx_LR_NN(pair);

    if (R_TYPE == Kingroup.PAIRWISE_R_WANG)
      return null;
//    return new RMtrx_W(pair);
    return null;
  }
}
