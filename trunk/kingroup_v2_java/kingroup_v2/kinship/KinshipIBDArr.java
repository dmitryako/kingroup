package kingroup_v2.kinship;
import java.util.ArrayList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 15:33:15
 */
//public class KinshipIBDArr extends IBDArr<KinshipIBD>
public class KinshipIBDArr extends ArrayList<KinshipIBD>
{
  public boolean find(KinshipIBD ibd)
  {
    for (KinshipIBD v: this){
      if (v.equals(ibd))
        return true;
    }
    return false;
  }
  public KinshipIBD[] toArray() {
    KinshipIBD[] res = new KinshipIBD[size()];
    for (int i = 0; i < size(); i++)
      res[i] = get(i);
    return res;
  }
}
