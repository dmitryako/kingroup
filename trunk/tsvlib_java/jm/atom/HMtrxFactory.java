package jm.atom;
import Jama.EigenvalueDecomposition;
import jm.grid.TransLogRToR;
import jm.grid.WFQuadrLcr;
import jm.grid.WFQuadrLogR;
import jm.grid.WFQuadrR;
import jm.laguerre.JmLagrrOrthLcr;
import jm.laguerre.LagrrLr;
import jm.laguerre.LagrrR;
import jm.shell.ConfigArr;
import jm.shell.ConfigArrFactory;
import jm.shell.ShellLS;

import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 10, 2004, Time: 7:29:48 AM
 */
public class HMtrxFactory {
//   public static HMtrx makeFromShiftedLegendra(int size
//                                        , int N, int L) {
//      StepGrid r = new StepGrid(0., 1., size);
//      BooleQuadr w = new BooleQuadr(r);
//      FuncArr arr = new ShiftedLegendraOrthonorm(r, N);
//      ConfigArr basisR = ConfigArrFactory.makeOneElectronForY(N, L, arr);
//
//      // DOES NOT WORK!!!!
//      SlaterR SlaterR    SysOneE hydR = new SysOneE(-1., sr);
//      return new HMtrx(basisR, hydR);
//   }
  public static HMtrx makeFromLgrrR(
    double first, double last, int size
    , int N, int L, int alpha, double lambda) {
    StepGrid r = new StepGrid(first, last, size);
    WFQuadrR w = new WFQuadrR(r);
    FuncArr arr = new LagrrR(r, N, alpha, lambda);
    ConfigArr basisR = ConfigArrFactory.makeOneElec(N, L, arr);
    Slater slater = new SlaterR(w);
    SysOneE hydR = new SysOneE(-1., slater);
    return new HMtrx(basisR, hydR);
  }
  public static HMtrx makeFromLaguerreLogR(
    double first, double last, int size
    , int N, int L, int alpha, double lambda) {
    StepGrid x = new StepGrid(first, last, size);
    TransLogRToR xToR = new TransLogRToR(x);
    WFQuadrLogR w = new WFQuadrLogR(x);
    FuncArr arr = new LagrrLr(xToR, N, alpha, lambda);
    ConfigArr basis = ConfigArrFactory.makeOneElec(N, L, arr);
    Slater slater = new SlaterLR(w);
    SysOneE hyd = new SysOneE(-1., slater);
    return new HMtrx(basis, hyd);
  }
  public static HMtrx makeHyFromLagrrLcr(
    double firstX, double last, int size
    , int N, int L, int alpha, double lambda) {
    StepGrid x = new StepGrid(firstX, last, size);
    // x=firstX -> r=0
    WFQuadrLcr w = new WFQuadrLcr(x);
    FuncArr arr = new JmLagrrOrthLcr(w.getLogCRToR(), N, alpha, lambda);
    ConfigArr basis = ConfigArrFactory.makeOneElec(N, L, arr);
    Slater slater = new SlaterLcr(w);
    SysOneE hyd = new SysOneE(-1., slater);
    return new HMtrx(basis, hyd);
  }
  public static HMtrx makeHeFromLagrrLcr(StepGrid xToR
    , int N, int L, int alpha, double lambda, ShellLS LS) {
    WFQuadrLcr w = new WFQuadrLcr(xToR);
    FuncArr arr = new JmLagrrOrthLcr(w.getLogCRToR(), N, alpha, lambda);
    ConfigArr basis = ConfigArrFactory.makeTwoElec(LS, N, L, arr);
    Slater slater = new SlaterLcr(w);
    SysOneE hyd = new SysOneE(-1., slater);
    return new HMtrx(basis, hyd);
  }
  public static EigenvalueDecomposition calcHeEnergies(StepGrid xToR
    , int N, int L, int alpha, double lambda, ShellLS LS) {
    HMtrx H = makeHeFromLagrrLcr(xToR, N, L, alpha, lambda, LS);
    return H.eig();
  }
}
