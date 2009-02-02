package kingroup_v2.like.milligan;
import junit.framework.TestCase;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.kinship.KinshipSysPopFactory;
import kingroup_v2.kinship.like.KinshipLikeCalculator;
import kingroup_v2.kinship.like.KinshipLikeMtrx;
import kingroup_v2.like.thompson.ThompsonIBD;
import kingroup_v2.like.thompson.ThompsonIBDFactory;
import kingroup_v2.like.thompson.ThompsonLikeCalculator;
import kingroup_v2.like.thompson.ThompsonLikeMtrx;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/03/2006, Time: 16:03:10
 */
public class MilliganDiploidLikeCalculatorTest extends TestCase
{
  private static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private static final double EPS = 1e-10;

  public void testCalc() {
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
      SysPop pair = KinshipSysPopFactory.makePair(kinIBD, freq);
      log.info("i=" + i + "\npair=\n" + pair);

      KinshipLikeMtrx kinMtrx = new KinshipLikeMtrx(pair);
      kinMtrx.calcLogs(new KinshipLikeCalculator(pair, kinIBD, kinship));

      ThompsonLikeMtrx thomMtrx = new ThompsonLikeMtrx(pair);
      thomMtrx.calcLogs(new ThompsonLikeCalculator(pair, thomIBD));

      assertEquals(kinMtrx.getLog(0, 1), thomMtrx.getLog(0, 1), EPS);
    }
  }
}
