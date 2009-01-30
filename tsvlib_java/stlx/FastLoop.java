package stlx;
import numeric.functor.Func;
import stlx.valarrayx.valarray;

import javax.vecmath.GVector;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 1, 2004, Time: 11:29:53 AM
 */
public class FastLoop {
  private final static int DUFF_SIZE = 8; // see p141 of C++, 3rd ed.
  public static void mult(valarray v, valarray s) {
    int count = Math.min(v.size(), s.size());
    if (count < 1)
      return;
    int n = (count + DUFF_SIZE - 1) / DUFF_SIZE - 1;
    int i = 0;
    switch (count % DUFF_SIZE) {
      case 0:
        v.set(i, s.get(i) * v.get(i));
        i++;
      case 7:
        v.set(i, s.get(i) * v.get(i));
        i++;
      case 6:
        v.set(i, s.get(i) * v.get(i));
        i++;
      case 5:
        v.set(i, s.get(i) * v.get(i));
        i++;
      case 4:
        v.set(i, s.get(i) * v.get(i));
        i++;
      case 3:
        v.set(i, s.get(i) * v.get(i));
        i++;
      case 2:
        v.set(i, s.get(i) * v.get(i));
        i++;
      case 1:
        v.set(i, s.get(i) * v.get(i));
        i++;
    }
    for (; n > 0; n--) {
      v.set(i, s.get(i) * v.get(i));
      i++;
      v.set(i, s.get(i) * v.get(i));
      i++;
      v.set(i, s.get(i) * v.get(i));
      i++;
      v.set(i, s.get(i) * v.get(i));
      i++;
      v.set(i, s.get(i) * v.get(i));
      i++;
      v.set(i, s.get(i) * v.get(i));
      i++;
      v.set(i, s.get(i) * v.get(i));
      i++;
      v.set(i, s.get(i) * v.get(i));
      i++;
    }
  }
//   public static double dot(valarray v, valarray v2) {
//      int count = Math.min(v.size(), v2.size());
//      double s = 0;
//      if (count < 1)
//         return s;
//      int n = (count + DUFF_SIZE - 1) / DUFF_SIZE - 1;
//      int i = 0;
//      switch(count % DUFF_SIZE) {
//         case 0: s += v.getLine(i) * v2.getLine(i); i++;
//         case 7: s += v.getLine(i) * v2.getLine(i); i++;
//         case 6: s += v.getLine(i) * v2.getLine(i); i++;
//         case 5: s += v.getLine(i) * v2.getLine(i); i++;
//         case 4: s += v.getLine(i) * v2.getLine(i); i++;
//         case 3: s += v.getLine(i) * v2.getLine(i); i++;
//         case 2: s += v.getLine(i) * v2.getLine(i); i++;
//         case 1: s += v.getLine(i) * v2.getLine(i); i++;
//      }
//      for (; n > 0; n--) {
//         s += v.getLine(i) * v2.getLine(i); i++; //1
//         s += v.getLine(i) * v2.getLine(i); i++; //2
//         s += v.getLine(i) * v2.getLine(i); i++; //3
//         s += v.getLine(i) * v2.getLine(i); i++; //4
//         s += v.getLine(i) * v2.getLine(i); i++; //5
//         s += v.getLine(i) * v2.getLine(i); i++; //6
//         s += v.getLine(i) * v2.getLine(i); i++; //7
//         s += v.getLine(i) * v2.getLine(i); i++; //8
//      }
//      return s;
//   }
  public static double dot(GVector v, GVector v2) {
    return dot(0, v.getSize() - 1, v, v2);
  }
  public static double dot(int startIdx, int endIdx, GVector v, GVector v2) {
    int count = Math.min(v.getSize(), v2.getSize());
    count = Math.min(count, endIdx - startIdx + 1);
    double s = 0;
    if (count < 1)
      return s;
    int n = (count + DUFF_SIZE - 1) / DUFF_SIZE - 1;
    int i = startIdx;
    switch (count % DUFF_SIZE) {
      case 0:
        s += v.getElement(i) * v2.getElement(i);
        i++;
      case 7:
        s += v.getElement(i) * v2.getElement(i);
        i++;
      case 6:
        s += v.getElement(i) * v2.getElement(i);
        i++;
      case 5:
        s += v.getElement(i) * v2.getElement(i);
        i++;
      case 4:
        s += v.getElement(i) * v2.getElement(i);
        i++;
      case 3:
        s += v.getElement(i) * v2.getElement(i);
        i++;
      case 2:
        s += v.getElement(i) * v2.getElement(i);
        i++;
      case 1:
        s += v.getElement(i) * v2.getElement(i);
        i++;
    }
    for (; n > 0; n--) {
      s += v.getElement(i) * v2.getElement(i);
      i++; //1
      s += v.getElement(i) * v2.getElement(i);
      i++; //2
      s += v.getElement(i) * v2.getElement(i);
      i++; //3
      s += v.getElement(i) * v2.getElement(i);
      i++; //4
      s += v.getElement(i) * v2.getElement(i);
      i++; //5
      s += v.getElement(i) * v2.getElement(i);
      i++; //6
      s += v.getElement(i) * v2.getElement(i);
      i++; //7
      s += v.getElement(i) * v2.getElement(i);
      i++; //8
    }
    return s;
  }
  public static double dot(GVector v, GVector v2, GVector v3, GVector v4) {
    return dot(0, v.getSize(), v, v2, v3, v4);
  }
  public static double dot(int startIdx, int endIdx
    , GVector v, GVector v2, GVector v3, GVector v4) {
    int count = Math.min(v.getSize(), v2.getSize());
    count = Math.min(count, v3.getSize());
    count = Math.min(count, v4.getSize());
    count = Math.min(count, endIdx - startIdx + 1);
    double s = 0;
    if (count < 1)
      return s;
    int n = (count + DUFF_SIZE - 1) / DUFF_SIZE - 1;
    int i = startIdx;
    switch (count % DUFF_SIZE) {
      case 0:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
        i++;
      case 7:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
        i++;
      case 6:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
        i++;
      case 5:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
        i++;
      case 4:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
        i++;
      case 3:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
        i++;
      case 2:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
        i++;
      case 1:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
        i++;
    }
    for (; n > 0; n--) {
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i) * v4.getElement(i);
      i++;
    }
    return s;
  }
  public static double dot(GVector v, GVector v2, GVector v3) {
    return dot(0, v.getSize(), v, v2, v3);
  }
  public static double dot(int startIdx, int endIdx
    , GVector v, GVector v2, GVector v3) {
    int count = Math.min(v.getSize(), v2.getSize());
    count = Math.min(count, v3.getSize());
    count = Math.min(count, endIdx - startIdx + 1);
    double s = 0;
    if (count < 1)
      return s;
    int n = (count + DUFF_SIZE - 1) / DUFF_SIZE - 1;
    int i = startIdx;
    switch (count % DUFF_SIZE) {
      case 0:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
        i++;
      case 7:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
        i++;
      case 6:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
        i++;
      case 5:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
        i++;
      case 4:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
        i++;
      case 3:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
        i++;
      case 2:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
        i++;
      case 1:
        s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
        i++;
    }
    for (; n > 0; n--) {
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
      i++;
      s += v.getElement(i) * v2.getElement(i) * v3.getElement(i);
      i++;
    }
    return s;
  }
  public static double polynom(double[] c, double x) {
    int count = c.length;
    double s = 0;
    if (count < 1)
      return s;
    int n = (count + DUFF_SIZE - 1) / DUFF_SIZE - 1;
    int i = 0;
    double pow_x = 1.;
    switch (count % DUFF_SIZE) {
      case 0:
        s += c[i] * pow_x;
        i++;
        pow_x *= x;
      case 7:
        s += c[i] * pow_x;
        i++;
        pow_x *= x;
      case 6:
        s += c[i] * pow_x;
        i++;
        pow_x *= x;
      case 5:
        s += c[i] * pow_x;
        i++;
        pow_x *= x;
      case 4:
        s += c[i] * pow_x;
        i++;
        pow_x *= x;
      case 3:
        s += c[i] * pow_x;
        i++;
        pow_x *= x;
      case 2:
        s += c[i] * pow_x;
        i++;
        pow_x *= x;
      case 1:
        s += c[i] * pow_x;
        i++;
        pow_x *= x;
    }
    for (; n > 0; n--) {
      s += c[i] * pow_x;
      i++;
      pow_x *= x;
      s += c[i] * pow_x;
      i++;
      pow_x *= x;
      s += c[i] * pow_x;
      i++;
      pow_x *= x;
      s += c[i] * pow_x;
      i++;
      pow_x *= x;
      s += c[i] * pow_x;
      i++;
      pow_x *= x;
      s += c[i] * pow_x;
      i++;
      pow_x *= x;
      s += c[i] * pow_x;
      i++;
      pow_x *= x;
      s += c[i] * pow_x;
      i++;
      pow_x *= x;
    }
    return s;
  }
  public static void calc(valarray v, Func f) {
    int count = v.size();
    if (count < 1)
      return;
    int n = (count + DUFF_SIZE - 1) / DUFF_SIZE - 1;
    int i = 0;
    switch (count % DUFF_SIZE) {
      case 0:
        v.set(i, f.calc(v.get(i)));
        i++;
      case 7:
        v.set(i, f.calc(v.get(i)));
        i++;
      case 6:
        v.set(i, f.calc(v.get(i)));
        i++;
      case 5:
        v.set(i, f.calc(v.get(i)));
        i++;
      case 4:
        v.set(i, f.calc(v.get(i)));
        i++;
      case 3:
        v.set(i, f.calc(v.get(i)));
        i++;
      case 2:
        v.set(i, f.calc(v.get(i)));
        i++;
      case 1:
        v.set(i, f.calc(v.get(i)));
        i++;
    }
    for (; n > 0; n--) {
      v.set(i, f.calc(v.get(i)));
      i++;
      v.set(i, f.calc(v.get(i)));
      i++;
      v.set(i, f.calc(v.get(i)));
      i++;
      v.set(i, f.calc(v.get(i)));
      i++;
      v.set(i, f.calc(v.get(i)));
      i++;
      v.set(i, f.calc(v.get(i)));
      i++;
      v.set(i, f.calc(v.get(i)));
      i++;
      v.set(i, f.calc(v.get(i)));
      i++;
    }
  }
}
