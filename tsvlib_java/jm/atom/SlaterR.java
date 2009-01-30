package jm.atom;
import jm.grid.WFQuadrR;
import jm.shell.Shell;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 5:44:01 PM
 */
public class SlaterR extends Slater {
  final private WFQuadrR wr;
  public SlaterR(WFQuadrR w) {
    super(w);
    wr = w;
  }
  /**
   * THIS FUNCTION IS BASIS DEPENDANT !!!!!!!!!!!
   * // After r*Rn(r) = Un(r)
   * //                 1 d^2    L(L+1)   Z
   * // Hamiltonian = - - --   + ------ + - = H(U)
   * //                 2 dr^2    2r^2    r
   * // oo
   * // |  dr Un'(r) H Un(r) = delta_n'n
   * // o
   * //                             (a)
   * //   LaguerreBasis's polinomials   L  (r), n = 0,..., nmax
   * //                              n
   * //   oo   (a)    (a)    -r  a
   * //   I   L  (r) L  (r) e   r  dr = Gamma(a + n + 1) / n!
   * //   0     n     n'
   * //
   * //     (2L+2)     -r/2  L+1
   * //    L     (r)  e     r    ;  with  dr
   * //     n
   */
  protected double calcOneKin(Shell sh, Shell SH2) {
    int L = sh.nL.L;
    if (L != SH2.nL.L)
      return 0.;
    //                 1 d^2    L(L+1)
    // Hamiltonian = - - --   + ------
    //                 2 dr^2    2r^2
    double L2 = 0.5 * (L * (L + 1));
    double int_L2 = calc(sh.wf, SH2.wf, wr.getDivR2());
    double int_d = calc(sh.drv, SH2.drv);
    // note 0.5 instead of (-0.5), that is because U' * U' replaced U*U"
    // x            x               |x
    // I dr RR" = - I dr R'R' + RR' |  = - drR'R' + RR'(x) - RR'(y)
    // y            y               |y
    double firstCorrection = sh.wf.getFirst() * SH2.drv.getFirst();
    double lastCorr = sh.wf.getLast() * SH2.drv.getLast();
    double res = 0.5 * (int_d + firstCorrection - lastCorr) + L2 * int_L2;
    return res;
  }
  protected double calcOneZPot(double z, Shell sh, Shell sh2) {
    if (sh.nL.L != sh2.nL.L)
      return 0.;
    //  oo                                    oo
    //  | r^2 dr Rn(r) Rn'(r) Z * 1/r Fn(r) = I dr Rn(r) Rn'(r) Z * r;
    //  o                                     o
    double int_z = calc(sh.wf, sh2.wf, wr.getDivR());
    double res = z * int_z;
    return res;
  }
}
