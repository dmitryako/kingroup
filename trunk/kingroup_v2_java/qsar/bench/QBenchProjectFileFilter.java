package qsar.bench;
import tsvlib.project.ProjectLogger;

import javax.swingx.filechooserx.SingleFileFilter;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 25/04/2007, 13:56:49
 */
public class QBenchProjectFileFilter  extends SingleFileFilter
{
  private final static ProjectLogger log = ProjectLogger.getLogger(QBenchProjectFileFilter.class);

  public QBenchProjectFileFilter() {
    super("description", "txt");
    String ext = QBenchProject.getInstance().getProjectFileExtension();
    setExtension(ext);
    setDescription("QSAR-BENCH project files (*." + getExtension() + ")");    
  }
}

