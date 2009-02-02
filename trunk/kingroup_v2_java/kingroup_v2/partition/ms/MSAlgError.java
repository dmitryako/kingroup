package kingroup_v2.partition.ms;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/05/2005, Time: 17:43:35
 */
public class MSAlgError extends MSAlg {
  public MSAlgError(SysPop pop, MSAlgModel model) {
    super(pop, model, new MSAlgAccessViaPairs(pop, model.getGenotypeDistAlg()));
  }
}
