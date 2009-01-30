package ledax;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 12, 2004, Time: 1:10:50 PM
 */
public class graph_no_list_edge extends graph_adaptor
  implements graph_i {
  final public edge new_edge(final node a, final node b) {
    edge res = new edge(a, b);
    a.add_adj_edge(res);
    return res;
  }
  final public list_edge adj_edges(final node a) {
    return a.adj_edges();
  }
  // edge G.rev_edge(edge e) reverses e (move_edge(e,target(e),source(e))).
  final public edge rev_edge(final edge e) {
    return rev_edge_v3(e);
  }
  final private edge rev_edge_v3(final edge e) {
    node s = e.source;
    s.del_adj_edge(e);
    node t = e.target;
    t.add_adj_edge(e);
    e.setSource(t);
    e.setTarget(s);
    return e;
  }
}
