package kingroup_v2.like.thompson;
import java.util.ArrayList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 10:35:02
 */
public class ThompsonIBDArr extends ArrayList<ThompsonIBD>
{
  public boolean find(ThompsonIBD ibd)
  {
    for (ThompsonIBD v: this){
      if (v.equals(ibd))
        return true;
    }
    return false;
  }
  public ThompsonIBD[] toArray() {
    ThompsonIBD[] res = new ThompsonIBD[size()];
    for (int i = 0; i < size(); i++)
      res[i] = get(i);
    return res;
  }
}

