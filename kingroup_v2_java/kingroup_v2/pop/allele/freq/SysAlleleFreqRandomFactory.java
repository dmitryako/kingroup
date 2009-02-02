package kingroup_v2.pop.allele.freq;

import tsvlib.project.ProjectLogger;

import javax.utilx.RandomSeed;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/03/2006, Time: 10:45:16
 */
public class SysAlleleFreqRandomFactory
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysAlleleFreqRandomFactory.class.getName());
  private final SysAlleleFreq freq;
  private final SysAlleleFreq cum;     //cumulative
  public SysAlleleFreqRandomFactory(SysAlleleFreq freq)
  {
    this.freq = freq;
    cum = SysAlleleFreqFactory.makeCumulative(freq);
  }

  /**
   * Select allele based on its population frequency
   * PRECOND: allele frequencies must be normalized to 1.
   * @param locIdx
   * @return randomly selected allele index
   */
  public int getRandomAllele(int locIdx)
  {
    double[] freqs = cum.getLocFreq(locIdx);
    int n = freqs.length;
    if ((float)freqs[n-1] != 1f)
    {
      String error = "Allele frequencies must be normalized to 1. Current norm="
        + (float)freqs[n-1];
      log.severe(error);
      throw new RuntimeException(error);
    }
    double x = RandomSeed.getInstance().nextDouble();
    return (int)Vec.findBinL(freqs, x);
  }

  public int getRandomAlleleSLOW(int locIdx) {
    double[] freqs = freq.getLocFreq(locIdx);
//    // log.info("locus = " + DoubleArr.toString(locus));
    RandomSeed rand = RandomSeed.getInstance();
    double x = rand.nextDouble();
//    // log.info("random value [0,1) x = " + x);
    double prob = 0;
    for (int a = 0; a < freqs.length; a++) {
      prob += freqs[a];
      if (x >= prob)
        continue;
//      // log.info("random allele = " + a);
      return (int) a;
    }
    return -1;
  }
}
