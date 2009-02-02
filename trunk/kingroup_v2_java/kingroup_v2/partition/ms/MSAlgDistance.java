package kingroup_v2.partition.ms;
import javax.utilx.pair.IntPair;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 18/05/2005, Time: 17:43:20
 */
public class MSAlgDistance extends IntPair {
  public final double dist;
  public MSAlgDistance(double dist, int a, int b) {
    super(a, b);
    this.dist = dist;
  }
  public String toString()
  {
    return "(" + a + ", " + b + ", " + (float)dist + ")";
  }

}
