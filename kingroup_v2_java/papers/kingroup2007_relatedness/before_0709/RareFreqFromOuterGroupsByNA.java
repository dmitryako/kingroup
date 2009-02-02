package papers.kingroup2007_relatedness.before_0709;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipAlleleFreqOpt;
import kingroup_v2.kinship.KinshipRMtrxBiasGroup;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.relatedness.PairwiseRMtrx;
import papers.kingroup2007_relatedness.Relate2007_Common;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.stats.Stats;
import javax.stats.StatsRes;
import javax.utilx.arrays.vec.Vec;

/**
 * Created by: jc1386591
 * Date: 16/06/2006. Time: 16:45:58
 */
public class RareFreqFromOuterGroupsByNA extends Relate2007_Common
{
  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();

  protected final double EPS = 1e-5;

  protected int N_GROUPS = 5;
  protected int GROUP_SIZE = 10;
  protected int N_TRIALS = 100;
  protected int MIN_N_ALLELES = 3;
  protected int MAX_N_ALLELES = 25;
  protected int N_ALLELES = 10;
  protected int N_LOCI = 1;
  protected PopBuilderModel POP_BUILDER_MODEL = new PopBuilderModel();
  protected int R_TYPE = Kingroup.PAIRWISE_R_QG;
  protected Kinship KINSHIP = new Kinship();

  public RareFreqFromOuterGroupsByNA() {
    N_TRIALS = 1000;  // 10,000 paper
//    N_TRIALS = 1000; // paper 10,000
    N_LOCI = 4;
    N_GROUPS = 5;
    GROUP_SIZE = 10;

    R_TYPE = Kingroup.PAIRWISE_R_QG;
//    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    POP_BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    POP_BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    POP_BUILDER_MODEL.setNumGroups(N_GROUPS);
    POP_BUILDER_MODEL.setGroupSize(GROUP_SIZE);
    POP_BUILDER_MODEL.setNumLoci(N_LOCI);
    POP_BUILDER_MODEL.setIncParents(false);
    KinshipAlleleFreqOpt opt = KINSHIP.getAlleleFreqOpt();
    opt.setAllowZeroFreq(true);
  }
  public void testQG() {
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.FULL_SIB_BUILDER);
    calc(0.5, "QG_FS_RAND_NL" + N_LOCI + "_GS" + GROUP_SIZE + "_NG" + N_GROUPS);

    //TODO
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.FULL_SIB_BUILDER);
//    calc(0.5, KinshipIBDFactory.makeParentOffspring(), "kinship_PO_RAND_NL" + N_LOCI);

    //TODO
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.FULL_SIB_BUILDER);
//    calc(0.0, KinshipIBDFactory.makeUnrelated(), "kinship_UN_RAND_NL" + N_LOCI);

    //TODO
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.FULL_SIB_BUILDER);
//    calc(0.25, KinshipIBDFactory.makeHalfSib(), "kinship_HS_RAND_NL" + N_LOCI);
  }

  public void calc(double trueR, String tag
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
        arr[i] = calcGroups(POP_BUILDER_MODEL);
//        log.info("\n arr["+i+"]=" + (float)arr[i]);
      }
//      log.info("\narr=\n" + DoubleArr.toString(arr));
      StatsRes stats = new Stats(arr);
      int idx = N_ALLELES - MIN_N_ALLELES;
      biasArr[idx] = stats.getMean() - trueR;
//      biasArr[idx] = stats.getMean();
      varArr[idx] = stats.getS2();
      locId[idx] = N_ALLELES;
      log.info("\nN_ALLELES="+N_ALLELES+", bias, stddev=" + (float)biasArr[idx] + ", " + (float)varArr[idx]);
    }
    LOG.saveToFile(locId, biasArr, DIR, "bias_"+tag+".csv");
    LOG.saveToFile(locId, varArr, DIR, "var_"+tag+".csv");
  }

  private double calcGroups(PopBuilderModel builderModel) {
    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
//    log.info("\nsysPop=\n" + sysPop);
//    log.info("\nfreq=\n" + sysPop.getFreq());

    SysPop[] groups = SysPopFactory.makeGroupsFrom(sysPop);
    double[] grpAvr = Vec.makeArray(0., groups.length);
    for (int i = 0; i < groups.length; i++) {
      PairwiseRMtrx mtrx = new KinshipRMtrxBiasGroup(groups[i], sysPop, KINSHIP);
      mtrx.calc();
      grpAvr[i] = mtrx.calcAvr();
//      log.info("\ngrpAvr[i]=" + (float)grpAvr[i]);
    }
    return Vec.avr(grpAvr);
  }

}
