package tsvlib.project;
import javax.swing.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.NoSuchElementException;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 20, 2004, Time: 4:08:14 PM
 */
public class Project extends ProjectModel {
  private static final ProjectLogger log = ProjectLogger.getLogger(Project.class);

  public void loadDefault(String appName, String appVersion) {
    setLookFeel(ProjectModel.CROSS_PLATFORM_LOOK);
    setOrgType("org");
    setAppName(appName);
    setAppVersion(appVersion);
    String name = buildDefaultDirName();
    setDirName(name);
    setFileName(appName + "ProjectModel.xml");
    setLastProjectFileName(getFileName());
    setProjectFileExtension("xml");
    setErrorLogFileName("error_log.txt");
  }
  final public String buildDirFileName() {
    log.trace("buildDirFileName");
    String name = getDirName() + File.separator + getFileName();
    log.trace("name=>", name, "<");
    return name;
  }
  public File makeFile(String name) {
    if (name == null || name.length() == 0)
      return new File("empty_file_name.txt");
    else
      return new File(name);
  }
  final public String getDefaultUserDirName() {
    String res = "";
    try {
      res = System.getProperty("user.home");
      //res = System.getProperty("user.dir");
    }
    catch (SecurityException e) {
    }
    return res;
  }
  final public String buildDefaultDirName() {
    String res = getDefaultUserDirName();
    if (res.length() != 0)
      res += File.separator;
    if (getOrgType() == null || getOrgType().length() == 0)
      res += "org";
    else
      res += getOrgType();
    if (getAppName() != null && getAppName().length() != 0)
      res += (File.separator + getAppName());
    else
      res += (File.separator + "project");
    if (getAppVersion() != null && getAppVersion().length() != 0)
      res += (File.separator + getAppVersion());
    return res; // e.g. "org/kinship/v2_03"
  }
  public void loadFromDisk(String fileName) {
    Object bean = readProject(fileName);
    if (bean instanceof Project)
      ((Project) bean).copyTo(this);
    else
      saveProjectToDefaultLocation();
  }
  public void loadFromDisk() {
    log.trace("loadFromDisk");
    String fileName = buildDirFileName();
    loadFromDisk(fileName);
  }
  protected Object readProject(String fileName) {
    File file = null;
    try {
      file = new File(fileName);
      if (!file.exists() || !file.canRead())
        return null;
      XMLDecoder decoder
        = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
      Object bean = decoder.readObject();
      decoder.close();
      return bean;
    }
    catch (FileNotFoundException e) {
      return null;
    }
    catch (IOException e) {
      return null;
    }
//    catch (SAXParseException e) {
//      String error = "Unable to parse file " + fileName
//        + ".\n\nError: " + e.toString();
//      log.severe(error);
//      JOptionPane.showMessageDialog(null, error);
//      return null;
//    }
    catch (NoSuchElementException e) {
      String error = "Unable to parse file " + fileName
        + ".\nIt may be an older version."
        + ".\n\nError: " + e.toString();
      log.severe(error);
      JOptionPane.showMessageDialog(null, error);
      return null;
    }
  }
  public boolean saveProjectToDefaultLocation() {
    return writeProject(buildDirFileName());
  }
  // should be static but XMLEncoder does not like statics?
  public boolean writeProject(String fileName) {
    File file = null;
    try {
      file = new File(fileName);
      if (!file.exists()) {
        File dir = file.getParentFile();
        if (dir != null)
          dir.mkdirs();
        file.createNewFile();
      }
      if (!file.canWrite()) {
        JOptionPane.showMessageDialog(null
          , "Default project file\n"
          + fileName
          + "\nCAN NOT be overwritten.\n"
          + "Check your read-write access to\n"
          + file.getCanonicalPath()
          + "\nYour changes to the project preferences will not be saved.\n");
        return false;
      }
      XMLEncoder en
        = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
      en.writeObject(this);
      en.close();
//         en.flush();
      return true;
    }
    catch (FileNotFoundException e) {
      return false;
    }
    catch (IOException e) {
      return false;
    }
  }
}
