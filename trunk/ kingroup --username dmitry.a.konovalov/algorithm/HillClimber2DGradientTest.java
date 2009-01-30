package algorithm;
import junit.framework.TestCase;

import java.util.logging.Logger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/04/2006, Time: 13:43:02
 */
public class HillClimber2DGradientTest extends TestCase
{
  private static Logger log = Logger.getAnonymousLogger();
  public void testFindMax(){
    double eps = 1e-7;
//    HillClimber2D alg = new HillClimber2DGradientOLD();
//    HillClimber2D alg = new HillClimber2DGradient();
    HillClimber2D alg = new HillClimber2DRandomGradient();
//    HillClimber2D alg = new HillClimber2D();
    alg.setEps(eps);
    alg.setMin(-0.1, -1);
    alg.setMax(10, 3);

    Func2DI func = new Func2DI() {
      public double calc(double x, double y)    {
        return - (x - 1)*(x - 1) - (y - 2)*(y - 2);
      }
    };
//    alg.findMax(new Func2DFrame(func));
    alg.findMax(func);
    log.info("\ncount = " + alg.getCount());
    assertEquals(1, alg.getResult().getX(), eps);
    assertEquals(2, alg.getResult().getY(), eps);


    alg.setMin(-10, -10);
    alg.setMax(10, 10);
    alg.findMax(new Func2DI() {
      public double calc(double x, double y)
      {
        return - 2*(x - 1)*(x - 1.5) - (y - 1.5)*(y - 2);
      }
    });
    log.info("\ncount = " + alg.getCount());
    assertEquals(1.25, alg.getResult().getX(), eps);
    assertEquals(1.75, alg.getResult().getY(), eps);


    alg.setMin(-10, -5);
    alg.setMax(5, 5);
    func = new Func2DI() {
      public double calc(double x, double y)       {
//        double f = 1 - (x - 1)*(x - 1) - (y - 2)*(y - 2);
        double f = 1 - 2*(x - 1)*(x - 1.5) - (y - 1.5)*(y - 2);
        if (f < 0)
          return 0;
        return f;
      }
    };
//    alg.findMax(new Func2DFrame(func));
    alg.findMax(func);
    log.info("\ncount = " + alg.getCount());
    log.info("\nmax func = " + alg.getMaxFunc());
    log.info("\nfunc.calc = " + func.calc(1.25, 1.75));
    assertEquals(1.25, alg.getResult().getX(), eps);
    assertEquals(1.75, alg.getResult().getY(), eps);
  }
  public void testFindMax2(){
    double eps = 1e-7;
//    HillClimber2D alg = new HillClimber2DGradientOLD();
//    HillClimber2D alg = new HillClimber2DGradient();
    HillClimber2D alg = new HillClimber2DRandomGradient();
//    HillClimber2D alg = new HillClimber2D();
    alg.setEps(eps);

    alg.setMin(-10, -5);
    alg.setMax(5, 5);
    Func2DI func = new Func2DI() {
      public double calc(double x, double y)       {
//        double f = 1 - (x - 1)*(x - 1) - (y - 2)*(y - 2);
        double f = 1 - 2*(x - 1)*(x - 1.5) - (y - 1.5)*(y - 2);
        if (f < 0)
          return 0;
        return f;
      }
    };
//    alg.findMax(new Func2DFrame(func));
    alg.findMax(func);
    log.info("\ncount = " + alg.getCount());
    log.info("\nmax func = " + alg.getMaxFunc());
    log.info("\nfunc.calc = " + func.calc(1.25, 1.75));
    assertEquals(1.25, alg.getResult().getX(), eps);
    assertEquals(1.75, alg.getResult().getY(), eps);
  }
}
