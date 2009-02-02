package papers.kingroup2005c_limit.figure_5;
import kingroup.population.OldPop;
import kingroup_v2.fsr.bound.FsrLBoundApproxGSLarge;
import papers.inpress.kingroup2006_apbc.v1.efficiency.APBC2006_Efficiency;
import papers.kingroup2005b_simpson.v1.Efficiency;

import javax.iox.LOG;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/10/2005, Time: 12:31:06
 */
public class LimitDist extends APBC2006_Efficiency {
  public static void main(String[] args) {
    LimitDist test = new LimitDist();
    LOG.setTrace(true);
    test.calcEfficiency();
    System.exit(0);
  }
  public LimitDist() {
    DIR = "papers" + File.separator + "kingroup2005c_limit" + File.separator + "output";

    // LIMIT
    GROUP_SIZE = 1;
    MIN_N_GROUPS = 2;
    MAX_N_GROUPS = 20;
    N_GROUPS_STEP = 1;
    N_ALLELES = 10;
    N_LOCI = 1;

    N_TRIALS = 2;
//    FILE = "LIMIT_GAIL";
    FILE = "LIMIT_MCCONNELL";
//    FILE = "LIMIT_BOXES";
    FILE = "LIMIT_BOXESx4";
//    FILE = "LIMIT_BOXES4";
  }

  protected Efficiency calcEfficiency(OldPop popA) {
//    FsrLBoundApproxGSOne bound = new FsrLBoundApproxGSOne(N_GROUPS, N_ALLELES);
//    double dist = bound.calcGailError();
//    double dist = bound.calcMcConnellError(N_LOCI);

    FsrLBoundApproxGSLarge bound = new FsrLBoundApproxGSLarge(N_GROUPS, N_ALLELES, N_LOCI);
    double dist = bound.calc(GROUP_SIZE).getAccuracyError() * POP_MODEL.getSize();

//    FsrLBoundApproxGSOne bound = new FsrLBoundApproxGSOne(4*N_GROUPS, N_ALLELES, N_LOCI);
//    double dist = bound.calcNumErrors(true)/4.;

    return new Efficiency(dist, 0, 0);
  }
}
