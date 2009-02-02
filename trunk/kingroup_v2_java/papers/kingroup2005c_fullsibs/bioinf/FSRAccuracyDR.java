package papers.kingroup2005c_fullsibs.bioinf;
import javax.iox.LOG;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/08/2005, Time: 15:12:51
 */
public class FSRAccuracyDR extends FSRAccuracy {
  public static void main(String[] args) {
    FSRAccuracyDR test = new FSRAccuracyDR();
    LOG.setTrace(true);
    test.setup_r2();
    test.run_n20_uniform();
//      test.run_r2_uniform();
//      test.run_r5_uniform();
//      test.run_r10_uniform();
    System.exit(0);
  }
  public void setup_r2() {
    POP_SIZE = -1;
    POP_MODEL.setSize(POP_SIZE);
    N_ALLELES = 10;
    MIN_NUM_LOCI = 1; // 1-paper
    MAX_NUM_LOCI = 12;//12-paper
    N_TRIALS = 100; // 100-paper
    FILE = "DR";
  }
}
