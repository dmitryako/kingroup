package kingroup_v2.partition.distance;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionFactory;
import kingroup.partition.bitsets.RandomGroupSizeFactory;
import kingroup_v2.partition.distance.AlmudevarField1999.AlmudevarFieldDistance;

import javax.iox.LOG;
import javax.utilx.bitset.CompBitSet;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Nov 4, 2004, Time: 8:00:00 AM
 */
public class LitowDistanceJUnit extends TestCase {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(LitowDistanceJUnit.class);
  }
  public void testDistance() {
    LOG.setTrace(true);
    final int POP_SIZE = 50; // 50, population size == universe
    final int SIM_SIZE = 100;
    PartitionFactory gen = new RandomGroupSizeFactory(POP_SIZE);
    AlmudevarFieldDistance distAF = new AlmudevarFieldDistance();
    LitowDistance distL = new LitowDistance();
    GusfieldDistance distG = new GusfieldDistance();
//      LitowDistance distJNI = new LitowDistanceJNI();
    Partition partA;
    Partition partB;
    for (int i = 0; i < SIM_SIZE; i++) {
      partA = gen.makeRandom();
      partB = gen.makeRandom();
      int vAF = distAF.distance(partA, partB);
      int vL = distL.distance(partA, partB);
//         int v3 = distJNI.distance(partA, partB);
      int vG = distG.distance(partA, partB);
//         LOG.trace(this, "partition A=", partA.toString());
//         LOG.trace(this, "partition B=", partB.toString());
      assertEquals(vAF, vL);
//         assertEquals(v, v3);
      assertEquals(vL, vG);
    }
  }
  public void testDistance2() {
    LOG.setTrace(true);
    int SIZE = 10;
    Partition A = new Partition(SIZE);
    Partition B = new Partition(SIZE);
    CompBitSet bs;
    for (int i = 0; i < SIZE; i++) {
      bs = new CompBitSet();
      bs.set(i);
      A.add(bs);
    }
    bs = new CompBitSet();
    bs.set(2);
    bs.set(6);
    B.add(bs);
    bs = new CompBitSet();
    bs.set(0);
    bs.set(5);
    bs.set(7);
    B.add(bs);
    bs = new CompBitSet();
    bs.set(1);
    bs.set(4);
    bs.set(8);
    B.add(bs);
    bs = new CompBitSet();
    bs.set(3);
    bs.set(9);
    B.add(bs);
    AlmudevarFieldDistance distAF = new AlmudevarFieldDistance();
    LitowDistance distJNI = new LitowDistanceJNI();
    LitowDistance distL = new LitowDistance();
    int v = distAF.distance(A, B);
    int v3 = distJNI.distance(A, B);
    int v2 = distL.distance(A, B);
//      LOG.trace(this, "partition A=", A.toString());
//      LOG.trace(this, "partition B=", B.toString());
    assertEquals(v, v3);
    assertEquals(v, v2);
  }
  public void testDistance3() {
    LOG.setTrace(true);
    int SIZE = 10;
    Partition A = new Partition(SIZE);
    Partition B = new Partition(SIZE);
    CompBitSet bs;
    for (int i = 0; i < SIZE; i++) {
      bs = new CompBitSet();
      bs.set(i);
      A.add(bs);
    }
    bs = new CompBitSet();
    bs.set(0);
    bs.set(1);
    B.add(bs);
    bs = new CompBitSet();
    bs.set(2);
    bs.set(3);
    B.add(bs);
    bs = new CompBitSet();
    bs.set(4);
    bs.set(5);
    bs.set(6);
    B.add(bs);
    bs = new CompBitSet();
    bs.set(7);
    bs.set(8);
    bs.set(9);
    B.add(bs);
    AlmudevarFieldDistance distAF = new AlmudevarFieldDistance();
    LitowDistance distJNI = new LitowDistanceJNI();
    LitowDistance distL = new LitowDistance();
    int v = distAF.distance(A, B);
    int v3 = distJNI.distance(A, B);
    int v2 = distL.distance(A, B);
//      LOG.trace(this, "partition A=", A.toString());
//      LOG.trace(this, "partition B=", B.toString());
    assertEquals(v, v3);
    assertEquals(v, v2);
  }
}