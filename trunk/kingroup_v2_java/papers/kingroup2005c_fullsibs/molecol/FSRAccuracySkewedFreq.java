package papers.kingroup2005c_fullsibs.molecol;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup_v2.partition.PartitionAccuracyTester;

import javax.iox.LOG;
import java.io.File;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Jan 10, 2005, Time: 1:56:44 PM
 */
public class FSRAccuracySkewedFreq extends PartitionAccuracyTester {
  public static void main(String[] args) {
    FSRAccuracySkewedFreq test = new FSRAccuracySkewedFreq();
    LOG.setTrace(true);
//      test.runSkew();
//      test.runSkew2();
    test.setupP50();
    test.runP50_SetA_SkewByBeyer();
    System.exit(0);
  }
  public void runSkew() {
    DIR += File.separator + "skew1";
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    POP_MODEL.loadDefaults();
    POP_MODEL.setIncParents(false);
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.SKEWED_BY_1);
    runP50_SetA();
  }
  public void runSkew2() {
    DIR += File.separator + "skew2";
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    POP_MODEL.loadDefaults();
    POP_MODEL.setIncParents(false);
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.SKEWED_BY_2);
    runP50_SetA();
  }
  public void runP50_SetA_SkewByBeyer() {
    DIR += File.separator + "skew_beyer";
//    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    POP_MODEL.loadDefaults();
    POP_MODEL.setIncParents(false);
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.SKEWED_BEYER);
    runP50_SetA();
  }
}