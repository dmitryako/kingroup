package javax.utilx.arrays.vec;
import tsvlib.project.ProjectLogger;

import javax.mathx.MathX;
import javax.utilx.IdxAbsComparator;
import javax.utilx.IdxComparator;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.IntVec;
import javax.utilx.pair.DoubleRange;
import java.util.*;

public class Vec
{
  private static ProjectLogger log = ProjectLogger.getLogger(Vec.class);

  private static final RandomSeed rand = RandomSeed.getInstance();
  public static int NOT_FOUND = -1;


  private double[] vec;

  public Vec(double[] v) {
    vec = v;
  }

  public String toString() {
    return toString(vec);
  }



  /**
   * L - left hand filled
   * PRE: arr and bins are sorted!!
   * @param arr
   * @param bins
   * @return histogram
   */
  public static double[] histL(double[] arr, double[] bins) {
    double[] res = new double[bins.length];
    int currBinIdx = 0;
    double currBinR = bins[currBinIdx]; // right side of the bin
    double prev = arr[0];
    for (double d : arr)
    {
      if (prev > d) {
        throw new RuntimeException("arr must be sorted before calling histL");
      }
      prev = d;

      if (d <= currBinR)
        res[currBinIdx]++;
      else if (currBinIdx < bins.length-1)
      {
        currBinIdx++;
        if (currBinR >= bins[currBinIdx]) {
          throw new RuntimeException("bins must be sorted before calling histL");
        }
        currBinR = bins[currBinIdx];
      }
    }
    return res;
  }


  public static double calcMedianFromSorted(double[] e)
  {
    int n = e.length;
    if (n % 2 == 0) {  //even
      int n2 = (n - 1) / 2;
      return 0.5 * (e[n2] + e[n2 + 1]);
    }
    else {  // odd
      return e[(n-1)/2];
    }
  }

  public static double medianSLOW(double[] e)
  {
    double[] tmp = Vec.copy(e);
    Arrays.sort(tmp);
    return calcMedianFromSorted(tmp);
  }


  public static void norm(double[] arr, double v) {
    double sum = sum(arr);
    mult(v / sum, arr);
  }

  // PRE: v != null
  public static double sum(double[] v) {
    return sum(v, v.length);
  }
  public static double sum(double[] v, int n) {
    double res = 0;
    n = Math.min(v.length, n);
    for (int i = 0; i < n; i++) {
      res += v[i];
    }
    return res;
  }
  public static double[] sum(double xx, double[] x, double yy, double[] y) {
    double[] res = new double[Math.max(x.length, y.length)];  // to trigger error if any
    for (int i = 0; i < res.length; i++) {
      res[i] = xx * x[i] + yy * y[i];
    }
    return res;
  }
  // PRE: v != null  &&  v.length > 0
  public static double avr(double[] v) {
    return sum(v) / v.length;
  }
  // PRE: v != null
  public static void mult(double d, double[] v) {
    for (int i = 0; i < v.length; i++) {
      v[i] *= d;
    }
  }
  // PRE: v != null
  public static void square(double[] v) {
    for (int i = 0; i < v.length; i++) {
      v[i] = v[i] * v[i];
    }
  }
  // PRE: v != null
  public static void add(double d, double[] v) {
    for (int i = 0; i < v.length; i++) {
      v[i] += d;
    }
  }
  public static void add(double to[], int[] from) {
    for (int i = 0; i < from.length; i++) {
      to[i] += from[i];
    }
  }
  public static void add(double to[], double[] from) {
    for (int i = 0; i < from.length; i++) {
      to[i] += from[i];
    }
  }
  public static void add(double to[], double mult, double[] from) {
    for (int i = 0; i < from.length; i++) {
      to[i] += mult * from[i];
    }
  }
  public static double[] add(double a, double A[], double b, double[] B) {
    int n = A.length;
    double[] res = new double[n];
    for (int i = 0; i < n; i++) {
      res[i] = a * A[i] + b * B[i];
    }
    return res;
  }
  // PRE: v != null
  public static void set(double d, double[] v) {
    for (int i = 0; i < v.length; i++) {
      v[i] = d;
    }
  }

  public static int maxIdx(double [] dx, int size) {
    if (dx == null)
      return NOT_FOUND;
    int n = Math.min(dx.length, size);
    if (n == 0)
      return NOT_FOUND;
    int idx = 0;
    double v = dx[idx];
    for (int i = 1; i < n; i++) {
      if (v >= dx[i])
        continue;
      idx = i;
      v = dx[i];
    }
    return idx;
  }
  public static int minIdx(double [] dx, int size) {
    if (dx == null)
      return NOT_FOUND;
    int n = Math.min(dx.length, size);
    if (n == 0)
      return NOT_FOUND;
    int idx = 0;
    double v = dx[idx];
    for (int i = 1; i < n; i++) {
      if (v <= dx[i])
        continue;
      idx = i;
      v = dx[i];
    }
    return idx;
  }
  static public ArrayList asList(double[] v) {
    ArrayList res = new ArrayList(v.length);
    for (int i = 0; i < v.length; i++) {
      res.add(new Double(v[i]));
    }
    return res;
  }
  // startInc, endInc are included.
  public static ArrayList makeList(int startInc, int endInc) {
    final int SIZE = endInc - startInc + 1;
    ArrayList res = new ArrayList(SIZE);
    for (int i = startInc; i <= endInc; i++) {
      res.add(new Double(i));
    }
    return res;
  }
  // startInc, endInc are included.
  public static double[] makeArray(int startInc, int endInc) {
    final int SIZE = endInc - startInc + 1;
    double[] res = new double[SIZE];
    int idx = 0;
    for (int i = startInc; i <= endInc; i++) {
      res[idx++] = i;
    }
    return res;
  }
  public static double[] makeArray(double initVal, int size) {
    double[] res = new double[size];
    for (int i = 0; i < size; i++) {
      res[i] = initVal;
    }
    return res;
  }
  public static double[] makeByStep(double initVal, double step, int size) {
    double[] res = new double[size];
    res[0] = initVal;
    for (int i = 1; i < size; i++) {
      res[i] = res[i-1] + step;
    }
    return res;
  }
  public static double[] makeBySize(double first, double last, int size) {
    double step = (last - first) / (size - 1);
    return makeByStep(first, step, size);
  }
  public static double[] makeGaussian(final int size, final double shift) {
    final double[] d = new double[size];
    for (int i = 0; i < d.length; i++) {
      d[i] = rand.nextGaussian() + shift;
    }
    return d;
  }

  public static double[][] asArray2D(ArrayList<double[]> arr) {
    double[][] res = new double[arr.size()][0];
    for (int i = 0; i < res.length; i++) {
      res[i] = arr.get(i);
    }
    return res;
  }
  public static double[] asArray(ArrayList arr) {
    double[] res = new double[arr.size()];
    for (int i = 0; i < res.length; i++) {
      res[i] = ((Double) arr.get(i)).doubleValue();
    }
    return res;
  }
  public static double[] asArray(ArrayList arr, int size) {
    int resSize = size;
    if (size == -1)
      resSize = arr.size();
    double[] res = new double[resSize];
    for (int i = 0; i < resSize; i++) {
      res[i] = ((Double) arr.get(i)).doubleValue();
    }
    return res;
  }
  public static double[] asArray(List list) {
    double[] res = new double[list.size()];
    Object[] objs = list.toArray();
    for (int i = 0; i < objs.length; i++) {
      Object obj = objs[i];
      res[i] = ((Double) obj).doubleValue();
    }
    return res;
  }
  public static double[] convert(int[] arr) {
    double[] res = new double[arr.length];
    for (int i = 0; i < res.length; i++) {
      res[i] = arr[i];
    }
    return res;
  }
  public static String toString(double[] a, int size) {
    int L = Math.min(a.length, size);
    StringBuffer buff = new StringBuffer();
    buff.append("{");
    buff.append(toCSV(a, size));
    buff.append("}");
    return buff.toString();
  }
  public static String toCSV(double[] a, int size) {
    int L = Math.min(a.length, size);
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < L; i++) {
      double v = a[i];
      buff.append(Float.toString((float) v));
      if (i != L - 1)
        buff.append(", ");
    }
    return buff.toString();
  }
  public static String toString(double[] a) {
    if (ProjectLogger.MAX_N_COLS_TO_STRING >= a.length)
      return toString(a, a.length);
    return toString(a, ProjectLogger.MAX_N_COLS_TO_STRING)
      + " ... " + a[a.length-1];
  }
  public static String toCSV(double[] a) {
    return toCSV(a, a.length);
  }
  public static int[] sortIdx(double[] v) {
    return sortIdx(v, true);
  }
  public static int[] sortIdx(double[] v, boolean ascending) {
    List list = IntVec.makeIdxList(v.length);
    Object[] res = list.toArray();
    Comparator comp = new IdxComparator(v, ascending);
    Arrays.sort(res, comp);
    return IntVec.toArray(res);
  }
  public static int[] sortIdxByAbs(double[] v, boolean ascending) {
    List list = IntVec.makeIdxList(v.length);
    Object[] res = list.toArray();
    Comparator comp = new IdxAbsComparator(v, ascending);
    Arrays.sort(res, comp);
    return IntVec.toArray(res);
  }
  public static double dot(double[] v, double[] v2) {
    double res = 0;
    for (int i = 0; i < v.length; i++) {
      res += v[i] * v2[i];
    }
    return res;
  }
  public static double dot(double[] v, double[] v2, double[] v3) {
    double res = 0;
    for (int i = 0; i < v.length; i++) {
      res += v[i] * v2[i] * v3[i];
    }
    return res;
  }
  public static double dot(double[] v, double[] v2, double[] v3, double[] v4) {
    double res = 0;
    for (int i = 0; i < v.length; i++) {
      res += v[i] * v2[i] * v3[i] * v4[i];
    }
    return res;
  }

  public static double[] makeCumulative(double[] arr)
  {
    int n = arr.length;
    double[] res = new double[n];
    res[0] = arr[0];
    for (int i = 1; i < res.length; i++) {
      res[i] = res[i-1] + arr[i];
    }
    return res;
  }

  /**
   * Find index of a bin (on the LEFT of the right boundary of the bin)
   * , e.g. idx = 1, if arr={0.5, 3} and x=2.
   * Last bin covers +oo
   * PRECOND: arr must be sorted and length>0
   * @param a
   * @param v
   * @return index
   */
  public static int findBinL(double[] a, double v)
  {
    final int LAST = a.length - 1;
    if (LAST == 0 || a[LAST-1] < v)
      return LAST;
    if (v <= a[0])
      return 0;

    // p.198 of R.Sedgewick "Algorithms in C"
    // binary search
    int L = 0;
    int R = LAST - 1;
    int x;
    while (R >= L)
    {
      if (a[L] > a[R])
        throw new RuntimeException("arr must be sorted before calling findBinL");
      x = (L + R) / 2;
      if (v <= a[x])
        R = x;
      else
        L = x;

      if (a[R-1] < v  &&  v <= a[R])
        return R;
    }
    return -1;
  }

  public static int findFirstIdx(double[] r, float v)
  {
    for (int i = 0; i < r.length; i++) {
      if (Float.compare((float)(r[i]), v) == 0)
        return i;
    }
    return -1;
  }
  public static int findLastIdx(double[] r, float v)
  {
    for (int i = r.length-1; i >= 0; i--) {
      if (Float.compare((float)(r[i]), v) == 0)
        return i;
    }
    return -1;
  }

  public static double[] copy(double[] from)
  {
    double[] res = new double[from.length];
    System.arraycopy(from, 0, res, 0, from.length);
    return res;
  }

  public static boolean floatEquals(double[] A, double[] B)
  {
    for (int i = 0; i < A.length; i++) {
      if ((float)A[i] != (float)B[i])
        return false;
    }
    return true;
  }


  public static void limit(double[] arr, double MIN, double MAX) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i] < MIN)
        arr[i] = MIN;
      if (arr[i] > MAX)
        arr[i] = MAX;
    }
  }

  public static double distL2(double[] v, double[] v2) {
    double dist = 0;
    for (int i = 0; i < v.length; i++) {
      double d = v[i] - v2[i];
      dist += d * d;
    }
    return dist;
  }

  public static double distAbs(double[] v, double[] v2) {
    double dist = 0;
    for (int i = 0; i < v.length; i++) {
      double d = v[i] - v2[i];
      dist += Math.abs(d);
    }
    return dist;
  }

  public static double distL2(int fromIdx, int toIdxExcl, double[] v, double[] v2) {
    double dist = 0;
    for (int i = fromIdx; i < toIdxExcl; i++) {
      double d = v[i] - v2[i];
      dist += d * d;
    }
    return dist;
  }

  public static double dist2(int fromIdx, double[] v, int fromIdx2, double[] v2, int n) {
    double dist = 0;
    for (int i = 0; i < n; i++) {
      double d = v[fromIdx + i] - v2[fromIdx2 + i];
      dist += d * d;
    }
    return dist;
  }

  public static double distSqrtL2(double[] v, double[] v2) {
    return Math.sqrt(distL2(v, v2));
  }

  public static double dist(int fromIdx, int toIdxExcl, double[] v, double[] v2) {
    return Math.sqrt(distL2(fromIdx, toIdxExcl, v, v2));
  }

  public static double[] makeRandom(final int size) {
    final double[] d = new double[size];
    for (int i = 0; i < d.length; i++) {
      d[i] = rand.nextDouble();
    }
    return d;
  }


  public static void normToRange(double[] arr, double min, double max)
  {
    if (min == max) {
      throw new RuntimeException("min == max");
    }
    DoubleRange range = calcRange(arr);
    if (range.getMin() == range.getMax()) {
      throw new RuntimeException("range.getMin() == range.getMax()");
    }

    double oldRange = range.getRange();
    double newRange = max - min;
    double scale = newRange / oldRange;
    for (int i = 0; i < arr.length; i++) {
      double v = arr[i];
      arr[i] = min + scale * (v - range.getMin());
    }
  }
  public static boolean isConst(double[] arr) {
    DoubleRange range = Vec.calcRange(arr);
    return (range.getRange() == 0);
  }

  public static DoubleRange calcRange(double[] arr)
  {
    DoubleRange res = new DoubleRange(arr[0], arr[0]);
    for (int i = 1; i < arr.length; i++) {
      double v = arr[i];
      if (res.getMax() < v)
        res.setMax(v);
      if (res.getMin() > v)
        res.setMin(v);
    }
    return res;
  }

  public static double[] order(double[] arr, int[] idxOrder)
  {
    double[] res = new double[arr.length];
    for (int i = 0; i < res.length; i++) {
      res[i] = arr[idxOrder[i]];
    }
    return res;
  }


  public static double[] add(int toIdx, double v, double[] from)
  {
//    log.info("\nfrom=" + Vec.toString(from));

    double[] res = new double[from.length + 1];
    res[toIdx] = v;
//    log.info("\nres=" + Vec.toString(res));

    for (int to = 0; to < toIdx; to++)
      res[to] = from[to];
//    log.info("\nres=" + Vec.toString(res));

    for (int fromIdx = toIdx; fromIdx < from.length; fromIdx++)
      res[fromIdx + 1] = from[fromIdx];
//    log.info("\nres=" + Vec.toString(res));

    return res;
  }

  public static double[] append(double[] arr, double[] arr2)
  {
    log.debug("arr=", new Vec(arr));
    log.debug("arr2=", new Vec(arr2));
    int L = arr.length;
    int L2 = arr2.length;
    double[] res = new double[L + L2];
    int idx = 0;
    for (int i = 0; i < L; i++) {
      res[idx++] = arr[i];
    }
    for (int i = 0; i < L2; i++) {
      res[idx++] = arr2[i];
    }
    log.debug("res=", new Vec(res));
    return res;
  }

  public static void replace(float oldVal, float newVal, double[] arr)
  {
    for (int i = 0; i < arr.length; i++) {
      if (Float.compare((float)(arr[i]), oldVal) == 0)
        arr[i] = newVal;
    }
  }

  public static double[] randomize(double[] v)
  {
    int[] order = IntVec.makeRandIdxArr(v.length);
    double[] res = new double[v.length];
    for (int i = 0; i < res.length; i++) {
      res[i] = v[order[i]];
    }
    return res;
  }

  public static void invert(double[] v)
  {
    for (int i = 0; i < v.length; i++) {
      v[i] = 1. / v[i];
    }
  }

  public static double[] get(BitSet set, double[] a)
  {
    double[] res = new double[set.cardinality()];
    int idx = 0;
    for (int i = 0; i < a.length; i++) {
      if (set.get(i))
        res[idx++] = a[i];
    }
    return res;
  }

  public static double[] get(double[] a, int[] order, int n)
  {
    n = MathX.min(a.length, order.length, n);
    double[] res = new double[n];
    int idx = 0;
    for (int i = 0; i < n; i++) {
      res[idx++] = a[order[i]];
    }
    return res;
  }

  public static void abs(double[] arr)
  {
    for (int i = 0; i < arr.length; i++)
      arr[i] = Math.abs(arr[i]);
  }
}
