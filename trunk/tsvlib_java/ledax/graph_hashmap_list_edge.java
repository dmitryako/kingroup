package ledax;
import java.util.HashMap;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 10, 2004, Time: 10:59:09 AM
 */
public class graph_hashmap_list_edge extends graph_adaptor implements graph_i {
  private HashMap map_ = new HashMap();
  final public edge new_edge(final node a, final node b) {
    edge res = new edge(a, b);
    list_edge A = adj_edges(a);
    if (A == null) {
      A = new list_edge();
      map_.put(a, A);
    }
    A.add(res);
    return res;
  }
  final public list_edge adj_edges(final node a) {
    return (list_edge) map_.get(a);
  }
  // edge G.rev_edge(edge e) reverses e (move_edge(e,target(e),source(e))).
  final public edge rev_edge(final edge e) {
    return rev_edge_v1(e);
//      return rev_edge_v2(e);
  }
  private edge rev_edge_v1(final edge e) {
    node a = e.source;
    list_edge A = adj_edges(a);
    A.remove(e);
    edge res = new_edge(e.target, a);
    e.setSource(res.source);
    e.setTarget(res.target);
    return res;
  }
  private edge rev_edge_v2(final edge e) {
    node s = e.source;
    node t = e.target;
    list_edge A = adj_edges(s);
    if (A != null)
      A.remove(e);
    list_edge B = adj_edges(t);
    if (B == null) {
      B = new list_edge();
      map_.put(t, B);
    }
    B.add(e);
    e.setSource(t);
    e.setTarget(s);
    return e;
  }
}

