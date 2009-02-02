package kingroup_v2.relatedness;
import kingroup_v2.kinship.CalculatorI;
import kingroup_v2.kinship.KinshipREstimator;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopMtrxI;
import tsvlib.project.ProjectLogger;

import javax.swingx.ProgressWnd;
import javax.vecmathx.matrix.LowTriangleMatrix;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/01/2006, Time: 12:22:10
 */
public class PairwiseRMtrx implements SysPopMtrxI {
  private ProgressWnd progress = null;
  protected static ProjectLogger log = ProjectLogger.getLogger(PairwiseRMtrx.class.getName());
  private final LowTriangleMatrix mtrx;
  protected final SysPop pop;
  protected CalculatorI estimator;
  private String name;

  public static String makeMessageAboutBias() {
    return "KINSHIP bias-corrections are not available for this estimator\n.";
  }
  public PairwiseRMtrx(SysPop pop) {
    mtrx = new LowTriangleMatrix(pop.size());
    estimator = new KinshipREstimator(pop);
    this.pop = pop;
  }

  public void setEstimator(CalculatorI calc) {
    this.estimator = calc;
  }

  public double calcAvr() {
    return mtrx.calcAvr();
  }
  public SysPop getPop()
  {
    return pop;
  }

  public int getId(int idx) {
    return pop.getIdIdx(idx);
  }
  public void calc() {
    // log.info("pop=\n"+pop);
    for (int i = 0; i < pop.size(); i++) {
      if (progress != null
        && progress.isCanceled(i, 0, pop.size())) {
        return;
      }
      for (int k = i + 1; k < pop.size(); k++) {
        double sum = estimator.calc(i, k);
        set(i, k, sum); // i < k // only the above diagonal elements are calculated
      }
    }
  }
  public int size() {
    return mtrx.size();
  }
  public int getStorageSize() {
    return mtrx.getStorageSize();
  }
// PRECOND: c < size  &&  r < size
  public double get(int r, int c) {
    if (r == c)
      return 1.;
//    return Like.MIN_LOG; // NOTE!!! This is used in KinshipLikeMtrxV1.sort()???
    return mtrx.get(r, c);
  }
// PRECOND: c < size  &&  r < size
  final public void set(int r, int c, double val) {
    mtrx.set(r, c, val);
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public ProgressWnd getProgress()
  {
    return progress;
  }

  public void setProgress(ProgressWnd progress)
  {
    this.progress = progress;
  }
  public String toString() {
    String tab = ",\t";
    StringBuffer buff = new StringBuffer();
    for (int r = 0; r < size(); r++) {
      for (int c = 0; c < size(); c++) {
        buff.append((float)get(r, c));
        if (c == size() - 1)
          buff.append("\n");
        else
          buff.append(tab);
      }
    }
    return buff.toString();
  }

  public double[][] toArray(double MIN, double MAX) {
    int n = pop.size();
    double[][] arr = new double[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j <= i; j++) {
        double r = get(i, j);
        if (MIN < MAX  && r < MIN)
          r = MIN;
        if (MIN < MAX  && r > MAX)
          r = MAX;
        arr[i][j] = r;
        arr[j][i] = r;
      }
    }
    return arr;
  }

}

