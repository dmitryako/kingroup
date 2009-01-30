package javax.vecmathx.matrix;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 21, 2004, Time: 3:17:18 PM
 */
public class SquareIntegerMatrixByCols extends SquareIntegerMatrix {
  public SquareIntegerMatrixByCols(int n, boolean init) {
    super(n, init);
  }
  final public void setElement(int row, int col, int i) {
    arr_[col][row] = i; // by columns
  }
  public final int[] getColumn(int i) {
    return arr_[i];
  }
  public final int[] getRow(int row) {
    int[] res = new int[size()];
    for (int col = 0; col < res.length; col++) {
      res[col] = getElement(row, col);
    }
    return res;
  }
  public void setColumn(int i, int[] ints) {
    arr_[i] = ints;
  }
  public void setRow(int row, int[] ints) {
    for (int col = 0; col < ints.length; col++) {
      setElement(row, col, ints[col]);
    }
  }
  public int getElement(int row, int col) {
    return arr_[col][row]; // by columns
  }
}