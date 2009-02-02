package kingroup.tmp.Util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.utilx.SetImmutable;
import javax.utilx.SetUniverse;

/**
 * Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */
public class SetImmutableTest extends TestCase {

  public static Test suite() {
      return new TestSuite(SetImmutableTest.class);
  }

  public SetImmutableTest(String name) {
    super(name);
  }

  public void testSetBitVectorEqualsTest1()
  {
    Object[] S1 = new Object[100];
    for(int i = 0; i < S1.length; i++)
    {
      S1[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(S1);

    SetImmutable bs1 = u.makeSet(S1);
    SetImmutable bs2 = u.makeSet(S1);
    assertEquals(bs1,bs1);
    assertEquals(bs2,bs2);
    assertEquals(bs1,bs2);
    assertEquals(bs2,bs1);
  }

  public void testSetBitVectorEqualsTest2()
  {
    Object[] S1 = new Object[32];
    for(int i = 0; i < S1.length; i++)
    {
      S1[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(S1);

    SetImmutable bs1 = u.makeSet(S1);
    SetImmutable bs2 = u.makeSet(S1);
    assertEquals(bs1,bs1);
    assertEquals(bs2,bs2);
    assertEquals(bs1,bs2);
    assertEquals(bs2,bs1);
  }

  public void testSetBitVectorCountingMeasureTest1() {

    Object[] all = new Object[100];
    Object[] S1 = new Object[75];
    for(int i = 0; i < 75; i++)
    {
      S1[i] = ""+i;
      all[i] = ""+i;
    }
    Object[] S2 = new Object[75];
    for(int i = 25; i < 100; i++)
    {
      S2[i-25] = ""+i;
      all[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(all);

    SetImmutable bs1 = u.makeSet(S1);
    SetImmutable bs2 = u.makeSet(S2);
    assertEquals(S1.length,bs1.countingMeasure());
    assertEquals(S2.length,bs2.countingMeasure());
  }

  public void testSetBitVectorIntersectionTest1() {

    Object[] all = new Object[100];
    Object[] S1 = new Object[75];
    for(int i = 0; i < 75; i++)
    {
      S1[i] = ""+i;
      all[i] = ""+i;
    }
    Object[] S2 = new Object[75];
    for(int i = 25; i < 100; i++)
    {
      S2[i-25] = ""+i;
      all[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(all);

    Object[] a = new Object[50];
    for(int i = 25; i < 75; i++)
    {
      a[i-25] = ""+i;
    }
    SetImmutable bs1 = u.makeSet(S1);
    SetImmutable bs2 = u.makeSet(S2);
    SetImmutable ans = u.makeSet(a);
    assertEquals(ans,bs1.intersection(bs2));
    assertEquals(ans,bs2.intersection(bs1));
  }

  public void testSetBitVectorUnionTest1() {

    Object[] all = new Object[100];
    Object[] S1 = new Object[75];
    for(int i = 0; i < 75; i++)
    {
      S1[i] = ""+i;
      all[i] = ""+i;
    }
    Object[] S2 = new Object[75];
    for(int i = 25; i < 100; i++)
    {
      S2[i-25] = ""+i;
      all[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(all);

    Object[] a = new Object[100];
    for(int i = 0; i < 100; i++)
    {
      a[i] = ""+i;
    }
    SetImmutable bs1 = u.makeSet(S1);
    SetImmutable bs2 = u.makeSet(S2);
    SetImmutable ans = u.makeSet(a);
    assertEquals(ans,bs1.union(bs2));
    assertEquals(ans,bs2.union(bs1));
  }

  public void testSetBitVectorSymmetricDifferenceTest1() {

    Object[] all = new Object[100];
    Object[] S1 = new Object[75];
    for(int i = 0; i < 75; i++)
    {
      S1[i] = ""+i;
      all[i] = ""+i;
    }
    Object[] S2 = new Object[75];
    for(int i = 25; i < 100; i++)
    {
      S2[i-25] = ""+i;
      all[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(all);

    Object[] a = new Object[50];
    for(int i = 0; i < 25; i++)
    {
      a[i] = ""+i;
    }
    for(int i = 75; i < 100; i++)
    {
      a[i-50] = ""+i;
    }
    SetImmutable bs1 = u.makeSet(S1);
    SetImmutable bs2 = u.makeSet(S2);
    SetImmutable ans = u.makeSet(a);
    assertEquals(ans,bs1.symmetricDifference(bs2));
    assertEquals(ans,bs2.symmetricDifference(bs1));
  }

  public void testSetBitVectorDifferenceTest1() {

    Object[] all = new Object[100];
    Object[] S1 = new Object[75];
    for(int i = 0; i < 75; i++)
    {
      S1[i] = ""+i;
      all[i] = ""+i;
    }
    Object[] S2 = new Object[75];
    for(int i = 25; i < 100; i++)
    {
      S2[i-25] = ""+i;
      all[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(all);

    Object[] a1 = new Object[25];
    for(int i = 0; i < 25; i++)
    {
      a1[i] = ""+i;
    }
    Object[] a2 = new Object[25];
    for(int i = 75; i < 100; i++)
    {
      a2[i-75] = ""+i;
    }
    SetImmutable bs1  = u.makeSet(S1);
    SetImmutable bs2  = u.makeSet(S2);
    SetImmutable ans1 = u.makeSet(a1);
    SetImmutable ans2 = u.makeSet(a2);
    assertEquals(ans1,bs1.difference(bs2));
    assertEquals(ans2,bs2.difference(bs1));
  }

  public void testSetBitVectorComplement1()
  {
    Object[] all = new Object[100];
    Object[] S1 = new Object[75];
    for(int i = 0; i < 75; i++)
    {
      S1[i] = ""+i;
      all[i] = ""+i;
    }
    Object[] A1 = new Object[25];
    for(int i = 75; i < 100; i++)
    {
      A1[i-75] = ""+i;
      all[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(all);

    SetImmutable bs1 = u.makeSet(S1);
    SetImmutable bs2 = u.getCompliment(bs1);
    SetImmutable ans = u.makeSet(A1);
    assertEquals(ans,bs2);

  }

  public void testSetBitVectorComplement2()
  {
    Object[] all = new Object[32];
    Object[] S1 = new Object[25];
    for(int i = 0; i < 25; i++)
    {
      S1[i] = ""+i;
      all[i] = ""+i;
    }
    Object[] A1 = new Object[7];
    for(int i = 25; i < 32; i++)
    {
      A1[i-25] = ""+i;
      all[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(all);

    SetImmutable bs1 = u.makeSet(S1);
    SetImmutable ans = u.makeSet(A1);
    SetImmutable bs2 = u.getCompliment(bs1);
    assertEquals(ans,bs2);

  }

//  public void testSetBitVectorToArrayTest1() {
//
//    SetUniverse U = new SetUniverse(100);
//    Object[] S1 = new Object[100];
//    for(int i = 0; i < S1.length; i++)
//    {
//      S1[i] = ""+i;
//    }
//    SetImmutable bs1  = U.makeSet(S1);
//    Object[] A1 = bs1.toArray();
//    assertEquals(S1.length, A1.length);
//    for(int i = 0; i < S1.length; i++)
//    {
//      assertEquals(S1[i],A1[i]);
//    }
//  }

//  public void testSetBitVectorToArrayTest2() {
//
//    Object[] S1 = new Object[32];
//    for(int i = 0; i < S1.length; i++)
//    {
//      S1[i] = ""+i;
//    }
//    SetUniverse U = new SetUniverse(S1);
//
//    SetImmutable bs1  = U.makeSet(S1);
//    Object[] A1 = bs1.toArray();
//    assertEquals(S1.length, A1.length);
//    for(int i = 0; i < S1.length; i++)
//    {
//      assertEquals(S1[i],A1[i]);
//    }
//  }

  public void testSetBitVectorHasObjectTest1()
  {
    Object[] S1 = new Object[100];
    for(int i = 0; i < S1.length; i++)
    {
      S1[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(S1);

    SetImmutable bs1  = u.makeSet(S1);
    for(int i = 0; i < S1.length; i++)
    {
      assertTrue(bs1.hasElement(u.getPos(S1[i])));
    }
  }

  public void testSetBitVectorGetAnyObject1() {

    Object[] S1 = new Object[32];
    for(int i = 0; i < S1.length; i++)
    {
      S1[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(S1);
    SetImmutable bs2;

    for(int i = 0; i < S1.length; i++)
    {
      bs2 = u.makeSet(new Object[]{""+i});
      assertNotNull(u.getObj(bs2.getAnyObject()));
    }
  }
  
  public void testSetBitVectorGetAnyObject2() {

    Object[] S1 = new Object[100];
    for(int i = 0; i < S1.length; i++)
    {
      S1[i] = ""+i;
    }
    SetUniverse u = new SetUniverse(S1);
    SetImmutable bs2;

    for(int i = 0; i < S1.length; i++)
    {
      bs2 = u.makeSet(new Object[]{""+i});
      assertNotNull(u.getObj(bs2.getAnyObject()));
    }
  }

}