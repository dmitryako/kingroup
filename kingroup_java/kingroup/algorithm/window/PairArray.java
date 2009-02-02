package kingroup.algorithm.window;
import java.util.LinkedList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 1/06/2005, Time: 09:13:43
 */
public class PairArray extends LinkedList
{
  public Object removeFirstPair() {
    if (size() == 0)
      return null;
    return removeFirst();
  }
}