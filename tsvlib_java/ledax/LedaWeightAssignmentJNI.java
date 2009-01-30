package ledax;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 11, 2004, Time: 1:34:45 PM
 */
public class LedaWeightAssignmentJNI {
  private int[][] w_;
  static {
    System.loadLibrary("LedaWeightAssignmentJNI");
  }
  public native int calc(int[][] w, boolean calcMax);
  public LedaWeightAssignmentJNI(int[][] array) {
    w_ = array;
  }
  public int calcMinCost() {
    return calc(w_, false);
  }
  public int calcMaxCost() {
    return calc(w_, true);
  }
  public static void main(String[] args) {
    int[][] w = {{1, 2}, {3, 4}};
    int res = new LedaWeightAssignmentJNI(w).calcMinCost();
    System.out.println("res=" + res);
  }
}
