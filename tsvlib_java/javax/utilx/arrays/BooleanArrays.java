package javax.utilx.arrays;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/07/2005, Time: 15:58:55
 */
public class BooleanArrays {
  public static String toString(boolean[][] a, int size) {
    int L = Math.min(a.length, size);
    StringBuffer buff = new StringBuffer();
    for (int r = 0; r < L; r++) {
      buff.append("" + r + ". ");
      for (int c = 0; c < L; c++) {
        if (a[r][c])
          buff.append("1 ");
        else
          buff.append("0 ");
      }
      buff.append('\n');
    }
    return buff.toString();
  }
}
