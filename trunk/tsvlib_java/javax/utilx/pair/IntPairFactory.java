package javax.utilx.pair;

import javax.utilx.RandomSeed;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/11/2005, Time: 07:49:57
 */
public class IntPairFactory
{
  private static final int LOOP_COUNT_LIMIT = 100;
  public static IntPair makeRandomIdxPair(int size) {
    RandomSeed rand = RandomSeed.getInstance();
    int a = rand.nextInt(size);
    int b = rand.nextInt(size);
    int count = 1;
    while (a == b) {
      b = rand.nextInt(size);
      if (count++ > LOOP_COUNT_LIMIT)
        throw new RuntimeException("count > LOOP_COUNT_LIMIT");
    }
    return new IntPair(a, b);
  }
}
