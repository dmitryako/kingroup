package kingroup_v2.like.milligan;
import kingroup_v2.Kingroup;
import kingroup_v2.kinship.like.KinshipLikeCalculatorI;
import kingroup_v2.kinship.like.KinshipLikeMtrx;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/03/2006, Time: 17:27:31
 */
public class MilliganRatioMtrx extends MilliganLikeMtrx {
//  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRatioMtrx.class.getName());
  public MilliganRatioMtrx(SysPop pop) {
    super(pop);
  }
  public boolean calcComplexNull(Kingroup kingroup) {
    Milligan model = kingroup.getMilligan();
    KinshipLikeCalculatorI c = new MilliganLikeCalculator(pop, model.getPrimIBD());
    if (!super.calcLogs(c)) // init to primary
      return false;

    MilliganLikeMtrx nullMtrx = new MilliganLikeMtrx(pop);
    if (!nullMtrx.calcNullLogs(model.getNullArr(), model))
      return false;
    loadRatio(this, nullMtrx);
    return true;
  }
  public boolean calcRatio(Milligan model) {
    MilliganLikeCalculator c = new MilliganLikeCalculator(pop, model.getPrimIBD());
    if (!super.calcLogs(c)) // init to primary
      return false;

    KinshipLikeMtrx nullMtrx = new KinshipLikeMtrx(pop);
    c = new MilliganLikeCalculator(pop, model.getNullIBD());
    if (!nullMtrx.calcLogs(c))
      return false;
    loadRatio(this, nullMtrx);
    return true;
  }
}

