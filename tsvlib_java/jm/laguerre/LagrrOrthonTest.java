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
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 9:19:43 AM
 */
public class LagrrOrthonTest extends TestCase {
  private double eps_ = 1e-16;
  public static Test suite() {
    return new TestSuite(LagrrOrthonTest.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testLagrrOrthon() {
    double FIRST = 0;
    double LAST = 60;
    int GRID_SIZE = 201;
    StepGrid r = new StepGrid(FIRST, LAST, GRID_SIZE);
    BooleQuadr w = new BooleQuadr(r);
    FIRST = -5;
    LAST = 7; // exp(4.6)=99.5
    StepGrid x = new StepGrid(FIRST, LAST, GRID_SIZE);
    BooleQuadr wx = new BooleQuadr(x);
    FuncVec xToR = new TransLogRToR(x);
    LAST = Math.exp(FIRST); // e.g. FIRST = -5;
    int SMALL_R_SIZE = 5;
    StepGrid rS = new StepGrid(0, LAST, SMALL_R_SIZE); // small r
    BooleQuadr wS = new BooleQuadr(rS);
    int L = 0;
    int N = 4;
    int alpha = 2 * L + 2;
    double lambda = 0.7;
    FuncArr arr = new LagrrOrthon(r, N, alpha, lambda);
    FuncArr arS = new LagrrOrthon(rS, N, alpha, lambda);
    FuncArr arX = new LagrrOrthon(xToR, N, alpha, lambda);
    double res = FastLoop.dot(arr.get(0), arr.get(0), w);
    double resX = FastLoop.dot(arX.get(0), arX.get(0), wx, xToR);
    double resS = FastLoop.dot(arS.get(0), arS.get(0), wS);
    assertEquals(0, Math.abs(res - 1), 2e-6);
    assertEquals(0, Math.abs(resX - 1), 2e-8);//BETTER!
    assertEquals(0, Math.abs(resX + resS - 1), 3e-15);//BEST!!
    res = FastLoop.dot(arr.get(0), arr.get(1), w);
    resX = FastLoop.dot(arX.get(0), arX.get(1), wx, xToR);
    resS = FastLoop.dot(arS.get(0), arS.get(1), wS);
    assertEquals(0, Math.abs(res), 1e-5);
    assertEquals(0, Math.abs(resX), 4e-8);//BETTER!
    assertEquals(0, Math.abs(resX + resS), 2e-15);//BEST!
    res = FastLoop.dot(arr.get(0), arr.get(2), w);
    resX = FastLoop.dot(arX.get(0), arX.get(2), wx, xToR);
    resS = FastLoop.dot(arS.get(0), arS.get(2), wS);
    assertEquals(0, Math.abs(res), 5e-2);
    assertEquals(0, Math.abs(resX), 5e-8);//BETTER!
    assertEquals(0, Math.abs(resX + resS), 3e-14);//BEST!
    res = FastLoop.dot(arr.get(1), arr.get(1), w);
    resX = FastLoop.dot(arX.get(1), arX.get(1), wx, xToR);
    resS = FastLoop.dot(arS.get(1), arS.get(1), wS);
    assertEquals(0, Math.abs(res - 1), 2e-5);
    assertEquals(0, Math.abs(resX - 1), 6e-8);//BETTER!
    assertEquals(0, Math.abs(resX + resS - 1), 4e-14);//BEST!
    res = FastLoop.dot(arr.get(1), arr.get(2), w);
    resX = FastLoop.dot(arX.get(1), arX.get(2), wx, xToR);
    resS = FastLoop.dot(arS.get(1), arS.get(2), wS);
    assertEquals(0, Math.abs(res), 5e-5);
    assertEquals(0, Math.abs(resX), 1e-7);//BETTER!
    assertEquals(0, Math.abs(resX + resS), 5e-13);//BEST!
  }
}
