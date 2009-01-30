package javax.vecmathx.function;
import stlx.valarrayx.valarray;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 2, 2004, Time: 2:32:03 PM
 */
public class FuncArr extends ApplyToArr {
  private valarray x; // todo: make final
  private FuncVec[] arr;
  public FuncArr(final valarray x, final int size) {
    this.x = x;
    init(size);
  }
  final public int size() {
    return arr.length;
  }
  final public valarray getX() {
    return x;
  }
  final public void init(int size) {
    arr = new FuncVec[size];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = new FuncVec(x, x.size());
    }
  }
  final public void setArr(FuncVec[] arr) {
    this.arr = arr;
  }
  final public void set(int i, FuncVec f) {
    arr[i] = f;
  }
  final public FuncVec[] getArray() {
    return arr;
  }
  final public FuncVec get(int i) {
    return arr[i];
  }
  final public void changeGrid(valarray g) {
    x = g;
    for (int i = 0; i < arr.length; i++) {
      arr[i].setX(g);
    }
  }
  protected void safeDivide(valarray r) {
    for (int i = 0; i < arr.length; i++) {
      arr[i].safeDivide(r);
    }
  }
}
