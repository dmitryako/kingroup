package algorithm;
import javax.utilx.RandomSeed;
import java.awt.geom.Point2D;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/04/2006, Time: 12:58:43
 */
public class HillClimber2DGradientOLD  extends HillClimber2D
{
  private static final int TMP_MAX_COUNT = 1;
  private static final double INIT_N_STEPS = 40;

  public boolean findMax(final Func2DI func) {
    initState();
    if (eps <= 0) {
      result = new Point2D.Double(0, 0);
      throw new RuntimeException("HillClimber.findMax called when eps <= 0");
    }
    if (max.distance(min) < eps) {
      result = min;
      throw new RuntimeException("HillClimber.findMax called when max - min < eps");
    }

    double x = start.getX();
    if (Math.abs(start.getX() - min.getX()) < eps
      || Math.abs(start.getX() - max.getX()) < eps)
      x = 0.5 * (min.getX() + max.getX());

    double y = start.getY();
    if (Math.abs(start.getY() - min.getY()) < eps
      || Math.abs(start.getY() - max.getY()) < eps)
      y = 0.5 * (min.getY() + max.getY());

    start.setLocation(x, y);

    HillClimber1D cX = new HillClimber1D();
    cX.setEps(eps);
    cX.setMin(min.getX());
    cX.setMax(max.getX());
    cX.setStart(x);

    HillClimber1D cY = new HillClimber1D();
    cY.setEps(eps);
    cY.setMin(min.getY());
    cY.setMax(max.getY());
    cY.setStart(y);

    cX.setMaxCount(TMP_MAX_COUNT);
    cY.setMaxCount(TMP_MAX_COUNT);

    Point2D.Double xy = new Point2D.Double();
    xy.setLocation(start);

    Point2D.Double lastXY = new Point2D.Double();
    lastXY.setLocation(min);

    double step = min.distance(max) / INIT_N_STEPS;
    double dfx = 1;
    double dfy = 1;

    for (count = 2; lastXY.distance(xy) >= 0.5*eps; count += 2)
    {
      lastXY.setLocation(x, y);
      x += step * dfx;
      y += step * dfy;

      xy.setLocation(x, y);
      makeValid(xy);
      x = xy.getX();
      y = xy.getY();

      // calc gradient
      cX.setStart(x);
      cY.setStart(y);
      cX.findMax(new ThisFX(func, y));
      cY.findMax(new ThisFY(func, x));
      if (maxFunc < cY.getMaxFunc()) {
        maxFunc = cY.getMaxFunc();
        result.setLocation(x, y);
      }
      else {
        step /= 2;
        x = lastXY.getX();
        y = lastXY.getY();
        continue;
      }

      dfx = cX.getLastDeriv();
      dfy = cY.getLastDeriv();
      if (dfx == 0  && dfy == 0) {
        x = RandomSeed.getInstance().nextDouble(min.getX(), max.getX());
        y = RandomSeed.getInstance().nextDouble(min.getY(), max.getY());
        lastXY.setLocation(x + eps, y + eps); // make sure this loop runs at least one more time.
      }
      else {
        double df = Math.sqrt(dfx * dfx + dfy * dfy);
        dfx /= df;
        dfy /= df;
      }

      if (count >= maxCount)
        return false;
    }
    return true;
  }

}
