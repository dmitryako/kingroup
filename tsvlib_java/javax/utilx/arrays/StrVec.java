package javax.utilx.arrays;
import tsvlib.project.ProjectLogger;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/05/2005, Time: 13:15:00
 */
public class StrVec {
  private static final ProjectLogger log = ProjectLogger.getLogger(StrVec.class);
//  private static final int MAX_TO_STRING = 100;
  private String[] vec;

  public StrVec(String[] a) {
    vec = a;
  }
  public String toString() {
    return StrVec.toString(vec);
  }

  public static String[] asArray(ArrayList arr) {
    String[] res = new String[arr.size()];
    for (int i = 0; i < res.length; i++)
      res[i] = (String) arr.get(i);
    return res;
  }
  public static String[] asArray(ArrayList arr, int size) {
    int resSize = size;
    if (size == -1)
      resSize = arr.size();
    String[] res = new String[resSize];
    for (int i = 0; i < resSize; i++) {
      res[i] = (String)arr.get(i);
    }
    return res;
  }
  static public String toString(String[] arr) {
    StringBuffer buff = new StringBuffer();
    buff.append("{");
    buff.append(toCSV(arr));
    buff.append("}");
    return buff.toString();
  }

  static public String[] addString(String s, String[] to) {
    String[] newArray = null;
    if (to == null)
      newArray = new String[1];
    else
      newArray = new String[to.length + 1];
    int i = 0;
    for (; i < to.length; i++)
      newArray[i] = to[i];
    newArray[i] = s;
    return newArray;
  }
  static public String toString(String[] arr, String delim) {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < arr.length; i++) {
      buff.append(arr[i]);
      if (i != arr.length - 1)
        buff.append(delim);
    }
    return buff.toString();
  }
  static public String toCSV(String[] arr) {
    return toString(arr, ", ");
  }
  public static String toString(String[][] a, String delim) {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < a.length; i++) {
      buff.append(StrVec.toString(a[i], delim));
      if (i != a.length - 1)
        buff.append("\n");
    }
    return buff.toString();
  }
  public static void set(String[] vec, String val) {
    for (int i = 0; i < vec.length; i++) {
      vec[i] = val;
    }
  }

  public static String[] order(String[] arr, int[] idxOrder)
  {
    int n = idxOrder.length;
    String[] res = new String[n];
    for (int i = 0; i < n; i++) {
      res[i] = arr[idxOrder[i]];
    }
    return res;
  }

  public static String[][] transpose(String[][] arr)
  {
    int nRows = arr.length;
    int nCols = arr[0].length;
    String[][] res = new String[nCols][nRows];
    for (int r = 0; r < nRows; r++) {
      for (int c = 0; c < nCols; c++) {
        res[c][r] = arr[r][c];
      }
    }
    return res;
  }


  public static int[] find(String[] names, String[] strings)
  {
    int[] res = new int[names.length];
    for (int i = 0; i < res.length; i++) {
      String name = names[i];
      res[i] = find(name, strings);
    }
    return res;
  }

  public static int find(String name, String[] strings)
  {
    for (int i = 0; i < strings.length; i++) {
      int res = strings[i].indexOf(name);
      if (res == 0)
        return i;
    }
    return -1;
  }


  public static String[] get(int firstIdx, int lastExcl, String[] from)
  {
    int n = lastExcl - firstIdx;
    String[] res = new String[n];
    for (int i = 0; i < n; i++) {
      res[i] = from[firstIdx + i];
    }
    return res;
  }


  public static String[] append(String[] arr, String[] arr2)
  {
    log.debug("arr=", new StrVec(arr));
    log.debug("arr2=", new StrVec(arr2));
    int L = arr.length;
    int L2 = arr2.length;
    String[] res = new String[L + L2];
    int idx = 0;
    for (int i = 0; i < L; i++) {
      res[idx++] = arr[i];
    }
    for (int i = 0; i < L2; i++) {
      res[idx++] = arr2[i];
    }
    log.debug("res=", new StrVec(res));
    return res;
  }

  public static String[] get(BitSet set, String[] from)
  {
    log.debug("select(set=", set);
    log.debug("from=", new StrVec(from));
    int n = set.cardinality();
    String[] res = new String[n];
    int idx = 0;
    for (int i = 0; i < from.length; i++) {
      if (set.get(i))
        res[idx++] = from[i];
    }
    log.debug("res=", new StrVec(res));
    return res;
  }
  public static String[] order(int[] idxOrder, String[] from)
  {
    log.debug("order(idxOrder=", new IntVec(idxOrder));
    log.debug("from=", new StrVec(from));
    String[] res = new String[from.length];
    for (int i = 0; i < from.length; i++) {
        res[i] = from[idxOrder[i]];
    }
    log.debug("res=", new StrVec(res));
    return res;
  }
}
