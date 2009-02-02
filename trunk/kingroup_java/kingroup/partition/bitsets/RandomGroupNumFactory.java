package kingroup.partition.bitsets;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 15, 2004, Time: 1:23:13 PM
 */
public class RandomGroupNumFactory extends PartitionFactory {
  public RandomGroupNumFactory(int universeSize) {
    super(universeSize);
  }
  public Partition makeRandom() {
    return makeRandomGroupNum();
  }
}