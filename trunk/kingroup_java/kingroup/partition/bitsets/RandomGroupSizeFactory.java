package kingroup.partition.bitsets;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 15, 2004, Time: 1:20:23 PM
 */
public class RandomGroupSizeFactory extends PartitionFactory {
  public RandomGroupSizeFactory(int universeSize) {
    super(universeSize);
  }
  public Partition makeRandom() {
    return makeRandomGroupSize();
  }
}
