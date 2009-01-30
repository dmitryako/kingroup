package javax.utilx.arrays;
import javax.utilx.RandomSeed;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 21, 2004, Time: 3:00:54 PM
 */
public class IntVec {
  public static final int NOT_FOUND = -1;
  private int[] arr;
  public IntVec(int[] from) {
    this.arr = from;
  }
  public String toString() {
    return IntVec.toString(arr);
  }

  public static int min(int[] ia) {
    if (ia == null) {
      throw new RuntimeException("ia == null");
    }
    if (ia.length == 0) {
      throw new RuntimeException("ia.length == 0");
    }
    int res = ia[0];
    for (int i = 1; i < ia.length; i++) {
      if (res <= ia[i])
        continue;
      res = ia[i];
    }
    return res;
  }
  public static int max(int[] ia) {
    if (ia == null) {
      throw new RuntimeException("ia == null");
    }
    if (ia.length == 0) {
      throw new RuntimeException("ia.length == 0");
    }
    int res = ia[0];
    for (int i = 1; i < ia.length; i++) {
      if (res >= ia[i])
        continue;
      res = ia[i];
    }
    return res;
  }
  public static int maxIdx(int [] dx, int size) {
    if (dx == null)
      return NOT_FOUND;
    int n = Math.min(dx.length, size);
    if (n == 0)
      return NOT_FOUND;
    int idx = 0;
    int v = dx[idx];
    for (int i = 1; i < n; i++) {
      if (v >= dx[i])
        continue;
      idx = i;
      v = dx[i];
    }
    return idx;
  }
  public static int minIdx(int [] dx, int size) {
    if (dx == null)
      return NOT_FOUND;
    int n = Math.min(dx.length, size);
    if (n == 0)
      return NOT_FOUND;
    int idx = 0;
    int v = dx[idx];
    for (int i = 1; i < n; i++) {
      if (v <= dx[i])
        continue;
      idx = i;
      v = dx[i];
    }
    return idx;
  }
  public static boolean has(int[] ia, int size, int val) {
    int L = Math.min(ia.length, size);
    for (int i = 0; i < L; i++) {
      if (ia[i] == val)
        return true;
    }
    return false;
  }
  public static int[] makeArray(int size, int val) {
    int[] res = new int[size];
    Arrays.fill(res, val);
    return res;
  }
  public static int minAbove(int excl, int[] ia) {
    if (ia == null || ia.length == 0)
      return 0;
    int res = excl;
    for (int i = 0; i < ia.length; i++) {
      final int v = ia[i];
      if (excl >= v)
        continue; // ignore below excluded boundary
      if (res == excl || res > v)
        res = v;
    }
    return res;
  }
  static public String toString(int[] ints, int size) {
    StringBuffer buff = new StringBuffer();
    buff.append("{");
    buff.append(toCSV(ints, size));
    buff.append("}");
    return buff.toString();
  }
  static public String toString(int[] ints) {
    return toString(ints, ints.length);
  }
  static public String toCSV(int[] ints) {
    return toCSV(ints, ints.length);
  }
  static public String toCSV(int[] ints, int size) {
    int L = Math.min(size, ints.length);
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < L; i++) {
      int anInt = ints[i];
      buff.append(anInt);
      if (i != L - 1)
        buff.append(", ");
    }
    return buff.toString();
  }
  public static int[] makeIdxArr(int size) {
    return makeIdxArr(0, size);
  }
  public static int[] makeIdxArr(int startIdx, int size) {
    int[] res = new int[size];
    for (int i = 0; i < size; i++) {
      res[i] = (startIdx + i);
    }
    return res;
  }
  public static int[] makeRandIdxArr(int size) {
    List pool = makeIdxList(size);
    RandomSeed rand = RandomSeed.getInstance();
    int[] res = new int[size];
    for (int i = 0; i < size; i++) {
      int from = rand.nextInt(pool.size());
      int idx = ((Integer)pool.remove(from)).intValue();
      res[i] = idx;
    }
    return res;
  }
  public static List makeIdxList(int size) {
    List res = new LinkedList();
    for (int i = 0; i < size; i++) {
      res.add(new Integer(i));
    }
    return res;
  }
  public static int[] toArray(Object[] from) {
    int[] res = new int[from.length];
    for (int i = 0; i < from.length; i++) {
      res[i] = ((Integer) from[i]).intValue();
    }
    return res;
  }
  public static int[] toArray(Integer[] from) {
    int[] res = new int[from.length];
    for (int i = 0; i < from.length; i++) {
      res[i] = from[i].intValue();
    }
    return res;
  }
  public static void set(int[] a, int val) {
    for (int i = 0; i < a.length; i++) {
      a[i] = val;
    }
  }
  public static void set(byte[] a, byte val) {
    for (int i = 0; i < a.length; i++) {
      a[i] = val;
    }
  }

  public static void add(int[] to, int[] from)
  {
    if (to.length != from.length) {
      throw new RuntimeException("to.length != from.length");
    }
    for (int i = 0; i < to.length; i++) {
      to[i] += from[i];
    }
  }

  public static LinkedList<Integer> toList(int[] from)
  {
    LinkedList<Integer> res = new LinkedList<Integer>();
    for (int i : from) {
      res.add(new Integer(i));
    }
    return res;
  }

  public static int[] move(int intVal, int toIdx, int[] res)
  {
    if (res[toIdx] == intVal)
      return res;  // already there
    LinkedList<Integer> list = IntVec.toList(res);
    list.remove(new Integer(intVal));
    list.add(toIdx, new Integer(intVal));
    return IntVec.toArray(list.toArray());
  }
}
