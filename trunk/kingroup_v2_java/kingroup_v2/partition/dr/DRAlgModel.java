package kingroup_v2.partition.dr;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBDFactory;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2005, Time: 17:41:56
 */
public class DRAlgModel
{
  private Kinship kinship;

  public DRAlgModel()
  {
    kinship = new Kinship();
    kinship.loadDefault();
    kinship.setComplexPrimIBD(KinshipIBDFactory.makeFullSib());
    kinship.setComplexNullIBD(KinshipIBDFactory.makeUnrelated());
    kinship.setTreatHaploid(Kinship.HAPLOID_IGNORE);
  }

  public Kinship getKinship()
  {
    return kinship;
  }

  public void setKinship(Kinship kinship)
  {
    this.kinship = kinship;
  }
}
