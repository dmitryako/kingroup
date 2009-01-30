package ledax;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 10, 2004, Time: 11:52:55 AM
 */
public interface graph_i {
  public edge new_edge(final node a, final node b);
  public list_edge adj_edges(final node a);
  // edge G.rev_edge(edge e) reverses e (move_edge(e,target(e),source(e))).
  public edge rev_edge(final edge e);
}
