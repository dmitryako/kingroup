package ledax;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 26, 2004, Time: 9:46:41 AM
 */
public class MIN_WEIGHT_ASSIGNMENT_T extends MAX_WEIGHT_ASSIGNMENT_T {
  public MIN_WEIGHT_ASSIGNMENT_T(graph G, list_node A, list_node B, edge_array_int c) {
    super(G, A, B, c);
  }
  public MIN_WEIGHT_ASSIGNMENT_T(graph G, list_node A, list_node B, edge_array_int c, node_array_int pot) {
    super(G, A, B, c, pot);
  }
/* template <class NT>
__temp_func_inline
list<edge> MIN_WEIGHT_ASSIGNMENT_T(graph& G,
const list<node>& A, const list<node>& B,
const edge_array<NT>& c, node_array<NT>& pot)
{ edge_array<NT> w(G);
edge e;
forall_edges(e,G) w[e] = - c[e];

list<edge> M = MAX_WEIGHT_ASSIGNMENT_T(G,A,B,w,pot);
node v;
forall_nodes(v,G) pot[v] = -pot[v];
return M;
}*/
  public list_edge calc() {
    edge_array_int w = c.deepClone(); //edge_array<NT> w(G);
    w.mult(-1); //edge e; forall_edges(e,G) w[e] = - c[e];
    //list<edge> M = MAX_WEIGHT_ASSIGNMENT_T(G,A,B,w,pot);
    list_edge M = new MAX_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot).calc();
    pot.mult(-1); //node v; forall_nodes(v,G) pot[v] = -pot[v];
    return M; //return M;
  }
}
