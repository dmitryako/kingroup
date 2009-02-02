package kingroup.genotype;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 7/03/2005, Time: 11:04:25
 */
public class GenotypeGroupArrays {
  static public GenotypeGroup[] add(GenotypeGroup s, GenotypeGroup[] to) {
    GenotypeGroup[] newArray = null;
    if (to == null)
      newArray = new GenotypeGroup[1];
    else
      newArray = new GenotypeGroup[to.length + 1];
    int i = 0;
    for (; i < to.length; i++)
      newArray[i] = to[i];
    newArray[i] = s;
    return newArray;
  }
  static public int find(GenotypeGroup s, GenotypeGroup[] in) {
    for (int i = 0; i < in.length; i++)
      if (in[i].equals(s))
        return i;
    return -1;
  }
  static public GenotypeGroup[] remove(GenotypeGroup s, GenotypeGroup[] from) {
    return remove(find(s, from), from);
  }
  static public GenotypeGroup[] remove(int idx, GenotypeGroup[] from) {
    if (from == null
      || idx < 0
      || idx >= from.length)
      return from;
    GenotypeGroup[] newArray = new GenotypeGroup[from.length - 1];
    int newIdx = 0;
    for (int i = 0; i < from.length; i++) {
      if (i != idx)
        newArray[newIdx++] = from[i];
    }
    return newArray;
  }
}
