package kingroup_v2.like.milligan;
import kingroup.genetics.Like;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.like.KinshipLogMtrx;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/03/2006, Time: 16:36:11
 */
public class MilliganLikeMtrx extends KinshipLogMtrx
{
  private static ProjectLogger log = ProjectLogger.getLogger(MilliganLikeMtrx.class.getName());
  public MilliganLikeMtrx(SysPop pop)
  {
    super(pop);
  }
  public boolean calcNullLogs(MilliganIBD[] nullArr, Milligan model)
  {
    MilliganIBD primIBD = model.getPrimIBD();
    nullArr = MilliganIBDFactory.remove(nullArr, primIBD);
    if (nullArr == null  || nullArr.length == 0) {
      String error = "Unable to calculate ratio:\n\nCheck if the primary and null hypotheses are the same.";
      log.severe(error);
      JOptionPane.showMessageDialog(KingroupFrame.getInstance(), error);
      return false;
    }

    init(Like.MIN_LOG);
    for (MilliganIBD tmpNull : nullArr) {
      MilliganLikeCalculator c = new MilliganLikeCalculator(pop, tmpNull);
      calcMaxLog(c);
    }
    return true;
  }
}

