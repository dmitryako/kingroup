package ledax;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 3, 2004, Time: 3:05:25 PM
 */
public class node_pq_int_junit extends TestCase {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(node_pq_int_junit.class);
  }
  protected void setUp() {
  }
  public void testAll() {
    node_pq_int PQ = new node_pq_int();
    graph G = new graph();
    node a0 = G.new_node("a");
    node a1 = G.new_node("a");
    node a2 = G.new_node("a");
    PQ.insert(a0, 1);
    assertEquals(a0, PQ.del_min());
    PQ.insert(a0, 2);
    PQ.insert(a1, 1);
    PQ.insert(a0, 0);
    assertEquals(a0, PQ.del_min());
    PQ.insert(a0, 3);
    PQ.insert(a1, 4);
    PQ.insert(a2, 2);
    assertEquals(a2, PQ.del_min());
    PQ.insert(a0, 5);
    assertEquals(a1, PQ.del_min());
  }
}