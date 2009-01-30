package ledax;
import javax.vecmathx.matrix.SquareIntegerMatrix;
import java.util.Iterator;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 10, 2004, Time: 11:55:21 AM
 */
abstract public class graph_adaptor implements graph_i {
  private int counter_ = 0; // sequence counter
  private list_node list_node_ = new list_node();
  final public list_node list_node() {
    return list_node_;
  }
  final public node new_node(final String tag) {
    final node res = new node();
    if (tag != null && tag.length() > 0)
      res.setStrId(tag + counter_);
    res.setId(counter_++);
    list_node_.add(res);
    return res;
  }
  //forall(e,B) G.rev_edge(e);
  final public void forall_rev_edge(final list_edge B) {
    if (B == null || B.isEmpty())
      return;
    for (Iterator it = B.iterator(); it.hasNext();) {
      edge e = (edge) it.next();
      rev_edge(e);
    }
  }
  //For directed graphs we often use out_edges(v) as a synonym for adj_edges(v).
  final public list_edge out_edges(final node b) {
    return adj_edges(b);
  }
  final public edge first_adj_edge(final node b) {
    final list_edge edges = adj_edges(b);
    if (edges == null || edges.isEmpty())
      return null;
    return (edge) edges.first();
  }
  final public list_node make_list_node(final int n, final String s) {
    final list_node res = new list_node();
    for (int i = 0; i < n; i++) {
      node a = new_node(s);
      res.append(a);
    }
    return res;
  }
  // precondition: A.size() == B.size() == w.size();
  final public edge_array_int make_edge_array_int(final list_node A
    , final list_node B
    , final SquareIntegerMatrix w) {
    final edge_array_int res = new edge_array_int();
    int r = 0;
    for (Iterator itA = A.iterator(); itA.hasNext(); r++) {
      node a = (node) itA.next();
      int c = 0;
      for (Iterator itB = B.iterator(); itB.hasNext(); c++) {
        node b = (node) itB.next();
        edge e = new_edge(a, b);
        res.put(e, w.getElement(r, c));
        //LOG.trace(this, "res[" + e + "]=", res.getInt(e));
      }
    }
    return res;
  }
}
