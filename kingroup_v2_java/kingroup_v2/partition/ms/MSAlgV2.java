package kingroup_v2.partition.ms;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/01/2006, Time: 09:54:49
 */
public class MSAlgV2  extends MSAlg
{
  public MSAlgV2(SysPop pop, MSAlgModel model) {
    super(pop, model
      , new MSAlgAccessViaGroups(pop, model.getGenotypeDistAlg(), model.getSibshipAlg()));
  }
}