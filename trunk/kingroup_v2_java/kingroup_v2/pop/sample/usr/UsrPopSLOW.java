package kingroup_v2.pop.sample.usr;
import kingroup_v2.pop.UserGenotype;
import kingroup_v2.pop.UserLocus;
import kingroup_v2.pop.allele.freq.UsrAlleleFreq;
import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
import java.util.ArrayList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/09/2005, Time: 13:33:27
 */
public class UsrPopSLOW extends UserPopFormat {
  private static ProjectLogger log = ProjectLogger.getLogger(UsrPopSLOW.class.getName());
  private UsrAlleleFreq freq;
  private ArrayList<UserGenotype> genotypes;

  public UsrPopSLOW() {
    genotypes = new ArrayList<UserGenotype>();
  }
  public void shallowCopyFrom(UsrPopSLOW from) {
    super.shallowCopyFrom(from);
    freq = from.freq;
    genotypes = from.genotypes;
  }

  public void resetGenotypes()
  {
    genotypes = new ArrayList<UserGenotype>();
  }
  public void addGenotype(UserGenotype g) {
    genotypes.add(g);
  }
  public UserGenotype getGenotype(int i) {
    return genotypes.get(i);
  }
  public String getPatId(int i) {
    return getGenotype(i).getPatId();
  }
  public String getMatId(int i) {
    return getGenotype(i).getMatId();
  }
  public String getId(int i) {
    return getGenotype(i).getId();
  }
  public int getGroupNum(int i) {
    return -1;
  }
  public String getGroupId(int i) {
   // must be done this way so it could be overwitten in UsrPopPart
    return getGenotype(i).getGroupId();
  }

  public UserLocus getLocus(int i, int L) {
    return getGenotype(i).get(L);
  }
  public String getAllele(int i, int L, int type) {
    UserLocus loc = getGenotype(i).get(L);
    return loc.getAllele(type);
  }

  public UsrAlleleFreq getFreq() {
    return freq;
  }
  public void setFreq(UsrAlleleFreq freq) {
    this.freq = freq;
  }
  public int size() {
    return genotypes.size();
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    buff.append(super.toString()).append(SysProp.EOL);
    for (int i = 0; i < size(); i++) {
      buff.append(toString(i)).append(SysProp.EOL);
    }
    return buff.toString();
  }
  public String toString(int i) {
    String tab = ",\t";
    StringBuffer buff = new StringBuffer();
    buff.append("[" + i + "]=");
//    buff.append("id=").append(getId(i)).append(tab);
    buff.append(getId(i)).append(tab);
    buff.append("grp=").append(getGroupId(i)).append(tab);
    buff.append("mid=").append(getMatId(i)).append(tab);
    buff.append("pid=").append(getPatId(i)).append(tab);
    for (int L = 0; L < getNumLoci(); L++)
    {
      buff.append(getAllele(i, L, MAT));
      buff.append(ALLELE_DELIM);
      buff.append(getAllele(i, L, PAT));
      if (L != getNumLoci() - 1)
        buff.append(tab);
    }
    return buff.toString();
  }
}