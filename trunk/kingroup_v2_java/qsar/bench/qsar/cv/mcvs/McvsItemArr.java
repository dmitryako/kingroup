package qsar.bench.qsar.cv.mcvs;
import tsvlib.project.ProjectLogger;

import javax.utilx.bitset.CompBitSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/06/2007, 15:17:11
 */
public class McvsItemArr
{
  private final static ProjectLogger log = ProjectLogger.getLogger(McvsItemArr.class);
  private TreeMap<CompBitSet, McvsItem> map;
  private int capacity;
  private static final int BUFFER_COEFF = 2;

  public McvsItemArr(int capacity)
  {
    init();
    this.capacity = capacity;
  }

  public void init() {
    map = new TreeMap<CompBitSet, McvsItem>();
  }

  public McvsItem add(McvsItem from)   {  log.debug("item=", from);
    McvsItem s = map.get(from);           //log.debug("s=", s);
    if (s == null) {
      map.put(from, from);
      return from;
    }
    return s;
  }

  public void removeExcess()
  {
    if (map.size() < capacity * BUFFER_COEFF)
      return;
    McvsItem[] sorted = sortByAmse();
    for (int i = capacity; i < sorted.length; i++) {
      map.remove(sorted[i]);
    }
  }

  public int size()
  {
    return map.size();
  }

  public McvsItem[] sortByAmse()
  {
    Collection<McvsItem> vals = map.values();
    Object[] arr = vals.toArray();
    Arrays.sort(arr, new Comparator() {
      public int compare(Object o1, Object o2) {
        McvsItem g1 = (McvsItem) o1;
        McvsItem g2 = (McvsItem) o2;
        int res = Double.compare(g1.getAvrLoss(), g2.getAvrLoss());
        return res;
      }
    });
    McvsItem[] res = new McvsItem[vals.size()];
    for (int i = 0; i < res.length; i++) {
      res[i] = (McvsItem)(arr[i]);
    }
    return res;
  }

  public McvsItem findMinLastLoss()
  {
    Collection<McvsItem> vals = map.values();
    Object[] arr = vals.toArray();
    Arrays.sort(arr, new Comparator() {
      public int compare(Object o1, Object o2) {
        McvsItem g1 = (McvsItem) o1;
        McvsItem g2 = (McvsItem) o2;
        int res = Double.compare(g1.getLastLoss(), g2.getLastLoss());
        return res;
      }
    });
    return (McvsItem)(arr[0]);
  }

  public Collection<McvsItem> values()
  {
    return map.values();    
  }

  public int getCapacity()
  {
    return capacity;
  }
}
