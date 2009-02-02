package kingroup_v2.pop;
import java.util.ArrayList;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/09/2005, Time: 15:59:58
 */
public class UserGenotype extends ArrayList<UserLocus>{
  private String groupId;
  private String id;
  private String matId;
  private String patId;
//  private ArrayList<UserLocus> loci;
  public UserGenotype() {
    
  }
  public UserGenotype(int nLoci) {
    for (int i = 0; i < nLoci; i++) {
      add(new UserLocus());
    }
  }

//  public void add(UserLocus locus) {
//    loci.add(locus);
//  }
//  public UserLocus get(int i) {
//    return (UserLocus) loci.get(i);
//  }
  public String getPatId() {
    return patId;
  }
  public void setPatId(String patId) {
    this.patId = patId;
  }
  public String getMatId() {
    return matId;
  }
  public void setMatId(String matId) {
    this.matId = matId;
  }
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getGroupId() {
    return groupId;
  }
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }
  public int nLoci() {
    return size();
  }
}
