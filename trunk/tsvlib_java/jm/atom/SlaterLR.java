package jm.atom;
import jm.JMatrixException;
import jm.grid.TransLogRToR;
import jm.grid.WFQuadrLogR;
import jm.shell.Shell;

import javax.iox.LOG;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 4:13:37 PM
 */
// NOTE!!! In the following transformation all radial extrems have been removed, eg z/r or L(L+1)/r2
//
// After sqrt(r)*Rn(r) = Fn(r)
// and r = exp(x);   dr = r dx
//                        2              2
//                     1 d        (L+1/2)
// r^2 * Hamiltonian = - - --   + -------- + r*Z = r^2 * H(X)
//                     2 dx^2        2
//  oo  2
//  |  r dx Fn'(exp(x)) H Fn(exp(x)) = delta_n'n
// -oo
// w are for 'x' integral
//
//                             (a)
//   LagrrBasis's polinomials   L  (r), n = 0,..., nmax
//                              n
//   oo   (a)    (a)    -r  a
//   I   L  (r) L  (r) e   r  dr = Gamma(a + n + 1) / n!
//   0     n     n'
//
//     (2L+2)     -r/2  L+1
//    L     (r)  e     r    ;  with  dr  or  rdx  for the integral
//     n
//
//     (2L+2)     -r/2  L+1/2
//    L     (r)  e     r      ;  with rdr  or  r^2dx   for the integral
//     n
public class SlaterLR extends Slater {
  final private TransLogRToR xToR; // r = exp(x)
  final private WFQuadrLogR wLogR;
  public SlaterLR(WFQuadrLogR w) {
    super(w);
    this.xToR = w.getLogRToR();
    wLogR = w;
  }
  /**
   * THIS FUNCTION IS BASIS DEPENDANT !!!!!!!!!!!
   * //                 1 d^2    (L+1/2)^2
   * // Hamiltonian = - - --   + -------- + r*Z = H(X)
   * //                 2 dx^2      2
   * //  oo
   * //  |  dx Fn'(exp(x)) H Fn(exp(x)) = delta_n'n
   * // -oo
   * // 1/r**2 cancels r**2
   */
  protected double calcOneKin(Shell sh, Shell SH2 /* make it stand out*/) {
    return calcKinDeriv(sh, SH2) + calcKinL(sh, SH2);
  }
  protected double calcKinDeriv(Shell sh, Shell SH2 /* make it stand out*/) {
    int L = sh.nL.L;
    if (L != SH2.nL.L)
      return 0.;
    double int_d = calc(sh.drv, SH2.drv);
    // note 0.5 instead of (-0.5), that is because U' * U' replaced U*U"
    // x            x               |x
    // I dr RR" = - I dr R'R' + RR' |  = - drR'R' + RR'(x) - RR'(y)
    // y            y               |y
    double firstCorrection = sh.wf.getFirst() * SH2.drv.getFirst();
    double lastCorr = sh.wf.getLast() * SH2.drv.getLast();
    double res = 0.5 * (int_d + firstCorrection - lastCorr);
    return res;
  }
  protected double calcKinL(Shell sh, Shell SH2 /* make it stand out*/) {
    int L = sh.nL.L;
    if (L != SH2.nL.L)
      return 0.;
    double L2 = 0.5 * (0.5 + L) * (0.5 + L);
    double int_L2 = calc(sh.wf, SH2.wf);
    double res = L2 * int_L2;
    return res;
  }
  protected double calcOneZPot(double z, Shell sh, Shell sh2) {
    if (sh.nL.L != sh2.nL.L)
      return 0.;
    if (xToR == null) {
      String error = "xToR == null";
      LOG.error(this, error, "");
      throw new JMatrixException(error);
    }
    //  oo
    //  |  r**2 dx Fn'(r) Z * 1/r Fn(r) =
    // -oo
    double int_z = calc(sh.wf, sh2.wf, xToR);
    double res = z * int_z;
    return res;
  }
}
