package kingroup_v2.kinship.like;
import algorithm.Func2DFrame;
import algorithm.HillClimber2DRandomGradient;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.utilx.RandomSeed;
import java.awt.geom.Point2D;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 9/05/2006, Time: 12:29:39
 */
public class KinshipRMaxLikeEstimatorDEBUG extends KinshipRMaxLikeEstimator
{
  public KinshipRMaxLikeEstimatorDEBUG(SysPop pop)
  {
    super(pop);
  }
  protected void init()
  {
//    solver = new HillClimber2D();
//    solver = new HillClimber2DGradient();
    solver = new HillClimber2DRandomGradient();
//    solver.setMin(-1, -1);
    solver.setMin(0, 0);
    solver.setMax(1, 1);
    solver.setEps(0.001);
    RandomSeed rand = RandomSeed.getInstance();
    Point2D.Double start = new Point2D.Double(rand.nextDouble(0, 0.1)
      , rand.nextDouble(0, 0.1));
    solver.setStart(start);
    solver.setMaxCount(10000);
  }

  public double calc(int ix, int iy) {
    Func2DFrame view = new Func2DFrame(new ThisFunc2D(pop, ix, iy));
    boolean ok = solver.findMax(view);
    return super.calc(ix, iy);
  }
}
