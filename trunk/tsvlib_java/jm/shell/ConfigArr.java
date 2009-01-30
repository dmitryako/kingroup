package jm.shell;
import jm.JMatrixException;

import javax.langx.SysProp;
import javax.iox.LOG;
import java.util.ArrayList;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 4:23:28 PM
 */
public class ConfigArr extends ArrayList {
  public ConfigArr() {
  }
  public ConfigArr(ConfigArr from) {
    if (from == null)
      return;
    for (int i = 0; i < from.size(); i++) {
      add(from.getConfig(i));
    }
  }
  public boolean add(Object obj) {
    String error = "use addLine(ShellConfig) instead";
    LOG.error(this, error, "");
    throw new JMatrixException(error);
  }
  public boolean add(ShellConfig fc) {
    if (find(fc))
      return false;
    return super.add(fc);
  }
  public boolean find(ShellConfig fc) {
    for (int i = 0; i < size(); i++) {
      ShellConfig fano = this.getConfig(i);
      if (fano.equals(fc))
        return true;
    }
    return false;
  }
  public ShellConfig getConfig(int i) {
    return (ShellConfig) get(i);
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size(); i++)
      buff.append(getConfig(i).toString()).append(SysProp.EOL);
    return buff.toString();
  }
}
