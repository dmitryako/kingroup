package ledax;
import javax.utilx.TreeMapToInt;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 26, 2004, Time: 11:49:22 AM
 */
//The data type edge_array<E> can be used to associate additional
// (LEDA or user defined) information of type E with the edges of source graph.
//public class edge_array_int extends HashMapToInt  {
public class edge_array_int extends TreeMapToInt {
  public edge_array_int deepClone() {
    edge_array_int res = new edge_array_int();
    deepClone(res);
    return res;
  }
}
