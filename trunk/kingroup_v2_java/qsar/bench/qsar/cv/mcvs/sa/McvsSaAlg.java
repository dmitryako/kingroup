package qsar.bench.qsar.cv.mcvs.sa;
import pattern.mvc.MVCTableView;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.qsar.MLR.MlrRes;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.QsarAlg;
import qsar.bench.qsar.cv.mccv.Mccv;
import qsar.bench.qsar.cv.mcvs.*;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swing.*;
import javax.swingx.ProgressWnd;
import javax.swingx.tablex.TableViewWithOpt;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.mtrx.MtrxFactory;
import javax.utilx.arrays.mtrx.MtrxPair;
import javax.utilx.arrays.vec.Vec;
import javax.utilx.bitset.BitSetFactory;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 6/06/2007, 14:50:33
 */
public class McvsSaAlg implements McvsAlg
{
  private final static ProjectLogger log = ProjectLogger.getLogger(McvsSaAlg.class);
  public static final String REFERENCE = "MCVS_SA";
  protected QBench project;
  protected QsarAlgFactory algFactory;
  protected Table zTable;
  protected TableViewWithOpt updateView;
  protected ProgressWnd progress = null;
  private static final double K_B = -1. / Math.log(0.5);
  //  private static final int IDX_Y = 0;
  protected int nV;
  protected int k;
  protected int kFixed;
  protected int n;
  protected int p;
  protected int N;
  protected McvsItemArr itemArr ;
  private static final int K_MAX = 10;
  protected Mcvs vs;
  protected double[][] zT;
  protected RandomSeed rand;

  public McvsSaAlg(QBench model, QsarAlgFactory algFactory, TableViewWithOpt updateView) {
    init(model);
    this.project = model;
    this.algFactory = algFactory;
    this.updateView = updateView;
  }
  private void init(QBench model) {
    rand = RandomSeed.getInstance();
    Mcvs vs = model.getMcvs();
    itemArr = new McvsItemArr(vs.getNumBest());
  }

  public void calcStats(Table zTable, boolean showProgress)
  {
    this.zTable = zTable;
    calcStats(zTable.getMtrx(), showProgress);
  }
  public void calcStats(double[][] z, boolean showProgress)
  {
    initCalc(z, showProgress);
    int countUpdate = 0;

    // STEP 1.
    McvsItem currJ = makeRandom(k);
    itemArr.add(currJ);
    loadItemArr(k);

    for (int i = 0; i < N; i++) {
      if (progress != null
        && progress.isCanceled(i, 0, N)) {
        cleanup();
        return;
      }

      // STEP 2.
      McvsItem newJ = swapRandom(currJ);
//      McvsItem newJ = floatK(currJ);
      int[] currI = IntVec.makeRandIdxArr(n);
      newJ = itemArr.add(newJ); // NOTE: newJ may have been already created
      calcBestArr(currI);
      markBestForP();
      itemArr.removeExcess();

      // STEP 3.
      if (newJ.getAvrLoss() <= currJ.getAvrLoss()  || acceptNew(newJ.getAvrLoss(), currJ.getAvrLoss(), i)) {
        currJ = newJ;
        countUpdate++;
        if (countUpdate >= vs.getNumUpdatesPerView()) {
          countUpdate = 0; // reset count
          updateView();
        }
      }
    }
    calcF();
    updateView();

    if (progress != null)
      progress.close();
    progress = null;
  }

  protected void initCalc(double[][] z, boolean showProgress) {
    log.trace("calcStats(z = \n", new Mtrx(z));
    zT = Mtrx.trans(z);    log.trace("zT = \n", new Mtrx(zT));

    if (showProgress)
      progress = new ProgressWnd(QBenchMainUI.getInstance(), "calculating MCVS ...");

    n = z.length;               log.debug("n=", n);
    p = z[0].length;            log.debug("p=", p);
    Mccv cv = project.getMccv();
    N = cv.getNumIter();        log.debug("N=", N);
    nV = cv.getValidSize();     log.debug("nV=", nV);

    vs = project.getMcvs();
    k = vs.getNumVars();        log.debug("k=", k);
    kFixed = vs.getNumFixedXCols();        log.debug("kFixed=", kFixed);
    if (k <= kFixed) {
      JOptionPane.showMessageDialog(QBenchMainUI.getInstance()
        , "All descriptors are fixed! \nReduce 'fixed' or increase 'variables'");
      return;
    }
  }

  protected void loadItemArr(int k)
  {
    int startIdx = kFixed + 1 + itemArr.getCapacity();
    if (startIdx >= p)
      return;
    for (int i = 0; i < itemArr.getCapacity(); i++) {
      BitSet bitSet = BitSetFactory.makeRandom(startIdx, k - kFixed - 1, p);
      bitSet.set(0, kFixed + 1);
      bitSet.set(kFixed + 1 + i);
      McvsItem res = new McvsItem(bitSet);           log.debug("makeRandom=", res);
      itemArr.add(new McvsItem(bitSet));
    }
  }

  protected void markBestForP()
  {
    McvsItem bestLoss = itemArr.findMinLastLoss();
    bestLoss.addBestCount();    log.debug("bestMse", bestLoss);
  }

  protected void calcBestArr(int[] currI)
  {
    for (McvsItem item : itemArr.values()) {   // for each loop
      loadErr(currI, item);
    }
  }

  protected McvsItem swapRandom(McvsItem oldSet) { log.debug("oldSet=", oldSet);
    BitSet newSet = BitSetFactory.swapRandom(kFixed + 1, oldSet, p);  log.debug("newSet=", newSet);
    return new McvsItem(newSet);
  }
  private McvsItem addRandom(McvsItem oldSet) { log.debug("oldSet=", oldSet);
    BitSet newSet = BitSetFactory.addRandom(kFixed + 1, oldSet, p);  log.debug("newSet=", newSet);
    return new McvsItem(newSet);
  }
  private McvsItem delRandom(McvsItem oldSet) { log.debug("oldSet=", oldSet);
    BitSet newSet = BitSetFactory.delRandom(kFixed + 1, oldSet);  log.debug("newSet=", newSet);
    return new McvsItem(newSet);
  }
  private McvsItem floatK(McvsItem oldSet) { log.debug("oldSet=", oldSet);
    RandomSeed rand = RandomSeed.getInstance();
    int type = rand.nextInt(3);
    BitSet newSet = swapRandom(oldSet);
    final int MINUS_K = 0;
//    final int CONST_K = 1;
    final int PLUS_K = 2;
    int kCurr = oldSet.cardinality();
    if (kCurr <= kFixed + 2  &&  type == MINUS_K)  // NOTE +1 for e.g. logBB,
      type++;

    if (kCurr >= K_MAX  &&  type == PLUS_K)
      type--;

    if (type == MINUS_K) {
      newSet = delRandom(oldSet);
    }
    else if (type == PLUS_K) {
      newSet = addRandom(oldSet);
    }
    return new McvsItem(newSet);
  }

  protected McvsItem makeRandom(int k) {
    BitSet bitSet = BitSetFactory.makeRandom(kFixed + 1, k - kFixed, p);
    bitSet.set(0, kFixed + 1, true);
    McvsItem res = new McvsItem(bitSet);           log.debug("makeRandom=", res);
    return res;
  }

  protected void updateView() {
    System.gc();
    McvsItem[] sorted = itemArr.sortByAmse();
    int nViews = Math.min(itemArr.size(), project.getMcvs().getNumBest());
    MVCTableView[] views = new MVCTableView[nViews];
    for (int i = 0; i < views.length; i++) {
      views[i] = new McvsItemView(sorted[i], zTable, project, i == 0);
    }
    if (updateView != null) {
      updateView.setTableView(MVCTableView.makeTableView(views));
      updateView.assembleWithOpt();
    }
  }

  protected void calcF() {
    for (McvsItem item : itemArr.values()) {   // for each loop
      loadF(item, zT);
    }
  }

  private void loadF(McvsItem item, double[][] zT)
  {
    double[][] setZT = Mtrx.getRows(item, zT);           log.trace("setZT=\n", new Mtrx(setZT));
    double[][] setZ = Mtrx.trans(setZT);  setZT = null;  log.trace("setZ = \n", new Mtrx(setZ));
    QsarAlg alg = algFactory.makeAlg(project, setZ);
    Mtrx mTrain = new Mtrx(setZ);
    double[][] xTrain = mTrain.getCols(1, mTrain.getNumCols()); log.trace("xTrain = \n", new Mtrx(xTrain));
    double[] yp = alg.calcYFromXZ(xTrain);
    if (yp == null)
      return;
    MlrRes mlrRes = alg.getMlrRes();
    mlrRes.calcMLR(mTrain.getColCopy(0), yp, mTrain.getNumCols() - 1);
    item.setLastF(mlrRes.getF());
  }

  private boolean acceptNew(double newL, double currL, int i)
  {
    double diffL = (newL - currL) / newL;       log.debug("diffL=", diffL);
    double T = (double)(N - i) / N;             log.debug("T=", T);
    double pr = Math.exp(- diffL / (K_B * T));  log.debug("Pr=", pr);
    return rand.makeRandomEvent(pr);
  }

  protected void cleanup() {
    if (progress != null)
      progress.close();
//    progress = null;
  }

  private void loadErr(int[] currI, McvsItem item) {
    double[][] setZT = Mtrx.getRows(item, zT);           log.trace("setZT=\n", new Mtrx(setZT));
    double[][] setZ = Mtrx.trans(setZT);  setZT = null;  log.trace("setZ = \n", new Mtrx(setZ));
    MtrxPair split = MtrxFactory.selectRows(nV, currI, setZ);

    Mtrx V = split.getA(); // validate/test
    Mtrx C = split.getB(); // calibrate/train
    double[] yV = V.getColCopy(0);                    log.trace("yV = ", new Vec(yV));
    double[][] xTest = V.getCols(1, V.getNumCols());  log.trace("xTest = \n", new Mtrx(xTest));

    QsarAlg alg = algFactory.makeAlg(project, C.getByRows());
    double[] yp = alg.calcYFromXZ(xTest);             log.trace("yp = ", new Vec(yp));
    if (yp == null)
      return;

    double loss;
    if (vs.getMaeNorm()) {
      loss = Vec.distAbs(yV, yp) / V.getNumRows();  log.debug("mae = ", loss);
    }
    else {
      loss = Vec.distL2(yV, yp) / V.getNumRows();  log.debug("mse = ", loss);
    }
    item.addLoss(loss);                                    log.debug("item = ", item);
  }

}