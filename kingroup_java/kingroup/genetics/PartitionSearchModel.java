package kingroup.genetics;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Jul 8, 2004, Time: 2:24:50 PM
 */
public class PartitionSearchModel {
  public PartitionSearchModel() {
    this.loadDefaults();
  }
  public PartitionSearchModel(PartitionSearchModel from) {
    from.copyTo(this);
  }
  // The method property.
  public static int RANDOM_WALK = 0;
  public static int DESCENDING_RATIO = 1;
  public static int EXHAUSTIVE_DESCENT = 2;
  private int method;  //search method: RANDOM_WALK, DESCENDING_RATIO, etc
  public int getMethod() {
    return method;
  }
  public void setMethod(int s) {
    method = s;
  }
  private int localSpaceLimit; // search space limit
  public int getLocalSpaceLimit() {
    return localSpaceLimit;
  }
  public void setLocalSpaceLimit(int s) {
    localSpaceLimit = s;
  }
  private boolean searchLocalSpace; // search space
  public boolean getSearchLocalSpace() {
    return searchLocalSpace;
  }
  public void setSearchLocalSpace(boolean s) {
    searchLocalSpace = s;
  }
  private boolean displaySorted; // display search results in likelihood descending order
  public boolean getDisplaySorted() {
    return displaySorted;
  }
  public void setDisplaySorted(boolean s) {
    displaySorted = s;
  }
  private boolean displaySearchSequence; //
  public boolean getDisplaySearchSequence() {
    return displaySearchSequence;
  }
  public void setDisplaySearchSequence(boolean s) {
    displaySearchSequence = s;
  }
  private boolean displayUniqueOnly;
  public boolean getDisplayUniqueOnly() {
    return displayUniqueOnly;
  }
  public void setDisplayUniqueOnly(boolean s) {
    displayUniqueOnly = s;
  }
  public void loadDefaults() {
    setMethod(PartitionSearchModel.DESCENDING_RATIO);
    setLocalSpaceLimit(100);
    setSearchLocalSpace(true);
    setDisplaySorted(true);
    setDisplaySearchSequence(false);
    setDisplayUniqueOnly(true);
  }
  public void copyTo(PartitionSearchModel bean) {
    bean.setMethod(getMethod());
    bean.setLocalSpaceLimit(getLocalSpaceLimit());
    bean.setSearchLocalSpace(getSearchLocalSpace());
    bean.setDisplaySorted(getDisplaySorted());
    bean.setDisplaySearchSequence(getDisplaySearchSequence());
    bean.setDisplayUniqueOnly(getDisplayUniqueOnly());
  }
}