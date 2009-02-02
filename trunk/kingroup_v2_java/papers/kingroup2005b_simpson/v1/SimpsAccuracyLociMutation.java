package papers.kingroup2005b_simpson.v1;
import kingroup.population.OldPop;
import kingroup.population.PopErrorFactory;

import javax.iox.LOG;
import java.io.File;

import papers.kingroup2005b_simpson.v1.SimpsAccuracy;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/06/2005, Time: 14:00:20
 */
public class SimpsAccuracyLociMutation extends SimpsAccuracy {
  public static final int ERROR_PERCENTAGE = 5;
//   public static final int ERROR_PERCENTAGE = 10;
  public static void main(String[] args) {
    SimpsAccuracyLociMutation test = new SimpsAccuracyLociMutation();
    LOG.setTrace(true);
    test.setupP50();
    test.runP50_SetB();
//      test.runP200();
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