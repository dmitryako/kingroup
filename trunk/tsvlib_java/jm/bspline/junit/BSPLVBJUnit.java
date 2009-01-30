package jm.bspline.junit;
import jm.bspline.BSPLVB;
import jm.bspline.BSplineArr;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import numeric.functor.FuncPolynom;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/04/2005, Time: 11:02:32
 */
public class BSPLVBJUnit extends TestCase {
  private double eps_ = 1e-16;
  public static Test suite() {
    return new TestSuite(BSPLVBJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testCalc() {
    double LAST = 5;
    int k = 3;
    StepGrid knots = new StepGrid(0., LAST, 6);
    double[] t = valarray.asArray(BSplineArr.makeTrueKnots(knots, k));
    int jhigh = k;
    int index = 1;
    double x = 2.2;
    int left = 4;//0,0,0,1,2
    double[] biatx = new double[jhigh];
    BSPLVB.calc(t, jhigh, index, x, left, biatx);
    double[] B3_0 = {0, 0, 0.5}; // from p1821 of Bachau etal (2001) RepProgPhys 64, p1815
    double[] B3_1 = {-1.5, 3, 1};
    double[] B3_2 = {4.5, -3, 0.5};
    double[] arrX = {0};
    arrX[0] = x;
    valarray valX = new valarray(arrX);
    FuncVec f = new FuncVec(valX, new FuncPolynom(B3_2));
    assertEquals(f.get(0), biatx[1], eps_);
    assertEquals(f.get(0), biatx[2], eps_);
    assertEquals(f.get(0), biatx[0], eps_);
//      assertEquals(1., arr.getLine(0).getLine(4), eps_);
//
////      arr = new BSplineArr(grid, 2, 0, 1);
////      assertEquals(0.5, arr.getLine(1).getLine(2), eps_);
////      assertEquals(0.5, a12.getLine(1).getLine(2), eps_);
////      assertEquals(0., arr.getLine(1
  }
}