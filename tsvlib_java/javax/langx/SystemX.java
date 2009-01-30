package javax.langx;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 6:04:56 PM
 */
public class SystemX {
  // use this to change between Java 1.4 and 1.5
  static public long time() {
    return System.nanoTime();
//      return System.currentTimeMillis();
  }
}
