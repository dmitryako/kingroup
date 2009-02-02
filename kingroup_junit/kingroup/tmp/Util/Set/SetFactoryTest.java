package kingroup.tmp.Util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import javax.utilx.SetUniverse;

/**
 * Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */
public class SetFactoryTest extends TestCase {

   public static Test suite() {
       return new TestSuite(SetFactoryTest.class);
   }

//  public void testUniverseBitVectorCreatSetTest1() {
//    Object[] S1 = new Object[32];
//    for(int i = 0; i < S1.length; i++)
//    {
//      S1[i] = ""+i;
//    }
//    SetUniverse U = new SetUniverse(S1);
//    SetImmutable bs1 = U.makeSet(S1);
//    SetImmutable bs2 = new SetImmutable(null,new int[]{0xFFFFFFFF},32);
//    assertEquals(bs2,bs1);
//  }

  public void testUniverseBitVectorGetPosTest1()
  {
    Object[] S1 = new Object[32];
    for(int i = 0; i < S1.length; i++)
    {
      S1[i] = "__"+i;
    }
    SetUniverse u = new SetUniverse(S1);
    for(int i = 0; i < S1.length; i++)
    {
      assertEquals(i,u.getPos("__"+i));
    }
  }

  public void testUniverseBitVectorGetPosTest2()
  {
    Object[] S1 = new Object[100];
    for(int i = 0; i < S1.length; i++)
    {
      S1[i] = "__"+i;
    }
    SetUniverse u = new SetUniverse(S1);
    for(int i = 0; i < S1.length; i++)
    {
      assertEquals(i,u.getPos("__"+i));
    }
  }

  public void testUniverseBitVectorGetObjTest1()
  {
    Object[] S1 = new Object[32];
    for(int i = 0; i < S1.length; i++)
    {
      S1[i] = "__"+i;
    }
    SetUniverse u = new SetUniverse(S1);
    for(int i = 0; i < S1.length; i++)
    {
      assertEquals("__"+i,u.getObj(i));
    }
  }

  public void testUniverseBitVectorGetObjTest2()
  {
    Object[] S1 = new Object[100];
    for(int i = 0; i < S1.length; i++)
    {
      S1[i] = "__"+i;
    }
    SetUniverse u = new SetUniverse(S1);
    for(int i = 0; i < S1.length; i++)
    {
      assertEquals("__"+i,u.getObj(i));
    }
  }


}