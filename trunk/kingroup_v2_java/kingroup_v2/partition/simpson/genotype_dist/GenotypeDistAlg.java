package kingroup_v2.partition.simpson.genotype_dist;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2005, Time: 16:23:04
 */
public abstract class GenotypeDistAlg
{
  public abstract double calcGenotypeDistance(SysPop pop, int x, int y);
  public int calcLocusDistanceSLOW(SysPop pop, int locIdx, int x, int y) {
    int a = pop.getAllele(x, locIdx, pop.PAT);
    int b = pop.getAllele(x, locIdx, pop.MAT);
    int c = pop.getAllele(y, locIdx, pop.PAT);
    int d = pop.getAllele(y, locIdx, pop.MAT);
    return calcSymmDist(a, b, c, d);
  }
  public static int calcAsymmDist(int a, int b, int c, int d) {
    int count = 2;
    if (a == c || a == d)
      count--;
    if (b == c || b == d)
      count--;
    return count;
  }
  public static int calcSymmDist(int a, int b, int c, int d) {
    int countA = calcAsymmDist(a, b, c, d);
    int countB = calcAsymmDist(c, d, a, b);
    return Math.max(countA, countB);
  }
}
