package kingroup_v2.fsr;
import junit.framework.TestCase;
import kingroup_v2.fsr.bound.FsrLBoundMonteCarlo;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/11/2005, Time: 14:47:29
 */
public class FsrLimitEmpiricalTest extends TestCase
{

  public void testMakeOffspringAllele()
  {
    int na = 10;
    int r = 3;
    int nLoci = 1;
    FsrLBoundMonteCarlo limit = new FsrLBoundMonteCarlo(r, na, nLoci);
    assertEquals(0, limit.idxFromAlleles(0, 0));
    assertEquals(9, limit.idxFromAlleles(9, 9));
    assertEquals(10, limit.idxFromAlleles(1, 0));
    assertEquals(10, limit.idxFromAlleles(0, 1));
    assertEquals(54, limit.idxFromAlleles(8, 9));
    assertEquals(54, limit.idxFromAlleles(9, 8));
  }
}
