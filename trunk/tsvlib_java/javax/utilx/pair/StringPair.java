package javax.utilx.pair;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/01/2006, Time: 09:32:34
 */
public class StringPair {
  final public String a;
  final public String b;
  public StringPair(String a, String b)
  {
    this.a = a;
    this.b = b;
  }

  public String toCSV() {
    return a + ", " + b;
  }
}
