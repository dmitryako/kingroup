package kingroup_v2.pop.sample.usr;
import kingroup_v2.pop.sample.PopFormat;

import javax.utilx.arrays.StrVec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/09/2005, Time: 06:02:04
 */
public class UserPopFormat extends PopFormat {
  private String[] locusIds = new String[0];
  private String idHeader;
  private String groupIdHeader;
  private String matIdHeader;
  private String patIdHeader;
  public UserPopFormat() {
  }
  public void shallowCopyFrom(UserPopFormat from) {
    super.copyFrom(from);
    locusIds = from.locusIds;
    idHeader = from.idHeader;
    groupIdHeader = from.groupIdHeader ;
    matIdHeader = from.matIdHeader;
    patIdHeader = from.patIdHeader;
  }
  public String[] getLocusIds() {
    return locusIds;
  }
  public void setLocusIds(String[] locusIds) {
    this.locusIds = locusIds;
  }
  public void setLocusId(int i, String s) {
    locusIds[i] = s;
  }
  public String getLocusId(int i) {
    return locusIds[i];
  }
  public int getNumLoci() {
    return locusIds.length;
  }
  public void setIdHeader(String idHeader) {
    this.idHeader = idHeader;
  }
  public String getIdHeader() {
    return idHeader;
  }
  public void setGroupIdHeader(String groupIdHeader) {
    this.groupIdHeader = groupIdHeader;
  }
  public String getGroupIdHeader() {
    return groupIdHeader;
  }
  public void setMatIdHeader(String matIdHeader) {
    this.matIdHeader = matIdHeader;
  }
  public String getMatIdHeader() {
    return matIdHeader;
  }
  public void setPatIdHeader(String patIdHeader) {
    this.patIdHeader = patIdHeader;
  }
  public String getPatIdHeader() {
    return patIdHeader;
  }
  public String toString() {
    String tab = ",\t";
    StringBuffer buff = new StringBuffer();
    buff.append(getIdHeader()).append(tab);
    buff.append(getGroupIdHeader()).append(tab);
    buff.append(getMatIdHeader()).append(tab);
    buff.append(getPatIdHeader()).append(tab);
    buff.append(StrVec.toCSV(locusIds)).append(tab);
    return buff.toString();
  }
}
