package algorithm;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 9/05/2006, Time: 12:33:05
 */
public class HillClimber2DGradient extends HillClimber2D
{
  private static Logger log = Logger.getAnonymousLogger();
  private static final double INIT_N_STEPS = 40;

  public Point2D.Double findByGradient(Func2DI func, Point2D.Double startP)
  {
    double step = calcMaxGradientStep();
    double minStep = 1e-7;
    Point2D.Double tryDeriv = new Point2D.Double();
    Point2D.Double tryP = new Point2D.Double();
    tryP.setLocation(startP);
    Point2D.Double res = new Point2D.Double();
    res.setLocation(startP);
    for ( ; ; count += 1)
    {
      if (count >= maxCount) {
        log.info("\n step=" + step);
        log.info("\n res("+(float)res.getX()+", "+(float)res.getY()+") = " + (float)maxFunc);
        log.info("\n lastFunc("+(float)tryP.getX()+", "+(float)tryP.getY()+") = " + (float)lastFunc);
        log.info("\n lastDeriv2D("+(float)lastDeriv2D.getX()+", "+(float)lastDeriv2D.getY()+")");
        return null;
      }

      tryDeriv = calcDeriv(func, tryP, Math.min(eps, step));

      if ((tryDeriv.getX() == 0  && tryDeriv.getY() == 0)  // DISCONTINUITY
        || (lastFunc < maxFunc)) { // OVERSHOT
        step /= 2;
        if (step < minStep)  { // HANDLE exect find
//          log.info("\n res("+(float)res.getX()+", "+(float)res.getY()+") = " + (float)maxFunc);
          return res;
        }
        tryP = calcNextGradientPoint(lastDeriv2D, res, step);
        continue;
      }
      lastDeriv2D = tryDeriv;
      maxFunc = lastFunc;
      res.setLocation(tryP);
      tryP = calcNextGradientPoint(lastDeriv2D, tryP, step);
      if (res.distance(tryP) < 0.5*eps) {
        res.setLocation(tryP);
//        log.info("\n res("+(float)res.getX()+", "+(float)res.getY()+") = " + (float)maxFunc);
        return res;
      }
    }
  }

  public double calcMaxGradientStep()
  {
    return min.distance(max) / INIT_N_STEPS;
  }

  private Point2D.Double calcNextGradientPoint(Point2D.Double deriv, Point2D.Double p, double step)
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
