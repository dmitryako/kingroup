package algorithm;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/04/2006, Time: 11:38:07
 */
public class HillClimber
{
  protected double eps;
  protected int maxCount;

  protected int count;
  protected double lastFunc;
  protected double lastDeriv;
  protected double maxFunc;

  public HillClimber(){
    initOpt();
    initState();
  }
  public void initOpt()
  {
    eps = 1e-8;
    maxCount = 10000;
  }

  public void initState()
  {
    count = 0;
    maxFunc = -Double.MAX_VALUE;
  }

  public double getMaxFunc()
  {
    return maxFunc;
  }

  public void setMaxFunc(double maxFunc)
  {
    this.maxFunc = maxFunc;
  }

  public double getLastDeriv()
  {
    return lastDeriv;
  }

  public void setLastDeriv(double lastDeriv)
  {
    this.lastDeriv = lastDeriv;
  }

  public double getLastFunc()
  {
    return lastFunc;
  }

  public void setLastFunc(double lastFunc)
  {
    this.lastFunc = lastFunc;
  }

  public int getCount()
  {
    return count;
  }

  public void setCount(int count)
  {
    this.count = count;
  }

  public int getMaxCount()
  {
    return maxCount;
  }

  public void setMaxCount(int maxCount)
  {
    this.maxCount = maxCount;
  }

  public double getEps()
  {
    return eps;
  }

  public void setEps(double eps)
  {
    this.eps = eps;
  }
}
