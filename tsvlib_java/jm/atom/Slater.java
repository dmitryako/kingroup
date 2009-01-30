package jm.atom;
import jm.shell.Shell;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.integration.Quadr;
import javax.vecmathx.integration.Quadr2;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 5:45:19 PM
 */
public class Slater extends Quadr2 {
  public Slater(Quadr w) {
    super(w);
  }
  protected FuncVec calcOneDensity(Shell sh, Shell sh2) {
    return null;
  }
  protected double calcOneKin(Shell sh, Shell sh2) {
    return 0;
  }
  protected double calcOneZPot(double z, Shell sh, Shell sh2) {
    return 0;
  }
}
