package jm.shell;
import jm.angular.Spin;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 3:14:58 PM
 */
public class ShellLS implements Comparable {
  private static String strL[] = {"s", "p", "d", "f", "g", "h"};
  private static String upL[] = {"S", "P", "D", "F", "G", "H"};
  public static ShellLS CLOSED_SHELL = new ShellLS(0, Spin.SINGLET);
  final public int L;
  final public Spin S;
  public static String toString(int L) {
    if (L < strL.length)
      return strL[L];
    else
      return "L" + L;
  }
  public static String toUpper(int L) {
    if (L < upL.length)
      return upL[L];
    else
      return "L" + L;
  }
  public static String toString(Spin s) {
    return Integer.toString(s.s2_1);
  }
  public String toString() {
    return ShellLS.toString(S) + ShellLS.toUpper(L);
  }
  public ShellLS(final ShellLS from) {
    this.L = from.L;
    this.S = from.S;
  }
  public ShellLS(final int L, final Spin S) {
    this.L = L;
    this.S = S;
  }
//   final public ShellLS ShellLS() {return this;}
  final public boolean equals(ShellLS obj) {
    if (obj == this)
      return true;
    return (L == obj.L && S.equals(obj.S));
  }
  public int compareTo(Object obj) {
    if (obj == this)
      return 0;
    ShellLS id = (ShellLS) obj;
    int res = L - id.L;
    if (res != 0)
      return res;
    return S.s2_1 - id.S.s2_1;
  }
}
