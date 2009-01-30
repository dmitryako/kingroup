package tomsk.old.danny;
import javax.vecmathx.grid.StepGridInLog;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 24, 2004, Time: 6:58:44 AM
 */
public class PotentialInLog {
  private double[] va_ = new double[0]; // array of values
  private StepGridInLog grid_;
  public PotentialInLog(StepGridInLog model) {
    grid_ = model;
  }
  public int size() {
    return va_.length;
  }
//   public double getAt(double r) {
//      double logr = Math.log(r);
//      return interpolate(logr);
//   }
//   // getLine at r**2
//   public double getAtPower2(double r2) {
//      double logr = 0.5 * Math.log(r2);
//      return interpolate(logr);
//   }
//
//   // returns left index
//   // grid: 0, 0.5, 1, 1.5
//   // x==0.6
//   // return 1
//   private int calcLeftIdx(double x) {
//      //grid_.xFirst;
//      //grid_.xLast;
//      //grid_.xStep;
//      return 0;
//   }
//
//   // x = log(r)
//   private double interpolate(double x) {
//      int idx = calcLeftIdx(x);
//      if (idx >= size() - 1 )
//         return va_[size() - 1]; // last
//
//   }
}
