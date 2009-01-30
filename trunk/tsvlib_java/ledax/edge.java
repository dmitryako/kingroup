package ledax;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 26, 2004, Time: 11:30:06 AM
 */
final public class edge implements Comparable {
  public node source;
  public node target;
  public edge(final node a, final node b) {
    this.source = a;
    this.target = b;
  }
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof edge))
      return false;
    return (compareTo(obj) == 0);
  }

//   final public node target() {
//      return target;
//   }
//
//   public node source() {
//      return source;
//   }
//
  public void setSource(node source) {
    this.source = source;
  }
  public void setTarget(node target) {
    this.target = target;
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    buff.append(source.getStrId());//.append("_" + source.getId());
    buff.append("->");
    buff.append(target.getStrId());//.append("_" + target.getId());
    //buff.append(source.getStrId()).append("->").append(target.getStrId());
    return buff.toString();
  }
  // NOTE! MUST be symmetric in source/target.
  public int compareTo(Object obj) {
    edge e = (edge) obj;
    int minA = Math.min(source.getId(), target.getId());
    int maxA = Math.max(source.getId(), target.getId());
    int minB = Math.min(e.source.getId(), e.target.getId());
    int maxB = Math.max(e.source.getId(), e.target.getId());
    if (minA != minB)
      return minA - minB;
    return maxA - maxB;
  }
}
