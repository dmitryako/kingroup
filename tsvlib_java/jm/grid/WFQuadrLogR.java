package jm.grid;
import stlx.valarrayx.valarray;

import javax.vecmathx.grid.StepGrid;
import javax.vecmathx.integration.BooleQuadr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/04/2005, Time: 11:46:01
 */
public class WFQuadrLogR extends BooleQuadr {
  private valarray wR2;
  private final TransLogRToR logRToR;
  public WFQuadrLogR(StepGrid grid) {
    super(grid);
    logRToR = new TransLogRToR(x);
  }
  public TransLogRToR getLogRToR() {
    return logRToR;
  }
  public valarray getWithR2() {
    if (wR2 == null) {
      wR2 = new valarray(logRToR.getR2());
      wR2.mult(this);
    }
    return wR2;
  }
  public valarray getNormWeights() {
    return getWithR2();
  }
}
