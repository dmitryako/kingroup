package jm.atom;
import jm.grid.TransLogCRToR;
import jm.grid.WFQuadrLogCR;
import jm.shell.Shell;

import javax.vecmathx.function.FuncVec;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/03/2005, Time: 14:57:32
 */
public class SlaterLCR extends SlaterLR {
  final private TransLogCRToR xToR; // r = exp(x)-exp(xFirst)
  final private WFQuadrLogCR wLogCR;
  public SlaterLCR(WFQuadrLogCR w) {
    super(w);
    this.xToR = w.getLogCRToR();
    wLogCR = w;
  }
  public final TransLogCRToR getLogCRToR() {
    return xToR;
  }
  public final WFQuadrLogCR getWeightsLogCR() {
    return wLogCR;
  }
  /**
   * THIS FUNCTION IS BASIS DEPENDANT !!!!!!!!!!!
   * see LaguerreLogCR for derivation
   * HF = -1/2F" +1/2*{1/4 + L(L+1)*(y/r)^2}*F + y^2U(r)*F
   * //  INTL dx Fn'(x) H Fn(x) = delta_n'n
   */
  protected double calcKinL(Shell sh, Shell SH2 /* make it stand out*/) {
    int L = sh.nL.L;
    if (L != SH2.nL.L)
      return 0.;
    double kin = calc(sh.wf, SH2.wf);
    double kinL = calc(sh.wf, SH2.wf, xToR.getCR2OverR2());
    double res = 0.125 * kin + 0.5 * L * (L + 1) * kinL;
    return res;
  }
  protected FuncVec calcOneDensity(Shell sh, Shell SH2 /* make it stand out*/) {
    int L = sh.nL.L;
    if (L != SH2.nL.L)
      return null;
    FuncVec res = new FuncVec(sh.wf);
    res.mult(SH2.wf);
//      res.scale(xToR.getDivCR());
    res.mult(xToR.getCR2());
    return res;
  }
  protected double calcOneZPot(double z, Shell sh, Shell sh2) {
    if (sh.nL.L != sh2.nL.L)
      return 0.;
    //  oo
    //  |  y^2 dx Fn'(r) Z * 1/r Fn(r) =
    // -oo
    double int_z = calc(sh.wf, sh2.wf, xToR.getCR2DivR());
    double res = z * int_z;
    return res;
  }
}
