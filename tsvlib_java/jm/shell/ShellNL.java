package jm.shell;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/03/2005, Time: 09:09:57
 */
public class ShellNL implements Comparable {
  public final int n;
  public final int L;
  public ShellNL(int n, int L) {
    this.n = n;
    this.L = L;
  }
  // THIS IS VERY VERY IMPORTANT!!!!!!!!
  // This is some fixed ordering
  // 1s, 2s, 2p, 3s, 3p, 3d, ...
  public int compareTo(Object obj) {
    if (obj == this)
      return 0;
    ShellNL id = (ShellNL) obj;
    int res = n - id.n;
    if (res != 0)
      return res;
    return res = L - id.L;
  }
  final public boolean equals(Object from) { // STOP overwriting
    return compareTo(from) == 0;
  }
  public String toString() {
    return Integer.toString(n) + ShellLS.toString(L);
  }
}
