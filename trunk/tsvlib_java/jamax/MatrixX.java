package jamax;
import Jama.Matrix;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/03/2005, Time: 15:33:15
 */
public class MatrixX {
  public static double[] getColCopy(Matrix m, int c) {
    double[] res = new double[m.getRowDimension()];
    for (int r = 0; r < res.length; r++) {
      res[r] = m.get(r, c);
    }
    return res;
  }
  public static double[] getRowCopy(Matrix m, int r) {
    double[] res = new double[m.getColumnDimension()];
    for (int c = 0; c < res.length; c++) {
      res[c] = m.get(r, c);
    }
    return res;
  }
}
