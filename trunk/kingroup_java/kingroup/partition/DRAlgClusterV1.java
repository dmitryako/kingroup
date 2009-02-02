package kingroup.partition;
import kingroup.util.FastId;

import javax.utilx.arrays.IntArrayList;
import javax.utilx.bitset.CompBitSet;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 10:55:39
 */
public class DRAlgClusterV1 extends IntArrayList {
  private FastId id = new FastId();
  public DRAlgClusterV1() {
    super();
  }
  public DRAlgClusterV1(int size) {
    super(size);
  }
  public DRAlgClusterV1(DRAlgClusterV1 from) {
    super(from);
  }
  public String getClusterId() {
    return id.getId();
  }
  public void setClusterId(String str) {
    id.setId(str);
  }
  public boolean hasGenoIdxSLOW(int genoIdx) {
    return hasInt(genoIdx);
  }
  public CompBitSet toComparableBitSet() {
    CompBitSet res = new CompBitSet();
    for (int i = 0; i < size(); i++) {
      res.set(this.get(i));
    }
    return res;
  }
}
