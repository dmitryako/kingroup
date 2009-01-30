package javax.mathx;
import javax.utilx.arrays.DoubleArrayList;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/05/2005, Time: 12:42:19
 */
public class FactorialLog {
  private static DoubleArrayList SAVED_LOGS = new DoubleArrayList();
  private static int STEP_FORWARD = 10; // to avoid going up by one
  public FactorialLog(int last) {
    loadLogs(SAVED_LOGS, last);
  }
  public double calc(int n) {
    if (n >= SAVED_LOGS.size()) {
      loadLogs(SAVED_LOGS, n + STEP_FORWARD);
    }
    return SAVED_LOGS.get(n);
  }
  protected void loadLogs(DoubleArrayList to, int last) {
    double logF = 0.;
    int i = 1;
    if (to.size() == 0) {// factorial 0!=1, 1!=1
      to.add(logF);
    } else {
      logF = to.last();
      i = to.size();
    }
    for (; i <= last; i++) {
      logF += Math.log(i);
      to.add(logF);
    }
  }
}
