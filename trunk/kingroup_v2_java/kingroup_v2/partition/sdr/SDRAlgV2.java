package kingroup_v2.partition.sdr;
import kingroup.algorithm.window.AlgWinAccessOrder;
import kingroup_v2.partition.dr.DRAlg;
import kingroup_v2.partition.dr.DRAlgModel;
import kingroup_v2.partition.ms.MSAlgAccessViaGroups;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/02/2006, Time: 14:43:33
 */
public class SDRAlgV2 extends DRAlg
{
  public SDRAlgV2(SysPop pop, DRAlgModel model)
  {
    super(pop, model);

    AlgWinAccessOrder order = new MSAlgAccessViaGroups(pop
      , new GenotypeDistLocusAvr()
      , new DiploidSibship());

    setOrder(order);
  }
}
