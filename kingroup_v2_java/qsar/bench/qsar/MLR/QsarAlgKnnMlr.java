package qsar.bench.qsar.MLR;
import qsar.bench.QBenchMainUI;
import qsar.bench.qsar.QsarAlg;
import qsar.kriging.LooAlgI;
import tsvlib.project.ProjectLogger;

import javax.swingx.ProgressWnd;
import javax.utilx.arrays.mtrx.Mtrx;
import javax.utilx.arrays.vec.Vec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/03/2007, 13:22:03
 */
public class QsarAlgKnnMlr  extends QsarAlg
  implements LooAlgI
{
  private static ProjectLogger log = ProjectLogger.getLogger(QsarAlgKnnMlr.class);
  public static final String REFERENCE = "k-Nearest Neighbors MLR";
  private int kNN;
//  private boolean norm;

//  public QsarAlgKnnMlr(QBench model, double[][] zTrain)
//  {
//    super(zTrain);
//    setName("kNN-MLR");
//    kNN = model.getKnn();
////    norm = model.getMeanZeroVarOne();
//  }

  public QsarAlgKnnMlr(int kNN, double[][] zTrain)
  {
    super(zTrain);
    setName("kNN-MLR");
    this.kNN = kNN;
//    norm = false;
  }

  public double[] calcYFromXZ(double[][] XZ) {
    int n = XZ.length;
    double[] res = new double[n];
    progress = new ProgressWnd(QBenchMainUI.getInstance(), "calculating kNN ...");
    for (int i = 0; i < n; i++) {
      if (progress != null
        && progress.isCanceled(i, 0, n)) {
        cleanup();
        return res;
      }

      double[] xz = XZ[i];
      res[i] = calcY(xz, z);
    }

    if (progress != null)
      progress.close();
    progress = null;
    return res;
  }

  private class tmpSort {
    public double d;
    public double[] z;
    public tmpSort(double d, double[] z)
    {
      this.d = d;
      this.z = z;
    }
  }

  /**
   *
   * @param xz - x or z vector
   * @param zTrain
   * @return
   */
  public double calcY(double[] xz, double[][] zTrain){
    int nR = zTrain.length;
    int nCZ = zTrain[0].length - 1; // num of columns
    int nCX = xz.length - 1;
    int startIdx = 0; // zero for Z, 1 for X;
    if (nCX == nCZ)
      startIdx = 1;
    ArrayList<tmpSort> set = new ArrayList<tmpSort>();
    for (int r = 0; r < nR; r++) {
      double d = Vec.dist2(1, zTrain[r], startIdx, xz, nCZ);
      set.add(new tmpSort(d, zTrain[r]));
    }
    Object[] arr = set.toArray();
    return calc(arr, xz, zTrain);
  }

  public double calc(int idx, double[][] Z){
    int nR = Z.length;
    int nC = Z[0].length;
    ArrayList<tmpSort> set = new ArrayList<tmpSort>();
    for (int r = 0; r < nR; r++) {
      if (r == idx) // EXCLUDE itself!
        continue;
      double d = Vec.distL2(1, nC, Z[r], Z[idx]); // NOTE: dist excludes the first column
      set.add(new tmpSort(d, Z[r]));
    }
    Object[] arr = set.toArray();
    return calc(arr, Z[idx], Z);
  }

  private double calc(Object[] arr, double[] X, double[][] Z) {
    Arrays.sort(arr, new Comparator() {
      public int compare(Object s, Object s2)    {
        return Double.compare(((tmpSort)s).d, ((tmpSort)s2).d);
      }});

    // BY SAMPLE SIZE
    double[][] knnZ = new double[kNN][0]; // SAMPLE z's
    for (int i = 0; i < kNN; i++)
      knnZ[i] = ((tmpSort)arr[i]).z;
    log.trace("nearest neighbors knnZ = ", new Mtrx(knnZ));

    // BY DISTANCE
//    ArrayList<double[]> dlist = new ArrayList<double[]>();
//    for (int i = 0; i < arr.length; i++) {
//      tmpSort t = (tmpSort)arr[i];
//      if (t.d > sampleDist)
//        dlist.add( ((tmpSort)arr[i]).z);
//    }
//    double[][] sz = Vec.asArray2D(dlist);

    MlrAlg reg = new MlrAlg();
    if (!reg.calc(knnZ)) {
      log.debug("knnZ = ", new Mtrx(knnZ));
      return Double.NaN;
    }
    double ey = reg.calcYFromXZ(X); // estimate
    return ey;
  }
}
