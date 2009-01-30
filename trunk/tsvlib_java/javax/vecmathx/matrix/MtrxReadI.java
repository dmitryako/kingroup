package javax.vecmathx.matrix;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/04/2006, Time: 16:57:14
 */
public interface MtrxReadI
{
  public double get(int r, int c);
//   public void set(int r, int c, double val);
  public int size();
  public int getStorageSize();
  //public String toString();
}
