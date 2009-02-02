package kingroup_v2.partition.ms;
import kingroup.algorithm.window.AlgAccessViaPairs;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistAlg;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/05/2005, Time: 16:20:25
 */
public class MSAlgAccessViaPairs extends AlgAccessViaPairs {
  public MSAlgAccessViaPairs(SysPop pop, GenotypeDistAlg alg) {
    super(pop.size(), new MSAlgPairs(pop, alg));
  }
}
