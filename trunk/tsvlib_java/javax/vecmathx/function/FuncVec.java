package javax.vecmathx.function;
import jm.JMatrixException;
import numeric.functor.Func;
import stlx.valarrayx.valarray;

import javax.iox.LOG;
import javax.vecmathx.derivative.DerivFactory;

/* FuncVec is a map of valarray to valarray
*/
public class FuncVec extends valarray {
  public valarray x;
  private valarray drv; // first derivative
  public FuncVec(final FuncVec f) {
    super(f);
    x = f.x;
  }
  public FuncVec(final valarray onX, int initSize) {
    super(initSize);
    this.x = onX;
  }
  public FuncVec(final valarray x, final Func f) {
    super(x);
    this.x = x;
    if (f != null)
      calc(f);
  }
  final public void setX(valarray g) {
    x = g;
  }
  // maximum absolute distance
  public double distSLOW(FuncVec f) {
    double res = Math.abs(f.get(0) - get(0));
    for (int i = 1; i < size(); i++) {
      if (f.x.get(i) != x.get(i)) {
        String error = "different grid " + (float) x.get(i) + "!=" + (float) f.x.get(i);
        LOG.error(this, error, "");
        throw new JMatrixException(error);
      }
      double dist = Math.abs(f.get(i) - get(i));
      if (res < dist)
        res = dist;
    }
    return res;
  }
  final public valarray getDeriv() {
    if (drv == null)
      drv = DerivFactory.makeDeriv(this);
    return drv;
  }
}
