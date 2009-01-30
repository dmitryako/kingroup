package jm.shell;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 22/03/2005, Time: 11:23:58
 */
public class TwoEShells extends ShellConfig {
  public TwoEShells(Shell sh) {
    super(sh);
  }
  public ShellPair getShellPair() {
    Shell a = getShell(0);
    Shell b = getShell(0);
    if (b.q == 1)
      b = getShell(1);
    return new ShellPair(a, b);
  }
}
