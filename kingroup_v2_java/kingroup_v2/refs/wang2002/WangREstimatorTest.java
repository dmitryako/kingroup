package kingroup_v2.refs.wang2002;
import junit.framework.TestCase;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/09/2006, Time: 13:06:23
 */
public class WangREstimatorTest extends TestCase
{
  public void testIsP() {
    assertEquals(true, REstimator_W.isP1(0, 0, 0, 0));
    assertEquals(true, REstimator_W.isP1(0, 1, 1, 0));
    assertEquals(true, REstimator_W.isP1(1, 0, 1, 0));
    assertEquals(true, REstimator_W.isP1(1, 0, 0, 1));
    assertEquals(true, REstimator_W.isP1(1, 0, 1, 0));

    assertEquals(true, REstimator_W.isP2(0, 0, 1, 0));
    assertEquals(true, REstimator_W.isP2(0, 0, 0, 1));
    assertEquals(true, REstimator_W.isP2(0, 1, 1, 1));
    assertEquals(true, REstimator_W.isP2(1, 0, 1, 1));

    assertEquals(true, REstimator_W.isP3(1, 0, 2, 1));
    assertEquals(true, REstimator_W.isP3(1, 0, 1, 2));
    assertEquals(true, REstimator_W.isP3(0, 1, 1, 2));
    assertEquals(true, REstimator_W.isP3(0, 1, 2, 1));
  }
}
