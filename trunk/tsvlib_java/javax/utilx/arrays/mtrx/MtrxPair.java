package javax.utilx.arrays.mtrx;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/04/2007, 11:34:16
 */
public class MtrxPair extends Mtrx
{
  private Mtrx a;
  private Mtrx b;

  public MtrxPair(double[][] z)
  {
    super(z);
  }

  public Mtrx getB()
  {
    return b;
  }

  public void setB(Mtrx b)
  {
    this.b = b;
  }
  public void setB(double[][] b)
  {
    this.b = new Mtrx(b);
  }

  public Mtrx getA()
  {
    return a;
  }

  public void setA(Mtrx a)
  {
    this.a = a;
  }
  public void setA(double[][] a)
  {
    this.a = new Mtrx(a);
  }

  public String toString() {
    return "Part A =\n" + a + "\nPart B =\n" + b;
  }
}
