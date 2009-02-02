package kingroup_v2.pedigree.ratio;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.KinshipLikeCalculator;
import kingroup_v2.kinship.like.KinshipLikeMtrx;
import kingroup_v2.kinship.like.KinshipRatioMtrx;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/03/2006, Time: 09:55:22
 */
public class PedigreeRatioMtrx  extends KinshipRatioMtrx {
  private static ProjectLogger log = ProjectLogger.getLogger(PedigreeRatioMtrx.class.getName());
  public PedigreeRatioMtrx(SysPop pop) {
    super(pop);
  }
  public boolean calcComplexNull(Kingroup kingroup) {
    Kinship kinship = kingroup.getKinship();
    KinshipLikeCalculator c = new KinshipLikeCalculator(pop, kinship.getPrimIBD(), kinship);
    if (!super.calcLogs(c)) // init to primary
      return false;

    KinshipLikeMtrx nullMtrx = new KinshipLikeMtrx(pop);
    if (!nullMtrx.calcNullLogs(kinship.getNullArr(), kinship))
      return false;
    loadRatio(this, nullMtrx);
    return true;
  }
}

