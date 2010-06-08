package jm.grid;
import numeric.functor.Func;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/03/2005, Time: 10:28:36
 */
public class TransLcrToR extends FuncVec {
  private valarray CR2OverR2;
  private valarray CR2DivR;
  private valarray R2;
  private valarray CR2;
  private valarray CR; // y is used in FuncVec
  private valarray divR;
  private valarray CRDivR;
  private valarray divSqrtCR;
  private valarray divCR;
  private final double param;
  // Just to help keeping track
  // equal step in x=ln(CR); CR=c+r; c > 0
  // firstX=ln(param)
  public TransLcrToR(valarray x) {
    super(x, null);
    param = Math.exp(x.getFirst());
    calc(new FuncLogCRToR(param));
  }

  public valarray getR2() {
    if (R2 == null)
      R2 = new FuncVec(x, new FuncLogCRToR2(param));
    return R2;
  }
  public valarray getCR2OverR2() {
    if (CR2OverR2 == null)
      CR2OverR2 = new FuncVec(x, new FuncLogCRToCR2DivR2(param));
    return CR2OverR2;
  }
  public valarray getCR2DivR() {
    if (CR2DivR == null)
      CR2DivR = new FuncVec(x, new functorLogCRToCR2DivR(param));
    return CR2DivR;
  }
  public valarray getDivR() {
    if (divR == null)
      divR = new FuncVec(x, new FuncLogCRToDivR(param));
    return divR;
  }
  public valarray getCRDivR() {
    if (CRDivR == null)
      CRDivR = new FuncVec(x, new FuncLogCRToCRDivR(param));
    return CRDivR;
  }
  public valarray getDivCR() {
    if (divCR == null)
      divCR = new FuncVec(x, new functorLogCRToDivCR());
    return divCR;
  }
  public valarray getDivSqrtCR() {
    if (divSqrtCR == null)
      divSqrtCR = new FuncVec(x, new functorLogCRToDivSqrtCR());
    return divSqrtCR;
  }
  public valarray getCR2() {
    if (CR2 == null)
      CR2 = new FuncVec(x, new functorLogCRToCR2());
    return CR2;
  }
  public valarray getCR() {
    if (CR == null)
      CR = new FuncVec(x, new functorLogCRToCR());
    return CR;
  }
  private class functorLogCRToDivSqrtCR extends functorLogCRToCR {
    public double calc(double x) {
      double cr = super.calc(x);
      return 1. / Math.sqrt(cr);
    }
  }
  private class functorLogCRToDivCR extends functorLogCRToCR {
    public double calc(double x) {
      double cr = super.calc(x);
      return 1. / cr;
    }
  }
  private class functorLogCRToCR implements Func {
    public double calc(double x) {
      return Math.exp(x);
    }
  }
  private class functorLogCRToCR2 extends functorLogCRToCR {
    public double calc(double x) {
      double cr = super.calc(x);
      return cr * cr;
    }
  }
  private class FuncLogCRToR2 extends FuncLogCRToR {
    public FuncLogCRToR2(double p) {
      super(p);
    }
    public double calc(double x) {
      double r = super.calc(x);
      if (r == 0)
        return 0;
      return r * r;
    }
  }
  private class functorLogCRToCR2DivR extends FuncLogCRToR {
    // f(x)=CR^2/r
    public functorLogCRToCR2DivR(double p) {
      super(p);
    }
    public double calc(double x) {
      double r = super.calc(x);
      if (r == 0)
        return 0;
      double cr = Math.exp(x);
      return cr * cr / r;
    }
  }
  private class FuncLogCRToDivR extends FuncLogCRToR {
    // f(x)=1/r
    public FuncLogCRToDivR(double p) {
      super(p);
    }
    public double calc(double x) {
      double r = super.calc(x);
      if (r == 0)
        return 0;
      return 1. / r;
    }
  }
  private class FuncLogCRToCRDivR extends FuncLogCRToR {
    // f(x)=CR/r
    public FuncLogCRToCRDivR(double p) {
      super(p);
    }
    public double calc(double x) {
      double r = super.calc(x);
      if (r == 0)
        return 0;
      return Math.exp(x) / r;
    }
  }
  private class FuncLogCRToCR2DivR2 extends FuncLogCRToCRDivR {
    // f(x)=(CR/r)^2
    public FuncLogCRToCR2DivR2(double p) {
      super(p);
    }
    public double calc(double x) {
      double yr = super.calc(x);
      if (yr == 0)
        return 0;
      return yr * yr;
    }
  }
}
class FuncLogCRToR implements Func {
  // x = ln(CR), CR=c+r; c > 0
  // exp(x) = c + r;  r = exp(x) - c
  private final double c;
  public FuncLogCRToR(final double p) {
    c = p;
  }
  public double calc(double x) {
    return Math.exp(x) - c;
  }
}

