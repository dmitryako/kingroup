package qsar.bench.qsar.MLR.irls;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2007, Time: 13:15:54
 */
public class NormL2 extends IrlsNorm
{
  public double calc(double e)
  {
    return e * e;
  }
}

