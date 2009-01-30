package javax.vecmathx.matrix;

import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
/**
 * Created by: jc1386591
 * Date: 15/06/2006. Time: 13:24:09
 */
public class IntSymmMtrx extends SymmMtrx {
  private static final ProjectLogger log = ProjectLogger.getLogger(IntSymmMtrx.class.getName());
  private int[] arr;
  public IntSymmMtrx(int size) {
    arr = new int[(size * size + size) / 2];
    this.size = size;
  }
  public int[][] getFullArray() {
    int[][] res = new int[size][size];
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
        buff.append(get(c, r));
        if (c == r)
          buff.append(SysProp.EOL);
        else
          buff.append(", ");
      }
    }
    return buff.toString();
  }
  // PRECOND: c < size  &&  r < size
  final public void set(int c, int r, int d) {
    assertUsage(c, r);
    if (c <= r)
      arr[posToIdx(c, r)] = d;
    else
      arr[posToIdx(r, c)] = d;
  }
  final public int get(int c, int r) {
    assertUsage(c, r);
    if (c <= r)
      return arr[posToIdx(c, r)];
    else
      return arr[posToIdx(r, c)];
  }

  public void add(int a, int a2, int v) {
//    log.info("a,a2,v=" + a + ", "+ a2 +", "+  v);
    int curr = get(a, a2);
    set(a, a2, curr + v);
  }
}
