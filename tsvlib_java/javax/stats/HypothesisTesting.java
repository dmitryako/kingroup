package javax.stats;
import javax.utilx.arrays.ArraysX;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2006, Time: 16:54:34
 */
public class HypothesisTesting
{
  public static float calcGETypeI(double v, double[] distr) {
    int i = ArraysX.binarySearchGE(distr, v);
    if (i == ArraysX.NOT_FOUND)
      i = distr.length;
    return (float) (distr.length - i) / distr.length;
  }

}
