package javax.mathx;
import junit.framework.TestCase;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 31/10/2005, Time: 10:40:41
 */
public class MathXTest extends TestCase
{
  public void testFactLn() {
    assertEquals(Math.log(6), MathX.factLn(3), 1e-10);

    assertEquals(Math.log(6), MathX.factLn2(3, 2), 1e-10);
    assertEquals(Math.log(3), MathX.factLn2(3, 1), 1e-10);

    assertEquals(Math.log(1), MathX.factLn3(0, 1), 1e-10);
    assertEquals(Math.log(1), MathX.factLn3(3, 0), 1e-10);
    assertEquals(Math.log(1), MathX.factLn3(3, 1), 1e-10);
    assertEquals(Math.log(1), MathX.factLn3(4, 1), 1e-10);
    assertEquals(Math.log(3./4), MathX.factLn3(4, 2), 1e-10);

    assertEquals(3, MathX.binomialCoeff(3, 1), 1e-10);
    assertEquals(3, MathX.binomialCoeff(3, 2), 1e-10);
    assertEquals(6, MathX.binomialCoeff(4, 2), 1e-10);
  }
}
