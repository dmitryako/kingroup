package ledax;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.vecmathx.matrix.SquareIntegerMatrixByCols;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 4, 2004, Time: 7:38:06 AM
 */
public class LedaWeightAssignmentJUnit extends TestCase {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(LedaWeightAssignmentJUnit.class);
  }
  protected void setUp() {
  }
  public void testCalcMinCost2() {
    int SIZE = 2;
    SquareIntegerMatrixByCols arr = new SquareIntegerMatrixByCols(SIZE, false);
    int row = 0;
    arr.setRow(row++, new int[]{1, 3});
    arr.setRow(row++, new int[]{4, 2});
    assertEquals(3, new LedaWeightAssignment(arr).calcMinCost());
    assertEquals(3, new LedaWeightAssignment(arr).calcMaxCost());
    row = 0;
    arr.setRow(row++, new int[]{1, 2});
    arr.setRow(row++, new int[]{3, 4});
    assertEquals(5, new LedaWeightAssignment(arr).calcMinCost());
    assertEquals(5, new LedaWeightAssignment(arr).calcMaxCost());
    row = 0;
    arr.setRow(row++, new int[]{1, 2});
    arr.setRow(row++, new int[]{2, 4});
    assertEquals(4, new LedaWeightAssignment(arr).calcMinCost());
    assertEquals(5, new LedaWeightAssignment(arr).calcMaxCost());
  }
  public void testCalcMinCost() {
    int SIZE = 5;
    SquareIntegerMatrixByCols arr = new SquareIntegerMatrixByCols(SIZE, false);
    int row = 0;
    arr.setRow(row++, new int[]{7, 2, 1, 9, 4});
    arr.setRow(row++, new int[]{9, 6, 9, 5, 5});
    arr.setRow(row++, new int[]{3, 8, 3, 1, 8});
    arr.setRow(row++, new int[]{7, 9, 4, 2, 3});
    arr.setRow(row++, new int[]{8, 4, 7, 4, 8});
    LedaWeightAssignment algo = new LedaWeightAssignment(arr);
    assertEquals(15, algo.calcMinCost());
    assertEquals(43, algo.calcMaxCost());
  }
}