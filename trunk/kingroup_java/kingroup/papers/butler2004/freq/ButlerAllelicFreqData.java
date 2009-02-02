package kingroup.papers.butler2004.freq;
import javax.utilx.pair.IntStringPair;
import java.util.Hashtable;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 28, 2004, Time: 4:40:46 PM
 */
public class ButlerAllelicFreqData extends Hashtable {
  public static final int A4 = 9;
  public static final int A8 = 10;
  public static final int DEQ = 11;
  public static final int DNEQ = 12;
  private static ButlerAllelicFreqData instance_ = new ButlerAllelicFreqData();
  public static ButlerAllelicFreqData getInstance() {
    return instance_;
  }
  private ButlerAllelicFreqData() {
    put(new Integer(DEQ), new IntStringPair(DEQ, "Uniform"));
    put(new Integer(DNEQ), new IntStringPair(DNEQ, "?Nonequal?"));
  }
//   final public ButlerFamily getFamily(int getId) {
//      Object obj = get(new Integer(getId));
//      if (obj == null)
//         return null;
//      return (ButlerFamily)obj;
//   }
  public IntStringPair[] getIntStringPairs() {
    IntStringPair[] res = new IntStringPair[size()];
    Object[] keys = keySet().toArray();
    for (int i = 0; i < size(); i++)
      res[i] = (IntStringPair) get(keys[i]);
    return res;
  }
  private Integer[] makeF1() {
    final int SIZE = 50;
    final int VAL = 1;
    Integer res[] = new Integer[SIZE];
    for (int i = 0; i < res.length; i++)
      res[i] = new Integer(VAL);
    return res;
  }
}