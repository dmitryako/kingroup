package kingroup_v2.like.thompson;
import junit.framework.TestCase;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.kinship.KinshipSysPopFactory;
import kingroup_v2.kinship.like.KinshipLikeCalculator;
import kingroup_v2.kinship.like.KinshipLikeMtrx;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 31/03/2006, Time: 11:02:55
 */
public class ThompsonDiploidLikeCalculatorTest extends TestCase
{
  private static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private static final double EPS = 1e-10;

  public void testCalc() {
    log.start("ThompsonDiploidLikeCalculatorTest");

    final int N_TRIALS = 100;
    final int N_LOCI = 2;
    final int N_ALLELES = 10;

    Kinship kinship = new Kinship();
    KinshipIBD kinIBD = KinshipIBDFactory.makeFullSib();
    ThompsonIBD thomIBD = ThompsonIBDFactory.makeFullSib();
//    ThompsonIBD nullIBD = ThompsonIBDFactory.makeUnrelated();

//    SysAlleleFreq freq = SysAlleleFreqFactory.makeEquifrequent(N_LOCI, N_ALLELES);
    SysAlleleFreq freq = SysAlleleFreqFactory.makeRandom(N_LOCI, N_ALLELES);
    log.info("\nfreq=\n" + freq);

    for (int i = 0; i < N_TRIALS; i++) {
      kinIBD = KinshipIBDFactory.makeRand();
      thomIBD = ThompsonIBDFactory.makeFrom(kinIBD);

      SysPop pair = KinshipSysPopFactory.makePair(kinIBD, freq);
      log.info("i=" + i + "\npair=\n" + pair);

      KinshipLikeMtrx kinMtrx = new KinshipLikeMtrx(pair);
      kinMtrx.calcLogs(new KinshipLikeCalculator(pair, kinIBD, kinship));

      ThompsonLikeMtrx thomMtrx = new ThompsonLikeMtrx(pair);
      thomMtrx.calcLogs(new ThompsonLikeCalculator(pair, thomIBD));

      log.info("\nlike=" + kinMtrx.getLog(0, 1));
      assertEquals(kinMtrx.getLog(0, 1), thomMtrx.getLog(0, 1), EPS);
    }
  }

}
