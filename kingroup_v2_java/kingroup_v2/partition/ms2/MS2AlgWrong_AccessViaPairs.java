package kingroup_v2.partition.ms2;
import kingroup_v2.partition.ms.MSAlgAccessViaPairs;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 26/06/2005, Time: 12:16:01
 */
public class MS2AlgWrong_AccessViaPairs extends MS2Alg {
  public MS2AlgWrong_AccessViaPairs(SysPop pop)
  {
    super(pop, new MS2AlgModel()
      , new MSAlgAccessViaPairs(pop, new GenotypeDistLocusAvr()));
  }
  public MS2AlgWrong_AccessViaPairs(SysPop pop, MS2AlgModel model)
  {
    super(pop, model, new MSAlgAccessViaPairs(pop, model.getGenotypeDistAlg()));
  }
}
