package javax.utilx.arrays.mtrx;
import junit.framework.TestCase;
import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
import javax.stats.Stats;
import javax.swingx.ProgressWnd;
import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.sortByRowsComparator;
import javax.utilx.arrays.vec.Vec;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 6/03/2007, 15:45:53
 */
public class Mtrx
{
  private static ProjectLogger log = ProjectLogger.getLogger(Mtrx.class);
  private double[][] mtrx;

  public Mtrx() {
    mtrx = new double[0][0];
  }
  public Mtrx(double[][] m) {
    mtrx = m;
  }
  public Mtrx(int nRows, int nCols) {
//    System.gc();
    mtrx = new double[nRows][nCols];
  }
  final public int getNumRows() {
    return mtrx.length;
  }
  final public int getNumCols() {
    return mtrx[0].length;
  }
  final public double[][] getByRows() {return mtrx;}

  final public double[][] getMtrx() {return mtrx;}

  final public void setMtrx(double[][] mtrx) {this.mtrx = mtrx;}

  final public void set(int r, int c, double v) {mtrx[r][c] = v;}

  final public double get(int r, int c) {return mtrx[r][c];}

  final public double[] getRow(int r) { return mtrx[r];}

  public String toString() {
    return toString(mtrx);
  }

  final public double[] getColCopy(int c) {
    double[] res = new double[getNumRows()];
    for (int r = 0; r < res.length; r++) {
      res[r] = mtrx[r][c];
    }
    return res;
  }
  final public double[][] getCols(int idxFirst, int idxExcl) {
    return getCols(idxFirst, idxExcl, mtrx);
  }


  public static String toString(double[][] a) {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < a.length; i++) {
      double[] arr = a[i];
      if (i < a.length - 1 &&  // show last 
        i >= ProjectLogger.MAX_N_ROWS_TO_STRING) {
        if (i == ProjectLogger.MAX_N_ROWS_TO_STRING)
          buff.append(" ... ").append(SysProp.EOL);
        continue;
      }
      buff.append("["+i+"][...]=").append(Vec.toString(arr)).append(SysProp.EOL);
    }
    return buff.toString();
  }
  public static String toCSV(double[][] a) {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < a.length; i++) {
      buff.append(Vec.toCSV(a[i]));
      if (i != a.length - 1)
        buff.append("\n");
    }
    return buff.toString();
  }

  public static double[][] getCols(int idxFirst, int idxExcl, double[][] arr) {
    int nCol = idxExcl - idxFirst;
    double[][] res = new double[arr.length][nCol];
    for (int r = 0; r < res.length; r++) {
      for (int c = idxFirst; c < idxExcl; c++)
        res[r][c - idxFirst] = arr[r][c];
    }
    return res;
  }
  public static double[][] getCols(double[][] arr, int[] idxArr)
  {
    int nR = arr.length;
    int nC = arr[0].length;
    int n = idxArr.length;
    double[][] res = new double[nR][n];
    for (int i = 0; i < n; i++) {
      for (int r = 0; r < nR; r++) {
        res[r][i] = arr[r][idxArr[i]];
      }
    }
    return res;
  }

/*
mirror flip rows, e.g. first row becomes last, etc
*/
  public static double[][] mirrorRows(double[][] arr)
  {
    int nRows = arr.length;
    double[][] res = new double[nRows][0];
    for (int i = 0; i < nRows; i++) {
      res[i] = arr[nRows - i - 1];
    }
    return res;
  }

  public static double[][] removeConstRows(double[][] arr)
  {
    BitSet cols = Mtrx.getNonConstCols(arr);
    return Mtrx.getCols(cols, arr);
  }

  public static double[][] getCols(double[][] z, int[] idxOrder, int nCols)
  {
    z = trans(z);
    z = getRows(z, idxOrder, nCols);
    z = trans(z);
    return z;
  }
  public double[][] getRows(int[] idxOrder, int nRows) {
    return getRows(this.getByRows(), idxOrder, nRows);
  }
  public static double[][] getRows(int idxFirst, int idxExc, double[][] arr)
  {
    int n = idxExc - idxFirst;
    double[][] res = new double[n][0];
    int count = 0;
    for (int i = idxFirst; i < idxExc; i++) {
      res[count++] = arr[i];
    }
    return res;
  }
  public static double[][] getRows(double[][] arr, int[] idxOrder, int nRows)
  {
    double[][] res = new double[nRows][0];
    for (int i = 0; i < nRows; i++) {
      res[i] = arr[idxOrder[i]];
    }
    return res;
  }

  public static double[] pDistByRows(double[][] arr)
  {
    int nRows = arr.length;
    int n = nRows * (nRows - 1) / 2;
    double[] res = new double[n];
    int idx = 0;
    for (int r = 0; r < nRows; r++) {
      for (int c = 0; c < r; c++) {
        double v = Vec.distSqrtL2(arr[r], arr[c]);
        res[idx++] = v;
      }
    }
    return res;
  }

  public static double[] pDistByRows(int fromColIdx, int toColIdxExcl, double[][] arr)
  {
    int nRows = arr.length;
    int n = nRows * (nRows - 1) / 2;
    double[] res = new double[n];
    int idx = 0;
    for (int r = 0; r < nRows; r++) {
      for (int c = 0; c < r; c++) {
        double v = Vec.dist(fromColIdx, toColIdxExcl, arr[r], arr[c]);
        res[idx++] = v;
      }
    }
    return res;
  }

  public static double[][] appendRow(double[] r, double[] r2)
  {
    double[][] res = new double[2][0];
    res[0] = r;
    res[1] = r2;
    return res;
  }

  public int[] sortRows(final int byColIdx, final boolean ascend) {
    return sortRows(byColIdx, this, ascend);
  }
  public static int[] sortRows(final int byColIdx, final Mtrx from, final boolean ascend) {
    log.debug("sortRows(byColIdx=", byColIdx);
    int nR = from.getNumRows();
    Object[] idxArr = IntVec.makeIdxList(nR).toArray();
    Arrays.sort(idxArr, new Comparator() {
      public int compare(Object o1, Object o2) {
        int row = ((Integer)o1).intValue();
        int r2 = ((Integer)o2).intValue();
        int res = Double.compare(from.get(row, byColIdx), from.get(r2, byColIdx));
        if (ascend)
          return res;
        else
          return -res;
      }
    });
    int[] res = IntVec.toArray(idxArr);   log.debug("res=", res);
    return res;
  }

  public static void sortByRows(final int colIdx, double[][] arr)
  {
    Arrays.sort(arr, new sortByRowsComparator(colIdx));
  }

  public static void setCol(int colIdx, double v, double[][] z)
  {
    for (int i = 0; i < z.length; i++)
      z[i][colIdx] = v;
  }
  public void setCol(int colIdx, double v[]) {
    setCol(colIdx, v, getByRows());
  }
  public static void setCol(int colIdx, double v[], double[][] z)
  {
    for (int i = 0; i < z.length; i++)
      z[i][colIdx] = v[i];
  }

  public static double[][] addCol(int colIdx, double[] y, double[][] x)
  {
    int nR = x.length;
//    int nC = x[0].length;
    double[][] res = new double[nR][0];
    for (int r = 0; r < nR; r++) {
      res[r] = Vec.add(colIdx, y[r], x[r]);
    }
    return res;
  }

  public static double[][] excludeRows(int idxFirst, int idxLast, double[][] fromArr)
  {
//    log.info("\nfrom=" + Vec.toString(fromArr));
    int nR = fromArr.length;
    int newNR = nR - (idxLast - idxFirst);
    double[][] res = new double[newNR][0];
    int idx = 0;
    for (int r = 0; r < nR; r++){
      if (r < idxFirst  ||  r >= idxLast)
        res[idx++] = fromArr[r];
    }
//    log.info("\nres=" + Vec.toString(res));
    return res;
  }

  public static double[][] trans(double[][] arr)
  {
    int nRows = arr.length;
    int nCols = arr[0].length;
    double[][] res = new double[nCols][nRows];
    for (int r = 0; r < nRows; r++) {
      for (int c = 0; c < nCols; c++) {
        res[c][r] = arr[r][c];
      }
    }
    return res;
  }

  public Mtrx trans()
  {
    return new Mtrx(trans(getByRows()));
  }

  public static double[][] appendRow(double[][] toArr, double[] lastRow)
  {
    double[][] res = new double[toArr.length + 1][0];
    for (int i = 0; i < toArr.length; i++)
      res[i] = toArr[i];
    res[res.length - 1] = lastRow;
    return res;
  }

  public static double[] getCol(int i, double[][] arr) {
    double[] res = new double[arr.length];
    for (int j = 0; j < res.length; j++)
      res[j] = arr[j][i];
    log.debug("res = ", new Vec(res));
    return res;
  }

  public static void checkOrthonorm(double[][] arr) {
    double EPS = 1e-10;
    for (int i = 0; i < arr.length; i++) {
      double[] v = getCol(i, arr);
      double norm = Vec.dot(v, v);
//log.info("\nnorm["+i+", "+i + "] = "+(float)norm);
      TestCase.assertEquals(1, norm, EPS);
      for (int j = 0; j < i; j++) {
        double[] v2 = getCol(j, arr);
        norm = Vec.dot(v, v2);
//log.info("\nnorm["+i+", "+j + "] = "+(float)norm);
        TestCase.assertEquals(0, norm, EPS);
      }
    }
  }

  public static double[] sumRows(double[][] arr) {
    double res[] = new double[arr.length];
    for (int i = 0; i < res.length; i++) {
      res[i] = Vec.sum(getCol(i, arr));
//log.info("\nt["+i+"] = "+(float)res[i]);
    }
    return res;
  }

  public static double[][] appendCols(double[][] m, double[][] m2)
  {
    log.debug("mtrx=\n", new Mtrx(m));
    log.debug("mtrx2=\n", new Mtrx(m2));

    int nR = m.length;
    int nR2 = m2.length;
    if (nR != nR2)
      return null;
    double[][] res = new double[nR][0];
    for (int r = 0; r < nR; r++) {
      res[r] = Vec.append(m[r], m2[r]);
    }
    log.debug("res=\n", new Mtrx(res));
    return res;
  }
  public static double[][] appendRows(double[][] m, double[][] m2)
  {
    log.debug("mtrx=\n", new Mtrx(m));
    log.debug("mtrx2=\n", new Mtrx(m2));

    if (m[0].length != m2[0].length)
      return null;
    int nR = m.length;
    int nR2 = m2.length;
    double[][] res = new double[nR + nR2][0];
    int count = 0;
    for (int r = 0; r < nR; r++) {
      res[count++] = m[r];
    }
    for (int r = 0; r < nR2; r++) {
      res[count++] = m2[r];
    }
    log.debug("res=\n", new Mtrx(res));
    return res;
  }

  public static double[][] getCols(BitSet set, double[][] z)
  {
    double[][] zt = Mtrx.trans(z);
    zt = getRows(set, zt);
    return Mtrx.trans(zt);
  }

  public static double[][] getRows(BitSet set, double[][] z)
  {
    double[][] res = new double[set.cardinality()][0];
    int idx = 0;
    for (int i = 0; i < z.length; i++) {
      if (set.get(i))
        res[idx++] = z[i];
    }
    return res;
  }

  public static BitSet getNonConstCols(double[][] z)
  {
    z = trans(z);
    return getNonConstRows(z);
  }
  public static BitSet findColsWith(float val, double[][] z)
  {
    z = trans(z);
    return findRowsWith(val, z);
  }
  public static BitSet getNonConstRows(double[][] z)
  {
    int nR = z.length;
    BitSet res = new BitSet();
    res.set(0, nR, true);      // assuming all non-const
    for (int r = 0; r < nR; r++) {
      if (Vec.isConst(z[r]))
        res.set(r, false);
    }
    return res;
  }
  public static BitSet findRowsWith(float val, double[][] z)
  {
    int nR = z.length;
    BitSet res = new BitSet();
    for (int r = 0; r < nR; r++) {
      if (Vec.findFirstIdx(z[r], val) != -1)
        res.set(r, true);
    }
    return res;
  }

  public static BitSet getDiffCols(double[][] z)
  {
    z = trans(z);
    return getDiffRows(z);
  }
  public static BitSet getDiffCorrCols(double[][] z, double maxCorr, int startIdx)
  {
    z = trans(z);
    return getDiffCorrRows(z, maxCorr, startIdx);
  }
  public static BitSet getDiffRows(double[][] z)
  {
    int nR = z.length;
    BitSet res = new BitSet();
    res.set(0, nR, true);      // assuming all different
    for (int r = 0; r < nR; r++) {
      if (!res.get(r))  // already excluded
        continue;
      for (int r2 = r + 1; r2 < nR; r2++) {
        if (Vec.floatEquals(z[r], z[r2]))
          res.set(r2, false);
      }
    }
    return res;
  }
  public static BitSet getDiffCorrRows(double[][] z, double maxCorr, int startIdx)
  {
    ProgressWnd progress = new ProgressWnd(null, "Calculating pairwise correlation");
    int nR = z.length;
    BitSet res = new BitSet();
    res.set(0, nR, true);      // assuming all different
    for (int r = startIdx; r < nR; r++) {  // NOTE!!! working with X only, r = 1
      if (progress != null
        && progress.isCanceled(r, 0, nR)) {
        return res;
      }
      if (!res.get(r))  // already excluded
        continue;
      for (int r2 = r + 1; r2 < nR; r2++) {
        double corr = Stats.corr(z[r], z[r2]);
//        if (corr > maxCorr)
        if (Math.abs(corr) > maxCorr)
          res.set(r2, false); // exclude
      }
    }
    progress.close();
    return res;
  }

  public int[] sortColsByCorr(boolean ascend) {
    return sortColsByCorr(getByRows(), ascend);
  }
  public int[] sortColsByAbsCorr(boolean ascend) {
    return sortColsByAbsCorr(getByRows(), ascend);
  }
  public static int[] sortColsByCorr(double[][] z, boolean ascend)
  {
    z = trans(z);
    return sortRowsByCorr(z, ascend);
  }
  public static double[] calcColCorr(int toColIdx, double[][] z)
  {
    z = trans(z);
    return calcRowCorr(toColIdx, z);
  }
  public double[] calcColCorr(int toColIdx)
  {
    return calcColCorr(toColIdx, this.getMtrx());
  }
  public static int[] sortColsByAbsCorr(double[][] z, boolean ascend)
  {
    z = trans(z);
    return sortRowsByAbsCorr(z, ascend);
  }
  public static int[] sortRowsByCorr(double[][] z, boolean ascend)
  {
    double[] pc = Stats.corr(z, 0);      log.debug("pc= \n", new Vec(pc));
    int[] res = Vec.sortIdx(pc, ascend);        log.debug("idxOrder= \n", new IntVec(res));
    return IntVec.move(0, 0, res);  // keep 0 fixed
  }
  public static double[] calcRowCorr(int toRowIdx, double[][] z)
  {
    double[] res = Stats.corr(z, toRowIdx);      log.debug("corrRowCorr= \n", new Vec(res));
    return res;
  }
  public static int[] sortRowsByAbsCorr(double[][] z, boolean ascend)
  {
    double[] pc = Stats.corr(z, 0);      log.debug("pc= \n", new Vec(pc));
    int[] res = Vec.sortIdxByAbs(pc, ascend);        log.info("idxOrder= \n", new IntVec(res));
    return IntVec.move(0, 0, res);  // keep 0 fixed
  }


  public static void normRowsToRange(double[][] arr, double min, double max)
  {
    for (int i = 0; i < arr.length; i++) {
      Vec.normToRange(arr[i], min, max);
    }
  }
  public static double[][] normColsToRange(double[][] arr, double min, double max)
  {
    double[][] res = trans(arr);
    normRowsToRange(res, min, max);
    return trans(res);
  }

  public void replace(float oldVal, float newVal)
  {
    Mtrx.replace(oldVal, newVal, getMtrx());
  }
  public static void replace(float oldVal, float newVal, double[][] m)
  {
    for (int r = 0; r < m.length; r++) {
      Vec.replace(oldVal, newVal, m[r]);
    }
  }

  public static void randomEachRow(double[][] m)
  {
    for (int r = 0; r < m.length; r++) {
      m[r] = Vec.randomize(m[r]);
    }
  }

  public static double[][] makeDiag(double[] v)
  {
    int n = v.length;
    double[][] res = new double[n][n];
    for (int i = 0; i < v.length; i++) {
      res[i][i] = v[i];
    }
    return res;
  }

//  public static double[][] times(double[] diag, double[][] mtrx)
//  {
//    int nR = mtrx.length;
//    int nC = mtrx[0].length;
//    double[][] res = new double[nR][nC];
//    for (int r = 0; r < nR; r++) {
//      for (int c = 0; c < nC; c++) {
//        res[r][c] = mtrx[r][c];
//      }
//    }
//    return res;
//  }

  public static void times(double[] diag, double[][] mtrx)
  {
    int nR = mtrx.length;
//    int nC = mtrx[0].length;
//    double[][] res = new double[nR][0];
    for (int r = 0; r < nR; r++)
      Vec.mult(diag[r], mtrx[r]);
  }
}
