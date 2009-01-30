package javax.vecmathx.interpolation;
import javax.iox.LOG;
import javax.vecmathx.VecMathException;
import javax.vecmathx.function.FuncVec;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/03/2005, Time: 17:12:23
 */
public class PolynomInterpol {

// CFuncPow      at small r,  f(r) = a * pow(r, b);
  public static double calcPowerSLOW(FuncVec func, int idx) {
    if (idx >= func.size() - 1) {
      String mssg = "idx >= func.size() - 1";
      LOG.error(null, mssg, "");
      throw new VecMathException(mssg);
    }
    if (func.x.get(idx) == 0 && func.x.get(idx + 1) == 0) {
      String mssg = "func.x.getLine(idx) == 0 && func.x.getLine(idx++) == 0";
      LOG.error(null, mssg, "");
      throw new VecMathException(mssg);
    }
    int i = idx;
    if (func.x.get(idx) == 0) {
      i++; // try to recover
      if (i >= func.size() - 1) {
        String mssg = "i >= func.size() - 1";
        LOG.error(null, mssg, "");
        throw new VecMathException(mssg);
      }
    }
    double f = func.get(i);
    double f2 = func.get(i + 1);
    if (f2 / f < 1.) {
      String mssg = "f2 / f < 1.  " + (float) f2 + "/" + (float) f;
      LOG.error(null, mssg, "");
      throw new VecMathException(mssg);
    }
    double x = func.x.get(i);
    double x2 = func.x.get(i + 1);
    if (x2 / x < 1.) {
      String mssg = "x2/x < 1.  " + (float) x2 + "/" + (float) x;
      LOG.error(null, mssg, "");
      throw new VecMathException(mssg);
    }
    double res = Math.log(f2 / f) / Math.log(x2 / x);
    return res;
/*
//    CFuncPow
bool CFuncPow::calc_low_pow() {
   if (size() < 2
   ||  size() > grid().size())
      return false;
   int i = 0;
   if (grid()[i] == 0.0)
      i++;
   double r  = grid()[i];
   double r2 = grid()[i + 1];
   b(log((*this)[i] / (*this)[i+1])
      / log( r / r2));
   a((*this)[i] / pow(r, b()));
   return (fabs(r/r2) < 1.);
}*/
  }
}

