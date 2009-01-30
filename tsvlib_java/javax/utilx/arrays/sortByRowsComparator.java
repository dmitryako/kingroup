package javax.utilx.arrays;
import java.util.Comparator;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/01/2007, Time: 11:56:51
 */
public class sortByRowsComparator implements Comparator<double[]> {
  private final int idx;
  public sortByRowsComparator(final int idx) {
    this.idx = idx;
  }
  public int compare(double[] a, double[] a2)
  {
    return Double.compare(a[idx], a2[idx]);
  }
}

