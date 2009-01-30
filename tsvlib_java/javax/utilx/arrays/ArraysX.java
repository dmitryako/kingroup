package javax.utilx.arrays;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
public class ArraysX {
  public static int NOT_FOUND = -1;
  // find the first element that is Greater or Equal than the key
  static public int binarySearchGE(double[] a, double key) {
    if (a == null || a.length == 0)
      return NOT_FOUND;

    //R.Sedgewick "Algorithms in C" p198
    int L = 0; // LEFT
    int R = a.length - 1;  // RIGHT
    int x;
    int res = NOT_FOUND;
    while (R >= L) {
      x = (L + R) / 2;
      if ((float) key == (float) a[x])
        return x;
      if (key < a[x]) {
        R = x - 1;
        res = x;
      } else
        L = x + 1;
    }
    return res;
/*
      int L = 1; // LEFT
      int R = a.length;  // RIGHT
      int x;
      while (R >= L) { // NOTE ">" instead of ">="
         x = (L + R) / 2;
         if ((float)key == (float)a[x])
            return x;
         if (key < a[x])
            R = x - 1; // NOTE no "-1"
         else
            L = x + 1;
      }
      return R;
*/
  }

  static public float[] addFloat(float s, float[] to) {
    float[] newArray = null;
    if (to == null)
      newArray = new float[1];
    else
      newArray = new float[to.length + 1];
    int i = 0;
    for (; i < to.length; i++)
      newArray[i] = to[i];
    newArray[i] = s;
    return newArray;
  }

  public static int maxIdx(float [] dx, int size) {
    if (dx == null)
      return NOT_FOUND;
    int n = Math.min(dx.length, size);
    if (n == 0)
      return NOT_FOUND;
    int idx = 0;
    float v = dx[idx];
    for (int i = 1; i < n; i++) {
      if (v >= dx[i])
        continue;
      idx = i;
      v = dx[i];
    }
    return idx;
  }
  public static int minIdx(float [] dx, int size) {
    if (dx == null)
      return NOT_FOUND;
    int n = Math.min(dx.length, size);
    if (n == 0)
      return NOT_FOUND;
    int idx = 0;
    float v = dx[idx];
    for (int i = 1; i < n; i++) {
      if (v <= dx[i])
        continue;
      idx = i;
      v = dx[i];
    }
    return idx;
  }
  //
  // EQUALS
  //
  static public boolean equals(String[] a, String[] b) {
    if (a == null || b == null)
      return false;
    if (a.length != b.length)
      return false;
    if (a == b)
      return true;
    for (int i = 0; i < a.length; i++)
      if (!a[i].equals(b[i]))
        return false;
    return true;
  }
}


