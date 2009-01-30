package javax.utilx.arrays.mtrx;
import junit.framework.TestCase;
import tsvlib.project.ProjectLogger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/04/2007, 11:55:31
 */
public class MtrxSplitFactoryTest extends TestCase
{
  private static ProjectLogger log = ProjectLogger.getLogger(Mtrx.class);
//  private static ProjectLogger log = ProjectLogger.getLogger(Mtrx.class);

  public void setUp() {
    log.start(Mtrx.class.getName());
    log.setAll();
    log.setThresholdAll();
  }
  public void textSelectRandomRows() {
//    log.start(Mtrx.class.getName());
//    log.setAll();
//    log.setThresholdAll();

    double[][] z = {{11, 12, 13}
      , {21, 22, 23}
      , {31, 32, 33}
      , {41, 42, 43}
    };

    int N_ROWS = 2;
    MtrxPair res = MtrxFactory.selectRandomRows(N_ROWS, z);

  }
}
