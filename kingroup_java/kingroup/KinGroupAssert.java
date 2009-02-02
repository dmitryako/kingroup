package kingroup;
import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/05/2005, Time: 12:05:31
 */
public class KinGroupAssert {
  // pre-,post-condition
  public static void assertTrue(Object from, boolean val, String mssg) {
    if (!val)
      LOG.error(from, mssg, "");
    assert(val);
  }
}
