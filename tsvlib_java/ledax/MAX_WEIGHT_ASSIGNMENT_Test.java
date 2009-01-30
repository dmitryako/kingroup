package ledax;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.iox.LOG;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 27, 2004, Time: 11:49:02 AM
 */
public class MAX_WEIGHT_ASSIGNMENT_Test extends TestCase {
  graph G = new graph();
  list_node A = new list_node();
  list_node B = new list_node();
  edge_array_int w = new edge_array_int();
  node a0
  ,
  a1
  ,
  a2
  ,
  a3
  ,
  a4
  ,
  a5;
  node b0
  ,
  b1
  ,
  b2
  ,
  b3
  ,
  b4
  ,
  b5;
  edge e0
  ,
  e1
  ,
  e2
  ,
  e3;
  edge e4
  ,
  e5
  ,
  e6
  ,
  e7
  ,
  e8
  ,
  e9
  ,
  e10
  ,
  e11
  ,
  e12
  ,
  e13
  ,
  e14
  ,
  e15
  ,
  e16
  ,
  e17
  ,
  e18
  ,
  e19;
  edge e20
  ,
  e21
  ,
  e22
  ,
  e23
  ,
  e24;
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(MAX_WEIGHT_ASSIGNMENT_Test.class);
  }
  protected void setUp() {
    privateSetUp();
  }
  private void privateSetUp() {
    G = new graph();
    A = new list_node();
    B = new list_node();
    w = new edge_array_int();
    a0 = G.new_node("a");
    A.append(a0);
    a1 = G.new_node("a");
    A.append(a1);
    a2 = G.new_node("a");
    A.append(a2);
    a3 = G.new_node("a");
    A.append(a3);
    b0 = G.new_node("b");
    B.append(b0);
    b1 = G.new_node("b");
    B.append(b1);
    b2 = G.new_node("b");
    B.append(b2);
    b3 = G.new_node("b");
    B.append(b3);
    e0 = G.new_edge(a0, b1);
    e1 = G.new_edge(a0, b3);
    e2 = G.new_edge(a1, b0);
    e3 = G.new_edge(a2, b0);
    e4 = G.new_edge(a2, b2);
    e5 = G.new_edge(a3, b2);
    e6 = G.new_edge(a3, b3);
    w.put(e0, 1);
    w.put(e1, 2);
    w.put(e2, 3); //weight[e0]=1;weight[e1]=2;weight[e2]=3;
    w.put(e3, 2);
    w.put(e4, 1);
    w.put(e5, 2); //weight[e3]=2;weight[e4]=1;weight[e5]=2;
    w.put(e6, 3); //weight[e6]=3;
  }
  public void testCalc() {
    privateSetUp();
    LOG.setTrace(true);
    node_array_int pot = new node_array_int(); //node_array<integer> pot(G);
    //list<edge> M=MAX_WEIGHT_ASSIGNMENT_T(G,A,B,weight,pot);
    list_edge M = new MAX_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot).calc();
  }
  public void test_calc_simple_heuristic() {
    privateSetUp();
    LOG.setTrace(true);
    node_array_int pot = new node_array_int(); //node_array<integer> pot(G);
    MAX_WEIGHT_ASSIGNMENT_T assign = new MAX_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    assign.calc_simple_heuristic();
    node_array_bool free = assign.getFree();
    assertEquals(false, free.getBool(a0));
    assertEquals(false, free.getBool(a1));
    assertEquals(true, free.getBool(a2));
    assertEquals(true, free.getBool(a3));
    assertEquals(false, free.getBool(b0));
    assertEquals(true, free.getBool(b1));
    assertEquals(true, free.getBool(b2));
    assertEquals(false, free.getBool(b3));
    LOG.trace(this, "free=\n", free.toString(true));
  }
}