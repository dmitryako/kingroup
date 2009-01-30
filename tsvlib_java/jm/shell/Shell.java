package jm.shell;
import jm.angular.Spin;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 3:27:37 PM
 */
public class Shell
//        extends ShellNL
{
  final public ShellLS LS;
  final public ShellNL nL;
  final public int q;
  final public FuncVec wf; // wave function
  final public valarray drv; // first derivative
//   final public FuncVec drv2; // 2nd derivative
  public Shell(int n, FuncVec f, int L) {
    this(n, f, 1, L, new ShellLS(L, Spin.ELECTRON));
  }
  public Shell(int n, FuncVec f, int q, int L, ShellLS LS) {
    this.nL = new ShellNL(n, L);
    this.LS = LS;
    this.q = q;
    wf = f;
    drv = wf.getDeriv();
//      drv2 = DerivFactory.makeDeriv(drv);
  }
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    Shell from = (Shell) obj;
    if (!nL.equals(from.nL))
      return false;
    if (q != from.q)
      return false;
    return LS.equals(from.LS);
  }

//   final public ShellLS ShellLS() {return ShellLS;}
  public String toString() {
    StringBuffer buff = new StringBuffer();
    if (q > 1) {
      buff.append("(").append(nL).append(")^" + q);
    } else {
      buff.append(nL);
    }
    return buff.toString();
  }
  public boolean isValid() {
    if (q == 1 || q == capacity() - 1) {
      if (nL.L != LS.L)
        return false;
      if (LS.S.s2_1 != 2)
        return false;
    }
    if (q == capacity()) {
      if (!LS.equals(LS.CLOSED_SHELL))
        return false;
    }
    return true;
  }
  private int capacity() {
    return 2 * (2 * nL.L + 1);
  }
}
