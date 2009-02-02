package kingroup.partition;
import kingroup.KinGroupError;
import kingroup.partition.bitsets.Partition;

import javax.langx.SysProp;
import javax.iox.LOG;
import javax.utilx.bitset.CompBitSet;
import java.util.ArrayList;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/03/2005, Time: 10:55:53
 */
public class PartitionV2 extends ArrayList {
  public void deepCopyPartitonV2From(PartitionV2 from) {
    for (int i = 0; i < from.size(); i++) {
      DRAlgClusterV1 cluster = from.getCluster(i);
      DRAlgClusterV1 deepCopy = new DRAlgClusterV1(cluster);
      add(deepCopy);
    }
  }
  public boolean add(DRAlgClusterV1 obj) {
    return super.add(obj);
  }
  public DRAlgClusterV1 getCluster(int idx) {
    return (DRAlgClusterV1) super.get(idx);
  }
  public DRAlgClusterV1 getClusterByGenoIdxSLOW(int genoIdx) {
    for (int i = 0; i < size(); i++) {
      DRAlgClusterV1 cluster = getCluster(i);
      if (cluster.hasGenoIdxSLOW(genoIdx))
        return cluster;
    }
    return null;
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size(); i++) {
      DRAlgClusterV1 cluster = getCluster(i);
      buff.append("[" + i + "]=");
      buff.append(cluster.toString());
      if (i != size() - 1)
        buff.append(SysProp.EOL);
    }
    return buff.toString();
  }
  public boolean add(Object obj) {
    String mssg = "Use PartitionV2.addLine(DRAlgClusterV1 obj) instead";
    LOG.error(this, mssg, "");
    throw new KinGroupError(mssg);
  }
  public Object get(int idx) {
    String mssg = "Use PartitionV2.getCluster(int) instead";
    LOG.error(this, mssg, "");
    throw new KinGroupError(mssg);
  }
  public Partition toBitSetPartition() {
    Partition res = new Partition();
    for (int i = 0; i < size(); i++) {
      DRAlgClusterV1 cluster = getCluster(i);
      CompBitSet set = cluster.toComparableBitSet();
      res.add(set);
    }
    return res;
  }
}
