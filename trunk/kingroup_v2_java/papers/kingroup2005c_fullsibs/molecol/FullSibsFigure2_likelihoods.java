package papers.kingroup2005c_fullsibs.molecol;
import junit.framework.TestCase;
import kingroup.genetics.KinshipRatioMtrxV1;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.model.HypothesisModel;
import kingroup.model.IBDFactory;
import kingroup.model.KinshipIBDModelV1;
import kingroup.papers.butler2004.ButlerPopBuilder;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.population.OldPop;

import javax.iox.LOG;
import javax.swingx.panelx.GridBagView;
import javax.utilx.DoubleArrayByInt;
import java.io.File;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Jan 7, 2005, Time: 9:14:23 AM
 */
public class FullSibsFigure2_likelihoods //extends PerformanceChart
{
  private DoubleArrayByInt allDists_ = new DoubleArrayByInt();
  private final String DIR = "fullsibs" + File.separator + "likelihoods";
  private final int NUM_LOCI = 8;  //8-paper
  private final int NUM_ALLELES = 8; //8-paper
  private final int POP_SIZE = 2; //2-paper
  private final boolean DISPLAY_RESULTS_ = false; // false-paper
  private final int NUM_REPEATS = 10000; // 10000-paper
  public static void main(String[] args) {
    FullSibsFigure2_likelihoods self = new FullSibsFigure2_likelihoods();
    LOG.setTrace(true);
    self.run();
    System.exit(0);
  }
  public FullSibsFigure2_likelihoods() {
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  public void run() {
    generateLogLkhd(DIR, "fullsibs.txt", ButlerFamilyData.BUTLER_1x2);
    generateLogLkhd(DIR, "unrelated.txt", ButlerFamilyData.BUTLER_2x1);
  }
  public void generateLogLkhd(String dirName, String fileName, int familyType) {
    allDists_ = new DoubleArrayByInt();
    HypothesisModel primHypo = new HypothesisModel(IBDFactory.makeFullSibs());
    KinshipIBDModelV1 nullHypo = IBDFactory.makeUnrelated();
    KinshipIBDModelV1 parent_offspring = IBDFactory.makeParentOffspring();
//      KinshipIBDModelV1 parent_unrelated = IBDFactory.makeComplexParentUnrelated();
    ButlerPopBuilderModel popModel = new ButlerPopBuilderModel();
    popModel.loadDefaults();
    popModel.setIncParents(false);
    popModel.setAllelicFreqType(ButlerFamilyData.EQUAL_FREQ);
    popModel.setFamilyType(familyType);
    popModel.setSize(POP_SIZE);
    popModel.setNumLoci(NUM_LOCI);
    popModel.setNumAlleles(NUM_ALLELES);
    OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(popModel);
    freq.normalize(1.0f, false);
    double prims[] = new double[NUM_REPEATS];
    double nulls[] = new double[NUM_REPEATS];
    double parent_nulls[] = new double[NUM_REPEATS];
    for (int i = 0; i < NUM_REPEATS; i++) {
      KinshipRatioMtrxV1 ru = calculateLogLkhd(freq, popModel, primHypo, nullHypo);
      KinshipRatioMtrxV1 rp = calculateLogLkhd(freq, popModel, primHypo, parent_offspring);
//         KinshipRatioMtrxV1 rpu = calculateLogLkhd(UsrPopFactory, popModel, primHypo, parent_unrelated);
      prims[i] = ru.getPrim().getLog(0, 1);
      nulls[i] = ru.getNull().getLog(0, 1);
      parent_nulls[i] = rp.getNull().getLog(0, 1);
    }
    saveStats(prims, nulls);
    LOG.saveToFile(prims, nulls, parent_nulls, dirName, fileName);
    LOG.saveToFile(prims, nulls, parent_nulls
      , dirName + File.separator + "NA" + NUM_ALLELES + "_" + NUM_REPEATS, fileName);
    if (DISPLAY_RESULTS_)
      displayResults(prims, "Full-siblings"
        , nulls, "Unrelated"
        , parent_nulls, "Parent-offspring");
  }
  private void saveStats(double[] prims, double[] nulls) {
//      java.util.List list = DoubleArr.asList(prims);
//      BoxAndWhiskerItem item
//         = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(list);
//      LOG.report(this, "prims mean=" + item.getMean());
//
//      list = DoubleArr.asList(nulls);
//      item = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(list);
//      LOG.report(this, "nulls mean=" + item.getMean());
  }
  private KinshipRatioMtrxV1 calculateLogLkhd(OldAlleleFreq freq, ButlerPopBuilderModel popModel
    , KinshipIBDModelV1 prim, KinshipIBDModelV1 nullModel) {
    OldPop popA = new ButlerPopBuilder(freq).buildButler(popModel);
    TestCase.assertEquals(popModel.getSize(), popA.size());
    KinshipRatioMtrxV1 res = new KinshipRatioMtrxV1(popA, prim, nullModel);
    if (!res.calculateAll())
      return null;
    return res;
  }
  private void displayResults(double[] a, String title
    , double[] a2, String title2
    , double[] a3, String title3) {
    GridBagView panel = new GridBagView();
//    HistogramInputData data = new HistogramInputData(a);
//    data.setPreferredViewSize(new Dimension(300, 200));
////      JPanel view = new HistogramView(title, data);
////      panel.endRow(view);
////
//    data = new HistogramInputData(a2);
//    data.setPreferredViewSize(new Dimension(300, 200));
////      view = new HistogramView(title2, data);
////      panel.endRow(view);
//    data = new HistogramInputData(a3);
//    data.setPreferredViewSize(new Dimension(300, 200));
//      view = new HistogramView(title3, data);
//      panel.endRow(view);
//    ApplyDialogUI dlg = new ApplyDialogUI(panel, this, true);
//    dlg.setVisible(true);
  }
}