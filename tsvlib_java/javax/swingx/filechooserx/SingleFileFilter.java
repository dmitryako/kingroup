package javax.swingx.filechooserx;
import tsvlib.project.ProjectLogger;

import javax.iox.FileX;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 15/05/2007, 17:07:47
 */
public class SingleFileFilter extends FileFilter
{
  private final static ProjectLogger log = ProjectLogger.getLogger(SingleFileFilter.class);
  private String description;
  private String extension;

  public SingleFileFilter(String descr, String ext) {
    setDescription(descr);
    setExtension(ext);
  }

  //Accept all directories and all *.extension files.
  public boolean accept(File f) {
    log.trace("accept(", f);
    if (f.isDirectory()) {
      log.debug("return true #1");
      return true;
    }
    String ext = FileX.getExtension(f);
    if (ext != null) {
      if (ext.equals(getExtension())) {
        log.debug("return true #2");
        return true;
      } else {
        log.debug("return false #2");
        return false;
      }
    }
    log.debug("return false #1");
    return false;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getExtension()
  {
    return extension;
  }

  public void setExtension(String extension)
  {
    this.extension = extension;
  }
}

