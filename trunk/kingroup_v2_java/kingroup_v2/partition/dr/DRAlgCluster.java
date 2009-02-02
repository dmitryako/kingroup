package kingroup_v2.partition.dr;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/10/2005, Time: 10:19:20
 */
public class DRAlgCluster extends BitSet
{
  public DRAlgCluster(DRAlgCluster from) {
    or(from); // copy from
  }
  public DRAlgCluster() {
  }
}
