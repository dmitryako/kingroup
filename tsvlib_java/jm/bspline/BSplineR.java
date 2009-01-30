package jm.bspline;
import jm.grid.WFQuadrR;
import stlx.valarrayx.valarray;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/04/2005, Time: 12:04:54
 */
public class BSplineR extends BSplineBasis {
  public BSplineR(WFQuadrR w, valarray t, int k) {
    super(w.x, t, k);
    makeOrthonorm(w);
//      JMatrix.trimTailSLOW(this);
  }
}
