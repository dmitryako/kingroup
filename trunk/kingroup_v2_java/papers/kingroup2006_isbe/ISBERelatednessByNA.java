package papers.kingroup2006_isbe;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.kinship.KinshipSysPopFactory;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.relatedness.PairwiseRMtrx;

import javax.iox.LOG;
import javax.stats.Stats;
import javax.stats.StatsRes;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/04/2006, Time: 12:42:14
 */
public class ISBERelatednessByNA   extends ISBERelatednessByL
{
  public ISBERelatednessByNA() {
    N_LOCI = 1;
    N_TRIALS = 10000;  // 100,000 paper
  }
  public void testKinship() {
    R_TYPE = Kingroup.PAIRWISE_R_QG;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "QG_FS_EQ_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "QG_PO_EQ_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "QG_UN_EQ_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "QG_HS_EQ_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "QG_FS_RAND_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "QG_PO_RAND_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "QG_UN_RAND_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "QG_HS_RAND_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "QG_FS_TR_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "QG_PO_TR_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "QG_UN_TR_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "QG_HS_TR_NL" + N_LOCI);
  }

  public void testWang() {
    R_TYPE = Kingroup.PAIRWISE_R_WANG;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "W_FS_EQ_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "W_PO_EQ_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "W_UN_EQ_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "W_HS_EQ_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "W_FS_RAND_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "W_PO_RAND_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "W_UN_RAND_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "W_HS_RAND_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "W_FS_TR_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "W_PO_TR_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "W_UN_TR_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "W_HS_TR_NL" + N_LOCI);
  }

  public void testKinship2() {
    R_TYPE = Kingroup.PAIRWISE_R_KINSHIP_2;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "QG2_FS_EQ_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "QG2_PO_EQ_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "QG2_UN_EQ_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "QG2_HS_EQ_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "QG2_FS_RAND_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "QG2_PO_RAND_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "QG2_UN_RAND_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "QG2_HS_RAND_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "QG2_FS_TR_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "QG2_PO_TR_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "QG2_UN_TR_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "QG2_HS_TR_NL" + N_LOCI);
  }

  public void testKinshipML() {
    N_TRIALS = 1000;
    R_TYPE = Kingroup.PAIRWISE_R_KINSHIP_ML;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipML_FS_EQ_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipML_PO_EQ_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipML_UN_EQ_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipML_HS_EQ_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipML_FS_RAND_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipML_PO_RAND_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipML_UN_RAND_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipML_HS_RAND_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipML_FS_TR_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipML_PO_TR_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipML_UN_TR_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipML_HS_TR_NL" + N_LOCI);
  }

  public void testKinshipMIN() {
    N_TRIALS = 10000;
    R_TYPE = Kingroup.PAIRWISE_R_MILLIGAN;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipMIN_FS_EQ_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipMIN_PO_EQ_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipMIN_UN_EQ_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipMIN_HS_EQ_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipMIN_FS_RAND_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipMIN_PO_RAND_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipMIN_UN_RAND_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipMIN_HS_RAND_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "kinshipMIN_FS_TR_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinshipMIN_PO_TR_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinshipMIN_UN_TR_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinshipMIN_HS_TR_NL" + N_LOCI);
  }

  public void testIdentix() {
    R_TYPE = Kingroup.PAIRWISE_R_IDENTIX;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "identix_FS_EQ_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "identix_PO_EQ_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "identix_UN_EQ_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "identix_HS_EQ_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "identix_FS_RAND_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "identix_PO_RAND_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "identix_UN_RAND_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "identix_HS_RAND_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "identix_FS_TR_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "identix_PO_TR_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "identix_UN_TR_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "identix_HS_TR_NL" + N_LOCI);
  }

  public void testLynch() {
    R_TYPE = Kingroup.PAIRWISE_R_LR;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "lynch_FS_EQ_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "lynch_PO_EQ_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "lynch_UN_EQ_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "lynch_HS_EQ_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "lynch_FS_RAND_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "lynch_PO_RAND_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "lynch_UN_RAND_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "lynch_HS_RAND_NL" + N_LOCI);

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    calc(0.5, KinshipIBDFactory.makeFullSib(), "lynch_FS_TR_NL" + N_LOCI);
    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "lynch_PO_TR_NL" + N_LOCI);
    calc(0.0, KinshipIBDFactory.makeUnrelated(), "lynch_UN_TR_NL" + N_LOCI);
    calc(0.25, KinshipIBDFactory.makeHalfSib(), "lynch_HS_TR_NL" + N_LOCI);
  }

  public void calc(double trueR, KinshipIBD pairIBD
    , String tag
  ) {
    int ARR_SIZE = MAX_N_ALLELES - MIN_N_ALLELES + 1;
    double[] locId = new double[ARR_SIZE];
    double[] varArr = new double[ARR_SIZE];
    double[] biasArr = new double[ARR_SIZE];
    for (N_ALLELES = MIN_N_ALLELES; N_ALLELES <= MAX_N_ALLELES; N_ALLELES++) {
      double[] arr = new double[N_TRIALS];
      for (int i = 0; i < N_TRIALS; i++) {
        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
        SysAlleleFreq freq = SysAlleleFreqFactory.makeSysAlleleFreq(POP_BUILDER_MODEL, null);
        if (i == 0 && N_ALLELES == 3)
          log.info("\nfreq["+tag+"]=" + freq);
        SysPop pair = KinshipSysPopFactory.makePair(pairIBD, freq);

        PairwiseRMtrx mtrx = makeRMtrx(pair);

        mtrx.calc();
        arr[i] = mtrx.get(0, 1);
//        log.info("\n arr["+i+"]=" + (float)arr[i]);
      }
//      log.info("\narr=\n" + DoubleArr.toString(arr));
      StatsRes stats = new Stats(arr);
      int idx = N_ALLELES - MIN_N_ALLELES;
      biasArr[idx] = stats.getMean() - trueR;
//      biasArr[idx] = stats.getMean();
      varArr[idx] = stats.getS2();
      locId[idx] = N_ALLELES;
//      log.info("\nnLoci="+nLoci+", bias, stddev=" + (float)biasArr[locIdx] + ", " + (float)devArr[locIdx]);
    }
    LOG.saveToFile(locId, biasArr, DIR, "bias_"+tag+".csv");
    LOG.saveToFile(locId, varArr, DIR, "var_"+tag+".csv");
  }
}
