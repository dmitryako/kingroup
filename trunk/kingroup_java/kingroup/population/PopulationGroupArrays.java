package kingroup.population;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 15, 2004, Time: 11:46:11 AM
 */
public class PopulationGroupArrays {
  static public PopGroup[] add(PopGroup s, PopGroup[] to) {
    PopGroup[] newArray = null;
    if (to == null)
      newArray = new PopGroup[1];
    else
      newArray = new PopGroup[to.length + 1];
    int i = 0;
    for (; i < to.length; i++)
      newArray[i] = to[i];
    newArray[i] = s;
    return newArray;
  }
  static public void arraycopy(PopGroup[] src, PopGroup[] dest, int len) {
    int size = Math.min(Math.min(dest.length, src.length), len);
    for (int i = 0; i < size; i++)
      dest[i] = src[i];
  }
}
