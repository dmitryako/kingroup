package kingroup_v2.kinship.like;
import kingroup_v2.kinship.Kinship;

import java.util.ArrayList;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/03/2006, Time: 15:52:10
 */
public class KinshipRatioSimArr extends KinshipRatioSimTable
{
  private ArrayList<KinshipRatioSimTable> arr;

  public KinshipRatioSimArr(Kinship kinship)
  {
//    super(kinship);
    arr = new ArrayList<KinshipRatioSimTable>();
    arr.add(this);
  }
  public void merge(KinshipRatioSimTable from)
  {
    arr.add(from);
    super.merge(from);
  }
  public float calcTypeI(double rLog) {
    float maxP = 0;
    for (KinshipRatioSimTable t : arr) {
      float p2;
      if (t == this)  // THIS IS A DESIGN ERROR!!!!!!!
        p2 = super.calcTypeI(rLog);
      else
        p2 = t.calcTypeI(rLog);
      if (maxP < p2)
        maxP = p2;
    }
    return maxP;
  }
}
