package jm.grid;
import stlx.valarrayx.valarray;

import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/03/2005, Time: 18:01:14
 */
public class WFQuadrLcr extends WFQuadrLogR {
  private valarray wCR2;
  private valarray wCR2DivR;
  private final TransLcrToR logCRToR;
  public WFQuadrLcr(StepGrid x) {
    super(x);
    logCRToR = new TransLcrToR(x);
  }

  public valarray getWithCR2DivR() {
    if (wCR2DivR == null) {
      wCR2DivR = new valarray(logCRToR.getCR2DivR());
      wCR2DivR.mult(this);
    }
    return wCR2DivR;
  }
  public TransLcrToR getLogCRToR() {
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
