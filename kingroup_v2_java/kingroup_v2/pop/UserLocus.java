package kingroup_v2.pop;
import java.util.ArrayList;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/09/2005, Time: 16:12:47
 */
public class UserLocus {
  private static final String ALLELE_DELIM = " / ";
  private ArrayList<String> alleles;
  public static final int MAX_SIZE = 2;
  public UserLocus() {
    alleles = new ArrayList();
  }
  public void add(String allele) {
    alleles.add(allele);
  }
  public String getAllele(int i) {
    if (i >= alleles.size()) {
      return "";
    }
    return alleles.get(i);
  }
  public int size() {
    return alleles.size();
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size(); i++) {
      buff.append(getAllele(i));
      if (i != size() - 1) {
        buff.append(ALLELE_DELIM);
      }
    }
    return buff.toString();
  }
}
