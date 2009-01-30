package jm.atom;
import jm.shell.Shell;
import jm.shell.ShellConfig;
import jm.shell.ShellPair;
import jm.shell.TwoEShells;

import javax.mathx.MathX;
import javax.utilx.pair.DoublePair;
import javax.vecmathx.function.FuncVec;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 4, 2004, Time: 6:01:07 PM
 */
public class SysTwoE extends Atom {
  private final static int TWO_ELEC = 2;
  private final SlaterLCR siLogCR;
  //public static final SysTwoE HELIUM = new SysTwoE(-2);
  public SysTwoE(double z, SlaterLCR si) {
    super(z, si);
    siLogCR = si;
  }
  public int getNumElec() {
    return TWO_ELEC;
  }
  public Energy calcConfigEnergy(ShellConfig fc, ShellConfig fc2) {
    assertLS(fc, fc2);
    // He, H+e, any two electron atomic system
    ShellPair sp = ((TwoEShells) fc).getShellPair();
    Shell a = sp.a;
    Shell b = sp.b;
    // <a(1) b(2) |...|a2(1) b2(2)>
    ShellPair sp2 = ((TwoEShells) fc2).getShellPair();
    Shell a2 = sp2.a;
    Shell b2 = sp2.b;
    Energy dir = calcShellPairEnergy(fc.LS().L, a, b, a2, b2); // potential only
    Energy exc = null;
    if (a2.nL.equals(b2.nL))
      exc = new Energy(dir);
    else
      exc = calcShellPairEnergy(fc.LS().L, a, b, b2, a2);
    int prty = 0; // see (21); no spectator electrons for He
    //exc *= MathX.pow(-1, (int)((CL)fc.s() + sp2.a()->nl().L() + sp2.b()->nl().L() + CL(1)) - (int)fc.L() );
    double excMult = MathX.pow(-1, (fc.LS().S.getInt() + a2.nL.L + b2.nL.L + 1) - fc.LS().L);
    exc.kin *= excMult;
    exc.pot *= excMult;
    DoublePair norm = normQ(a, b, a2, b2, prty);
    Energy res = new Energy(norm.a * dir.kin + norm.b * exc.kin
      , norm.a * dir.pot + norm.b * exc.pot);
    return res;
  }
  public FuncVec calcConfigDensity(ShellConfig fc, ShellConfig fc2) {
    assertLS(fc, fc2);
    // He, H+e, any two electron atomic system
    ShellPair sp = ((TwoEShells) fc).getShellPair();
    Shell a = sp.a;
    Shell b = sp.b;
    // <a(1) b(2) |...|a2(1) b2(2)>
    ShellPair sp2 = ((TwoEShells) fc2).getShellPair();
    Shell a2 = sp2.a;
    Shell b2 = sp2.b;
    FuncVec dir = calcOneDensity(a, b, a2, b2); // potential only
    FuncVec exc = null;
    if (a2.nL.equals(b2.nL)) {
      if (dir != null)
        exc = new FuncVec(dir);
    } else {
      exc = calcOneDensity(a, b, b2, a2);
    }
    if (exc == null && dir == null) {
      return null;
    }
    int prty = 0; // see (21); no spectator electrons for He
    double excMult = MathX.pow(-1, (fc.LS().S.getInt() + a2.nL.L + b2.nL.L + 1) - fc.LS().L);
    DoublePair norm = normQ(a, b, a2, b2, prty);
    if (dir != null) {
      dir.scale(norm.a);
    }
    if (exc != null) {
      exc.scale(excMult);
      exc.scale(norm.b);
    }
    if (dir != null && exc != null) {
      dir.add(exc);
    } else if (dir == null) {
      dir = exc;
    }
    return dir;
  }
  public double calcRk(Shell a, Shell b, Shell a2, Shell b2, int kk) {
    return RkLCR.calcRkLogCR(siLogCR.getWeightsLogCR()
      , a.wf, b.wf, a2.wf, b2.wf, kk);
  }
}
