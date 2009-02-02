package kingroup_v2.partition.distance.AlmudevarField1999;
import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.partition.bitsets.Partition;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 15, 2004, Time: 9:37:12 AM
 */
public class AlmudevarFieldDistanceJUnit extends BitSetPartitionTest {
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(AlmudevarFieldDistanceJUnit.class);
  }
  protected void setUp() {
    super.setUp();
  }
  public void testDistance() {
    AlmudevarFieldDistance dist = new AlmudevarFieldDistance();
    assertEquals(0, dist.distance(a_b_c, a_b_c));
    assertEquals(1, dist.distance(a_b_c, ab_c));
    assertEquals(1, dist.distance(a_b_c, a_bc));
    assertEquals(1, dist.distance(a_b_c, ac_b));
    assertEquals(2, dist.distance(a_b_c, abc));
    assertEquals(0, dist.distance(ab_c, ab_c));
    assertEquals(1, dist.distance(ab_c, a_b_c));
    assertEquals(1, dist.distance(ab_c, a_bc));
    assertEquals(1, dist.distance(ab_c, ac_b));
    assertEquals(1, dist.distance(ab_c, abc));
  }
  public void testDistance2() {
    AlmudevarFieldDistance dist = new AlmudevarFieldDistance();
    int UNIVERSE_SIZE = 3;
    Partition c_b_a = new Partition(UNIVERSE_SIZE);
    c_b_a.add(bs001);
    c_b_a.add(bs010);
    c_b_a.add(bs100);
    Partition a_c_b = new Partition(UNIVERSE_SIZE);
    c_b_a.add(bs100);
    c_b_a.add(bs001);
    c_b_a.add(bs010);
    assertEquals(0, dist.distance(c_b_a, a_c_b));
  }
}

