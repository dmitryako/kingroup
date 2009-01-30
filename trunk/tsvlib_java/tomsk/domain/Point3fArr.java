package tomsk.domain;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 16/04/2007, 14:26:55
 */
public class Point3fArr
{
  private final float[] arr;
  private final int size;

  private static final int N_COORD = 3;
  private static final int X_IDX = 0;
  private static final int Y_IDX = 1;
  private static final int Z_IDX = 2;

  public Point3fArr(int size)
  {
    this.size = size;
    arr = new float[size * N_COORD];
  }
  public Point3fArr(float[] arr)
  {
    this.size = arr.length / N_COORD;
    this.arr = arr;
  }
  public int size() {return size;}

  public float[] getArr() {
    return arr;
  }

  public void setX(int i, float v)
  {
    arr[getPosFromIdx(X_IDX, i)] = v;
  }
  public void setY(int i, float v)
  {
    arr[getPosFromIdx(Y_IDX, i)] = v;
  }
  public void setZ(int i, float v)
  {
    arr[getPosFromIdx(Z_IDX, i)] = v;
  }

  public float getX(int i)
  {
    return arr[getPosFromIdx(X_IDX, i)];
  }
  public float getY(int i)
  {
    return arr[getPosFromIdx(Y_IDX, i)];
  }
  public float getZ(int i)
  {
    return arr[getPosFromIdx(Z_IDX, i)];
  }

  final private int getPosFromIdx(int type, int i) {
    return N_COORD * i + type;
  }

  public void addX(int i, float v)
  {
    arr[getPosFromIdx(X_IDX, i)] += v;
  }
  public void addY(int i, float v)
  {
    arr[getPosFromIdx(Y_IDX, i)] += v;
  }
  public void addZ(int i, float v)
  {
    arr[getPosFromIdx(Z_IDX, i)] += v;
  }
}

