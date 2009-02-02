package kingroup_v2.partition.dr;
import kingroup.algorithm.window.AlgAccessViaPairs;

import javax.vecmathx.matrix.MtrxReadI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/06/2005, Time: 15:08:08
 */
public class DRAlgAccessOrder extends AlgAccessViaPairs {
  public DRAlgAccessOrder(MtrxReadI pr) {
    super(pr.size(), new DRAlgPairs(pr));
  }
}
