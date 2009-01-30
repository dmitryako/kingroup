package javax.swingx.filechooserx;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 15/05/2007, 17:05:55
 */
public class JPEGFileFilter extends SingleFileFilter
{
  private final static ProjectLogger log = ProjectLogger.getLogger(JPEGFileFilter.class);

  public JPEGFileFilter() {
    super("JPEG image files (*.jpg)", "jpg");
  }
}

