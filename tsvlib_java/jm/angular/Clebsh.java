package jm.angular;
import javax.mathx.MathX;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/03/2005, Time: 15:28:03
 */
public class Clebsh {
  public static double calc(float A1, float B1, float C1, float X1, float Y1, float Z1) {
    return Wign3j.calcClebsh(A1, B1, C1, X1, Y1, Z1);
  }
  public static double calcAllSum(float j1, float j2, float j3, float m, float mp) {
    double sum = 0;
    int IJ1 = Math.round(j1);
//   for (CM m1 = -(int)j1; (int)m1 <= (int)j1; m1++)
    for (int m1 = -IJ1; m1 <= IJ1; m1++) {
//      for (CM m2 = -(int)j1; (int)m2 <= (int)j1; m2++) {
      for (int m2 = -IJ1; m2 <= IJ1; m2++) {
//         dbg << "+=dclebg(j1, j2, j3, m1, m2, m)="  << (double)CClebsh(j1, j2, j3, m1, m2, m)  << endl;
//         dbg << " *dclebg(j1, j2, j3, m1, m2, mp)=" << (double)CClebsh(j1, j2, j3, m1, m2, mp) << endl;
//         sum += (double)CClebsh(j1, j2, j3, m1, m2, m)
//              * (double)CClebsh(j1, j2, j3, m1, m2, mp);
        sum += Clebsh.calc(j1, j2, j3, m1, m2, m)
          * Clebsh.calc(j1, j2, j3, m1, m2, mp);
//         dbg << "sum=" << sum << endl;
      }
    }
    return sum;//   res(sum);
  }
  public static double calcSum(float j1, float j2, float j3, float m) {
    double sum = 0;
    int IJ1 = Math.round(j1);
//      for (CM m1 = -(int)j1; (int)m1 <= (int)j1; m1++) {
    for (int m1 = -IJ1; m1 <= IJ1; m1++) {
//         sum += pow((double)CClebsh(j1, j2, j3, m1, (int)m - (int)m1, m), 2);
      sum += MathX.pow(Clebsh.calc(j1, j2, j3, m1, m - m1, m), 2);
    }
    return sum;//   res(sum);
  }
}
