package tomsk.domain;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 16/04/2007, 11:50:18
 */
public class Point3dArr
{
  private final double[] arr;
  private final int size;

  private static final int N_COORD = 3;
  private static final int X_IDX = 0;
  private static final int Y_IDX = 1;
  private static final int Z_IDX = 2;

  public Point3dArr(int size)
  {
    this.size = size;
    arr = new double[size * N_COORD];
  }
  public Point3dArr(double[] arr)
  {
    this.size = arr.length / N_COORD;
    this.arr = arr;
  }
  public int size() {return size;}

  public double[] getArr() {
    return arr;
  }

  public void setX(int i, double v)
  {
    arr[getPosFromIdx(X_IDX, i)] = v;
  }
  public void setY(int i, double v)
  {
    arr[getPosFromIdx(Y_IDX, i)] = v;
  }
  public void setZ(int i, double v)
  {
    arr[getPosFromIdx(Z_IDX, i)] = v;
  }

  public double getX(int i)
  {
    return arr[getPosFromIdx(X_IDX, i)];
  }
  public double getY(int i)
  {
    return arr[getPosFromIdx(Y_IDX, i)];
  }
  public double getZ(int i)
  {
    return arr[getPosFromIdx(Z_IDX, i)];
  }

  final private int getPosFromIdx(int type, int i) {
    return N_COORD * i + type;
  }

  public void addX(int i, double v)
  {
    arr[getPosFromIdx(X_IDX, i)] += v;
  }
  public void addY(int i, double v)
  {
    arr[getPosFromIdx(Y_IDX, i)] += v;
  }
  public void addZ(int i, double v)
  {
    arr[getPosFromIdx(Z_IDX, i)] += v;
  }
}
