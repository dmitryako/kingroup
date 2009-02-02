package kingroup_v2.kinship.like;
import kingroup.genetics.Like;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBD;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/09/2005, Time: 07:49:42
 */
public class KinshipLikeMtrx extends KinshipLogMtrx
{
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipLikeMtrx.class.getName());
  public KinshipLikeMtrx(SysPop pop)
  {
    super(pop);
  }
  public boolean calcNullLogs(KinshipIBD[] nullArr, Kinship kinship)
  {
    KinshipIBD primIBD = kinship.getPrimIBD();
    nullArr = KinshipIBDFactory.remove(nullArr, primIBD);
    if (nullArr == null  || nullArr.length == 0) {
      String error = "Unable to calculate likelihood:\n\nCheck if the primary and null hypotheses are the same.";
      log.severe(error);
      JOptionPane.showMessageDialog(KingroupFrame.getInstance(), error);
      return false;
    }

    init(Like.MIN_LOG);
    for (KinshipIBD tmpNull : nullArr) {
      KinshipLikeCalculator c = new KinshipLikeCalculator(pop, tmpNull, kinship);
      calcMaxLog(c);
    }
    return true;
  }
}
