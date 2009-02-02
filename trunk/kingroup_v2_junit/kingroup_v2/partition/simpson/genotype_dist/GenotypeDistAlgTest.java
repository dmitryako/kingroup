package kingroup_v2.partition.simpson.genotype_dist;

import junit.framework.TestCase;

/**
 * Created by: jc1386591
 * Date: 8/06/2006. Time: 13:52:13
 */
public class GenotypeDistAlgTest extends TestCase {
  public void testCalcSymmDist() {
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;

    assertEquals(0, GenotypeDistAlg.calcSymmDist(a, a, a, a));

    assertEquals(0, GenotypeDistAlg.calcSymmDist(a, b, a, b));
    assertEquals(0, GenotypeDistAlg.calcSymmDist(a, b, b, a));

    assertEquals(1, GenotypeDistAlg.calcSymmDist(a, a, a, b));
    assertEquals(1, GenotypeDistAlg.calcAsymmDist(b, a, a, a));

    assertEquals(1, GenotypeDistAlg.calcSymmDist(a, b, a, c));
    assertEquals(1, GenotypeDistAlg.calcSymmDist(b, a, a, c));
    assertEquals(1, GenotypeDistAlg.calcSymmDist(b, a, c, a));

    assertEquals(2, GenotypeDistAlg.calcSymmDist(a, a, d, c));
    assertEquals(2, GenotypeDistAlg.calcSymmDist(a, a, c, d));

    assertEquals(2, GenotypeDistAlg.calcSymmDist(a, b, c, d));
    assertEquals(2, GenotypeDistAlg.calcSymmDist(a, b, d, c));
  }
}
