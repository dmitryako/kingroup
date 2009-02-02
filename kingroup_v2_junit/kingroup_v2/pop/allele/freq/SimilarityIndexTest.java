package kingroup_v2.pop.allele.freq;
import junit.framework.TestCase;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import tsvlib.project.ProjectLogger;

/**
 * Created by: jc1386591
 * Date: 8/06/2006. Time: 14:02:18
 */
public class SimilarityIndexTest extends TestCase {
  private static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private static final double EPS = 1e-10;
  public void testCalc() {
    final int N_TRIALS = 100;
    final int N_LOCI = 10;
    final int N_ALLELES = 10;

    SysAlleleFreq freq = SysAlleleFreqFactory.makeEquifrequent(N_LOCI, N_ALLELES);
//    SysAlleleFreq freq = SysAlleleFreqFactory.makeRandom(N_LOCI, N_ALLELES);
    log.info("\nfreq=\n" + freq);

    double[] exactArr = SimilarityIndex.calcFromFreq(freq);
    double exact = SimilarityIndex.averageOverLoci(exactArr);
    log.info("\nexact=\n" + exact);
    assertEquals((2.*N_ALLELES - 1.) / (N_ALLELES * N_ALLELES), exact, EPS);

    int POP_SIZE = 10;
    SysPop pop = SysPopFactory.makeUnrelated(POP_SIZE, freq);

  }
  public void testCalcSymmDist() {
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;
    double eps = 1.e-10;

    assertEquals(1, SimilarityIndex.calcSimilarity(a, a, a, a), eps);

    assertEquals(1, SimilarityIndex.calcSimilarity(a, b, a, b), eps);
    assertEquals(1, SimilarityIndex.calcSimilarity(a, b, b, a), eps);

    assertEquals(0.75, SimilarityIndex.calcSimilarity(a, a, a, b), eps);
    assertEquals(0.75, SimilarityIndex.calcSimilarity(b, a, a, a), eps);

    assertEquals(0.5, SimilarityIndex.calcSimilarity(a, b, a, c), eps);
    assertEquals(0.5, SimilarityIndex.calcSimilarity(b, a, a, c), eps);
    assertEquals(0.5, SimilarityIndex.calcSimilarity(b, a, c, a), eps);

    assertEquals(0, SimilarityIndex.calcSimilarity(a, a, d, c), eps);
    assertEquals(0, SimilarityIndex.calcSimilarity(a, a, c, d), eps);

    assertEquals(0, SimilarityIndex.calcSimilarity(a, b, c, d), eps);
    assertEquals(0, SimilarityIndex.calcSimilarity(a, b, d, c), eps);
  }
}
