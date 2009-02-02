package papers.kingroup2005_TSP.tsp;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 31/05/2005, Time: 13:50:44
 */
public class TSPBruteAlgorithmJUnit extends TestCase {
  private static final double EPS = 1e-10;
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(TSPBruteAlgorithmJUnit.class);
  }
  public void setUp() {
  }
  public void testSolve() {
    int CAPACITY = 3;
    TSPMap map = new TSPMap(CAPACITY);
    map.add(0, 0);
    map.add(1, 0);
    map.add(1, 1);
    TSPBruteAlgorithm brute = new TSPBruteAlgorithm(map);
    TSPPath res = brute.solve();
    assertEquals(2 + Math.sqrt(2), res.distance(), EPS);
  }
  public void testSolveB() {
    int CAPACITY = 3;
    TSPMap map = new TSPMap(CAPACITY);
    map.add(1, 1);
    map.add(0, 0);
    map.add(0.5, 0.5);
    TSPBruteAlgorithm brute = new TSPBruteAlgorithm(map);
    TSPPath res = brute.solve();
    assertEquals(2 * Math.sqrt(2), res.distance(), EPS);
  }
  public void testSolveC() {
    int CAPACITY = 4;
    TSPMap map = new TSPMap(CAPACITY);
    map.add(1, 1);
    map.add(0, 0);
    map.add(1, 0);
    map.add(0.5, 0.5);
    TSPBruteAlgorithm brute = new TSPBruteAlgorithm(map);
    TSPPath res = brute.solve();
    assertEquals(2 + Math.sqrt(2), res.distance(), EPS);
  }
}