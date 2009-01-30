package javax.vecmathx.interpolation;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import numeric.functor.FuncPolynom;
import numeric.functor.FuncPow;
import numeric.functor.FuncPow2;
import stlx.valarrayx.valarray;

import javax.vecmathx.function.FuncVec;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/03/2005, Time: 17:38:37
 */
public class PolynomInterpolJUnit extends TestCase {
  public static Test suite() {
    return new TestSuite(PolynomInterpolJUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testCalcPowerSLOW() {
    double[] x = {0., 0.1, 0.3};
    valarray r = new valarray(x);
    double[] c = {0., 1};
    FuncVec f = new FuncVec(r, new FuncPolynom(c));
    double b = PolynomInterpol.calcPowerSLOW(f, 0);
    assertEquals(c[1], b, 1e-20);
    f = new FuncVec(r, new FuncPow2());
    b = PolynomInterpol.calcPowerSLOW(f, 0);
    assertEquals(0, Math.abs(b - 2), 1e-20);
    double a = 0.5;
    double t = 3.1;
    f = new FuncVec(r, new FuncPow(a, t));
    b = PolynomInterpol.calcPowerSLOW(f, 0);
    assertEquals(0, Math.abs(b - t), 1e-200);
  }
}