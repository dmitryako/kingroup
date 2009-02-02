package kingroup_v2.partition.dr;
import javax.utilx.pair.IntPair;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/06/2005, Time: 15:16:57
 */
public class DRAlgRatioLog extends IntPair {
  public final double log;
  public DRAlgRatioLog(double log, int a, int b) {
    super(a, b);
    this.log = log;
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    buff.append(Float.toString((float)log)).append("@");
    buff.append(super.toString());
    return buff.toString();
  }
}