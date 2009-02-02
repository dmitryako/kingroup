package kingroup_v2.kinship.view;
import kingroup_v2.KingroupFrame;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.like.KinshipLogMtrx;
import kingroup_v2.kinship.like.KinshipPMtrxFactory;
import kingroup_v2.kinship.like.KinshipRatioSimTable;
import kingroup_v2.pop.sample.sys.SysPopMtrxI;
import pattern.mvc.MVCTableView;

import javax.swingx.ProgressWnd;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/04/2006, Time: 16:51:35
 */
public class KinshipRatioMtrxViewFactory
{
  public static MVCTableView makeView(SysPopMtrxI m, Kinship kinship, KinshipRatioSimTable sigTable)
  {
    if (kinship.getDisplaySorted()) {
      if (kinship.getPerformSigTest())    {
        if (kinship.getDisplaySigFlag()) {
          boolean savedOrder = kinship.getDisplayAscending();
          kinship.setDisplayAscending(!savedOrder); // high logR -> low P
          MVCTableView view = new KinshipSortedMtrxFlag(m, kinship, sigTable, "FLAG");
          kinship.setDisplayAscending(savedOrder);
          return view;
        }
        m = KinshipPMtrxFactory.makePMtrx(m, sigTable);
        return new KinshipSortedMtrxView(m, kinship, "PVALUE");
      }
      return new KinshipSortedMtrxView(m, kinship, "RATIO");
    }

    if (kinship.getPerformSigTest())
      return new KinshipRatioMtrxFlag(m, kinship, sigTable);
    return new KinshipLikeMtrxView(m, kinship);
  }

  public static MVCTableView makeArrView(KinshipLogMtrx[] mtrxArr, Kinship kinship
    , KinshipRatioSimTable sigTable)
  {
    ProgressWnd progress = new ProgressWnd(KingroupFrame.getInstance(), "View Groups");

    MVCTableView[] views = new MVCTableView[mtrxArr.length];
    for (int i = 0; i < views.length; i++) {
      if (progress != null
        && progress.isCanceled(i, 0, views.length)) {
        if (progress != null)
          progress.close();
      }
      views[i] = makeView(mtrxArr[i], kinship, sigTable);
    }
    if (progress != null)
      progress.close();
    return new KinshipLikeMtrxArrView(views, kinship);
  }
}
