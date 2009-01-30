package kingroup_v2.kinship.like;
import junit.framework.TestCase;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.kinship.KinshipSysPopFactory;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 31/03/2006, Time: 11:05:15
 */
public class KinshipLikeCalculatorTest extends TestCase
{
  private static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private static final double EPS = 1e-10;

  public void testCalcExamples() {
    final int N_LOCI = 2;
    final int N_ALLELES = 10;

    Kinship kinship = new Kinship();
    KinshipIBD kinIBD = KinshipIBDFactory.makeFullSib();

//    SysAlleleFreq freq = SysAlleleFreqFactory.makeEquifrequent(N_LOCI, N_ALLELES);
    SysAlleleFreq freq = SysAlleleFreqFactory.makeRandom(N_LOCI, N_ALLELES);
    log.info("\nfreq=\n" + freq);

    SysPop pair = KinshipSysPopFactory.makePair(kinIBD, freq);
    log.info("\npair=\n" + pair);

    KinshipLikeMtrx kinMtrx = new KinshipLikeMtrx(pair);

    // self: Rm=Rp=1
    KinshipIBD ibd = new KinshipIBD();
    ibd.setRm(1);
    ibd.setRp(1);
    kinMtrx.calcLogs(new KinshipLikeCalculator(pair, ibd, kinship));

//    assertEquals(kinMtrx.getLog(0, 1), thomMtrx.getLog(0, 1), EPS);
  }
}
