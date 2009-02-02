package kingroup_v2.pop.allele.freq;
import junit.framework.TestCase;
import kingroup_v2.pop.sample.PopBuilderModel;
import tsvlib.project.ProjectLogger;

import javax.langx.SystemX;
import javax.utilx.RandomSeed;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/03/2006, Time: 11:09:35
 */
public class SysAlleleFreqRandomFactoryTest extends TestCase
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysAlleleFreqRandomFactoryTest.class.getName());
  private static final int N_ALLELES = 10;
  private static final int N_LOCI = 10;
  public static final long TIME_SCALE = 1000000000;

  public void testMakeRandomAllele()
  {
    PopBuilderModel builder = new PopBuilderModel();
    builder.setNumAlleles(N_ALLELES);
    builder.setNumLoci(N_LOCI);
    builder.setParentAlleleFreqType(builder.FREQ_RANDOM);
//    builder.setParentAlleleFreqType(builder.FREQ_EQUAL);
    SysAlleleFreq freq = SysAlleleFreqFactory.makeSysAlleleFreq(builder, null);

    RandomSeed rand = RandomSeed.getInstance();
    SysAlleleFreqRandomFactory randFreq = new SysAlleleFreqRandomFactory(freq);
    log.info("freq=\n" + freq);
    int locIdx = 0;
    int a, a2;

    final int N_TRIALS = 1000000;
    for (int i = 0; i < N_TRIALS; i++) {
      rand.setSeed(i);
      a = freq.getRandomAllele(locIdx);
      rand.setSeed(i);
      a2 = randFreq.getRandomAlleleSLOW(locIdx);
      if (a != a2)  {
        log.info("a != a2 @" + i);
      }
      assertEquals(a, a2);
    }

    long time = SystemX.time();
    for (int i = 0; i < N_TRIALS; i++) {
      a = freq.getRandomAllele(locIdx);
    }
    double dtime = (double) (SystemX.time() - time) / TIME_SCALE;
    log.info("\nfast time = " + (float)dtime);

    time = SystemX.time();
    for (int i = 0; i < N_TRIALS; i++) {
      a2 = randFreq.getRandomAlleleSLOW(locIdx);
    }
    dtime = (double) (SystemX.time() - time) / TIME_SCALE;
    log.info("\nslow time = " + (float)dtime);
  }
}
