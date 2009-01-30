package jm.atom;
import jm.JMatrix;
import jm.JMatrixException;
import jm.angular.Reduced3j;
import jm.angular.Wign6j;
import jm.shell.Shell;
import jm.shell.ShellConfig;

import javax.iox.LOG;
import javax.mathx.MathX;
import javax.utilx.pair.DoublePair;
import javax.vecmathx.VecMathException;
import javax.vecmathx.function.FuncVec;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 5:31:38 PM
 */
abstract public class Atom {
  final public double Z;
  final protected Slater si;
  public Atom(double z, Slater si) {
    Z = z;
    this.si = si;
  }
  final public double calcTot(ShellConfig fc, ShellConfig fc2) {
    Energy res = calcConfigEnergy(fc, fc2);
    return res.kin + res.pot;
  }
  final public double calcKin(ShellConfig fc, ShellConfig fc2) {
    Energy res = calcConfigEnergy(fc, fc2);
    return res.kin;
  }
  final public double calcPot(ShellConfig fc, ShellConfig fc2) {
    Energy res = calcConfigEnergy(fc, fc2);
    return res.pot;
  }
  abstract public Energy calcConfigEnergy(ShellConfig fc, ShellConfig fc2);
  public FuncVec calcConfigDensity(ShellConfig fc, ShellConfig fc2) {
    return null;
  }
  public void assertLS(ShellConfig fc, ShellConfig fc2) {
    if (!fc.LS().equals(fc2.LS())) {// this is not possible, and must be a bug
      String mssg = "!fc.ShellLS().equals(fc2.ShellLS()); " + fc.LS() + "!=" + fc2.LS();
      LOG.error(this, mssg, "");
      throw new VecMathException(mssg);
    }
  }
  abstract public int getNumElec();
  public double calcRk(Shell a, Shell b, Shell a2, Shell b2, int kk) {
    String mssg = "must overwrite before use";
    LOG.error(this, mssg, "");
    throw new JMatrixException(mssg);
  }
  public Energy calcShellPairEnergy(int LL, Shell a, Shell b, Shell a2, Shell b2) {
    Energy res = calcOneEnergy(a, b, a2, b2);
    res.pot += calcTwoPot(LL, a, b, a2, b2);
    return res;
  }
  public Energy calcOneEnergy(Shell a, Shell b, Shell a2, Shell b2) {
    Energy res = new Energy();
    if (a.nL.L != a2.nL.L || b.nL.L != b2.nL.L)
      return res;

    // <aa||aa> // not that <aa||cc> = 0 if a!=c
    if (a.nL.equals(b.nL)  // <aa|
      && a2.nL.equals(b2.nL) //    |cc>
      && a.nL.equals(a2.nL)) { // <aa||aa> no need to calculate the same z_pot twice
      res = calcOneEnergy(a, a2);
      res.kin *= 2;
      res.pot *= 2;
    } else {
      if (a.nL.equals(a2.nL))  // <ab||ab'>
        res = calcOneEnergy(b, b2);  // b, b2 could be different
      if (b.nL.equals(b2.nL)) {  // <ab||a'b>
        Energy res2 = calcOneEnergy(a, a2);  // a, a2 could be different
        res.kin += res2.kin;
        res.pot += res2.pot;
      }
    }
    res.kin /= (getNumElec() - 1);
    res.pot /= (getNumElec() - 1);
    return res;
  }
  public FuncVec calcOneDensity(Shell a, Shell b, Shell a2, Shell b2) {
    FuncVec res = null;
    if (a.nL.L != a2.nL.L || b.nL.L != b2.nL.L)
      return null;

    // <aa||aa> // note that <aa||cc> = 0 if a!=c
    if (a.nL.equals(b.nL)  // <aa|
      && a2.nL.equals(b2.nL) //    |cc>
      && a.nL.equals(a2.nL)) { // <aa||aa> no need to calculate the same twice
      res = calcOneDensity(a, a2);
      res.scale(2);
    } else {
      if (a.nL.equals(a2.nL))  // <ab||ab'>
        res = calcOneDensity(b, b2);  // b, b2 could be different
      if (b.nL.equals(b2.nL)) {  // <ab||a'b>
        FuncVec res2 = calcOneDensity(a, a2);  // a, a2 could be different
        if (res == null)
          res = res2;
        else if (res2 != null)
          res.addSafe(res2);
      }
    }
    if (res != null)
      res.scale(1. / (getNumElec() - 1));
    return res;
  }
  public double calcTwoPot(int LL, Shell a, Shell b, Shell a2, Shell b2) {
    double res = 0;
    //      CL k_min(std::max(
    //           abs((int)sp.a()->nl().L() - (int)sp2.a()->nl().L())
    //         , abs((int)sp.b()->nl().L() - (int)sp2.b()->nl().L())));
    int k_min = Math.max(Math.abs(a.nL.L - a2.nL.L), Math.abs(b.nL.L - b2.nL.L));
    //      CL k_max(std::min(
    //           abs(sp.a()->nl().L() + sp2.a()->nl().L())
    //         , abs(sp.b()->nl().L() + sp2.b()->nl().L())));
    int k_max = Math.min(Math.abs(a.nL.L + a2.nL.L), Math.abs(b.nL.L + b2.nL.L));
    //      for (CL kk = k_min; kk <= k_max; kk = (int)kk + 2) {
    for (int kk = k_min; kk <= k_max; kk += 2) {
      //         double ang = calcTwoElecCoupling(
      //              sp.a()->nl().L(), sp.b()->nl().L()
      //            , sp2.a()->nl().L(), sp2.b()->nl().L()
      //            , kk, LL); // angular momentum coefficient
      double ang = calcTwoElecCoupling(a, b, a2, b2, kk, LL);
      if (JMatrix.isZero(ang))
        continue;
//         res += ang * TRk<FUNC>(basis().w(), basis().r()
//            , *sp.a(), *sp.b(), *sp2.a(), *sp2.b(), kk);
      res += ang * calcRk(a, b, a2, b2, kk);
    }
    return res;
  }
  private double calcTwoElecCoupling(Shell a, Shell b, Shell a2, Shell b2, int k, int L) {
//      double two_el_LL_coupling( const CL& a,  const CL& b
//                 , const CL& a2, const CL& b2
//                 , const CL& k,  const CL& L ) const {
//         double res = (double)CWign6j(L, b, a, k, a2, b2);
//         if (fabs(res) < EPS18)
//            return 0.;
//         return res * pow(-(int)1, (int)(a2 + b + L))
//            * CReduced(a, k, a2) //TBD: need to store all values as in the CFactorial
//            * CReduced(b, k, b2);
    double res = (double) Wign6j.calc(L, b.nL.L, a.nL.L, k, a2.nL.L, b2.nL.L);
    if (JMatrix.isZero(res))
      return 0.;
    return res * MathX.pow(-1, (int) (a2.nL.L + b.nL.L + L))
      * Reduced3j.calc(a.nL.L, k, a2.nL.L) //TBD: need to store all values as in the CFactorial
      * Reduced3j.calc(b.nL.L, k, b2.nL.L);
  }
  public DoublePair normQ(Shell a, Shell b, Shell a2, Shell b2, int prty) {
    //=============================
    // formular (22) from pA70
    // Normalize by number of getShell electrons
    int d = MathX.delta(a.nL, b.nL);
    int d2 = MathX.delta(a2.nL, b2.nL); // delta prime
    double P = MathX.pow(-1, prty) * 0.5;
    double nn = a.q * (b.q - d)
      * a2.q * (b2.q - d2);
    // sum              (1 - eta * delta_ab) * (1 - eta'* delta_a'b') (-1)**(eta-eta') =...
    //    eta,ets'=0,1
    // ...= dir * (1 + (1 - delta_ab) * (1 - delta_a'b'))
    //    - exc * (2 - delta_ab - delta_a'b')
    double S = Math.sqrt(nn);
    double D = S * (1 + (1 - d) * (1 - d2));
    double E = S * (2 - d - d2);
    return new DoublePair(P * D, -P * E);
  }
//   public Energy normQ(Shell a, Shell b, Shell a2, Shell b2
//                             , Energy dir, Energy exc, int prty) {
//      Energy res = null;
//      //=============================
//      // formular (22) from pA70
//      // Normalize by number of getShell electrons
//      if (JMatrix.ignore(dir) && JMatrix.ignore(exc))
//         return res;
//      int d  = MathX.delta(a.nL, b.nL);
//      int d2 = MathX.delta(a2.nL, b2.nL); // delta prime
//      double P = MathX.pow(-1, prty) * 0.5;
//      res = new Energy(P, P);
//      double nn =  a.q *  (b.q - d)
//                * a2.q * (b2.q - d2);
//      // sum              (1 - eta * delta_ab) * (1 - eta'* delta_a'b') (-1)**(eta-eta') =...
//      //    eta,ets'=0,1
//      // ...= dir * (1 + (1 - delta_ab) * (1 - delta_a'b'))
//      //    - exc * (2 - delta_ab - delta_a'b')
//      double S = Math.sqrt(nn);
//      double D = S * (1 + (1 - d) * (1 - d2));
//      double E = S * (2 - d - d2);
//      res.kin *= (dir.kin * D - exc.kin * E);
//      res.pot *= (dir.pot * D - exc.pot * E);
////      double dir_exc = dir * (1 + (1 - d) * (1 - d2))  - exc * (2 - d - d2);
////      res *= (Math.sqrt(nn) * dir_exc);
//      return res;
//   }

//   public FuncVec normQDensity(Shell a, Shell b, Shell a2, Shell b2
//                             , FuncVec dir, FuncVec exc, int prty) {
//      FuncVec res = new FuncVec(dir);
//      // formular (22) from pA70
//      // Normalize by number of getShell electrons
//      int d  = MathX.delta(a.nL, b.nL);
//      int d2 = MathX.delta(a2.nL, b2.nL); // delta prime
//      double P = MathX.pow(-1, prty) * 0.5;
//      double nn =  a.q *  (b.q - d)
//                * a2.q * (b2.q - d2);
//      // sum              (1 - eta * delta_ab) * (1 - eta'* delta_a'b') (-1)**(eta-eta') =...
//      //    eta,ets'=0,1
//      // ...= dir * (1 + (1 - delta_ab) * (1 - delta_a'b'))
//      //    - exc * (2 - delta_ab - delta_a'b')
//      double S = Math.sqrt(nn);
//      double D = S * (1 + (1 - d) * (1 - d2));
//      double E = S * (2 - d - d2);
//      dir.scale(P * D);
//      exc.scale(-P * E);
//      dir.addLine(exc);
//      return dir;
//   }
  public Energy calcOneEnergy(Shell sh, Shell sh2) {
    Energy res = new Energy();
    if (sh.nL.L != sh2.nL.L)
      return res;
    res.pot = si.calcOneZPot(Z, sh, sh2);
    res.kin = si.calcOneKin(sh, sh2);
    return res;
  }
  public FuncVec calcOneDensity(Shell sh, Shell sh2) {
    if (sh.nL.L != sh2.nL.L)
      return null;
    return si.calcOneDensity(sh, sh2);
  }
  public double calcOnePotZ(Shell sh, Shell sh2) {
    if (sh.nL.L != sh2.nL.L)
      return 0.;
    return si.calcOneZPot(Z, sh, sh2);
  }
  public double calcOneKin(Shell sh, Shell sh2) {
    return si.calcOneKin(sh, sh2);
  }
}
