package kingroup_v2.partition.ms;
import kingroup.algorithm.window.AlgAccessViaGroups;
import kingroup_v2.partition.simpson.SibshipAlg;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistAlg;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 15:43:14
 */
public class MSAlgAccessViaGroups  extends AlgAccessViaGroups
{
  public MSAlgAccessViaGroups(SysPop pop
    , GenotypeDistAlg alg
    , SibshipAlg sibship)
  {
    super(pop.size(), new MSAlgGroups(pop, alg, sibship));
  }
}
