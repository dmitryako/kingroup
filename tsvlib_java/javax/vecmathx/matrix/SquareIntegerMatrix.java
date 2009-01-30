package javax.vecmathx.matrix;
import javax.utilx.arrays.IntVec;
import java.util.Arrays;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 21, 2004, Time: 3:17:45 PM
 */
abstract public class SquareIntegerMatrix {
  protected int[][] arr_;
  public SquareIntegerMatrix(int n, boolean init) {
    arr_ = new int[n][n];
    if (init)
      init();
  }
  public int[][] getArray() {
    return arr_;
  }
  private void init() {
    for (int i = 0; i < arr_.length; i++) {
      Arrays.fill(arr_[i], 0);
    }
  }
  abstract public int[] getRow(int row);
  abstract public int[] getColumn(int col);
  abstract public int getElement(int row, int col);
  public String toString() {
    StringBuffer buff = new StringBuffer();
    for (int r = 0; r < size(); r++) {
      buff.append(IntVec.toString(getRow(r))).append('\n');
    }
    return buff.toString();
  }
  final public int size() {
    return arr_.length;
  }
}
