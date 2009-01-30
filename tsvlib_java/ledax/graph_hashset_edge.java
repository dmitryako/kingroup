package ledax;
import java.util.HashSet;
import java.util.Iterator;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 10, 2004, Time: 2:37:35 PM
 */
public class graph_hashset_edge extends graph_adaptor
  implements graph_i {
  final private HashSet setNode_ = new HashSet();
//   final private TreeMap mapListNode_ = new TreeMap();
  private boolean mapIsValid_ = false;
  public edge new_edge(node a, node b) {
    mapIsValid_ = false;
    edge res = new edge(a, b);
    if (setNode_.contains(res))
      setNode_.remove(res);
    setNode_.add(res);
    return res;
  }
  public list_edge adj_edges(node a) {
    final list_edge res = new list_edge();
    for (Iterator it = setNode_.iterator(); it.hasNext();) {
      edge e = (edge) it.next();
      if (e.source.equals(a))
        res.add(e);
    }
    return res;
  }
  // edge G.rev_edge(edge e) reverses e (move_edge(e,target(e),source(e))).
  public edge rev_edge(edge e) {
    mapIsValid_ = false;
    node saved = e.source;
    e.setSource(e.target);
    e.setTarget(saved);
    return e;
  }
}
