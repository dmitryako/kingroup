package javax.utilx;
import tsvlib.project.ProjectLogger;

import java.util.Date;
import java.util.Random;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 30, 2004, Time: 12:59:14 PM
 */
public class RandomSeed extends Random
{
  private static ProjectLogger log = ProjectLogger.getLogger(RandomSeed.class.getName());
  static private RandomSeed instance;
  static private boolean reportedOnce = false;
  private long seed = 2;
  static public RandomSeed getInstance()
  {
    if (instance == null)
      instance = new RandomSeed();
    return instance;
  }
  private RandomSeed() {
    seed = new Date().getTime();
//    if (!reportedOnce) {
//      reportedOnce = true;
//      log.severe("RandomSeed is disabled!!!!!");
//    }
    setSeed(seed);
  }

  public long getSeed()
  {
    return seed;
  }
  public double nextDouble(double L, double R) {
    return L + (R - L) * nextDouble();
  }
//  public int nextInt(int L, int R) {
//    return L + nextInt(R-L);
//  }

  public boolean makeRandomEventPercent(int percentError)
  {
    if (nextDouble() < 0.01 * percentError)
      return true;
    return false;
  }
  public boolean makeRandomEvent(double pr)
  {
    if (nextDouble() < pr)
      return true;
    return false;
  }
}
