package ledax;
import javax.iox.LOG;
import javax.vecmathx.matrix.SquareIntegerMatrix;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 2, 2004, Time: 4:52:35 PM
 */
public class LedaWeightAssignment {
  final private SquareIntegerMatrix cost_;
  public LedaWeightAssignment(SquareIntegerMatrix cost) {
    cost_ = cost;
  }
  public int calcMinCost() {
    return calcCost(false);
  }
  public int calcMaxCost() {
    return calcCost(true);
  }
  private int calcCost(boolean max_cost) {
    graph G = new graph();
    final int n = cost_.size();
    list_node A = G.make_list_node(n, "a");
    list_node B = G.make_list_node(n, "b");
    edge_array_int w = G.make_edge_array_int(A, B, cost_);
    //node_array_int pot = new node_array_int();
    MAX_WEIGHT_ASSIGNMENT_T assign;
    if (max_cost)
      assign = new MAX_WEIGHT_ASSIGNMENT_T(G, A, B, w);
    else
      assign = new MIN_WEIGHT_ASSIGNMENT_T(G, A, B, w);
    list_edge M = assign.calc();
    boolean ok = assign.check(M, max_cost);
    if (!ok) {
      LOG.error(this, assign.getError(), "");
      return 0;
    }
    return assign.getTotalCost(M);
  }
}
