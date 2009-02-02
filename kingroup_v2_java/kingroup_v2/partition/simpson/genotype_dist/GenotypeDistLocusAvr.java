package kingroup_v2.partition.simpson.genotype_dist;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2005, Time: 16:32:37
 */
public class GenotypeDistLocusAvr extends GenotypeDistAlg
{
  public double calcGenotypeDistance(SysPop pop, int idA, int idB)
  {
    assert(pop.getNumLoci() > 0);
    int dist = 0;
    for (int L = 0; L < pop.getNumLoci(); L++) {
      dist += calcLocusDistanceSLOW(pop, L, idA, idB);
    }
    return ((double) dist) / pop.getNumLoci();
  }
}
