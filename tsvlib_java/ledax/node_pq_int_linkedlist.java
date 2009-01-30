package ledax;
import java.util.LinkedList;
import java.util.TreeSet;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 16, 2004, Time: 3:30:08 PM
 */
public class node_pq_int_linkedlist {
  private LinkedList list_ = new LinkedList();
  private TreeSet idxMap_ = new TreeSet();
  public void insert(node key, int p) { // priority
    node_int_pair obj = new node_int_pair(key, p);
    if (idxMap_.remove(key)) {
      list_.remove(obj);
    }
    int i = list_.size() - 1;
    for (; i >= 0; i--) {
      node_int_pair e = (node_int_pair) list_.get(i);
      if (e.getInt() <= p)
        break;
    }
    list_.add(i + 1, obj);
    idxMap_.add(key);
  }
  public void decrease_p(node key, int p) {
    insert(key, p);
  }
  public boolean empty() {
    return list_.isEmpty();
  }
  public node del_min() {
    if (list_.isEmpty())
      return null;
    node_int_pair res = (node_int_pair) list_.remove(0);
    idxMap_.remove(res.getNode());
    return res.getNode();
  }
  public boolean member(node key) {
    return idxMap_.contains(key);
  }
  public void del(node key) {
    if (idxMap_.remove(key)) {
      list_.remove(new node_int_pair(key, 0));
    }
  }
}