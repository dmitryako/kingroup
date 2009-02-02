package qsar.bench.qsar.MLR.irls;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2007, Time: 13:16:48
 */
public class NormL1  extends IrlsNorm
{
  public double calc(double e)
  {
    return Math.abs(e);
  }
}

