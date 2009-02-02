package kingroup_v2.kinship.like;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDComplex;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/10/2005, Time: 09:07:19
 */
public class KinshipRatioMtrxAll extends KinshipRatioMtrx {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipRatioMtrxAll.class.getName());
  private KinshipRatioMtrx ratioMtrx;
  private KinshipLikeMtrx primMtrx;
  private KinshipLikeMtrx nullMtrx;
  public KinshipRatioMtrxAll(SysPop pop)
  {
    super(pop);
  }

  public boolean calcComplexNull(Kinship kinship)
  {
    KinshipIBD prim = kinship.getPrimIBD();
    KinshipIBDComplex nullM = kinship.getComplexNullIBD();
    primMtrx = new KinshipLikeMtrx(pop);
    KinshipLikeCalculator c = new KinshipLikeCalculator(pop, prim, kinship);
    if (!primMtrx.calcLogs(c))
      return false;
    
    nullMtrx = new KinshipLikeMtrx(pop);
    KinshipIBD[] nullArr = KinshipIBDFactory.makeAll(kinship.getComplexNullIBD());
    if (!nullMtrx.calcNullLogs(nullArr, kinship))
      return false;
    loadRatio(primMtrx, nullMtrx);
    return true;
  }

  public KinshipRatioMtrx getRatio() {return this;}
  public KinshipLikeMtrx getPrim() {return primMtrx;}
  public KinshipLikeMtrx getNull() {return nullMtrx;}
}