package kingroup.genotype;
import javax.langx.SysProp;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Oct 8, 2004, Time: 4:16:50 PM
 */
public class GenotypeArrays {
  static public Genotype[] add(Genotype s, Genotype[] to) {
    Genotype[] newArray = null;
    if (to == null)
      newArray = new Genotype[1];
    else
      newArray = new Genotype[to.length + 1];
    int i = 0;
    for (; i < to.length; i++)
      newArray[i] = to[i];
    newArray[i] = s;
    return newArray;
  }
  static public Genotype[] remove(Genotype s, Genotype[] from) {
    return remove(find(s, from), from);
  }
  static public Genotype[] remove(int idx, Genotype[] from) {
    if (from == null
      || idx < 0
      || idx >= from.length)
      return from;
    Genotype[] newArray = new Genotype[from.length - 1];
    int newIdx = 0;
    for (int i = 0; i < from.length; i++) {
      if (i != idx)
        newArray[newIdx++] = from[i];
    }
    return newArray;
  }
  static public int find(Genotype s, Genotype[] in) {
    for (int i = 0; i < in.length; i++)
      if (in[i].equals(s))
        return i;
    return -1;
  }
  public static String toString(Genotype[] v) {
    StringBuffer res = new StringBuffer();
    if (v == null)
      return "null";
    if (v.length == 0)
      return "empty";
    for (int i = 0; i < v.length; i++)
      res.append(v[i].toString()).append(SysProp.EOL);
    return res.toString();
  }
}
