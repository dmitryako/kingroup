package javax.vecmathx.derivative;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import numeric.functor.FuncPolynom;

import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 4:43:17 PM
 */
public class DerivPts5JUnit extends TestCase {
  static double eps18 = 1e-18;
  public static Test suite() {
    return new TestSuite(DerivPts5JUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testDeriv() {
    int idx = 0;
    double first = 0;
    double last = 1;
    int size = 5;
    StepGrid x = new StepGrid(first, last, size);
    double[] coeff = {1};
    FuncVec f = new FuncVec(x, new FuncPolynom(coeff));
    FuncVec df = new DerivPts5(f);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    double[] c2 = {0, 0.5};
    f = new FuncVec(x, new FuncPolynom(c2));
    df = new DerivPts5(f);
    idx = 0;
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    double[] c3 = {0, 0, 0.5};
    f = new FuncVec(x, new FuncPolynom(c3));
    double[] c3_deriv = {0, 1};
    df = new FuncVec(x, new FuncPolynom(c3_deriv));
    FuncVec ndf = new DerivPts5(f); // numeric derivative
    idx = 0;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    double[] c4 = {0.5, 1, 2, 3, 4};
    f = new FuncVec(x, new FuncPolynom(c4));
    double[] c4_deriv = {1, 4, 9, 16};
    df = new FuncVec(x, new FuncPolynom(c4_deriv));
    ndf = new DerivPts5(f); // numeric derivative
    idx = 0;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
  }
}
