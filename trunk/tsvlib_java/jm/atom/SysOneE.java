package jm.atom;
import jm.shell.ShellConfig;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 4, 2004, Time: 6:00:54 PM
 */
public class SysOneE extends Atom {
  private final static int ONE_ELEC = 1;
  public SysOneE(double z
    , Slater si) { // grid/basis dependent!!!
    super(z, si);
  }
  public int getNumElec() {
    return ONE_ELEC;
  }
  public Energy calcConfigEnergy(ShellConfig fc, ShellConfig fc2) {
    assertLS(fc, fc2);
    Energy res = new Energy();
    res.kin = calcOneKin(fc.getShell(0), fc2.getShell(0)); // potential only
    res.pot = calcOnePotZ(fc.getShell(0), fc2.getShell(0)); // potential only
    return res;
  }

//   public double calcConfigPot(ShellConfig fc, ShellConfig fc2) {
//      // He+, H, any one electron atomic system
//      // All configs must have the same ShellLS
//      assertLS(fc, fc2);
//      double res = calcOneElecPotZ(fc.getShell(0), fc2.getShell(0)); // potential only
//      return res;
//   }
//
//   public double calcConfigKin(ShellConfig fc, ShellConfig fc2) {
//      // He+, H, any one electron atomic system
//      // All configs must have the same ShellLS
//      assertLS(fc, fc2);
//      double res = calcOneElecKin(fc.getShell(0), fc2.getShell(0)); // potential only
//      return res;
//   }
//
}
