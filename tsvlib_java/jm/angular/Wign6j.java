package jm.angular;
import jm.JMatrix;

import javax.mathx.MathX;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/03/2005, Time: 15:24:33
 */
//   CWign6j = {a, b, c}
//             {x, y, z}
public class Wign6j {
  private final float a;
  private final float b;
  private final float c;
  private final float x;
  private final float y;
  private final float z;
  public Wign6j(float a, float b, float c, float x, float y, float z) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public static double calc(float A1, float B1, float C1, float X1, float Y1, float Z1) {
    return Wign3j.calcWign6j(A1, B1, C1, X1, Y1, Z1);
  }
  public static double calc_abc0(float a, float b, float c) {
    // from p522 of Landau
    // {a b c}
    // {0 c b}
    //      return pow(((2. * b() + 1) * (2. * c() + 1)), -0.5)
    //         * pow(-1, int(a() + b() + c()));
    return MathX.pow(-1, Math.round(a + b + c)) / Math.sqrt(((2. * b + 1) * (2. * c + 1)));
  }
  public static double calcCondonSum(Wign6j w, float z2) {
    //// from p.180 of Condon&Odabasi
    ////   SUM  {a, b, k}  {a, b, k}   (2k+1) * (2z+1) = delta(z, z')
    ////    k   {x, y, z}  {x, y, z'}
    //      double CWign6jTestSum::calc_Condon() const {
    //         CWign6j w(w_);
    Wign6j w2 = new Wign6j(w.a, w.b, w.c, w.x, w.y, z2);//         CWign6j w2(w_);
    //         w2.z(z2_, false);
    double res = 0;
    //         for (double k = fabs(w.a() - w.b()); k <= w.a() + w.b(); k++)
    for (float k = w.a - w.b; k <= w.a + w.b; k++)
//?                    res += w.c(k) * w2.c(k) * (2 * k + 1);//res += w.c(k) * w2.c(k) * (2 * k + 1);
      //
      if (JMatrix.isZero(res))//         if (fabs(res) < EPS18)
        return 0;
    return res * (2. * w.z + 1);//return res * (2 * w.z() + 1);
  }
//   public static double calcLandauSum() {
//   }
}
