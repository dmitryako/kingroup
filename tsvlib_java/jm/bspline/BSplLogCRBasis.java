package jm.bspline;
import jm.grid.TransLcrToR;
import jm.grid.WFQuadrLcr;
import stlx.valarrayx.valarray;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/04/2005, Time: 11:15:33
 */
public class BSplLogCRBasis extends BSplBoundBasis {
  private static int k = 5;
//   public BSplLogCRBasis(WFQuadrLcr w, int N, int k) {
//      super(w.getLogCRToR()
//              , makeBSplineKnotsFromGrid(N, w.getLogCRToR(), k), k);
//      int knotsNum = calcKnotsNumFromBasisSize(basisSize, k); //
  public BSplLogCRBasis(WFQuadrLcr w, valarray t, int k) {
    super(w.getLogCRToR(), t, k);
    loadLogCRBasis(w.getLogCRToR());
    makeOrthonorm(w);
    changeGrid(w.getLogCRToR().x); // set to X-x !!!!!******
//      JMatrix.trimTailSLOW(this);
  }
  public BSplLogCRBasis(WFQuadrLcr w, int N) {
    super(w.getLogCRToR()
      , BSplineBasis.makeBSplineKnotsFromGrid(N, w.getLogCRToR(), k), k);
    loadLogCRBasis(w.getLogCRToR());
    makeOrthonorm(w);
    changeGrid(w.getLogCRToR().x); // set to X-x !!!!!******
//      JMatrix.trimTailSLOW(this);
  }
  //   Rn(r) = Ln(r) / r; since Ln is integrable with dr not r^2dr
  // INTL r^2dr Rn*Rn' = INTL r^2 ydx y/r^2 Fn*Fn' = INTL y^2 dx Fn*Fn'
  //   Fn(x) = r/sqrt(c+r) L_n(y) / r = L_n(y) / sqrt(c+r);  with rdr  or  r^2dx   for the integral
  private void loadLogCRBasis(TransLcrToR xToR) {
    for (int n = 0; n < size(); n++) {
      get(n).mult(xToR.getDivSqrtCR());
    }
  }
}