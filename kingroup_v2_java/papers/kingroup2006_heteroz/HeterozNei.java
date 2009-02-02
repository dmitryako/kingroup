package papers.kingroup2006_heteroz;

import kingroup_v2.cervus.AlleleAnalysisFactory;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipAlleleFreqOpt;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.stats.Stats;
import javax.stats.StatsRes;

/**
 * Created by: jc1386591
 * Date: 28/06/2006. Time: 20:29:36
 */
public class HeterozNei extends HeterozCommon
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  protected final double EPS = 1e-5;

  protected int MIN_GROUP_SIZE = 1;
  protected int MIN_N_GROUPS = 3;
  protected int MAX_N_GROUPS = 7;
  protected int N_GROUPS = 4;

  protected int N_TRIALS = 1000;
  protected int MIN_N_ALLELES = 4;
  protected int MAX_N_ALLELES = 25;
  protected int N_ALLELES = 10;
  protected int N_LOCI = 1;
  protected PopBuilderModel POP_BUILDER_MODEL = new PopBuilderModel();
  protected Kinship KINSHIP = new Kinship();

  public HeterozNei() {
    N_TRIALS = 1000;  // 10,000 paper
    N_LOCI = 4;
    MIN_N_ALLELES = 4;
    MAX_N_ALLELES = 20;
    MIN_GROUP_SIZE = 1;
    N_GROUPS = 5; // 5
    MIN_N_GROUPS = 3;
    MAX_N_GROUPS = 16;

    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
    POP_BUILDER_MODEL.setGroupSize(MIN_GROUP_SIZE);
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setIncParents(true); // not really used anyway

    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.PEDIGREE_BUILDER);

    KinshipAlleleFreqOpt opt = KINSHIP.getAlleleFreqOpt();
    opt.setAllowZeroFreq(true);
  }
  public void testByNA() {
    N_LOCI = 4;
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_TRIANGULAR);
    calcByNA("_TRI_NL" + N_LOCI + "_FGS" + MIN_GROUP_SIZE + "_NG" + N_GROUPS);
  }

  public void testAll() {
    testByNA();
    testByNG();
  }
  public void testByNG() {
    N_ALLELES = 10;
    N_LOCI = 10;
    calcByNG("_TRI_NL" + N_LOCI + "_FGS" + MIN_GROUP_SIZE + "_NA" + N_ALLELES);
  }

  public void calcByNA(String tag) {
    int ARR_SIZE = MAX_N_ALLELES - MIN_N_ALLELES + 1;
    double[] locId = new double[ARR_SIZE];
    double[] varNA = new double[ARR_SIZE];
    double[] biasNA = new double[ARR_SIZE];
    for (N_ALLELES = MIN_N_ALLELES; N_ALLELES <= MAX_N_ALLELES; N_ALLELES++) {
      double[] bias = new double[N_TRIALS];
      for (int i = 0; i < N_TRIALS; i++) {
        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
        POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
        POP_BUILDER_MODEL.setGroupSize(MIN_GROUP_SIZE);
        bias[i] = calcHeteroz(POP_BUILDER_MODEL);
      }

      int idxNA = N_ALLELES - MIN_N_ALLELES;
      StatsRes stats = new Stats(bias);
      biasNA[idxNA] = stats.getMean();
      varNA[idxNA] = stats.getS2();
      log.info("\nbiasNA[idxNA] = " + (float)biasNA[idxNA]);
      locId[idxNA] = N_ALLELES;
    }

    LOG.saveToFile(locId, biasNA, DIR, "bias"+tag+".csv");
    LOG.saveToFile(locId, varNA, DIR, "var"+tag+".csv");
  }


  public void calcByNG(String tag) {
    int ARR_SIZE = MAX_N_GROUPS - MIN_N_GROUPS + 1;
    double[] locId = new double[ARR_SIZE];
    double[] varNA = new double[ARR_SIZE];
    double[] biasNA = new double[ARR_SIZE];
    for (N_GROUPS = MIN_N_GROUPS; N_GROUPS <= MAX_N_GROUPS; N_GROUPS++) {
      double[] bias = new double[N_TRIALS];
      for (int i = 0; i < N_TRIALS; i++) {
        POP_BUILDER_MODEL.setNumAlleles(N_ALLELES);
        POP_BUILDER_MODEL.setNumLoci(N_LOCI);
        POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
        POP_BUILDER_MODEL.setGroupSize(MIN_GROUP_SIZE);
        bias[i] = calcHeteroz(POP_BUILDER_MODEL);
      }

      int idxNA = N_GROUPS - MIN_N_GROUPS;
      StatsRes stats = new Stats(bias);
      biasNA[idxNA] = stats.getMean();
      varNA[idxNA] = stats.getS2();
      log.info("\nbiasNA[idxNA] = " + (float)biasNA[idxNA]);
      locId[idxNA] = N_ALLELES;
      log.info("\nbiasNA[FS][idxNA] = " + (float)biasNA[idxNA]);
      locId[idxNA] = N_GROUPS;
    }

    LOG.saveToFile(locId, biasNA, DIR, "bias"+tag+".csv");
    LOG.saveToFile(locId, varNA, DIR, "var"+tag+".csv");
  }

  protected double calcHeteroz(PopBuilderModel builderModel) {
    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
    double trueHet = AlleleAnalysisFactory.calcTrueHeterozAvr(sysPop.getFreq());
//    log.info("\nsysPop=\n" + sysPop);

    SysAlleleFreq observedFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);
//    log.info("observedFreq=\n" + observedFreq);
    sysPop.setFreq(observedFreq);
    double het = AlleleAnalysisFactory.calcNeiHeterozAvr(sysPop);
    return trueHet - het;
  }

}
