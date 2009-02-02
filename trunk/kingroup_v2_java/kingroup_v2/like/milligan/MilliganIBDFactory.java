package kingroup_v2.like.milligan;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/03/2006, Time: 17:13:23
 */
public class MilliganIBDFactory
{
  public static MilliganIBD[] remove(MilliganIBD[] nullPairs, MilliganIBD excludePair) {
    MilliganIBDArr arr = new MilliganIBDArr();
    for (MilliganIBD ibd : nullPairs) {
      if (!ibd.equals(excludePair))
        arr.add(ibd);
    }
    return arr.toArray();
  }
}
