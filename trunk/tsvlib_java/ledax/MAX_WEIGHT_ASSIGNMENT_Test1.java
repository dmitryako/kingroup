package ledax;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.iox.LOG;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 1, 2004, Time: 12:06:45 PM
 */
public class MAX_WEIGHT_ASSIGNMENT_Test1 extends MAX_WEIGHT_ASSIGNMENT_Test {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(MAX_WEIGHT_ASSIGNMENT_Test1.class);
  }
  protected void setUp() {
    LOG.setTrace(true);
  }
  protected void setUp_1() {
    G = new graph();
    A = new list_node();
    B = new list_node();
    w = new edge_array_int();
    a0 = G.new_node("a");
    A.append(a0);
    b0 = G.new_node("b");
    B.append(b0);
    e0 = G.new_edge(a0, b0);
    w.put(e0, 1);
  }
  protected void init_2() {
    G = new graph();
    A = new list_node();
    B = new list_node();
    w = new edge_array_int();
    a0 = G.new_node("a");
    A.append(a0);
    a1 = G.new_node("a");
    A.append(a1);
    b0 = G.new_node("b");
    B.append(b0);
    b1 = G.new_node("b");
    B.append(b1);
    e0 = G.new_edge(a0, b0);
    e1 = G.new_edge(a0, b1);
    e2 = G.new_edge(a1, b0);
    e3 = G.new_edge(a1, b1);
  }
  protected void setUp_2a() {
    init_2();
    w.put(e0, 4);
    w.put(e1, 3);
    w.put(e2, 2);
    w.put(e3, 2);
  }
  protected void setUp_2b() {
    init_2();
    w.put(e0, 4);
    w.put(e1, 3);
    w.put(e2, 3);
    w.put(e3, 1);
  }
  protected void init_3() {
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
    b0 = G.new_node("b");
    B.append(b0);
    b1 = G.new_node("b");
    B.append(b1);
    b2 = G.new_node("b");
    B.append(b2);
    e0 = G.new_edge(a0, b0);
    e1 = G.new_edge(a0, b1);
    e2 = G.new_edge(a0, b2);
    e3 = G.new_edge(a1, b0);
    e4 = G.new_edge(a1, b1);
    e5 = G.new_edge(a1, b2);
    e6 = G.new_edge(a2, b0);
    e7 = G.new_edge(a2, b1);
    e8 = G.new_edge(a2, b2);
  }
  protected void setUp_3a() {
    init_3();
    w.put(e0, 4);
    w.put(e1, 3);
    w.put(e2, 2);
    w.put(e3, 1);
    w.put(e4, 2);
    w.put(e5, 3);
    w.put(e6, 5);
    w.put(e7, 5);
    w.put(e8, 7);
  }
  protected void setUp_3b() {
    init_3();
    w.put(e0, 4);
    w.put(e1, 3);
    w.put(e2, 2);
    w.put(e3, 4);
    w.put(e4, 2);
    w.put(e5, 5);
    w.put(e6, 5);
    w.put(e7, 5);
    w.put(e8, 7);
  }
  protected void init_5() {
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
    a4 = G.new_node("a");
    A.append(a4);
    b0 = G.new_node("b");
    B.append(b0);
    b1 = G.new_node("b");
    B.append(b1);
    b2 = G.new_node("b");
    B.append(b2);
    b3 = G.new_node("b");
    B.append(b3);
    b4 = G.new_node("b");
    B.append(b4);
    e0 = G.new_edge(a0, b0);
    e1 = G.new_edge(a0, b1);
    e2 = G.new_edge(a0, b2);
    e3 = G.new_edge(a0, b3);
    e4 = G.new_edge(a0, b4);
    e5 = G.new_edge(a1, b0);
    e6 = G.new_edge(a1, b1);
    e7 = G.new_edge(a1, b2);
    e8 = G.new_edge(a1, b3);
    e9 = G.new_edge(a1, b4);
    e10 = G.new_edge(a2, b0);
    e11 = G.new_edge(a2, b1);
    e12 = G.new_edge(a2, b2);
    e13 = G.new_edge(a2, b3);
    e14 = G.new_edge(a2, b4);
    e15 = G.new_edge(a3, b0);
    e16 = G.new_edge(a3, b1);
    e17 = G.new_edge(a3, b2);
    e18 = G.new_edge(a3, b3);
    e19 = G.new_edge(a3, b4);
    e20 = G.new_edge(a4, b0);
    e21 = G.new_edge(a4, b1);
    e22 = G.new_edge(a4, b2);
    e23 = G.new_edge(a4, b3);
    e24 = G.new_edge(a4, b4);
  }
  protected void setUp_5a() {
    init_5();
    w.put(e0, 7);
    w.put(e1, 2);
    w.put(e2, 1);
    w.put(e3, 9);
    w.put(e4, 4);
    w.put(e5, 9);
    w.put(e6, 6);
    w.put(e7, 9);
    w.put(e8, 5);
    w.put(e9, 5);
    w.put(e10, 3);
    w.put(e11, 8);
    w.put(e12, 3);
    w.put(e13, 1);
    w.put(e14, 8);
    w.put(e15, 7);
    w.put(e16, 9);
    w.put(e17, 4);
    w.put(e18, 2);
    w.put(e19, 3); // 2 gives non unique
    w.put(e20, 8);
    w.put(e21, 4);
    w.put(e22, 7);
    w.put(e23, 4);
    w.put(e24, 8);
  }
  public void testCalc_1() {
    setUp_1();
    node_array_int pot = new node_array_int();
    MAX_WEIGHT_ASSIGNMENT_T assign = new MAX_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    list_edge M = assign.calc();
    assertEquals(1, M.size());
    edge m0 = new edge(a0, b0);
    assertEquals(m0, M.first());
    assertTrue(assign.check(M, true));
    assertEquals(1, assign.getTotalCost(M));
    setUp_1();
    pot = new node_array_int();
    assign = new MIN_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    M = assign.calc();
    assertEquals(1, M.size());
    assertEquals(m0, M.first());
    assertTrue(assign.check(M, false));
    assertEquals(1, assign.getTotalCost(M));
  }
  public void testCalc_2() {
    setUp_2a();
    node_array_int pot = new node_array_int();
    MAX_WEIGHT_ASSIGNMENT_T assign = new MAX_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    assign.calc_simple_heuristic();
    node_array_bool free = assign.getFree();
    assertEquals(false, free.getBool(a0));
    assertEquals(true, free.getBool(a1));
    assertEquals(false, free.getBool(b0));
    assertEquals(true, free.getBool(b1));
    setUp_2a();
    pot = new node_array_int();
    assign = new MAX_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    list_edge M = assign.calc();
    assertEquals(2, M.size());
    edge m0 = new edge(a0, b0);
    edge m1 = new edge(a1, b1);
    LOG.trace(this, "M=", M.toString());
    assertEquals(true, M.contains(m0));
    assertEquals(true, M.contains(m1));
    assertTrue(assign.check(M, true));
    assertEquals(6, assign.getTotalCost(M));
    setUp_2a();
    pot = new node_array_int();
    assign = new MIN_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    M = assign.calc();
    assertEquals(2, M.size());
    m0 = new edge(a0, b1);
    m1 = new edge(a1, b0);
    LOG.trace(this, "M=", M.toString());
    assertEquals(true, M.contains(m0));
    assertEquals(true, M.contains(m1));
    assertTrue(assign.check(M, false));
    assertEquals(5, assign.getTotalCost(M));
    setUp_2b();
    pot = new node_array_int();
    assign = new MAX_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    M = assign.calc();
    assertEquals(2, M.size());
    m0 = new edge(a0, b1);
    m1 = new edge(a1, b0);
    LOG.trace(this, "M=", M.toString());
    assertEquals(true, M.contains(m1));
    assertEquals(true, M.contains(m0));
    assertTrue(assign.check(M, true));
    assertEquals(6, assign.getTotalCost(M));
    setUp_2b();   //MIN
    pot = new node_array_int();
    assign = new MIN_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    M = assign.calc();
    assertEquals(2, M.size());
    m0 = new edge(a0, b0);
    m1 = new edge(a1, b1);
    LOG.trace(this, "M=", M.toString());
    assertEquals(true, M.contains(m1));
    assertEquals(true, M.contains(m0));
    assertTrue(assign.check(M, false));
    assertEquals(5, assign.getTotalCost(M));
  }
  public void testCalc_3a() {
    setUp_3a();
    node_array_int pot = new node_array_int();
    MAX_WEIGHT_ASSIGNMENT_T assign = new MAX_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    list_edge M = assign.calc();
    assertEquals(3, M.size());
    edge m0 = new edge(a0, b0);
    edge m1 = new edge(a1, b1);
    edge m2 = new edge(a2, b2);
    LOG.trace(this, "M=", M.toString());
    assertEquals(true, M.contains(m0));
    assertEquals(true, M.contains(m1));
    assertEquals(true, M.contains(m2));
    assertTrue(assign.check(M, true));
    assertEquals(13, assign.getTotalCost(M));
    setUp_3a();      //MIN
    pot = new node_array_int();
    assign = new MIN_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    M = assign.calc();
    assertEquals(3, M.size());
    m0 = new edge(a0, b2);
    m1 = new edge(a1, b0);
    m2 = new edge(a2, b1);
    LOG.trace(this, "M=", M.toString());
    assertEquals(true, M.contains(m0));
    assertEquals(true, M.contains(m1));
    assertEquals(true, M.contains(m2));
    assertTrue(assign.check(M, false));
    assertEquals(8, assign.getTotalCost(M));
  }
  public void testCalc_3b() {
    setUp_3b();
    node_array_int pot = new node_array_int();
    MAX_WEIGHT_ASSIGNMENT_T assign = new MAX_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    list_edge M = assign.calc();
    assertEquals(3, M.size());
    edge m0 = new edge(a0, b0);
    edge m1 = new edge(a1, b2);
    edge m2 = new edge(a2, b1);
    LOG.trace(this, "M=", M.toString());
    assertEquals(true, M.contains(m0));
    assertEquals(true, M.contains(m1));
    assertEquals(true, M.contains(m2));
    assertTrue(assign.check(M, true));
    assertEquals(14, assign.getTotalCost(M));
    setUp_3b(); //MIN
    pot = new node_array_int();
    assign = new MIN_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    M = assign.calc();
    assertEquals(3, M.size());
    m0 = new edge(a0, b2);
    m1 = new edge(a1, b1);
    m2 = new edge(a2, b0);
    LOG.trace(this, "M=", M.toString());
    assertEquals(true, M.contains(m0));
    assertEquals(true, M.contains(m1));
    assertEquals(true, M.contains(m2));
    assertTrue(assign.check(M, false));
    assertEquals(9, assign.getTotalCost(M));
  }
  public void testCalc_5a() {
    setUp_5a();
    node_array_int pot = new node_array_int();
    MAX_WEIGHT_ASSIGNMENT_T assign = new MIN_WEIGHT_ASSIGNMENT_T(G, A, B, w, pot);
    list_edge M = assign.calc();
    assertEquals(5, M.size());
    edge m0 = new edge(a0, b2);
    edge m1 = new edge(a1, b4);
    edge m2 = new edge(a2, b0);
    edge m3 = new edge(a3, b3);
    edge m4 = new edge(a4, b1);
    LOG.trace(this, "M=", M.toString());
    assertEquals(true, M.contains(m0));
    assertEquals(true, M.contains(m1));
    assertEquals(true, M.contains(m2));
    assertEquals(true, M.contains(m3));
    assertEquals(true, M.contains(m4));
    assertTrue(assign.check(M, false));
    assertEquals(15, assign.getTotalCost(M));
  }
}
