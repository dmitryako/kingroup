package kingroup_v2.partition.simpson.genotype_dist;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2005, Time: 16:32:29
 */
public class GenotypeDistLocusMax_WRONG extends GenotypeDistAlg
{
  public double calcGenotypeDistance(SysPop pop, int idA, int idB)
  {
    assert(pop.getNumLoci() > 0);
    int L = 0;
    int maxDist = calcLocusDistanceSLOW(pop, L++, idA, idB);
    for (; L < pop.getNumLoci(); L++) {
      int locusDist = calcLocusDistanceSLOW(pop, L, idA, idB);
      if (maxDist < locusDist)
        maxDist = locusDist;
    }
    return maxDist;
  }
}
