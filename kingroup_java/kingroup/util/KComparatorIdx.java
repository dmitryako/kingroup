package kingroup.util;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */
import java.util.Comparator;

// THIS is for forting array of indexes into an array of doubles
public class KComparatorIdx implements Comparator {
  double[] arr_ = null;
  public KComparatorIdx(double[] arr) {
    arr_ = arr;
  }
  public int compare(Object idx, Object idx2) {
    int i = ((Integer) idx).intValue();
    int i2 = ((Integer) idx2).intValue();
    if (arr_[i] == arr_[i2])
      return 0;
    if (arr_[i] > arr_[i2])
      return -1;
    return 1;
  }
}
