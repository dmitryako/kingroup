package javax.vecmathx.interpolation;
import javax.vecmathx.function.FuncVec;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 27, 2004, Time: 8:36:11 AM
 */
public class LinearInterpolation {
  public LinearInterpolation(FuncVec src) {
    // precalculate all coefficients
  }
//   // y1 = a * x1 + b
//   // y2 = a * x2 + b
//   //
//   final public double interpolateLinear(double x) {
//      int idxL = grid_.findLeftIdx(x);
//   }

//   public double getAt(double r) {
//      double logr = Math.log(r);
//      return interpolate(logr);
//   }

/*
   // getLine at r**2
   public double getAtPower2(double r2) {
      double logr = 0.5 * Math.log(r2);
      return interpolate(logr);
   }


   // x = log(r)
   private double interpolate(double x) {
      int idx = calcLeftIdx(x);
      if (idx >= size() - 1 )
         return va_[size() - 1]; // last
   }
*/
}
