package kingroup_v2.partition;
import kingroup.partition.bitsets.Partition;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/05/2005, Time: 12:01:25
 */
public abstract class PartitionAlg {
  private boolean showProgress;
  abstract public Partition partition();
  public void setShowProgress(boolean showProgress) {
    this.showProgress = showProgress;
  }
  public boolean isShowProgress() {
    return showProgress;
  }
}
