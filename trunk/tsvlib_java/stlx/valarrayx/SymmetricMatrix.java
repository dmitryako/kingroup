package stlx.valarrayx;
import javax.langx.SysProp;
import javax.iox.LOG;
import javax.vecmathx.VecMathException;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 8, 2004, Time: 2:47:14 PM
 */
public class SymmetricMatrix extends valarray {
  private int size_;
  public SymmetricMatrix(int _size) {
    super((_size * _size + _size) / 2);
//      super(calcStorageSize(_size));
    size_ = _size;
  }
  final public int getNumRows() {
    return size_;
  }
  public double[][] getFullArray() {
    double[][] res = new double[size_][size_];
    for (int c = 0; c < size_; c++) {
      for (int r = c; r < size_; r++) {
        res[c][r] = get(c, r);
        res[r][c] = get(c, r);
      }
    }
    return res;
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    for (int r = 0; r < size_; r++) {
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
  private void assertUsage(int c, int r) {
    if (c >= size_ || r >= size_) {
      String error = "Index out of bounds: r=" + r + ", c=" + c + ", size =" + size_;
      LOG.error(this, error, "");
      throw new VecMathException(error);
    }
  }
  // PRECOND: c < size  &&  r < size  &&  c < r
  final public void set(int c, int r, double d) {
    assertUsage(c, r);
    if (c <= r)
      set(posToIdx(c, r), d);
    else
      set(posToIdx(r, c), d);
  }
  final public double get(int c, int r) {
    assertUsage(c, r);
    if (c <= r)
      return get(posToIdx(c, r));
    else
      return get(posToIdx(r, c));
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
    return c * size_ + r - c * (c + 1) / 2;
  }
}
