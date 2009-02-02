package papers.kingroup2005c_fullsibs.molecol;
import kingroup.population.OldPop;
import kingroup.population.PopErrorFactory;
import kingroup_v2.partition.PartitionAccuracyTester;

import javax.iox.LOG;
import java.io.File;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Jan 10, 2005, Time: 9:44:57 AM
 */
public class FullSibsLociMutation extends PartitionAccuracyTester {
//   public static final int ERROR_PERCENTAGE = 5;
  public static final int ERROR_PERCENTAGE = 10;
  public static void main(String[] args) {
    FullSibsLociMutation test = new FullSibsLociMutation();
    LOG.setTrace(true);
    test.setupP50();
//      test.runP50_SetA();
    test.runP50_SetB();
    System.exit(0);
  }
  public void setupP50() {
    super.setupP50();
    DIR += File.separator + "mutation" + ERROR_PERCENTAGE;
  }
  protected double calcOneDistance(OldPop popA) {
    OldPop pop = PopErrorFactory.makeLociMutationFrom(popA, ERROR_PERCENTAGE);
    return super.calcOneDistance(pop);
  }
}
