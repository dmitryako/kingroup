package kingroup.genetics;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 24, 2004, Time: 1:31:50 PM
 */
public class Like {
  final public static float MIN_LOG = -Float.MAX_VALUE;
  final public static double MAX_LOG = Float.MAX_VALUE;
  final public static double IGNORE = Double.POSITIVE_INFINITY;
  public static double probToLog(double prob) {
    if (prob <= 0.0
      || prob > 1.0)
      return MIN_LOG; // impossible
    return Math.log(prob);
  }
  public static double logToProb(double log_value) {
    if (log_value == MIN_LOG)
      return 0; // impossible
    return Math.exp(log_value);
  }
}
