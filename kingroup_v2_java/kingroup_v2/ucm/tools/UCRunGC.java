package kingroup_v2.ucm.tools;
import pattern.ucm.UCController;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/10/2005, Time: 14:57:15
 */
public class UCRunGC implements UCController {
  public boolean run() {
    System.gc();
    return true;
  }
}