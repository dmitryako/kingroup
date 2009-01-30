package javax.iox;
import tsvlib.project.ProjectLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 27, 2004, Time: 11:21:26 AM
 */
final public class FileX {
  private final static ProjectLogger log = ProjectLogger.getLogger(FileX.class);

  static public String getShortFileName(String from, int levels) {
    String error = "None";
    if (levels <= 0 || from == null || from.length() == 0)
      return error;
    StringBuffer buff = new StringBuffer();
    File f = new File(from);
    buff.append(f.getName());
    levels--;
    f = f.getParentFile();
    while (f != null && levels > 0) {
      buff.insert(0, File.separatorChar);
      buff.insert(0, f.getName());
      levels--;
      f = f.getParentFile();
    }
    return buff.toString();
  }
  static public String getFileName(File file) {
    String name = "";
    try {
      name = file.getCanonicalPath();
    }
    catch (FileNotFoundException e) {
//      LOG.error(this, ": ", e);
    }
    catch (IOException e) {
//      LOG.error(this, ": ", e);
    }
    return name;
  }

  /**
   * from Utils.java
   * http://java.sun.com/docs/books/tutorial/uiswing/components/examples/index.html#FileChooserDemo2
   * @param f
   * @return file extension
   */
  public static String getExtension(File f) {
    log.trace("getExtension(", f);
    String ext = null;
    String s = f.getName();
    int i = s.lastIndexOf('.');
    if (i > 0 &&  i < s.length() - 1) {
      ext = s.substring(i+1).toLowerCase();
    }
    log.debug("extension = >", ext);
    return ext;
  }

}
