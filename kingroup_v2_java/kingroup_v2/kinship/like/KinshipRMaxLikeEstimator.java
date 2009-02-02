package kingroup_v2.kinship.like;
import algorithm.Func2DFrame;
import algorithm.Func2DI;
import algorithm.HillClimber2D;
import algorithm.HillClimber2DRandomGradient;
import kingroup_v2.kinship.CalculatorI;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.utilx.RandomSeed;
import java.awt.geom.Point2D;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/04/2006, Time: 16:37:34
 */
public class KinshipRMaxLikeEstimator
  implements CalculatorI
{
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRMaxLikeEstimator.class.getName());
  protected final SysPop pop;
  protected HillClimber2D solver;

  public KinshipRMaxLikeEstimator(SysPop pop) {
    this.pop = pop;
    init();
  }

  protected void init()
  {
//    solver = new HillClimber2D();
//    solver = new HillClimber2DGradient();
    solver = new HillClimber2DRandomGradient();
    solver.setMin(-1, -1);
    solver.setMax(1, 1);
    solver.setEps(0.001);
    RandomSeed rand = RandomSeed.getInstance();
    Point2D.Double start = new Point2D.Double(rand.nextDouble(0, 0.05)
      , rand.nextDouble(0.06, 0.1));
    solver.setStart(start);
    solver.setMaxCount(10000);
  }

  public double calc(int ix, int iy) {
    boolean ok = solver.findMax(new ThisFunc2D(pop, ix, iy));
//    Func2DFrame view = new Func2DFrame(new ThisFunc2D(pop, ix, iy));
//    boolean ok = solver.findMax(view);
    if (!ok) {
      String error = "Unable to find max likelihood value for\n"
        +pop.toString(ix) + "\n"
        +pop.toString(iy) + "\n"
        +"from population\npop=\n" + pop
        +"with allele frequencies\nfreq=\n" + pop.getFreq();
      log.severe(error);

      Func2DFrame view = new Func2DFrame(new ThisFunc2D(pop, ix, iy));
      ok = solver.findMax(view);
      boolean DEBUG = solver.findMax(new ThisFunc2D(pop, ix, iy));

      throw new RuntimeException();
    }
    Point2D.Double p = solver.getResult();
    double res = 0.5 * (p.getX() + p.getY());
//    log.info("\npop = \n" + pop);
//    log.info("\nr = " + (float)res);
//    log.info("\nx = " + (float)p.getX());
//    log.info("\ny = " + (float)p.getY());
    return res;
  }

  public void setMin(double x, double y)
  {
    solver.setMin(x, y);
  }

  protected class ThisFunc2D implements Func2DI {
    private Kinship kinship;
    private final KinshipLikeMtrx mtrx;
    private KinshipIBD ibd;
    private final int ix;
    private final int iy;
    public ThisFunc2D(SysPop pop, int ix, int iy){
      mtrx = new KinshipLikeMtrx(pop);
      ibd = new KinshipIBD();
      this.kinship = new Kinship();
      kinship.setTreatHaploid(Kinship.HAPLOID_IGNORE);
      this.ix = ix;
      this.iy = iy;
    }

    public double calc(double x, double y)
    {
      ibd.setRm(x);
      ibd.setRp(y);
      KinshipLikeCalculator c = new KinshipLikeCalculator(pop, ibd, kinship);
      mtrx.setCalc(c);
      double f =  mtrx.calcLog(ix, iy);
//      f +=  mtrx.calcLog(iy, ix);
//      log.info("\nf("+(float)x+", "+(float)y+") = " + (float)f);
      return f;
    }
  }
}
