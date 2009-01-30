package javax.vecmathx.derivative;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import numeric.functor.FuncExp;
import numeric.functor.FuncPolynom;

import javax.iox.LOG;
import javax.vecmathx.function.FuncVec;
import javax.vecmathx.grid.StepGrid;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 6, 2004, Time: 1:33:12 PM
 */
public class DerivPts9JUnit extends TestCase {
  static double eps18 = 1e-18;
  public static Test suite() {
    return new TestSuite(DerivPts9JUnit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testDeriv() {
    int idx = 0;
    double first = 0;
    double last = 1;
    int size = 9;
    StepGrid x = new StepGrid(first, last, size);
    double[] coeff = {1};
    FuncVec f = new FuncVec(x, new FuncPolynom(coeff));
    FuncVec df = new DerivPts9(f);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    assertEquals(0, df.get(idx++), eps18);
    double[] c2 = {0, 0.5};
    f = new FuncVec(x, new FuncPolynom(c2));
    df = new DerivPts9(f);
    idx = 0;
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    assertEquals(0.5, df.get(idx++), eps18);
    double[] c3 = {0, 0, 0.5};
    f = new FuncVec(x, new FuncPolynom(c3));
    double[] c3_deriv = {0, 1};
    df = new FuncVec(x, new FuncPolynom(c3_deriv));
    FuncVec ndf = new DerivPts9(f); // numeric derivative
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
    ndf = new DerivPts9(f); // numeric derivative
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
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    assertEquals(df.get(idx), ndf.get(idx), eps18);
    idx++;
    f = new FuncVec(x, new FuncExp(2.));
    df = new FuncVec(x, new FuncExp(2.));
    df.scale(2.);
    ndf = new DerivPts9(f); // numeric derivative
    idx = 0;
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 1e-5);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 2e-6);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 4e-7);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 2e-7);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 2e-7);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 2e-7);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 4e-7);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 2e-6);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 2e-5);
    idx++;
    size = 11;
    x = new StepGrid(first, last, size);
    f = new FuncVec(x, new FuncExp(2.));
    df = new FuncVec(x, new FuncExp(2.));
    df.scale(2.);
    ndf = new DerivPts9(f); // numeric derivative
    FuncVec nd3 = new DerivPts3(f);
    FuncVec nd5 = new DerivPts5(f);
    LOG.report(this, "step**2 = " + (float) Math.pow(x.getStep(), 2));
    LOG.report(this, "step**3 = " + (float) Math.pow(x.getStep(), 3));
    LOG.report(this, "step**4 = " + (float) Math.pow(x.getStep(), 4));
    LOG.report(this, "step**5 = " + (float) Math.pow(x.getStep(), 5));
    idx = 0;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 4e-2);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 1e-3);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 2e-6);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 2e-2);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 3e-4);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 2e-7);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 2e-2);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 2e-4);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 5e-8);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 3e-2);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 2e-4);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 3e-8);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 3e-2);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 3e-4);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 2e-8);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 4e-2);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 4e-4);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 3e-8);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 5e-2);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 4e-4);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 3e-8);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 0.1);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 5e-4);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 4e-8);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 0.1);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 1e-3);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 8e-8);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 0.1);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 1e-3);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 3e-7);
    idx++;
    assertEquals(0., Math.abs(df.get(idx) - nd3.get(idx)), 0.2);
    assertEquals(0., Math.abs(df.get(idx) - nd5.get(idx)), 4e-3);
    assertEquals(0., Math.abs(df.get(idx) - ndf.get(idx)), 3e-6);
    idx++;
  }
}