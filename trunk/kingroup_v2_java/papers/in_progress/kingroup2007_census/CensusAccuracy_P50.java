package papers.in_progress.kingroup2007_census;
import java.io.File;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 15, 2004, Time: 1:55:28 PM
 */
public class CensusAccuracy_P50 extends CensusFigure1 {
//   public static final int ERROR_PERCENTAGE = 5;
//   public static final int ERROR_PERCENTAGE = 10;
  public static final int ERROR_PERCENTAGE = 0;
  public static void main(String[] args) {
    CensusAccuracy_P50 test = new CensusAccuracy_P50();
//      LOG.setTrace(true);
    test.runP50();
//      test.runP200();
    System.exit(0);
  }
  public CensusAccuracy_P50() {
    DIR += File.separator + "misprint" + ERROR_PERCENTAGE;
    POP_MODEL.setMisprintRate(ERROR_PERCENTAGE);
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
//   protected int calcOneDistance(Population popA, PartitionModel partitionModel) {
//      Population pop = PopErrorFactory.makeLociMutationFrom(popA, ERROR_PERCENTAGE);
////      CensusPopBuilder.purgeIdentical(pop); // DO NOT PURGE IDENTICAL!!!!!
//      return super.calcOneDistance(pop, partitionModel);
//   }
}