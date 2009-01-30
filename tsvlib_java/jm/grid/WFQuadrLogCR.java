package jm.grid;
import stlx.valarrayx.valarray;

import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/03/2005, Time: 18:01:14
 */
public class WFQuadrLogCR extends WFQuadrLogR {
  private valarray wCR2;
  private valarray wCR2DivR;
  private final TransLogCRToR logCRToR;
  public WFQuadrLogCR(StepGrid x) {
    super(x);
    logCRToR = new TransLogCRToR(x);
  }
  public valarray getWithCR2DivR() {
    if (wCR2DivR == null) {
      wCR2DivR = new valarray(logCRToR.getCR2DivR());
      wCR2DivR.mult(this);
    }
    return wCR2DivR;
  }
  public TransLogCRToR getLogCRToR() {
    return logCRToR;
  }
  public valarray getWithCR2() {
    if (wCR2 == null) {
      wCR2 = new valarray(logCRToR.getCR2());
      wCR2.mult(this);
    }
    return wCR2;
  }
  public valarray getNormWeights() {
    return getWithCR2();
  }
}
