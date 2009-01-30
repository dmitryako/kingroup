package algorithm;
import numeric.functor.Func;

import javax.utilx.RandomSeed;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/04/2006, Time: 14:52:50
 */
public class HillClimber2D extends HillClimber
{
  private static Logger log = Logger.getAnonymousLogger();
  protected Point2D.Double min;
  protected  Point2D.Double start;
  protected Point2D.Double max;
  protected Point2D.Double result;
  protected Point2D.Double lastDeriv2D;

  public HillClimber2D(){
    initOpt();
    initState();
  }

  public void makeValid(Point2D.Double p)
  {
    double x = p.getX();
    double y = p.getY();
    if (x < min.getX())
      x = min.getX();
    if (y < min.getY())
      y = min.getY();

    if (x > max.getX())
      x = max.getX();
    if (y > max.getY())
      y = max.getY();

    p.setLocation(x, y);
  }
  public Point2D.Double calcDeriv(Func2DI func, Point2D.Double p, double step)
  {
    // FIRST DERIVs
    double dx = step;
    double dy = step;
    double x = p.getX();
    double y = p.getY();
    double x2 = x + dx;
    double y2 = y + dy;
    if (x2 > max.getX()) {
      dx *= (-1);
      x2 = x + dx;
    }
    if (y2 > max.getY()) {
      dy *= (-1);
      y2 = y + dy;
    }
    lastFunc = func.calc(x, y);
//    log.info("\nf("+(float)x+", "+(float)y+") = " + (float)lastFunc);
    double fx2 = func.calc(x2, y);
    double fy2 = func.calc(x, y2);
    return new Point2D.Double((fx2 - lastFunc) / dx, (fy2 - lastFunc) / dy);
  }

  public Point2D.Double findStartPoint(Func2DI func)
  {
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
    Point2D.Double tryP = new Point2D.Double();
    tryP.setLocation(start);
    for ( ; ; count += 1)
    {
      if (count >= maxCount) {
        return null;
      }
      lastDeriv2D = calcDeriv(func, tryP, eps);
      if (lastDeriv2D.getX() != 0  || lastDeriv2D.getY() != 0) {
        maxFunc = lastFunc;
        return tryP;
      }

      x = RandomSeed.getInstance().nextDouble(min.getX(), max.getX());
      y = RandomSeed.getInstance().nextDouble(min.getY(), max.getY());
      tryP.setLocation(x, y);
    }
  }

  public boolean findMax(final Func2DI func) {
    initState();
    start = findStartPoint(func);

    HillClimber1D cX = new HillClimber1D();
    cX.setEps(eps);
    cX.setMin(min.getX());
    cX.setMax(max.getX());
    cX.setStart(start.getX());

    HillClimber1D cY = new HillClimber1D();
    cY.setEps(eps);
    cY.setMin(min.getY());
    cY.setMax(max.getY());
    cY.setStart(start.getY());

    double nX = calcMaxCount(eps, max.getX(), min.getX());
    cX.setMaxCount((int)nX);
    double nY = calcMaxCount(eps, max.getY(), min.getY());
    cY.setMaxCount((int)nY);

    Point2D.Double xy = new Point2D.Double();
    xy.setLocation(start);

    Point2D.Double lastXY = new Point2D.Double();
    lastXY.setLocation(min);

    double x = start.getX();
    double y = start.getY();
    for (int loopCount = 1; lastXY.distance(xy) >= 0.5*eps; loopCount++) {
      lastXY.setLocation(x, y);

      cX.setMaxCount(cX.getMaxCount() + 2 * loopCount);
      cX.findMax(new ThisFX(func, y));
      x = cX.getResult();
      count += cX.getCount();

      cY.setMaxCount(cY.getMaxCount() + 2 * loopCount);
      cY.findMax(new ThisFY(func, x));
      y = cY.getResult();
      count += cY.getCount();

      xy.setLocation(x, y);
      lastFunc = cY.getLastFunc();
      lastDeriv = cY.getLastDeriv();
      if (maxFunc < cY.getMaxFunc()) {
        maxFunc = cY.getMaxFunc();
        result.setLocation(x, y);
      }

      if (lastDeriv == 0) {
//        x = RandomSeed.getInstance().nextDouble(cX.getL(), cX.getR());
//        y = RandomSeed.getInstance().nextDouble(cY.getL(), cY.getR());
        x = RandomSeed.getInstance().nextDouble(min.getX(), max.getX());
        y = RandomSeed.getInstance().nextDouble(min.getY(), max.getY());
        lastXY.setLocation(x + eps, y + eps); // make sure this loop runs at least one more time.
      }
      if (count >= maxCount)
        return false;
    }
    return true;
  }

  protected class ThisFX implements Func {
    private Func2DI func2D;
    private double y;
    public ThisFX(Func2DI func2D, double y) {
      this.func2D = func2D;
      this.y = y;
    }
    public double calc(double x)    {
      return func2D.calc(x, y);
    }
  }
  protected class ThisFY implements Func {
    private Func2DI func2D;
    private double x;
    public ThisFY(Func2DI func2D, double x) {
      this.func2D = func2D;
      this.x = x;
    }
    public double calc(double y)    {
      return func2D.calc(x, y);
    }
  }
  private double calcMaxCount(double eps, double minV, double maxV) {
    return Math.log(eps / (minV - maxV)) / Math.log(0.5);
  }

  public void initOpt()
  {
    super.initOpt();
    min = new Point2D.Double(0, 0);
    max = new Point2D.Double(1, 1);
    start = new Point2D.Double();
    start.setLocation(min);
  }

  public void initState()
  {
    super.initState();
    result = new Point2D.Double(0, 0);
  }

  public Point2D.Double getResult()  {    return result;  }
  public void setMin(Point2D.Double min)  {         this.min = min;   }
  public void setMin(double x, double y)  {         this.min.setLocation(x, y);   }
  public Point2D.Double getMin()     {    return min;   }
  public void setMax(Point2D.Double max)    {       this.max = max;   }
  public void setMax(double x, double y)  {         this.max.setLocation(x, y);   }
  public Point2D.Double getMax()    {        return max;    }
  public Point2D.Double getStart()    {         return start;    }
  public void setStart(Point2D.Double start)   {       this.start = start;  }
}
