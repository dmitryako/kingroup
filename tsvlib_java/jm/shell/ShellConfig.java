package jm.shell;
import java.util.ArrayList;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 4:20:03 PM
 */
public class ShellConfig {
  private ShellLS LS;
  private ArrayList<Shell> arr = new ArrayList<Shell>();
  public ShellConfig(Shell sh) {
    LS = new ShellLS(sh.LS);
    arr.add(sh);
  }
  public ShellConfig addShell(Shell sh, ShellLS LS) {
    arr.add(sh);
    this.LS = LS;
    return this;
  }
  public Shell getShell(int i) {
    return arr.get(i);
  }
  final public boolean equals(Object obj) {
    if (this == obj)
      return true;
    ShellConfig fc = (ShellConfig) obj;
    if (fc.size() != this.size())
      return false;
    if (!fc.LS().equals(LS()))
      return false;
    for (int s = 0; s < arr.size(); s++) {
      Shell sh = this.getShell(s);
      Shell sh2 = fc.getShell(s);
      if (!sh.equals(sh2))
        return false;
    }
    return true;
  }
  private int size() {
    return arr.size();
  }
  final public ShellLS LS() {
    return LS;
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
//      if (base == null)
//         buff.append(getShell.toString());
//      else
//         buff.append(base.toString()).append(getShell.toString());
    for (int i = 0; i < arr.size(); i++) {
      buff.append(getShell(i).toString());
    }
    buff.append(" ").append(LS.toString());
    return buff.toString();
  }
}
