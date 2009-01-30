package jm.bspline;
import jm.grid.WFQuadrLogCR;
import stlx.valarrayx.valarray;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/04/2005, Time: 11:44:50
 */
public class BSplineFactory {
  public static BSplLogCRBasis makeBSplineLogCR(WFQuadrLogCR w, int basisSize, int k) {
    int knotsNum = BSplBoundBasis.calcKnotsNumFromBasisSize(basisSize, k);
    valarray t = BSplBoundBasis.makeBSplineKnotsFromGrid(knotsNum, w.getLogCRToR(), k);
    return new BSplLogCRBasis(w, t, k);
  }
}
