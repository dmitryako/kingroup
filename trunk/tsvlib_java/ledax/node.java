package ledax;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 26, 2004, Time: 11:30:13 AM
 */
final public class node implements Comparable {
  private list_edge adj_ = new list_edge();
  private int id;
  private String strId;
  public void setId(int i) {
    id = i;
  }
  public int getId() {
    return id;
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();//.append('[');
    if (strId == null)
      buff.append(Integer.toString(id));
    else
      buff.append(strId);
    return buff.toString();
    //return buff.append(']').toString();
  }
  public String getStrId() {
    return strId;
  }
  public void setStrId(String strId) {
    this.strId = strId;
  }
  final public boolean equals(final node n) {
    return id == n.id;
  }
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!(obj instanceof node))
      return false;
    return (compareTo(obj) == 0);
  }
  public int compareTo(Object obj) {
    return id - ((node) obj).id;
  }
  public void del_adj_edge(edge e) {
    adj_.remove(e);
  }
  public void add_adj_edge(edge e) {
    adj_.add(e);
  }
  public list_edge adj_edges() {
    return adj_;
  }
}
