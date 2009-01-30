package javax.utilx;
import java.util.BitSet;
import java.util.Hashtable;
/**
 * Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */
public class SetUniverse {
  private Hashtable objMap_ = new Hashtable();
  private Hashtable objPosMap_ = new Hashtable();
  private int size_;
  private SetImmutable universe_ = new SetImmutable(new BitSet(0));
  public SetUniverse(Object[] allElements) {
    if (allElements == null)
      return;
    size_ = allElements.length;
    for (int i = 0; i < allElements.length; i++) {
      objPosMap_.put(allElements[i], "" + i);
      objMap_.put(Integer.toString(i), allElements[i]);
    }
    BitSet temp = new BitSet(allElements.length);
    temp.set(0, allElements.length, true);
    universe_ = new SetImmutable(temp);
  }
  public final SetImmutable makeSet(Object[] setElements) {
    BitSet set = new BitSet(setElements.length);
    for (int i = 0; i < setElements.length; i++) {
      if (setElements[i] != null) {
        set.set(Integer.parseInt(objPosMap_.get(setElements[i]).toString()));
      }
    }
    return new SetImmutable(set);
  }
  public final int getPos(Object tofind) {
    Object temp = objPosMap_.get(tofind);
    if (temp == null) {
      return -1;
    }
    return Integer.parseInt(temp.toString());
  }
  public final Object getObj(int pos) {
    if (pos >= size_) {
      return null;
    }
    return objMap_.get("" + pos);
  }
  public final SetImmutable getCompliment(SetImmutable set) {
    return universe_.difference(set);
  }
}
