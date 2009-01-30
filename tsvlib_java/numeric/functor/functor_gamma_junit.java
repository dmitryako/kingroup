package numeric.functor;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import netlib.math.complex.Complex;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 7:19:48 AM
 */
public class functor_gamma_junit extends TestCase {
  public static Test suite() {
    return new TestSuite(functor_gamma_junit.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testDot() {
    /*
    // p101 of Russian edition of Abramowitz & Stegun
    // NOTE!!! valid results are LOG values
    Data(complex(1.0, 0.2), complex(-0.032476292318, -0.112302222644))
    ,  Data(complex(1.2, 2.2), complex(-1.979067237432, 0.541922948431))
    // http://functions.wolfram.com/GammaBetaErf/Gamma/03/02/
    ,  Data(complex(0.5, 0.0), complex(log(sqrt(PI)), 0.0))
    ,  Data(complex(-3.0 / 2.0, 0.0), complex(log(4.0 / 3.0 * sqrt(PI)), 0.0))
    */
    FuncGamma G = new FuncGamma();
    Complex res = G.apply(new Complex(1.0, 0.2)).log();
    assertEquals(0, Math.abs(-0.032476292318 - res.real()), 2e-13);
    assertEquals(0, Math.abs(-0.112302222644 - res.imag()), 2e-13);
    res = G.apply_slow(new Complex(1.0, 0.2)).log();
    assertEquals(0, Math.abs(-0.032476292318 - res.real()), 2e-13);
    assertEquals(0, Math.abs(-0.112302222644 - res.imag()), 2e-13);
    res = G.apply(new Complex(1.2, 2.2)).log();
    assertEquals(0, Math.abs(-1.979067237432 - res.real()), 4e-13);
    assertEquals(0, Math.abs(0.541922948431 - res.imag()), 4e-13);
    res = G.apply_slow(new Complex(1.2, 2.2)).log();
    assertEquals(0, Math.abs(-1.979067237432 - res.real()), 1e-6);
    assertEquals(0, Math.abs(0.541922948431 - res.imag()), 2e-7);
    res = G.apply(new Complex(0.5, 0)).log();
    assertEquals(0, Math.abs(Math.log(Math.sqrt(Math.PI)) - res.real()), 1e-14);
    assertEquals(0, Math.abs(0 - res.imag()), 2e-13);
    res = G.apply_slow(new Complex(0.5, 0)).log();
    assertEquals(0, Math.abs(Math.log(Math.sqrt(Math.PI)) - res.real()), 3e-16);
    assertEquals(0, Math.abs(0 - res.imag()), 2e-16);
    res = G.apply(new Complex(-3. / 2, 0)).log();
    assertEquals(0, Math.abs(Math.log(4. / 3 * Math.sqrt(Math.PI)) - res.real()), 1e-14);
    assertEquals(0, Math.abs(0 - res.imag()), 2e-13);
    res = G.apply_slow(new Complex(-3. / 2, 0)).log();
    assertEquals(0, Math.abs(Math.log(4. / 3 * Math.sqrt(Math.PI)) - res.real()), 1e-11);
    assertEquals(0, Math.abs(0 - res.imag()), 2e-13);
  }
}
