package tsvlib.project;
import javax.swing.*;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 20, 2004, Time: 4:08:48 PM
 */
public class ProjectModel {
  public final static String CROSS_PLATFORM_LOOK = UIManager.getCrossPlatformLookAndFeelClassName();
  public final static String SYSTEM_LOOK = UIManager.getSystemLookAndFeelClassName();
  private static ProjectLogger log = ProjectLogger.getLogger(ProjectModel.class);

  private String lookFeel;
  private String orgType;
  private String appName;
  private String appVersion;
  private String dirName;
  private String fileName;
  private String lastProjectFileName;
  private String projectFileExtension;
  private String errorLogFileName;

  public void copyTo(ProjectModel to) {
    to.setLookFeel(getLookFeel());
    to.setOrgType(getOrgType());
    to.setAppName(getAppName());
    to.setAppVersion(getAppVersion());
    to.setDirName(getDirName());
    to.setFileName(getFileName());
    to.setLastProjectFileName(getLastProjectFileName());
    to.setErrorLogFileName(getErrorLogFileName());
  }
  final public void setOrgType(String v) {
    orgType = v;
  }
  final public String getOrgType() {
    return orgType;
  }
  final public void setAppName(String v) {
    appName = v;
  }
  final public String getAppName() {
    return appName;
  }
  final public void setAppVersion(String v) {
    appVersion = v;
  }
  final public String getAppVersion() {
    return appVersion;
  }
  final public void setFileName(String v) {
    fileName = v;
  }
  final public String getFileName() {
    return fileName;
  }
  final public void setDirName(String v) {
    dirName = v;
  }
  final public String getDirName() {
    return dirName;
  }
  public String getLookFeel() {
    return lookFeel;
  }
  public void setLookFeel(String lookFeel) {
    this.lookFeel = lookFeel;
  }
  public String getLastProjectFileName() {
    log.trace("getLastProjectFileName()");
    log.trace("lastProjectFileName = ", lastProjectFileName);
    return lastProjectFileName;
  }
  public void setLastProjectFileName(String v) {
    log.trace("setLastProjectFileName(", v);
    this.lastProjectFileName = v;
  }

  public String getProjectFileExtension()
  {
    log.trace("getProjectFileExtension()");
    log.trace("projectFileExtension = ", lastProjectFileName);
    return projectFileExtension;
  }

  public void setProjectFileExtension(String v)
  {
    log.trace("setProjectFileExtension(", v);
    this.projectFileExtension = v;
  }
  public String getErrorLogFileName()
  {
    return errorLogFileName;
  }

  public void setErrorLogFileName(String errorLogFileName)
  {
    this.errorLogFileName = errorLogFileName;
  }

}
