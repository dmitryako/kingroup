package algorithm;
import numeric.functor.Func;

import javax.utilx.RandomSeed;
import java.util.logging.Logger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/04/2006, Time: 15:09:40
 */
public class HillClimber1D  extends HillClimber
{
  private static final Logger log = Logger.getAnonymousLogger();
  private double min;
  private double start;
  private double max;

  private double result;
  private double L;
  private double R;

  public HillClimber1D(){
    initOpt();
    initState();
  }
  public boolean findMax(Func func) {
    initState();
    if (eps <= 0) {
      result = 0;
      throw new RuntimeException("HillClimber.findMax called when eps <= 0");
    }
    if (Math.abs(max - min) < eps) {
      result = min;
      throw new RuntimeException("HillClimber.findMax called when |max - min| < eps");
    }
    if (Math.abs(start - min) < eps
      || Math.abs(start - max) < eps)
      start = 0.5 * (min + max);
    L = min;
    R = max;
    double x = start;
    double lastX = min;

    for (count = 1; Math.abs(lastX - x) >= 0.5*eps; count++) {
//      log.info("\n[L="+(float)L+", R="+(float)R+"]");
      lastX = x;
      lastDeriv = deriv(func, x);
//      log.info("\nfunc("+(float)x+") = " + (float)lastFunc);
      if (maxFunc < lastFunc) {
        maxFunc = lastFunc;
        result = x;
//        log.info("\nmaxFunc("+(float)x+") = " + (float)maxFunc);
      }

      if (lastDeriv == 0) {
        x = RandomSeed.getInstance().nextDouble(L, R);
        lastX = x + eps; // make sure this loop runs at least one more time.
      }
      else {
        if (lastDeriv > 0)
          L = x;
        else
          R = x;
//        x = RandomSeed.getInstance().nextDouble(L + 0.5*eps, R - 0.5*eps);
        x = 0.5 * (L + R);
      }
      if (count >= maxCount)
        return false;
    }
    return true;
  }

  private double deriv(Func f, double x)
  {
    lastFunc = f.calc(x);
    return f.calc(x + getEps()) - lastFunc;
  }

  public void initOpt()
  {
    super.initOpt();
    min = 0;
    max = 1;
    start = min;
  }

  public void initState()
  {
    super.initState();
    result = 0;
    L = min;
    R = max;
  }

  public double getResult()  {    return result;  }
  public void setMin(double min)  {    this.min = min;  }
  public double getMin()  {    return min;  }
  public void setMax(double max)  {       this.max = max;     }
  public double getMax()  {    return max;  }
  public double getStart()  {    return start;  }
  public void setStart(double start)  {    this.start = start;  }

  public double getL()
  {
    return L;
  }

  public double getR()
  {
    return R;
  }
}
