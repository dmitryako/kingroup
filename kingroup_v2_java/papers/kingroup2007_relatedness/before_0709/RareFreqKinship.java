package papers.kingroup2007_relatedness.before_0709;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.KinshipAlleleFreqOpt;
import kingroup_v2.pop.sample.PopBuilderModel;
import papers.kingroup2007_relatedness.Relate2007_Common;
import tsvlib.project.ProjectLogger;

/**
 * Created by: jc1386591
 * Date: 23/06/2006. Time: 08:32:44
 */
public class RareFreqKinship extends Relate2007_Common
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  protected final double EPS = 1e-5;
  protected int CLASS_I_ERROR = 5;

  public RareFreqKinship() {
    N_TRIALS = 10000;  // 10,000 paper
    N_LOCI = 4;
    MIN_N_ALLELES = 4;
    MAX_N_ALLELES = 20;
    GROUP_SIZE = 9;
    N_GROUPS = 2; // 2
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 10;

    MIN_PROP = 10;
    MAX_PROP = 50;
    PROP_STEP = 5;

    R_MTRX_TYPE = Kingroup.PAIRWISE_R_QG;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
    POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setIncParents(true); // not really used anyway

    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_BUILDER);

    KinshipAlleleFreqOpt opt = KINSHIP.getAlleleFreqOpt();
    opt.setAllowZeroFreq(true);
  }
  public void testByNA() {
//    N_TRIALS = 10000;  // 10,000 paper
//    N_LOCI = 4;
//    MIN_N_ALLELES = 4;
//    MAX_N_ALLELES = 20;
//    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_BUILDER);
    calcByNA("_QG_TRI_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NG" + N_GROUPS);
  }
  public void testByNL() {
    R_MTRX_TYPE = Kingroup.PAIRWISE_R_QG;
    N_ALLELES = 20;
    MIN_N_LOCI = 2;
    MAX_N_LOCI = 10;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_BUILDER);
    GROUP_SIZE = 9;
    N_GROUPS = 3;
    calcByNL("_QG_RND_NA" + N_ALLELES + "_FGS" + GROUP_SIZE + "_NG" + N_GROUPS);
  }
  public void testDeirkes() {
    N_LOCI = 5;
    N_ALLELES = 20;
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_DIERKES_2005);

//    SysPop sysPop = SysPopFactory.makeSysPopFrom(POP_BUILDER_MODEL);
//    double[] hetArr = AlleleAnalysisFactory.calcTrueHeteroz(sysPop);
//    log.info("hetArr=" + DoubleArr.toString(hetArr));
//
//    double hetAvr = AlleleAnalysisFactory.calcTrueHeterozAvr(sysPop);
//    log.info("hetAvr=" + (float)hetAvr);

    calcByNG("_QG_DEI_NL" + N_LOCI + "_FGS" + GROUP_SIZE);
  }
  public void testAllByNA() {
    testByNA();
    new RareFreqKinshipTrueFreq().testByNA();
    new RareFreqKinshipOuterPairs().testByNA();
    new RareFreqHegKon().testByNA();
    new RareFreqLynch().testByNA();
    new RareFreqWang().testByNA();
  }

  public void testAllByNL() {
    testByNL();
//    new RareFreqKinshipTrueFreq().testByNL();
//    new RareFreqKinshipOuterPairs().testByNL();
    new RareFreqHegKon().testByNL();
    new RareFreqLynch().testByNL();
    new RareFreqWang().testByNL();
  }

  public void testAllByNG() {
    testByNG();
    new RareFreqKinshipTrueFreq().testByNG();
    new RareFreqKinshipOuterPairs().testByNG();
    new RareFreqHegKon().testByNG();
    new RareFreqLynch().testByNG();
    new RareFreqWang().testByNG();
  }
  public void testAllDeirkes() {
    testDeirkes();
    new RareFreqKinshipTrueFreq().testDeirkes();
    new RareFreqKinshipOuterPairs().testDeirkes();
    new RareFreqHegKon().testDeirkes();
    new RareFreqLynch().testDeirkes();
    new RareFreqWang().testDeirkes();
  }
//  public void testAllSingleFamily() {
//    testSingleFamilyV2();
////    new RareFreqKinshipTrueFreq().testSingleFamily();
////    new RareFreqKinshipOuterPairs().testSingleFamily();
////    new RareFreqHegKon().testSingleFamily();
//    new RareFreqLynch().testSingleFamilyV2();
//    new RareFreqWang().testSingleFamilyV2();
//
//    new RareFreqML().testSingleFamilyV2();
//    new RareFreqMilliganML().testSingleFamilyV2();
//  }
  public void testAllSingleFamilyV3() {
    N_TRIALS = 10000;  // 10,000 for the paper
    testSingleFamilyV3(N_TRIALS);
    new RareFreqKinshipTrueFreq().testSingleFamilyV3(N_TRIALS);
//    new RareFreqKinshipOuterPairs().testSingleFamilyV3(N_TRIALS);
    new RareFreqHegKon().testSingleFamilyV3(N_TRIALS);
    new RareFreqLynch().testSingleFamilyV3(N_TRIALS);
    new RareFreqWang().testSingleFamilyV3(N_TRIALS);
  }

  public void testAllSingleFamily_forML() {
    N_TRIALS = 10000;  // 10,000 for the paper
    testAllSingleFamily_forML(N_TRIALS);
    new RareFreqLynch().testAllSingleFamily_forML(N_TRIALS);
    new RareFreqWang().testAllSingleFamily_forML(N_TRIALS);

//    new RareFreqMilliganML().testAllSingleFamily_forML(N_TRIALS);
//    new RareFreqML().testAllSingleFamily_forML(N_TRIALS);
  }
  public void testAllSingleFamilyV3_bySize() {
    N_TRIALS = 10000;  // 10,000 for the paper
    testSingleFamilyV3_bySize(N_TRIALS);

//    new RareFreqKinshipTrueFreq().testSingleFamilyV3_bySize(N_TRIALS);
//    new RareFreqKinshipOuterPairs().testSingleFamily(N_TRIALS);

    new RareFreqHegKon().testSingleFamilyV3_bySize(N_TRIALS);
    new RareFreqLynch().testSingleFamilyV3_bySize(N_TRIALS);
    new RareFreqWang().testSingleFamilyV3_bySize(N_TRIALS);
  }
  public void testByNG() {
    N_ALLELES = 10;
    N_LOCI = 10;
    calcByNG("_QG_TRI_NL" + N_LOCI + "_FGS" + GROUP_SIZE + "_NA" + N_ALLELES);
  }
//  public void testSingleFamilyV2() {
//    N_TRIALS = 10000;  // 10,000 paper
//    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
//    N_ALLELES = 10;
//    N_LOCI = 5;
////    GROUP_SIZE = 50;
//    GROUP_SIZE = 100;
////    MIN_PROP = 10;
//    calcByProportion("_QG_TRI_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
//  }

  public void testSingleFamilyV3(int nTrials) {
    R_MTRX_TYPE = Kingroup.PAIRWISE_R_QG;
    N_TRIALS = nTrials;  // 10,000 paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    N_ALLELES = 10;
    N_LOCI = 10;
    GROUP_SIZE = 100;
    MIN_PROP = 10;
    MAX_PROP = 80;
    PROP_STEP = 10;
    calcByProportion("_QG_EQ_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }

  public void setupAllSingleFamily_forML() {
    N_ALLELES = 10;
    N_LOCI = 5;
    GROUP_SIZE = 100;
    MIN_PROP = 10;
    MAX_PROP = 40;
    PROP_STEP = 5;
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
  }
  public void testAllSingleFamily_forML(int nTrials) {
    setupAllSingleFamily_forML();
    R_MTRX_TYPE = Kingroup.PAIRWISE_R_QG;
    N_TRIALS = nTrials;
    calcByProportion("_QG_TR_NL" + N_LOCI + "_GS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }

  public void testSingleFamilyV3_bySize(int nTrials) {
    R_MTRX_TYPE = Kingroup.PAIRWISE_R_QG;
    N_TRIALS = nTrials;  // 10,000 paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY_BY_SIZE);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_ONE_COMMON);
    N_ALLELES = 5;
    N_LOCI = 10;
    GROUP_SIZE = 10; // number of full-sibs!!!!
    MIN_PROP = 20;   // MIN of the total sample size
    MAX_PROP = 150;
    PROP_STEP = 10;
    calcByProportion("_QG_EQ_NL" + N_LOCI + "_NFS" + GROUP_SIZE  + "_NA" + N_ALLELES);
  }


//  public void calcByNA(String tag) {
//    int ARR_SIZE = MAX_N_ALLELES - MIN_N_ALLELES + 1;
//    double[] locId = new double[ARR_SIZE];
//    double[][] varNA = new double[N_KIN][ARR_SIZE];
//    double[][] biasNA = new double[N_KIN][ARR_SIZE];
//    for (N_ALLELES = MIN_N_ALLELES; N_ALLELES <= MAX_N_ALLELES; N_ALLELES++) {
//      double[][] mean = new double[N_KIN][N_TRIALS];
//      double[][] var = new double[N_KIN][N_TRIALS];
//      for (int i = 0; i < N_TRIALS; i++) {
//        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
//        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
//        POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
//        POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
//
//        calcRMatrix(POP_BUILDER_MODEL);
//        for (int k = 0; k < N_KIN; k++) {
//          mean[k][i] = biasMean[k];
//          var[k][i] = rVar[k];
//        }
//      }
//
//      int idxNA = N_ALLELES - MIN_N_ALLELES;
//      for (int k = 0; k < N_KIN; k++) {
//        StatsRes stats = new Stats(mean[k]);
//        biasNA[k][idxNA] = stats.getMean();
//
//        stats = new Stats(var[k]);
//        varNA[k][idxNA] = stats.getS2();
//      }
//      log.info("\nbiasNA[FS][idxNA] = " + (float)biasNA[FS][idxNA]);
//      locId[idxNA] = N_ALLELES;
//    }
//
//    LOG.saveToFile(locId, biasNA[FS], DIR, "bias_FS"+tag+".csv");
//    LOG.saveToFile(locId, varNA[FS], DIR, "var_FS"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[HS], DIR, "bias_HS"+tag+".csv");
//    LOG.saveToFile(locId, varNA[HS], DIR, "var_HS"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[PO], DIR, "bias_PO"+tag+".csv");
//    LOG.saveToFile(locId, varNA[PO], DIR, "var_PO"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[NR], DIR, "bias_NR"+tag+".csv");
//    LOG.saveToFile(locId, varNA[NR], DIR, "var_NR"+tag+".csv");
//  }
//
//  public void calcByNL(String tag) {
//    int ARR_SIZE = MAX_N_LOCI - MIN_N_LOCI + 1;
//    double[] locId = new double[ARR_SIZE];
//    double[][] varNA = new double[N_KIN][ARR_SIZE];
//    double[][] biasNA = new double[N_KIN][ARR_SIZE];
//    double[][] pNA = new double[N_KIN][ARR_SIZE];
//    for (N_LOCI = MIN_N_LOCI; N_LOCI <= MAX_N_LOCI; N_LOCI++) {
//      double[][] tmpBiasMean = new double[N_KIN][N_TRIALS];
//      double[][] tmpPMean = new double[N_KIN][N_TRIALS];
//      double[][] tmpRVar = new double[N_KIN][N_TRIALS];
//      for (int i = 0; i < N_TRIALS; i++) {
//        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
//        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
//        POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
//        POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
//
//        calcRMatrix(POP_BUILDER_MODEL);
//        for (int k = 0; k < N_KIN; k++) {
//          tmpBiasMean[k][i] = biasMean[k];
//          tmpRVar[k][i] = rVar[k];
//          tmpPMean[k][i] = pMean[k];
//        }
//      }
//
//      int idxNA = N_LOCI - MIN_N_LOCI;
//      for (int k = 0; k < N_KIN; k++) {
//        StatsRes stats = new Stats(tmpBiasMean[k]);
//        biasNA[k][idxNA] = stats.getMean();
//
//        stats = new Stats(tmpRVar[k]);
//        varNA[k][idxNA] = stats.getS2();
//
//        stats = new Stats(tmpPMean[k]);
//        pNA[k][idxNA] = stats.getMean();
//      }
//      log.info("\nbiasNA[FS][idxNA] = " + (float)biasNA[FS][idxNA]);
//      locId[idxNA] = N_LOCI;
//    }
//
//    LOG.saveToFile(locId, biasNA[FS], DIR, "bias_FS"+tag+".csv");
//    LOG.saveToFile(locId, varNA[FS], DIR, "var_FS"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[HS], DIR, "bias_HS"+tag+".csv");
//    LOG.saveToFile(locId, varNA[HS], DIR, "var_HS"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[PO], DIR, "bias_PO"+tag+".csv");
//    LOG.saveToFile(locId, varNA[PO], DIR, "var_PO"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[NR], DIR, "bias_NR"+tag+".csv");
//    LOG.saveToFile(locId, varNA[NR], DIR, "var_NR"+tag+".csv");
//
//    // P-VAL
//    LOG.saveToFile(locId, pNA[FS], DIR, "pval_FS"+tag+".csv");
//    LOG.saveToFile(locId, pNA[HS], DIR, "pval_HS"+tag+".csv");
//    LOG.saveToFile(locId, pNA[PO], DIR, "pval_PO"+tag+".csv");
//    LOG.saveToFile(locId, pNA[NR], DIR, "pval_NR"+tag+".csv");
//  }
//  public void calcByNG(String tag) {
//    int ARR_SIZE = MAX_N_GROUPS - MIN_N_GROUPS + 1;
//    double[] locId = new double[ARR_SIZE];
//    double[][] varNA = new double[N_KIN][ARR_SIZE];
//    double[][] biasNA = new double[N_KIN][ARR_SIZE];
//    for (N_GROUPS = MIN_N_GROUPS; N_GROUPS <= MAX_N_GROUPS; N_GROUPS++) {
//      double[][] mean = new double[N_KIN][N_TRIALS];
//      double[][] var = new double[N_KIN][N_TRIALS];
//      for (int i = 0; i < N_TRIALS; i++) {
//        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
//        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
//        POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
//        POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
//
//        calcRMatrix(POP_BUILDER_MODEL);
//        for (int k = 0; k < N_KIN; k++) {
//          mean[k][i] = biasMean[k];
//          var[k][i] = rVar[k];
//        }
//      }
//
//      int idxNA = N_GROUPS - MIN_N_GROUPS;
//      for (int k = 0; k < N_KIN; k++) {
//        StatsRes stats = new Stats(mean[k]);
//        biasNA[k][idxNA] = stats.getMean();
//
//        stats = new Stats(var[k]);
//        varNA[k][idxNA] = stats.getS2();
//      }
//      log.info("\nbiasNA[FS][idxNA] = " + (float)biasNA[FS][idxNA]);
//      locId[idxNA] = N_GROUPS;
//    }
//
//    LOG.saveToFile(locId, biasNA[FS], DIR, "bias_FS"+tag+".csv");
//    LOG.saveToFile(locId, varNA[FS], DIR, "var_FS"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[HS], DIR, "bias_HS"+tag+".csv");
//    LOG.saveToFile(locId, varNA[HS], DIR, "var_HS"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[PO], DIR, "bias_PO"+tag+".csv");
//    LOG.saveToFile(locId, varNA[PO], DIR, "var_PO"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[NR], DIR, "bias_NR"+tag+".csv");
//    LOG.saveToFile(locId, varNA[NR], DIR, "var_NR"+tag+".csv");
//  }
//  public void calcByProportion(String tag) {
//    int ARR_SIZE = (MAX_PROP - MIN_PROP) / PROP_STEP + 1;
//    double[] locId = new double[ARR_SIZE];
//    double[][] varNA = new double[N_KIN][ARR_SIZE];
//    double[][] biasNA = new double[N_KIN][ARR_SIZE];
//    double[][] pNA = new double[N_KIN][ARR_SIZE];
//    for (PROP = MIN_PROP; PROP <= MAX_PROP; PROP += PROP_STEP) {
//      double[][] tmpBiasMean = new double[N_KIN][N_TRIALS];
//      double[][] tmpPMean = new double[N_KIN][N_TRIALS];
//      double[][] tmpRVar = new double[N_KIN][N_TRIALS];
//      for (int i = 0; i < N_TRIALS; i++) {
//        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
//        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
//        POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
//        POP_BUILDER_MODEL.setNumFullSibs(PROP);
//
//        calcRMatrix(POP_BUILDER_MODEL);
//        for (int k = 0; k < N_KIN; k++) {
//          tmpBiasMean[k][i] = biasMean[k];
//          tmpRVar[k][i] = rVar[k];
//          tmpPMean[k][i] = pMean[k];
//        }
//      }
//
//      int idxNA = (PROP - MIN_PROP) / PROP_STEP;
//      for (int k = 0; k < N_KIN; k++) {
//        StatsRes stats = new Stats(tmpBiasMean[k]);
//        biasNA[k][idxNA] = stats.getMean();
//
//        stats = new Stats(tmpRVar[k]);
//        varNA[k][idxNA] = stats.getS2();
//
//        stats = new Stats(tmpPMean[k]);
//        pNA[k][idxNA] = stats.getMean();
//      }
//      log.info("\nbiasNA[FS][idxNA] = " + (float)biasNA[FS][idxNA]);
//      locId[idxNA] = PROP;
//    }
//
//    LOG.saveToFile(locId, biasNA[FS], DIR, "bias_FS"+tag+".csv");
//    LOG.saveToFile(locId, varNA[FS], DIR, "var_FS"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[HS], DIR, "bias_HS"+tag+".csv");
//    LOG.saveToFile(locId, varNA[HS], DIR, "var_HS"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[PO], DIR, "bias_PO"+tag+".csv");
//    LOG.saveToFile(locId, varNA[PO], DIR, "var_PO"+tag+".csv");
//
//    LOG.saveToFile(locId, biasNA[NR], DIR, "bias_NR"+tag+".csv");
//    LOG.saveToFile(locId, varNA[NR], DIR, "var_NR"+tag+".csv");
//
//    // P-VAL
//    LOG.saveToFile(locId, pNA[FS], DIR, "pval_FS"+tag+".csv");
//    LOG.saveToFile(locId, pNA[HS], DIR, "pval_HS"+tag+".csv");
//    LOG.saveToFile(locId, pNA[PO], DIR, "pval_PO"+tag+".csv");
//    LOG.saveToFile(locId, pNA[NR], DIR, "pval_NR"+tag+".csv");
//  }
//
//  protected SysPop makeSysPopFrom(PopBuilderModel builderModel) {
//    SysPop res = null;
//    while (true) {
//      res = SysPopFactory.makeSysPopFrom(builderModel);
////    log.info("\nsysPop=\n" + res);
////    log.info("\npopFreq=\n" + res.getFreq());
//
//      if (SysAlleleFreqFactory.hasZeroHeterLocus(res)) {
//        log.info("\nsysPop=\n" + res);
//        continue;
//      }
//
//      SysAlleleFreq observedFreq = SysAlleleFreqFactory.makeFrom(res, true);
////    log.info("observedFreq=\n" + observedFreq);
//      res.setFreq(observedFreq);
//      break;
//    }
//    return res;
//  }
//  protected void calcRMatrix(PopBuilderModel builderModel) {
//    SysPop sysPop = makeSysPopFrom(builderModel);
//
//    PairwiseRMtrxFactory factory = new PairwiseRMtrxFactory(R_MTRX_TYPE);
//    PairwiseRMtrx rm = factory.makeRMtrx(sysPop, sysPop);
//    loadKin(FS, 0.5, SysPopFactory.getFS(rm, sysPop));
//    loadKin(HS, 0.25, SysPopFactory.getHS(rm, sysPop));
//    loadKin(PO, 0.5, SysPopFactory.getPO(rm, sysPop));
//    loadKin(NR, 0.0, SysPopFactory.getNR(rm, sysPop));
//
//    PairwiseRMtrx pm = new PairwisePMtrx(factory).makePMtrx(sysPop);
//    loadPVal(FS, SysPopFactory.getFS(pm, sysPop));
//    loadPVal(HS, SysPopFactory.getHS(pm, sysPop));
//    loadPVal(PO, SysPopFactory.getPO(pm, sysPop));
//    loadPVal(NR, SysPopFactory.getNR(pm, sysPop));
//  }
//
//  protected void loadKin(int kinIdx, double trueR, double[] arr) {
////    log.info("arr["+kinIdx+"].length = " + arr.length);
//    StatsRes stats = new Stats(arr);
//    biasMean[kinIdx] = stats.getMean() - trueR;
//    rVar[kinIdx] = stats.getS2();
//  }
//  protected void loadPVal(int kinIdx, double[] arr) {
//    StatsRes stats = new Stats(arr);
//    pMean[kinIdx] = stats.getMean();
//  }
}
