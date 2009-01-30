package javax.utilx.bitset;
import junit.framework.TestCase;
import tsvlib.project.ProjectLogger;

import javax.utilx.RandomSeed;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 7/06/2007, 11:22:49
 */
public class BitSetFactoryTest extends TestCase
{
  private static ProjectLogger log = ProjectLogger.getLogger(BitSetFactoryTest.class);
  private static final int N_TESTS = 10;
  private static final int START_IDX = 1;

  public static void main( String[] args )
  {
    new BitSetFactoryTest().testGetRandomSetIdx();
//    JPopupMenu.setDefaultLightWeightPopupEnabled( false );
//    ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
//    new PDBReaderTest().testRead();
//    new PDBReaderTest().test2();
  }

  public void testGetRandomSetIdx() {
    log.start("testGetRandomSetIdx");
    ProjectLogger.getRootLogger().setAll();
    ProjectLogger.getLogger(BitSetFactory.class).setInfo();
//    ProjectLogger.getLogger(View3dFactoryParticleVisitor.class).setInfo();

    BitSet set = new BitSet();
    int n = 10;
    RandomSeed rand = RandomSeed.getInstance();
    set.set(0);
    set.set(rand.nextInt(n));
    log.debug("bitset=", set);
    set.set(rand.nextInt(n));
    log.debug("bitset=", set);
    set.set(rand.nextInt(n));
    log.debug("bitset=", set);

    for (int i = 0; i < N_TESTS; i++) {
      int idx = BitSetFactory.getRandomSetIdx(set);
      log.debug("idx=", idx);
    }
    log.debug("START_IDX=", START_IDX);
    for (int i = 0; i < N_TESTS; i++) {
      int idx = BitSetFactory.getRandomSetIdx(START_IDX, set);
      log.debug("START_IDX idx=", idx);
    }
    for (int i = 0; i < N_TESTS; i++) {
      BitSet tmp = BitSetFactory.swapRandom(set, n);
      log.debug("swap=", tmp);
    }
    for (int i = 0; i < N_TESTS; i++) {
      BitSet tmp = BitSetFactory.swapRandom(START_IDX, set, n);
      log.debug("START_IDX swap=", tmp);
    }
  }


}
