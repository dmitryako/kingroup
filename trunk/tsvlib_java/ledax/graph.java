package ledax;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 26, 2004, Time: 11:28:24 AM
 */
/* The data type graph is the basic data type for representing graphs in LEDA.
It can represent directed and undirected graphs G=(V,E),
where V is the list of nodes and E is the list of directed, respectively undirected, edges.
Nodes and edges are of type item.
*/

final public class graph extends graph_no_list_edge {
}
//final public class graph extends graph_hashmap_list_edge {}
//final public class graph extends graph_hashmap_list_edge_jit {}

// NOTE!!! graph_hashset_edge is SLOWER than graph_hashmap_list_edge
//final public class graph extends graph_hashset_edge {    }
