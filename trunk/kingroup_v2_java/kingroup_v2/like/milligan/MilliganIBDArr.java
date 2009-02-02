package kingroup_v2.like.milligan;
import java.util.ArrayList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/03/2006, Time: 15:35:44
 */
public class MilliganIBDArr extends ArrayList<MilliganIBD>
{
  public boolean find(MilliganIBD ibd)
  {
    for (MilliganIBD v: this){
      if (v.equals(ibd))
        return true;
    }
    return false;
  }
  public MilliganIBD[] toArray() {
    MilliganIBD[] res = new MilliganIBD[size()];
    for (int i = 0; i < size(); i++)
      res[i] = get(i);
    return res;
  }
}