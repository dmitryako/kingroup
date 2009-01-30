package javax.iox.table;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/02/2008, Time: 12:44:17
 */
public class TablePair
{
  private Table a;
  private Table b;

  public TablePair(Table a, Table b)
  {
    this.a = a;
    this.b = b;
  }

  public Table getB()
  {
    return b;
  }

  public void setB(Table b)
  {
    this.b = b;
  }

  public Table getA()
  {
    return a;
  }

  public void setA(Table a)
  {
    this.a = a;
  }

  public String toString() {
    return "Part A =\n" + a + "\nPart B =\n" + b;
  }
}
