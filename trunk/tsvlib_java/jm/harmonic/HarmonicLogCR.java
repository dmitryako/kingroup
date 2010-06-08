package jm.harmonic;
import jm.JMatrix;
import jm.grid.TransLcrToR;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/04/2005, Time: 08:15:58
 */
public class HarmonicLogCR extends HarmonicBasis {
  public HarmonicLogCR(TransLcrToR xToR, int size, int L, double B) {
    super(xToR.getR2(), size, L, B);
    loadLogCRBasis(xToR);
    changeGrid(xToR.x); // set to X-x !!!!!******
    JMatrix.trimTailSLOW(this);
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