package kingroup_v2.partition.ms;
import kingroup_v2.partition.ms2.MS2AlgModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2005, Time: 17:26:06
 */
public class MSAlgModel extends MS2AlgModel
{
  private int windSize;

  public int getWindSize()
  {
    return windSize;
  }

  public void setWindSize(int windSize)
  {
    this.windSize = windSize;
  }
}
