package jm.grid;
import numeric.functor.Func;
import numeric.functor.FuncExp;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/03/2005, Time: 10:22:28
 */
public class TransLogRToR extends FuncVec {
  private valarray R2;
  private valarray divSqrtR;
  private final FuncVec mapLogRToR2;
  // Just to help keeping track
  // equal step in x=ln(r)
  public TransLogRToR(valarray x) {
    super(x, new FuncExp(1));
    mapLogRToR2 = new FuncVec(x, new functorLogRToR2());
  }
  public FuncVec getMapLogRToR2() {
    return mapLogRToR2;
  }
  public valarray getR2() {
    if (R2 == null)
      R2 = new FuncVec(x, new functorLogRToR2());
    return R2;
  }
  public valarray getDivSqrtR() {
    if (divSqrtR == null)
      divSqrtR = new FuncVec(x, new functorLogRToDivSqrtR());
    return divSqrtR;
  }
  private class functorLogRToR implements Func {
    // f(x)=r
    public double calc(double x) {
      return Math.exp(x);
    }
  }
  private class functorLogRToR2 extends functorLogRToR {
    // f(x)=r^2
    public double calc(double x) {
      double r = super.calc(x);
      return r * r;
    }
  }
  private class functorLogRToDivSqrtR extends functorLogRToR {
    // f(x)=1/sqrt(r)
    public double calc(double x) {
      double cr = super.calc(x);
      return 1. / Math.sqrt(cr);
    }
  }
}
