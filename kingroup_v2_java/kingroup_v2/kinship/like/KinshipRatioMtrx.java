package kingroup_v2.kinship.like;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 08:18:20
 */
public class KinshipRatioMtrx extends KinshipLikeMtrx {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRatioMtrx.class.getName());
  public KinshipRatioMtrx(SysPop pop) {
    super(pop);
  }
  public boolean calcComplexNull(Kingroup kingroup) {
    Kinship kinship = kingroup.getKinship();
    KinshipLikeCalculator c = new KinshipLikeCalculator(pop, kinship.getPrimIBD(), kinship);
    if (!super.calcLogs(c)) // init to primary
      return false;

    KinshipLikeMtrx nullMtrx = new KinshipLikeMtrx(pop);
    KinshipIBD[] nullArr = KinshipIBDFactory.makeAll(kinship.getComplexNullIBD());
    if (!nullMtrx.calcNullLogs(nullArr, kinship))
      return false;
    loadRatio(this, nullMtrx);
    return true;
  }
  public boolean calcRatio(Kinship kinship) {
    KinshipLikeCalculator c = new KinshipLikeCalculator(pop, kinship.getPrimIBD(), kinship);
    if (!super.calcLogs(c)) // init to primary
      return false;

    KinshipLikeMtrx nullMtrx = new KinshipLikeMtrx(pop);
    c = new KinshipLikeCalculator(pop, kinship.getNullIBD(), kinship);
    if (!nullMtrx.calcLogs(c))
      return false;
    loadRatio(this, nullMtrx);
    return true;
  }
}
