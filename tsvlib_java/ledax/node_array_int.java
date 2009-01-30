package ledax;
import javax.utilx.TreeMapToInt;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 26, 2004, Time: 12:17:48 PM
 */
//The data type node_array<E> can be used to associate additional
// (LEDA or user defined) information of type E with the nodes of source graph.
//public class node_array_int extends HashMapToInt {
public class node_array_int extends TreeMapToInt {
  public node_array_int() {
  }
  public node_array_int(graph g, int i) {
    set_forall(g.list_node(), i);
  }
  public void set_forall(list_node b, int i) {
    for (int j = 0; j < b.size(); j++) {
      node n = (node) b.get(j);
      put(n, new Integer(i));
    }
  }
}
