package qsar.bench.qsar.MLR.irls;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2007, Time: 13:10:34
 */
public class HuberIrslNorm extends IrlsNorm
{
//  public HuberIrslNorm(double param) {
//    this.param = param;
  //  }
  public double calc(double e)
  {
    e = Math.abs(e);
    if (e < param)
      return 0.5 * e * e;
    return param * e - 0.5 * param * param;
  }
  public double calcW(double e)
  {
    e = Math.abs(e);
    if (e < param)
      return 1.;
    return param / e;
  }
}
