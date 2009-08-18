package javax.vecmathx.matrix;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.VecMathException;
import java.util.Arrays;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Jul 20, 2004, Time: 4:17:58 PM
 */
public class LowTriangleMatrix implements MtrxReadI {
  private static ProjectLogger log = ProjectLogger.getLogger(LowTriangleMatrix.class.getName());
  private int size;
  private double[] v = null;
  public LowTriangleMatrix(int size) {
//    log.setLevel(Level.OFF);
    this.size = size;
    prepareStorageFor(size);
  }
  final public int size() {
    return size;
  }
  final public int getStorageSize() {
    return v.length;
  }
  public void init(double val) {
    Arrays.fill(v, val);
  }
  private void assertUsage(int c, int r) {
    if (c == r) {
      String error = "Low Triangle Matrix cannot have r=c";
      LOG.error(this, error, "");
      throw new VecMathException(error);
    }
  }
  // PRECOND: c < size  &&  r < size  &&  c < r
  final public void set(int r, int c, double d) {
    assertUsage(r, c);
    if (r > c)
      v[lowerTriangleToIdx(r, c)] = d;
    else
      v[lowerTriangleToIdx(c, r)] = d;
  }

//  // PRECOND: c < size  &&  r < size  &&  c < r
//  final private double getLowerTriangle(int row, int col) {
//    assertUsage(row, col);
//    return v[lowerTriangleToIdx(row, col)];
//  }
  final public double get(int r, int c) {
    if (c == r || c >= size || r >= size)
      return 0;
    if (r > c)
      return v[lowerTriangleToIdx(r, c)];
    else
      return v[lowerTriangleToIdx(c, r)];
  }
  final private void prepareStorageFor(int _size) {
    // [ 1 2 3 ...
    // [ 2
    // [ 3
    // [ ...
    // total number: size * size
    // subtract diagonal: - size
    // divide by 2 to getLine upper diagonal elements
    v = new double[(_size * _size - _size) / 2];
  }

  // PRECOND: r > c
  final private int lowerTriangleToIdx(int r, int c)
  {
    return lowerTriangleToIdx(r, c, size);
  }

  // PRECOND: r > c
  public static int lowerTriangleToIdx(int r, int c, int size) {
    //int idx = c * size_ + r; // if it was a full array
    //int col_num = c + 1;
    //idx -= ((col_num * col_num - col_num) /2 + col_num) ;
    //idx -=  col_num * (col_num + 1) / 2 ;
    int idx = c * size + r - (c + 1) * (c + 2) / 2;
//    // log.info("r="+r+", c="+c+" -> idx="+idx);
    return idx;
  }

  public double calcAvr() {
    return Vec.mean(v);
  }
}
