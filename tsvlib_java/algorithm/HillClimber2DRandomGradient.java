package algorithm;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.vec.Vec;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/05/2006, Time: 09:55:29
 */
public class HillClimber2DRandomGradient   extends HillClimber2DGradient
{
  private static Logger log = Logger.getAnonymousLogger();
  private static final int MAX_N_TRIALS = 100;

  public boolean findMax(final Func2DI func) {
    initState();
    start = findStartPoint(func);
    if (start == null)
      return false;
    result = findByGradient(func, start);
    if (result == null)
      return false;
    result = findByRandomWalk(func, result);
    return true;
  }
  public void makeValid(Point2D.Double p)
  {
    super.makeValid(p);
    double x = p.getX();       // ORDER SYMMETRIC pairs
    double y = p.getY();
    p.setLocation(Math.min(x, y), Math.max(x, y));
  }

  private Point2D.Double findByRandomWalk(Func2DI func, Point2D.Double startP)
  {
    LinkedList<Point2D.Double> path = new LinkedList<Point2D.Double>();
    Point2D.Double tryP = new Point2D.Double();
    tryP.setLocation(startP);
    Point2D.Double res = new Point2D.Double();
    res.setLocation(startP);
    path.addFirst(startP);
    RandomSeed rand = RandomSeed.getInstance();
    Point2D.Double stepP = new Point2D.Double();
    stepP.setLocation(eps, eps);
    int trial = 0;
    for (count = 0; ; count += 1)
    {
      trial++;
      if (count >= maxCount)
        return res;
      if (trial >= MAX_N_TRIALS)
        return res;
      lastFunc = func.calc(tryP.getX(), tryP.getY());
      if (maxFunc < lastFunc) {
//        log.info("\n new maxFunc("+(float)tryP.getX()+", "+(float)tryP.getY()+") = " + (float)lastFunc);
        trial = 0; // RESET trial counter!!!!
        maxFunc = lastFunc;
        res.setLocation(tryP);

        Point2D.Double saved = new Point2D.Double();
        saved.setLocation(res);
        path.addFirst(saved);

        recalcSteps(path, stepP);
      }
      tryP.setLocation(res.getX() + rand.nextDouble(-stepP.getX(), stepP.getX())
        , res.getY() + rand.nextDouble(-stepP.getY(), stepP.getY()));
      makeValid(tryP);
    }
  }

  private void recalcSteps(LinkedList<Point2D.Double> path, Point2D.Double stepP)
  {
    int N_AVR = 4;
    if (path.size() < N_AVR + 1)
      return;
    if (path.size() > N_AVR + 1)
      path.removeLast();
    double[] arrX = new double[N_AVR];
    double[] arrY = new double[N_AVR];
    Iterator<Point2D.Double> it = path.iterator();
    for (int i = 0; i < N_AVR + 1; i++) {
      Point2D.Double p = it.next();
      if (i < N_AVR) {
        arrX[i] = p.getX();
        arrY[i] = p.getY();
      }
      if (i > 0) {
        arrX[i-1] -= p.getX(); // a[0] = x[0] - x[1], etc
        arrY[i-1] -= p.getY();
      }
    }
    double avrX = Vec.mean(arrX);
    double avrY = Vec.mean(arrY);
    stepP.setLocation(3. * avrX, 3. * avrY); // NOTE *2!!! otherwise it can only decrease
//    log.info("\n steps("+(float)stepP.getX()+", "+(float)stepP.getY()+")");
  }


  private Point2D.Double calcNextRandomPoint(Point2D.Double p, double step)
  {
    RandomSeed rand = RandomSeed.getInstance();
    Point2D.Double res = new Point2D.Double(p.getX() + rand.nextDouble(-step, step)
      , p.getY() + rand.nextDouble(-step, step));
    makeValid(res);
    return res;
  }

  private Point2D.Double calcNextPathPoint(Point2D.Double deriv, Point2D.Double p, double step)
  {
    double x = p.getX();
    double y = p.getY();
    double dfx = deriv.getX();
    double dfy = deriv.getY();
    double df = Math.sqrt(dfx * dfx + dfy * dfy);
    dfx /= df;
    dfy /= df;
    Point2D.Double res = new Point2D.Double(x + step * dfx, y + step * dfy);
    makeValid(res);
    return res;
  }


}


