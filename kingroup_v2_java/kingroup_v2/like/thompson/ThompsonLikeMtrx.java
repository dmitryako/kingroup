package kingroup_v2.like.thompson;
import kingroup.genetics.Like;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.like.KinshipLogMtrx;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 11:14:39
 */
public class ThompsonLikeMtrx extends KinshipLogMtrx {
  private static ProjectLogger log = ProjectLogger.getLogger(ThompsonLikeMtrx.class.getName());
  public ThompsonLikeMtrx(SysPop pop)
  {
    super(pop);
  }
  public boolean calcNullLogs(ThompsonIBD[] nullArr, Thompson model)
  {
    ThompsonIBD primIBD = model.getPrimIBD();
    nullArr = ThompsonIBDFactory.remove(nullArr, primIBD);
    if (nullArr == null  || nullArr.length == 0) {
      String error = "Unable to calculate ratio:\n\nCheck if the primary and null hypotheses are the same.";
      log.severe(error);
      JOptionPane.showMessageDialog(KingroupFrame.getInstance(), error);
      return false;
    }

    init(Like.MIN_LOG);
    for (ThompsonIBD tmpNull : nullArr) {
      ThompsonLikeCalculator c = new ThompsonLikeCalculator(pop, tmpNull);
      calcMaxLog(c);
    }
    return true;
  }
}