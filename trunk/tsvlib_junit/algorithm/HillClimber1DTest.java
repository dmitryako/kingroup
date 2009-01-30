package algorithm;
import junit.framework.TestCase;
import numeric.functor.FuncPolynom;

import java.util.logging.Logger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/04/2006, Time: 15:10:27
 */
public class HillClimber1DTest extends TestCase
{
  private static Logger log = Logger.getAnonymousLogger();
  public void testFindMax(){
    double eps = 1e-7;
    HillClimber1D alg = new HillClimber1D();
    alg.setEps(eps);
    alg.setMin(-0.1);
    alg.setMax(10);

    alg.findMax(new FuncPolynom(new double[] {0, -1}));
    log.info("\ncount = " + alg.getCount());
    assertEquals(-0.1, alg.getResult(), eps);

    alg.findMax(new FuncPolynom(new double[] {0, 0, -1}));
    log.info("\ncount = " + alg.getCount());
    assertEquals(0, alg.getResult(), eps);

    double c = 100;
    double b = 2;
    double a = -1;
    alg.findMax(new FuncPolynom(new double[] {c, b, a}));
    log.info("\ncount = " + alg.getCount());
    assertEquals(-0.5*b/a, alg.getResult(), eps);

    c = 0;
    b = 10;
    alg.setMin(-1000);
    alg.setStart(-100);
    alg.findMax(new FuncPolynom(new double[] {c, b, a}) {
      public double calc(double x)
      {
        double f = super.calc(x);
        if (f < 0)
          return 0;
        return f;
      }
    });
    log.info("\ncount = " + alg.getCount());
    assertEquals(-0.5*b/a, alg.getResult(), eps);
  }
}
