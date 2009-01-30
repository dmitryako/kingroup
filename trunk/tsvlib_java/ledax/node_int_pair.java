package ledax;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Oct 28, 2004, Time: 6:43:39 PM
 */
public class node_int_pair implements Comparable {
  private final node node_;
  private final int int_;
  public node_int_pair(node n, int i) {
    this.node_ = n;
    this.int_ = i;
  }
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    return (compareTo(obj) == 0);
  }
  public int compareTo(Object obj) {
    node_int_pair other = (node_int_pair) obj;
    if (node_.compareTo(other.node_) == 0)
      return 0;
    if (int_ != other.int_)
      return int_ - other.int_;
    return node_.getId() - other.node_.getId();
  }
  public node getNode() {
    return node_;
  }
  public int getInt() {
    return int_;
  }
}
