package jm.laguerre;
import jm.grid.TransLogCRToR;
import jm.grid.TransLogRToR;
import jm.grid.WFQuadrLogCR;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.FastLoop;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
import javax.vecmathx.integration.BooleQuadr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/03/2005, Time: 17:04:50
 */
public class LaguerreLogCRJUnit extends TestCase {
  private double eps_ = 1e-16;
  public static Test suite() {
    return new TestSuite(LaguerreLogRJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testL() {
    double FIRST = -3;
    int NUM_STEPS = 261;
    double LAST = 5;
    StepGrid x = new StepGrid(FIRST, LAST, NUM_STEPS);
    WFQuadrLogCR wCR = new WFQuadrLogCR(x);
    TransLogCRToR xToR = wCR.getLogCRToR();
    int L = 1;
    double Zeff = 1.6875;// from p445 of Clementi Roetti, Atomic Data 14, 177 (1974)
    int alpha = 2 * L + 2;
    double lambda = 2. * Zeff;
    int N = 12;
    FuncArr arr = new LagrrLogCR(xToR, N, alpha, lambda);
    double res = wCR.calcMaxOrthonErr(arr);
    assertEquals(0, res, 3e-11);
  }
  public void testLaguerreArray() {
    double FIRST = 0;
    double LAST = 60;
    int GRID_SIZE = 201;
    FIRST = -5;
    LAST = 7; // exp(4.6)=99.5
    StepGrid x = new StepGrid(FIRST, LAST, GRID_SIZE);
    BooleQuadr wx = new BooleQuadr(x);
    TransLogRToR xToR = new TransLogRToR(x);
    FuncVec xToR2 = xToR.getMapLogRToR2();
    TransLogCRToR cToR = new TransLogCRToR(x);
    valarray xToY2 = cToR.getCR2();
    LAST = Math.exp(FIRST); // e.g. FIRST = -5;
    int SMALL_R_SIZE = 5;
    StepGrid rS = new StepGrid(0, LAST, SMALL_R_SIZE); // small r
    BooleQuadr wS = new BooleQuadr(rS);
    int L = 0;
    int N = 4;
    int alpha = 2 * L + 2;
    double lambda = 0.7;
    FuncArr arS = new LagrrOrthon(rS, N, alpha, lambda);
    FuncArr arX = new LaguerreLogR(xToR, N, alpha, lambda);
    FuncArr arC = new LagrrLogCR(cToR, N, alpha, lambda);
    double resX = FastLoop.dot(arX.get(0), arX.get(0), wx, xToR2);
    double resS = FastLoop.dot(arS.get(0), arS.get(0), wS);
    double resC = FastLoop.dot(arC.get(0), arC.get(0), wx, xToY2);
    assertEquals(0, Math.abs(resX - 1), 2e-8);
    assertEquals(0, Math.abs(resX + resS - 1), 3e-15);
    assertEquals(0, Math.abs(resC - 1), 2e-15);//EVEN BETTER!!!
    resX = FastLoop.dot(arX.get(0), arX.get(1), wx, xToR2);
    resS = FastLoop.dot(arS.get(0), arS.get(1), wS);
    resC = FastLoop.dot(arC.get(0), arC.get(1), wx, xToY2);
    assertEquals(0, Math.abs(resX), 4e-8);
    assertEquals(0, Math.abs(resX + resS), 2e-15);
    assertEquals(0, Math.abs(resC), 2e-15);//BETTER! same precision, less points
    resX = FastLoop.dot(arX.get(0), arX.get(2), wx, xToR2);
    resS = FastLoop.dot(arS.get(0), arS.get(2), wS);
    resC = FastLoop.dot(arC.get(0), arC.get(2), wx, xToY2);
    assertEquals(0, Math.abs(resX), 5e-8);
    assertEquals(0, Math.abs(resX + resS), 3e-14);
    assertEquals(0, Math.abs(resC), 3e-14);//BETTER!   same precision, less points
    resX = FastLoop.dot(arX.get(1), arX.get(1), wx, xToR2);
    resS = FastLoop.dot(arS.get(1), arS.get(1), wS);
    resC = FastLoop.dot(arC.get(1), arC.get(1), wx, xToY2);
    assertEquals(0, Math.abs(resX - 1), 6e-8);
    assertEquals(0, Math.abs(resX + resS - 1), 4e-14);
    assertEquals(0, Math.abs(resC - 1), 4e-14);//BETTER!  same precision, less points
    resX = FastLoop.dot(arX.get(1), arX.get(2), wx, xToR2);
    resS = FastLoop.dot(arS.get(1), arS.get(2), wS);
    resC = FastLoop.dot(arC.get(1), arC.get(2), wx, xToY2);
    assertEquals(0, Math.abs(resX), 1e-7);
    assertEquals(0, Math.abs(resX + resS), 5e-13);
    assertEquals(0, Math.abs(resC), 5e-13);//BETTER!  same precision, less points
  }
}