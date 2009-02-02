package kingroup_v2.partition.smc;
import kingroup_v2.partition.simpson.SIMPS2AlgModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/11/2005, Time: 08:35:44
 */
public class MCSAlgModel  extends SIMPS2AlgModel
{
  private boolean simpsonAlg;

  public boolean getSimpsonAlg()
  {
    return simpsonAlg;
  }

  public void setSimpsonAlg(boolean simpsonAlg)
  {
    this.simpsonAlg = simpsonAlg;
  }
}
