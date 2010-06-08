package jm.laguerre;
import jm.grid.TransLogRToR;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import stlx.FastLoop;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.function.FuncArr;
import javax.vecmathx.grid.StepGrid;
import javax.vecmathx.integration.BooleQuadr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/03/2005, Time: 16:44:51
 */
public class LaguerreLogRJUnit extends TestCase {
  private double eps_ = 1e-16;
  public static Test suite() {
    return new TestSuite(LaguerreLogRJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
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
    LAST = Math.exp(FIRST); // e.g. FIRST = -5;
    int SMALL_R_SIZE = 5;
    StepGrid rS = new StepGrid(0, LAST, SMALL_R_SIZE); // small r
    BooleQuadr wS = new BooleQuadr(rS);
    int L = 0;
    int N = 4;
    int alpha = 2 * L + 2;
    double lambda = 0.7;
    FuncArr arS = new LagrrOrth(rS, N, alpha, lambda);
    FuncArr arX = new LagrrLr(xToR, N, alpha, lambda);
    double resX = FastLoop.dot(arX.get(0), arX.get(0), wx, xToR2);
    double resS = FastLoop.dot(arS.get(0), arS.get(0), wS);
    assertEquals(0, Math.abs(resX - 1), 2e-8);//BETTER!
    assertEquals(0, Math.abs(resX + resS - 1), 3e-15);//BEST!!
    resX = FastLoop.dot(arX.get(0), arX.get(1), wx, xToR2);
    resS = FastLoop.dot(arS.get(0), arS.get(1), wS);
    assertEquals(0, Math.abs(resX), 4e-8);//BETTER!
    assertEquals(0, Math.abs(resX + resS), 2e-15);//BEST!
    resX = FastLoop.dot(arX.get(0), arX.get(2), wx, xToR2);
    resS = FastLoop.dot(arS.get(0), arS.get(2), wS);
    assertEquals(0, Math.abs(resX), 5e-8);//BETTER!
    assertEquals(0, Math.abs(resX + resS), 3e-14);//BEST!
    resX = FastLoop.dot(arX.get(1), arX.get(1), wx, xToR2);
    resS = FastLoop.dot(arS.get(1), arS.get(1), wS);
    assertEquals(0, Math.abs(resX - 1), 6e-8);//BETTER!
    assertEquals(0, Math.abs(resX + resS - 1), 4e-14);//BEST!
    resX = FastLoop.dot(arX.get(1), arX.get(2), wx, xToR2);
    resS = FastLoop.dot(arS.get(1), arS.get(2), wS);
    assertEquals(0, Math.abs(resX), 1e-7);//BETTER!
    assertEquals(0, Math.abs(resX + resS), 5e-13);//BEST!
  }
}
