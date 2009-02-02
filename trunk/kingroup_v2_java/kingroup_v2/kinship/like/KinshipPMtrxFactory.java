package kingroup_v2.kinship.like;
import kingroup_v2.pop.sample.sys.SysPopMtrxI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/04/2006, Time: 12:59:07
 */
public class KinshipPMtrxFactory
{
  public static SysPopMtrxI makePMtrx(SysPopMtrxI logMtrx, KinshipRatioSimTable sigTable)
  {
    KinshipLogMtrx res = new KinshipLogMtrx(logMtrx.getPop());
    for (int r = 0; r < res.size(); r++) {
      for (int c = 0; c < r; c++) {
        float pv = sigTable.calcTypeI(logMtrx.get(r, c));
        res.setLog(r, c, pv);
      }
    }
    res.setName(logMtrx.getName());
    return res;
  }
}
