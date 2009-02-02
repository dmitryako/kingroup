package kingroup_v2.partition.simpson.genotype_dist;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2005, Time: 16:32:49
 */
public class GenotypeDistLocusLog  extends GenotypeDistLocusAvr
{
  public double calcGenotypeDistance(SysPop pop, int idA, int idB)
  {
    double dist = 2. - super.calcGenotypeDistance(pop, idA, idB);
    if (dist == 0)
      return Double.MAX_VALUE;
    return -Math.log(dist / 2.);
  }
}
