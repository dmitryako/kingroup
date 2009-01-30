package jm.angular;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 3:15:21 PM
 */
public class AngularMomentum {
  public static boolean isTriangle(final int i, final int i2, final int i3) {
    if ((-i + i2 + i3) < 0
      || (i - i2 + i3) < 0
      || (i + i2 - i3) < 0)
      return false;
    return true;
  }
}
