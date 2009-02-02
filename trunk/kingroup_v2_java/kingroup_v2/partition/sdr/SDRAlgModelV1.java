package kingroup_v2.partition.sdr;
import kingroup_v2.partition.ms2.MS2AlgModel;
import kingroup_v2.partition.dr.DRAlgModel;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2005, Time: 17:40:50
 */
public class SDRAlgModelV1
{
  private MS2AlgModel ms2AlgModel;
  private DRAlgModel drAlgModel;
  private boolean keepLargest = true;

  public SDRAlgModelV1()
  {
    ms2AlgModel = new MS2AlgModel();
    drAlgModel = new DRAlgModel();
  }
  public MS2AlgModel getMs2AlgModel()
  {
    return ms2AlgModel;
  }

  public void setMs2AlgModel(MS2AlgModel ms2AlgModel)
  {
    this.ms2AlgModel = ms2AlgModel;
  }

  public DRAlgModel getDrAlgModel()
  {
    return drAlgModel;
  }

  public void setDrAlgModel(DRAlgModel drAlgModel)
  {
    this.drAlgModel = drAlgModel;
  }

  public boolean getKeepLargest()
  {
    return keepLargest;
  }

  public void setKeepLargest(boolean keepLargest)
  {
    this.keepLargest = keepLargest;
  }
}
