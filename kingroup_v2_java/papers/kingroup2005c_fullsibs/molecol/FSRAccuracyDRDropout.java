package papers.kingroup2005c_fullsibs.molecol;
import kingroup.population.OldPop;
import kingroup_v2.partition.PartitionAccuracyTester;

import javax.iox.LOG;
import java.io.File;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Jan 10, 2005, Time: 10:16:03 AM
 */
public class FSRAccuracyDRDropout extends PartitionAccuracyTester {
  public static final float ERROR_PERCENTAGE = 5;
  public static void main(String[] args) {
    FSRAccuracyDRDropout test = new FSRAccuracyDRDropout();
    LOG.setTrace(true);
//      test.setupP50_SetB();
//      test.runP200();
    System.exit(0);
  }
  public FSRAccuracyDRDropout() {
    DIR += File.separator + "dropout" + Integer.toString((int) ERROR_PERCENTAGE);
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  protected double calcOneDistance(OldPop popA) {
//    OldPop pop = PopErrorFactory.makeLociDropoutFrom(popA, ERROR_PERCENTAGE);
//    return super.calcOneDistance(pop);
    return super.calcOneDistance(popA);
  }
}