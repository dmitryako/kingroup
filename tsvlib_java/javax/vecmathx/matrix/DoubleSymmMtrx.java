package javax.vecmathx.matrix;

import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
/**
 * Created by: jc1386591
 * Date: 15/06/2006. Time: 13:08:06
 */
public class DoubleSymmMtrx extends SymmMtrx {
  private static ProjectLogger log = ProjectLogger.getLogger(DoubleSymmMtrx.class.getName());
  private double[] arr;
  public DoubleSymmMtrx(int size) {
    arr = new double[(size * size + size) / 2];
    this.size = size;
  }
  public double[][] getFullArray() {
    double[][] res = new double[size][size];
    for (int c = 0; c < size; c++) {
      for (int r = c; r < size; r++) {
        res[c][r] = get(c, r);
        res[r][c] = get(c, r);
      }
    }
    return res;
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    for (int r = 0; r < size; r++) {
      buff.append("[").append(r).append("] ");
      for (int c = 0; c <= r; c++) {
        buff.append((float) get(c, r));
        if (c == r)
          buff.append(SysProp.EOL);
        else
          buff.append(", ");
      }
    }
    return buff.toString();
  }
  // PRECOND: c < size  &&  r < size
  final public void set(int c, int r, double d) {
    assertUsage(c, r);
    if (c <= r)
      arr[posToIdx(c, r)] = d;
    else
      arr[posToIdx(r, c)] = d;
  }
  final public double get(int c, int r) {
    assertUsage(c, r);
    if (c <= r)
      return arr[posToIdx(c, r)];
    else
      return arr[posToIdx(r, c)];
  }
}
