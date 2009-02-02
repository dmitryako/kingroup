package kingroup_v2.pop.sample;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/09/2005, Time: 07:04:53
 */
public class PopFormat extends PopParentId
{
  private boolean hasGroupId;
  private boolean hasId;
  public PopFormat(PopFormat from) {
    super(from);
    hasGroupId = from.hasGroupId;
    hasId = from.hasId;
  }
  public PopFormat() {
  }
  public void loadDefault() {
    setHasGroupId(true);
    setHasId(true);
  }
  public boolean getHasGroupId() {
    return hasGroupId;
  }
  public void setHasGroupId(boolean hasGroupId) {
    this.hasGroupId = hasGroupId;
  }
  public boolean getHasId() {
    return hasId;
  }
  public void setHasId(boolean hasId) {
    this.hasId = hasId;
  }
  public void copyFrom(PopFormat from) {
    super.copyFrom(from);
    hasGroupId = from.hasGroupId;
    hasId = from.hasId;
  }
}
