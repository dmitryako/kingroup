package javax.utilx.pair;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 17, 2004, Time: 4:29:25 PM
 */
public class IntStringPair {
  private int val_;
  private String name_;
  public IntStringPair(int val, String name) {
    val_ = val;
    name_ = name;
  }
  final public int getInt() {
    return val_;
  }
  final public String toString() {
    return name_;
  }
}
