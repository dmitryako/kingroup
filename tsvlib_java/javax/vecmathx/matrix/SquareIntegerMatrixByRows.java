package javax.vecmathx.matrix;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 20, 2004, Time: 2:41:07 PM
 */
public class SquareIntegerMatrixByRows extends SquareIntegerMatrix {
  public SquareIntegerMatrixByRows(int n, boolean init) {
    super(n, init);
  }
  final public void setElement(int row, int col, int i) {
    arr_[row][col] = i; // by rows
  }
  public final int[] getRow(int i) {
    return arr_[i];
  }
  public final int[] getColumn(int row) {
    int[] res = new int[size()];
    for (int col = 0; col < res.length; col++) {
      res[col] = getElement(row, col);
    }
    return res;
  }
  public int getElement(int row, int col) {
    return arr_[row][col]; // by columns
  }
}
