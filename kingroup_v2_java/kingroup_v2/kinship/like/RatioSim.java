package kingroup_v2.kinship.like;
import kingroup.genetics.Like;

import javax.stats.HypothesisTesting;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/03/2006, Time: 16:27:22
 */
public class RatioSim  {
//  private static ProjectLogger log = ProjectLogger.getLogger(RatioSim.class.getName());
  protected double[] rLogs;
  public double[] getLogs() {return rLogs;}

  public double[] calcSigLevelLogs(float[] sigLevels) {
    double[] res = new double[sigLevels.length];
    for (int i = 0; i < res.length; i++) {
      res[i] = calcSigLevelLog(sigLevels[i]);
    }
    return res;
  }
  // Type I error
  // DEF: Rejection of the null hypothesis when it is true called a TYPE I(one) ERROR
  // The probability of committing a type I error, also called the level of significance
  //
  // RETURN: sl - significance level
  // e.g. p=0.05 will produce sl < {r}, 5% will lay above sl
  // NOTE "<" is used!!!!!!!!!!!!!!!!!!!********************************
  public double calcSigLevelLog(float pValue) {
    if (pValue > 1
      || pValue < 0
      || rLogs == null
      || rLogs.length == 0)
      return Like.MIN_LOG;

    // significance level p=0.05 corresponds to 0.95
    double d = excludeBelowPercent(1.0 - pValue, rLogs);
    return d;
  }
  // Type I error
  // DEF: Rejection of the null hypothesis when it is true called a TYPE I(one) ERROR
  // The probability of committing a type I error, also called the level of significance
  public float calcTypeI(double rLog) {
    if (rLog == Like.MIN_LOG)
      return 1.0f;
    if (rLog == Like.MAX_LOG)
      return 0;
    if (rLogs == null || rLogs.length == 0)
      return 0;
    return HypothesisTesting.calcGETypeI(rLog, rLogs);
//    int i = ArraysX.binarySearchGE(rLogs, rLog);
//    if (i == ArraysX.NOT_FOUND)
//      i = rLogs.length;
//    return (float) (rLogs.length - i) / rLogs.length;
  }
  
  // PRECOND: 1. array of at least 1 element
  //          2. array is sorted in acsending order
  //          3. v belongs to [0,1]
  // RETURNS: the point of exclusion
  //  e.g. <= 95% of all {r}
  // NOTE "<=" is used!!!!!!!!!!!!!!!!!!!!!!!!********************************
  public double excludeBelowPercent(double v, double[] a) {
    // Examples:
    // v = 1.0 (sl=0.0)
    //   means exclude 100%, must return LAST element in the array
    //   idxF = 1.0 * a.length - 1 ;
    //   idx = a.length - 1 ; // LAST
    //
    // v = 0.0 (sl=1.0)
    //   means exclude 0%, must return LESS THAN the FIRST element in the array
    //   since 100% would have to lay ABOVE
    //   CURRENTLY will return a[0]
    //   idxF = 0.0 * a.length - 1 ;
    //   idx = -1 ; // FIRST
    //
    // v = 0.95 and a[100]
    //   means exclude 95%, must return 94th element in the array of 100
    //   idxF = 0.95 * 100 - 1 = 94.0;
    //   idx = 94;
    //
    // v = 0.999 and a[100]
    //   means exclude 99.9%, must return 99th element in the array of 100
    //   idxF = 0.999 * 100 - 1 = 98.9;
    //   idx = 99;
    //
    // v = 0.5 and a={0.19, 0.2, 0.3, 0.4}
    //   means exclude 50%, must return a[1]
    //   idxF = 0.5 * 4 - 1 = 1.0;
    //   idx = 1;
    //
    // v = 0.6 and a={0.19, 0.2, 0.3}
    //   means exclude 60%, must return the best fit, a[1] which excludes 66.66%
    //   idxF = 0.6 * 3.0  - 1 = 0.8;
    //   idx = round(0.8) = 1;
    float idxF = (float) (v * a.length - 1);
    int idx = Math.round(idxF);  // (int)Math.floor(a + 0.5f)
    if (idx < 0)
      return a[0];
    return a[idx];
  }
  public double[] calcTypeTwoLogs(double[] typeOneLogs) {
    double[] res = new double[typeOneLogs.length];
    for (int i = 0; i < res.length; i++) {
      res[i] = calcTypeTwoLog(typeOneLogs[i]);
    }
    return res;
  }
  // Type II error
  // DEF: Acceptance of the null hypothesis when it is false is called a TYPE II(two) ERROR
  public double calcTypeTwoLog(double ci) {
    if (rLogs == null || rLogs.length == 0)
      return Like.MIN_LOG;
    int i = 0;
    for (i = 0; i < rLogs.length; i++) {
      if (rLogs[i] <= ci)
        continue;
      break;
    }
    return (double) i / rLogs.length;
  }
}

