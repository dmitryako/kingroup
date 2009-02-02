package papers.kingroup2007_relatedness;
import junit.framework.TestCase;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.relatedness.PairwisePMtrx;
import kingroup_v2.relatedness.PairwiseRMtrx;
import kingroup_v2.ucm.relatedness.PairwiseRMtrxFactory;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.stats.Stats;
import javax.stats.StatsRes;
import javax.utilx.arrays.vec.Vec;

/**
 * Created by: jc1386591
 * Date: 16/06/2006. Time: 16:44:30
 */
public class Relate2007_Common extends TestCase
{
  protected final static ProjectLogger log = ProjectLogger.getLogger(Relate2007_Common.class);
  
//  public String DIR = "papers" + File.separator + "kingroup2007_relatedness" + File.separator + "output";
  public String DIR = "E:\\dev\\kingroup\\papers\\2007_relatedness\\output";

  protected PopBuilderModel POP_BUILDER_MODEL = new PopBuilderModel();
  protected Kinship KINSHIP = new Kinship();
  protected int R_MTRX_TYPE = Kingroup.PAIRWISE_R_QG;

  protected int GROUP_SIZE = 1;
  protected int MIN_N_GROUPS = 3;
  protected int MAX_N_GROUPS = 7;
  protected int N_GROUPS = 4;
  protected int PROP = 1;     // proportion of full sibs in the sample
  protected int MIN_PROP = 0;
  protected int MAX_PROP = 50;
  protected int PROP_STEP = 5;

  protected int N_TRIALS = 1000;
  protected int MIN_N_ALLELES = 4;
  protected int MAX_N_ALLELES = 25;
  protected int N_ALLELES = 10;

  protected int MIN_N_LOCI = 2;
  protected int MAX_N_LOCI = 10;
  protected int N_LOCI = 1;

  protected int NR = 0;
  protected int FS = 1;
  protected int HS = 2;
  protected int PO = 3;
  protected int N_KIN = 4;
  protected double[] pMean = Vec.makeArray(0.0, N_KIN);
  protected double[] biasMean = Vec.makeArray(0.0, N_KIN);
  protected double[] rVar = Vec.makeArray(0.0, N_KIN);


  public Relate2007_Common() {
    log.start(Relate2007_Common.class.getName());
    log.setThresholdInfo();
  }

  public void calcByNA(String tag) {
    int ARR_SIZE = MAX_N_ALLELES - MIN_N_ALLELES + 1;
    double[] locId = new double[ARR_SIZE];
    double[][] varNA = new double[N_KIN][ARR_SIZE];
    double[][] biasNA = new double[N_KIN][ARR_SIZE];
    for (N_ALLELES = MIN_N_ALLELES; N_ALLELES <= MAX_N_ALLELES; N_ALLELES++) {
      double[][] mean = new double[N_KIN][N_TRIALS];
      double[][] var = new double[N_KIN][N_TRIALS];
      for (int i = 0; i < N_TRIALS; i++) {
        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
        POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
        POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);

        calcRMatrix(POP_BUILDER_MODEL);
        for (int k = 0; k < N_KIN; k++) {
          mean[k][i] = biasMean[k];
          var[k][i] = rVar[k];
        }
      }

      int idxNA = N_ALLELES - MIN_N_ALLELES;
      for (int k = 0; k < N_KIN; k++) {
        StatsRes stats = new Stats(mean[k]);
        biasNA[k][idxNA] = stats.getMean();

        stats = new Stats(var[k]);
        varNA[k][idxNA] = stats.getS2();
      }
      log.info("\nbiasNA[FS][idxNA] = " + (float)biasNA[FS][idxNA]);
      locId[idxNA] = N_ALLELES;
    }

    LOG.saveToFile(locId, biasNA[FS], DIR, "bias_FS"+tag+".csv");
    LOG.saveToFile(locId, varNA[FS], DIR, "var_FS"+tag+".csv");

    LOG.saveToFile(locId, biasNA[HS], DIR, "bias_HS"+tag+".csv");
    LOG.saveToFile(locId, varNA[HS], DIR, "var_HS"+tag+".csv");

    LOG.saveToFile(locId, biasNA[PO], DIR, "bias_PO"+tag+".csv");
    LOG.saveToFile(locId, varNA[PO], DIR, "var_PO"+tag+".csv");

    LOG.saveToFile(locId, biasNA[NR], DIR, "bias_NR"+tag+".csv");
    LOG.saveToFile(locId, varNA[NR], DIR, "var_NR"+tag+".csv");
  }

  public void calcByNL(String tag) {
    int ARR_SIZE = MAX_N_LOCI - MIN_N_LOCI + 1;
    double[] locId = new double[ARR_SIZE];
    double[][] varNA = new double[N_KIN][ARR_SIZE];
    double[][] biasNA = new double[N_KIN][ARR_SIZE];
    double[][] pNA = new double[N_KIN][ARR_SIZE];
    for (N_LOCI = MIN_N_LOCI; N_LOCI <= MAX_N_LOCI; N_LOCI++) {
      double[][] tmpBiasMean = new double[N_KIN][N_TRIALS];
      double[][] tmpPMean = new double[N_KIN][N_TRIALS];
      double[][] tmpRVar = new double[N_KIN][N_TRIALS];
      for (int i = 0; i < N_TRIALS; i++) {
        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
        POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
        POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);

        calcRMatrix(POP_BUILDER_MODEL);
        for (int k = 0; k < N_KIN; k++) {
          tmpBiasMean[k][i] = biasMean[k];
          tmpRVar[k][i] = rVar[k];
          tmpPMean[k][i] = pMean[k];
        }
      }

      int idxNA = N_LOCI - MIN_N_LOCI;
      for (int k = 0; k < N_KIN; k++) {
        StatsRes stats = new Stats(tmpBiasMean[k]);
        biasNA[k][idxNA] = stats.getMean();

        stats = new Stats(tmpRVar[k]);
        varNA[k][idxNA] = stats.getS2();

        stats = new Stats(tmpPMean[k]);
        pNA[k][idxNA] = stats.getMean();
      }
      log.info("\nbiasNA[FS][idxNA] = " + (float)biasNA[FS][idxNA]);
      locId[idxNA] = N_LOCI;
    }

    LOG.saveToFile(locId, biasNA[FS], DIR, "bias_FS"+tag+".csv");
    LOG.saveToFile(locId, varNA[FS], DIR, "var_FS"+tag+".csv");

    LOG.saveToFile(locId, biasNA[HS], DIR, "bias_HS"+tag+".csv");
    LOG.saveToFile(locId, varNA[HS], DIR, "var_HS"+tag+".csv");

    LOG.saveToFile(locId, biasNA[PO], DIR, "bias_PO"+tag+".csv");
    LOG.saveToFile(locId, varNA[PO], DIR, "var_PO"+tag+".csv");

    LOG.saveToFile(locId, biasNA[NR], DIR, "bias_NR"+tag+".csv");
    LOG.saveToFile(locId, varNA[NR], DIR, "var_NR"+tag+".csv");

    // P-VAL
    LOG.saveToFile(locId, pNA[FS], DIR, "pval_FS"+tag+".csv");
    LOG.saveToFile(locId, pNA[HS], DIR, "pval_HS"+tag+".csv");
    LOG.saveToFile(locId, pNA[PO], DIR, "pval_PO"+tag+".csv");
    LOG.saveToFile(locId, pNA[NR], DIR, "pval_NR"+tag+".csv");
  }
  public void calcByNG(String tag) {
    int ARR_SIZE = MAX_N_GROUPS - MIN_N_GROUPS + 1;
    double[] locId = new double[ARR_SIZE];
    double[][] varNA = new double[N_KIN][ARR_SIZE];
    double[][] biasNA = new double[N_KIN][ARR_SIZE];
    for (N_GROUPS = MIN_N_GROUPS; N_GROUPS <= MAX_N_GROUPS; N_GROUPS++) {
      double[][] mean = new double[N_KIN][N_TRIALS];
      double[][] var = new double[N_KIN][N_TRIALS];
      for (int i = 0; i < N_TRIALS; i++) {
        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
        POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
        POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);

        calcRMatrix(POP_BUILDER_MODEL);
        for (int k = 0; k < N_KIN; k++) {
          mean[k][i] = biasMean[k];
          var[k][i] = rVar[k];
        }
      }

      int idxNA = N_GROUPS - MIN_N_GROUPS;
      for (int k = 0; k < N_KIN; k++) {
        StatsRes stats = new Stats(mean[k]);
        biasNA[k][idxNA] = stats.getMean();

        stats = new Stats(var[k]);
        varNA[k][idxNA] = stats.getS2();
      }
      log.info("\nbiasNA[FS][idxNA] = " + (float)biasNA[FS][idxNA]);
      locId[idxNA] = N_GROUPS;
    }

    LOG.saveToFile(locId, biasNA[FS], DIR, "bias_FS"+tag+".csv");
    LOG.saveToFile(locId, varNA[FS], DIR, "var_FS"+tag+".csv");

    LOG.saveToFile(locId, biasNA[HS], DIR, "bias_HS"+tag+".csv");
    LOG.saveToFile(locId, varNA[HS], DIR, "var_HS"+tag+".csv");

    LOG.saveToFile(locId, biasNA[PO], DIR, "bias_PO"+tag+".csv");
    LOG.saveToFile(locId, varNA[PO], DIR, "var_PO"+tag+".csv");

    LOG.saveToFile(locId, biasNA[NR], DIR, "bias_NR"+tag+".csv");
    LOG.saveToFile(locId, varNA[NR], DIR, "var_NR"+tag+".csv");
  }
  public void calcByProportion(String tag) {
    int ARR_SIZE = (MAX_PROP - MIN_PROP) / PROP_STEP + 1;
    double[] locId = new double[ARR_SIZE];
    double[][] varNA = new double[N_KIN][ARR_SIZE];
    double[][] biasNA = new double[N_KIN][ARR_SIZE];
    double[][] pNA = new double[N_KIN][ARR_SIZE];
    for (PROP = MIN_PROP; PROP <= MAX_PROP; PROP += PROP_STEP) {
      double[][] tmpBiasMean = new double[N_KIN][N_TRIALS];
      double[][] tmpPMean = new double[N_KIN][N_TRIALS];
      double[][] tmpRVar = new double[N_KIN][N_TRIALS];
      for (int i = 0; i < N_TRIALS; i++) {
        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
        POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
        POP_BUILDER_MODEL.setNumFullSibs(PROP);

        calcRMatrix(POP_BUILDER_MODEL);
        for (int k = 0; k < N_KIN; k++) {
          tmpBiasMean[k][i] = biasMean[k];
          tmpRVar[k][i] = rVar[k];
          tmpPMean[k][i] = pMean[k];
        }
      }

      int idxNA = (PROP - MIN_PROP) / PROP_STEP;
      for (int k = 0; k < N_KIN; k++) {
        StatsRes stats = new Stats(tmpBiasMean[k]);
        biasNA[k][idxNA] = stats.getMean();

        stats = new Stats(tmpRVar[k]);
        varNA[k][idxNA] = stats.getS2();

        stats = new Stats(tmpPMean[k]);
        pNA[k][idxNA] = stats.getMean();
      }
      log.info("\nbiasNA[FS][idxNA] = " + (float)biasNA[FS][idxNA]);
      locId[idxNA] = PROP;
    }

    LOG.saveToFile(locId, biasNA[FS], DIR, "bias_FS"+tag+".csv");
    LOG.saveToFile(locId, varNA[FS], DIR, "var_FS"+tag+".csv");

    LOG.saveToFile(locId, biasNA[HS], DIR, "bias_HS"+tag+".csv");
    LOG.saveToFile(locId, varNA[HS], DIR, "var_HS"+tag+".csv");

    LOG.saveToFile(locId, biasNA[PO], DIR, "bias_PO"+tag+".csv");
    LOG.saveToFile(locId, varNA[PO], DIR, "var_PO"+tag+".csv");

    LOG.saveToFile(locId, biasNA[NR], DIR, "bias_NR"+tag+".csv");
    LOG.saveToFile(locId, varNA[NR], DIR, "var_NR"+tag+".csv");

    // P-VAL
    LOG.saveToFile(locId, pNA[FS], DIR, "pval_FS"+tag+".csv");
    LOG.saveToFile(locId, pNA[HS], DIR, "pval_HS"+tag+".csv");
    LOG.saveToFile(locId, pNA[PO], DIR, "pval_PO"+tag+".csv");
    LOG.saveToFile(locId, pNA[NR], DIR, "pval_NR"+tag+".csv");
  }

  protected SysPop makeSysPopFrom(PopBuilderModel builderModel) {
    SysPop res = null;
    while (true) {
      res = SysPopFactory.makeSysPopFrom(builderModel);
//    log.info("\nsysPop=\n" + res);
//    log.info("\npopFreq=\n" + res.getFreq());

      if (SysAlleleFreqFactory.hasZeroHeterLocus(res)) {
        log.info("\nsysPop=\n" + res);
        continue;
      }

      SysAlleleFreq observedFreq = SysAlleleFreqFactory.makeFrom(res, true);
//    log.info("observedFreq=\n" + observedFreq);
      res.setFreq(observedFreq);
      break;
    }
    return res;
  }
  protected void calcRMatrix(PopBuilderModel  builderModel) {
    SysPop sysPop = makeSysPopFrom(builderModel);

    PairwiseRMtrxFactory factory = new PairwiseRMtrxFactory(R_MTRX_TYPE);
    PairwiseRMtrx rm = factory.makeRMtrx(sysPop, sysPop);
    loadKin(FS, 0.5, SysPopFactory.getFS(rm, sysPop));
    loadKin(HS, 0.25, SysPopFactory.getHS(rm, sysPop));
    loadKin(PO, 0.5, SysPopFactory.getPO(rm, sysPop));
    loadKin(NR, 0.0, SysPopFactory.getNR(rm, sysPop));

    PairwiseRMtrx pm = new PairwisePMtrx(factory).makePMtrx(sysPop);
    loadPVal(FS, SysPopFactory.getFS(pm, sysPop));
    loadPVal(HS, SysPopFactory.getHS(pm, sysPop));
    loadPVal(PO, SysPopFactory.getPO(pm, sysPop));
    loadPVal(NR, SysPopFactory.getNR(pm, sysPop));
  }

  protected void loadKin(int kinIdx, double trueR, double[] arr) {
//    log.info("arr["+kinIdx+"].length = " + arr.length);
    StatsRes stats = new Stats(arr);
    biasMean[kinIdx] = stats.getMean() - trueR;
    rVar[kinIdx] = stats.getS2();
  }
  protected void loadPVal(int kinIdx, double[] arr) {
    StatsRes stats = new Stats(arr);
    pMean[kinIdx] = stats.getMean();
  }

}