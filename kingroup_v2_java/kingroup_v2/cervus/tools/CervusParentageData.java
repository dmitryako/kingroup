package kingroup_v2.cervus.tools;

import javax.iox.TextFile;

import javax.utilx.pair.StringPair;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/01/2006, Time: 09:28:10
 */
public class CervusParentageData extends ArrayList<StringPair>{
  private static StringBuffer buff = new StringBuffer();
  private TextFile inputData;

  public TextFile getInputData() {
    return inputData;
  }

  public void setInputData(TextFile inputData) {
    this.inputData = inputData;
  }

  public int countErrors() {
    TreeSet<String> set = new TreeSet<String>();
    for (int i = 0; i < size(); i++) {
      StringPair p = get(i);
      String n = getGroupName(p.a);
      String n2 = getGroupName(p.b);
      if (!n.equals(n2))
        set.add(p.a); // ONE ERROR per offspring
    }
    return set.size();
  }

  private String getGroupName(String b) {
    buff.setLength(0);
    return b.substring(0, b.indexOf('_') + 1);
  }
}
