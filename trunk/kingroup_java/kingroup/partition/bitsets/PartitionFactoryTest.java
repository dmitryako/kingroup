package kingroup.partition.bitsets;
import javax.iox.LOG;
import javax.utilx.bitset.CompBitSet;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Jan 13, 2005, Time: 1:01:36 PM
 */
public class PartitionFactoryTest {
  public static void main(String[] args) {
    PartitionFactoryTest self = new PartitionFactoryTest();
    self.testMakeByMoving();
    System.exit(0);
  }
  public void testMakeByMoving() {
    LOG.setTrace(true);
    int SIZE = 3;
    CompBitSet set001 = new CompBitSet();
    set001.set(2);
    CompBitSet set11 = new CompBitSet();
    set11.set(0);
    set11.set(1);
    Partition A = new Partition(SIZE);
    A.add(set001);
    A.add(set11);
    LOG.trace(this, "A=", A);
    PartitionFactory factory = new PartitionFactory(SIZE);
    Partition B = factory.makeByMoving(A, 1);
    LOG.trace(this, "B=", B);
    B = factory.makeByMoving(A, 1);
    LOG.trace(this, "B=", B);
    B = factory.makeByMoving(A, 1);
    LOG.trace(this, "B=", B);
  }
}
