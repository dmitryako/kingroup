package kingroup_v2.partition.ms2;
import kingroup_v2.partition.simpson.DiploidSibship;
import kingroup_v2.partition.simpson.SibshipAlg;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistAlg;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2005, Time: 17:28:20
 */
public class MS2AlgModel
{
  private GenotypeDistAlg genotypeDistAlg;
  private SibshipAlg sibshipAlg;

  public MS2AlgModel()
  {
    setSibshipAlg(new DiploidSibship());
    setGenotypeDistAlg(new GenotypeDistLocusAvr());
  }
  public GenotypeDistAlg getGenotypeDistAlg()
  {
    return genotypeDistAlg;
  }

  public void setGenotypeDistAlg(GenotypeDistAlg genotypeDistAlg)
  {
    this.genotypeDistAlg = genotypeDistAlg;
  }

  public SibshipAlg getSibshipAlg()
  {
    return sibshipAlg;
  }

  public void setSibshipAlg(SibshipAlg sibshipAlg)
  {
    this.sibshipAlg = sibshipAlg;
  }
}
