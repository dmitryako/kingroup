package ledax;
import java.util.HashMap;
import java.util.TreeSet;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 10, 2004, Time: 5:46:38 PM
 */
public class graph_hashmap_list_edge_jit extends graph_adaptor
  implements graph_i {
  private HashMap map_ = new HashMap();
  private TreeSet invalidEgdes_ = new TreeSet();
  public edge new_edge(node a, node b) {
    edge res = new edge(a, b);
    list_edge A = adj_edges(a);
    if (A == null) {
      A = new list_edge();
      map_.put(a, A);
    }
    A.add(res);
    return res;
  }
  public list_edge adj_edges(node a) {
    if (!invalidEgdes_.isEmpty())
      rebuildMap();
    return (list_edge) map_.get(a);
  }
  private void rebuildMap() {
    while (!invalidEgdes_.isEmpty()) {
      edge e = (edge) invalidEgdes_.first();
      invalidEgdes_.remove(e);
      list_edge A = (list_edge) map_.get(e.source);
      if (A == null) {
        A = new list_edge();
        map_.put(e.source, A);
      }
      A.add(e);
      list_edge B = (list_edge) map_.get(e.target);
      if (B != null)
        B.remove(e);
    }
  }
  // edge G.rev_edge(edge e) reverses e (move_edge(e,target(e),source(e))).
  public edge rev_edge(edge e) {
    invalidEgdes_.add(e);
    node saved = e.source;
    e.setSource(e.target);
    e.setTarget(saved);
    return e;
  }
}


