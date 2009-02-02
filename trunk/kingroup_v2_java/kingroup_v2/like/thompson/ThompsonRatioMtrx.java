package kingroup_v2.like.thompson;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.like.KinshipLikeCalculatorI;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 11:14:06
 */
public class ThompsonRatioMtrx extends ThompsonLikeMtrx {
//  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRatioMtrx.class.getName());
  public ThompsonRatioMtrx(SysPop pop) {
    super(pop);
  }
  public boolean calcComplexNull(Kingroup kingroup) {
    Thompson model = kingroup.getThompson();
    KinshipLikeCalculatorI c = new ThompsonLikeCalculator(pop, model.getPrimIBD());
    if (!super.calcLogs(c)) // init to primary
      return false;

    ThompsonLikeMtrx nullMtrx = new ThompsonLikeMtrx(pop);
    if (!nullMtrx.calcNullLogs(model.getNullArr(), model))
      return false;
    loadRatio(this, nullMtrx);
    return true;
  }
  public boolean calcRatio(Thompson model) {
    ThompsonLikeCalculator c = new ThompsonLikeCalculator(pop, model.getPrimIBD());
    if (!super.calcLogs(c)) // init to primary
      return false;

    ThompsonLikeMtrx nullMtrx = new ThompsonLikeMtrx(pop);
    c = new ThompsonLikeCalculator(pop, model.getNullIBD());
    if (!nullMtrx.calcLogs(c))
      return false;
    loadRatio(this, nullMtrx);
    return true;
  }
}

