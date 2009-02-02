package papers.kingroup2006_isbe;
import junit.framework.TestCase;
import tsvlib.project.ProjectLogger;

import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 31/03/2006, Time: 10:49:16
 */
public class ISBECommon extends TestCase
{
  public String DIR = "papers"+ File.separator +"kingroup_papers_ideas"
    + File.separator + "kingroup2006_isbe" + File.separator + "output";
  protected static final ProjectLogger log = ProjectLogger.getLogger(ISBECommon.class);

  public ISBECommon() {
    log.start("ISBECommon");    
  }
}
