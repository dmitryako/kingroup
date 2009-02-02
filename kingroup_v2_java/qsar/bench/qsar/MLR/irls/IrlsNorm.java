package qsar.bench.qsar.MLR.irls;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2007, Time: 13:10:48
 */
public abstract class IrlsNorm
{
  protected double param;

  abstract public double calc(double e);
  public double calcW(double e) {return 0;}

  public double getParam()
  {
    return param;
  }

  public void setParam(double param)
  {
    this.param = param;
  }
}
