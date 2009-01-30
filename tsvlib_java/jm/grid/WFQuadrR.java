package jm.grid;
import numeric.functor.FuncPowInt;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
import javax.vecmathx.integration.BooleQuadr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/03/2005, Time: 15:42:25
 */
public class WFQuadrR extends BooleQuadr {
//   private valarray wDivR2;
  private valarray divR2;
  private valarray divR;
  public WFQuadrR(StepGrid x) {
    super(x);
  }
//   public valarray withDivR2() {
//      if (wDivR2 == null) {
//         wDivR2 = new FuncVec(this.x, new FuncPowInt(1., -2));
//         wDivR2.scale(this);
//      }
//      return wDivR2;
//   }
  public valarray getDivR2() {
    if (divR2 == null) {
      divR2 = new FuncVec(this.x, new FuncPowInt(1., -2));
    }
    return divR2;
  }
  public valarray getDivR() {
    if (divR == null) {
      divR = new FuncVec(this.x, new FuncPowInt(1., -1));
    }
    return divR;
  }
}
