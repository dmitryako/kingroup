package kingroup_v2.partition.ms2;
import kingroup_v2.partition.ms.MSAlgAccessViaGroups;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/01/2006, Time: 14:37:04
 */
public class MS2AlgV2 extends MS2Alg {
  public MS2AlgV2(SysPop pop)
  {
    super(pop, new MS2AlgModel(), new MSAlgAccessViaGroups(pop
      , new GenotypeDistLocusAvr(), new DiploidSibship()));
  }
  public MS2AlgV2(SysPop pop, MS2AlgModel model) {
    super(pop, model
      , new MSAlgAccessViaGroups(pop
      , model.getGenotypeDistAlg()
      , model.getSibshipAlg()));
  }
}
