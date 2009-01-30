package javax.vecmathx.matrix;

import tsvlib.project.ProjectLogger;

/**
 * Created by: jc1386591
 * Date: 15/06/2006. Time: 13:22:08
 */
public class SymmMtrx {
  private static ProjectLogger log = ProjectLogger.getLogger(DoubleSymmMtrx.class.getName());
  protected int size;
  final public int size() {
    return size;
  }
  protected void assertUsage(int c, int r) {
    if (c >= size || r >= size) {
      String error = "Index out of bounds: r=" + r + ", c=" + c + ", size=" + size;
      log.severe(error);
      throw new RuntimeException(error);
    }
  }
  // PRECOND: c <= r
  public int posToIdx(int c, int r) {
    // BELOW formula for low triangle:
    //    int idx = c * size_ + r; // if it was a full array
    //    int col_num = c + 1;
    //    idx -= ((col_num * col_num - col_num) /2 + col_num) ;
    //    idx -=  col_num * (col_num + 1) / 2 ;
    // ADD diagonal
    // idx += (c + 1)
    return c * size + r - c * (c + 1) / 2;
  }
}
