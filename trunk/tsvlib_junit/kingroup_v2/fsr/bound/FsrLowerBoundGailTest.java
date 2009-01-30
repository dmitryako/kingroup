package kingroup_v2.fsr.bound;
import junit.framework.TestCase;
import kingroup_v2.fsr.bound.FsrLowerBoundGail;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/11/2005, Time: 13:34:05
 */
public class FsrLowerBoundGailTest extends TestCase
{
  public void testCalcGailP()
  {
    double eps = 1e-10;
    int na = 10; // num of alleles
    int r = 2;
    FsrLowerBoundGail bound = new FsrLowerBoundGail(r, na);
    assertEquals(0.0095, 0.5 * bound.calcGailDist(), eps);
//    assertEquals(boundMax.calcGailP(r, na), boundMax.calcSlowP(r, na), eps);

//    r = 3;
//    FsrLBoundApproxGSOne bound4 = new FsrLBoundApproxGSOne(4, na);
//    assertEquals(boundMax.calcGailP(), bound4.calcGailP(), eps);

//    assertEquals(boundMax.calcGailP(r, na), boundMax.calcSlowP(r, na), eps);
  }
}
