package javax.utilx.arrays.mtrx;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 29/03/2007, 17:28:11
 */
public class MtrxT<T>  // TODO????
{
  private T[][] mtrx;

//  public Mtrx() {
//    mtrx = new T[0][0];
//  }
//  public Mtrx(int nRows, int nCols) {
//    mtrx = new T[nRows][nCols];
//  }
//  public int getNumRows() {
//    return mtrx.length;
//  }
//  public int getNumCols() {
//    return mtrx[0].length;
//  }
//  public T[][] getByRows() {return mtrx;}
//  public void set(int r, int c, T v) {mtrx[r][c] = v;}
//  public T get(int r, int c) {return mtrx[r][c];}
//  public T[] getRow(int r) { return mtrx[r];}
//  public T[] getColCopy(int c) {
//    T[] res = new T[getNumRows()];
//    for (int r = 0; r < res.length; r++) {
//      res[r] = mtrx[r][c];
//    }
//    return res;
//  }
////  public T[][] getCols(int idxFirst, int idxExcl) {
////    return getCols(idxFirst, idxExcl, mtrx);
////  }
//
//  public T[][] getCols(int idxFirst, int idxExcl) {
//    int nCol = idxExcl - idxFirst;
//    T[][] res = new T[mtrx.length][nCol];
//    for (int r = 0; r < res.length; r++) {
//      for (int c = idxFirst; c < idxExcl; c++)
//        res[r][c - idxFirst] = mtrx[r][c];
//    }
//    return res;
//  }
//  public static T[][] getCols(T[][] arr, int[] idxArr)
//  {
//    int nR = arr.length;
//    int nC = arr[0].length;
//    int n = idxArr.length;
//    T[][] res = new T[nR][n];
//    for (int i = 0; i < n; i++) {
//      for (int r = 0; r < nR; r++) {
//        res[r][i] = arr[r][idxArr[i]];
//      }
//    }
//    return res;
//  }
}
