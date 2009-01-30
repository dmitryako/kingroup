package jm.angular;
import javax.mathx.MathX;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/03/2005, Time: 12:54:40
 */
public class Reduced3j {
  public static double calc(float a, float b, float c) {
//      double v = dwig3j_(a(), b(), c(), x(), y(), z());
//      v *= pow(-1, (int)a())
//         * pow((2. * a() + 1) * (2. * c() + 1), 0.5);
//      return res(v);
    double res = Wign3j.calc(a, b, c, 0, 0, 0);
    int ia = Math.round(a);
    res *= (MathX.pow(-1, ia) * Math.sqrt((2. * a + 1) * (2. * c + 1)));
    return res;
  }
}
