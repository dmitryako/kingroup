package kingroup.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.utilx.arrays.ArraysX;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 10, 2004, Time: 4:01:41 PM
 */
public class KArraysTest extends TestCase {
   private static int NOT_FOUND = -1;
   private float[] empty_ = new float[0];
   private float[] undefined_ = null;

   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      return new TestSuite(KArraysTest.class);
   }
   public void testBinarySearch() {
      double[]    a = {-0.1, 0,  0.5};
      double[] keys = {-0.1, 0,  0.5, -0.2, -0.05, 0.1, 0.6};
      int[] correct = {0,    1,  2,    0,    1,    2,   NOT_FOUND};
      for (int i = 0; i < keys.length; i++)
         assertEquals(correct[i], ArraysX.binarySearchGE(a, keys[i]));
   }
   public void testBinarySearch2() {
      double[]    a = {-0.11, 0};
      double[] keys = {-0.11, 0, -0.2, -0.05, 0.6};
      int[] correct = {0,     1,    0,    1,  NOT_FOUND};
      for (int i = 0; i < keys.length; i++)
         assertEquals(correct[i], ArraysX.binarySearchGE(a, keys[i]));
   }
   public void testBinarySearch3() {
      double[]    a = {-0.12};
      double[] keys = {-0.12, -0.2, -0.05};
      int[] correct = {0,      0,    NOT_FOUND};
      for (int i = 0; i < keys.length; i++)
         assertEquals(correct[i], ArraysX.binarySearchGE(a, keys[i]));
   }
   public void testBinarySearch4() {
      double[]    a = {};
      double[] keys = {-0.05};
      int[] correct = {NOT_FOUND};
      for (int i = 0; i < keys.length; i++)
         assertEquals(correct[i], ArraysX.binarySearchGE(a, keys[i]));
   }

   public void testMinMaxFloat() {
      float[] x = {0f, 0.5f, -1f};
      int minIdx = 2;
      int maxIdx = 1;
      assertEquals(minIdx, ArraysX.minIdx(x, x.length));
      assertEquals(maxIdx, ArraysX.maxIdx(x, x.length));

      int size = 2; // limit to first size-elements
      minIdx = 0;
      assertEquals(minIdx, ArraysX.minIdx(x, size));
      assertEquals(maxIdx, ArraysX.maxIdx(x, size));

      assertEquals(NOT_FOUND, ArraysX.minIdx(empty_, 0));
      assertEquals(NOT_FOUND, ArraysX.maxIdx(empty_, 0));

      assertEquals(NOT_FOUND, ArraysX.minIdx(undefined_, 0));
      assertEquals(NOT_FOUND, ArraysX.maxIdx(undefined_, 0));
   }

   public void testMinMaxDouble() {
      double[] x = {0f, 0.5f, -1f};
      int minIdx = 2;
      int maxIdx = 1;
      assertEquals(minIdx, Vec.minIdx(x, x.length));
      assertEquals(maxIdx, Vec.maxIdx(x, x.length));

      int size = 2; // limit to first size-elements
      minIdx = 0;
      assertEquals(minIdx, Vec.minIdx(x, size));
      assertEquals(maxIdx, Vec.maxIdx(x, size));

      assertEquals(NOT_FOUND, ArraysX.minIdx(empty_, 0));
      assertEquals(NOT_FOUND, ArraysX.maxIdx(empty_, 0));

      assertEquals(NOT_FOUND, ArraysX.minIdx(undefined_, 0));
      assertEquals(NOT_FOUND, ArraysX.maxIdx(undefined_, 0));
   }

   public void testEqualsString() {
      String[][] a = {
         {"A"}
         , {"A"}
         , {"B1", "b2"}
         , {"B1", "b2"}
      };
      String[][] a2 = {
         {"A"}
         , {"A"}
         , {"B1", "b2"}
         , {"B1", "b2"}
      };
      for (int i = 0; i < a.length; i++) {
         assertEquals(true, ArraysX.equals(a[i], a[i])); // self
         assertEquals(true, ArraysX.equals(a[i], a2[i]));
      }

      String[][] b = {
         {""}
         , {"Ax"}
         , {"B1"}
         , {"B1", "b2x"}
      };
      assertEquals(false, ArraysX.equals(null, null));
      for (int i = 0; i < a.length; i++) {
         assertEquals(false, ArraysX.equals(a[i], null));
         assertEquals(false, ArraysX.equals(null, b[i]));
         assertEquals(false, ArraysX.equals(a[i], b[i]));
      }
   }
}
