package kingroup_v2.pop.sample;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/10/2005, Time: 16:44:03
 */
public class PopParentId {
  public static final String ALLELE_DELIM = "/";
  public static final String DELIM = ", ";
  public static final int MAT = 0;
  public static final int PAT = 1;
  public static final int N_TYPES = 2;
  private boolean hasPatId;
  private boolean hasMatId;
  private String name;

  public PopParentId() {
  }
  public PopParentId(PopParentId from) {
    hasMatId = from.hasMatId;
    hasPatId = from.hasPatId;
  }
  public boolean getHasPatId() {
    return hasPatId;
  }
  public void setHasPatId(boolean hasPatId) {
    this.hasPatId = hasPatId;
  }
  public boolean getHasMatId() {
    return hasMatId;
  }
  public void setHasMatId(boolean hasMatId) {
    this.hasMatId = hasMatId;
  }
  public void copyFrom(PopParentId from) {
    hasMatId = from.hasMatId;
    hasPatId = from.hasPatId;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  public int getNumTypes()
  {
    return N_TYPES;    
  }
}
