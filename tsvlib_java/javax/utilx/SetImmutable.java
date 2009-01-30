package javax.utilx;
import java.util.BitSet;
public class SetImmutable {
  private BitSet bitset_;
  public SetImmutable(BitSet b) {
    bitset_ = b;
  }
  //Bitwise and
  public final SetImmutable intersection(SetImmutable toIntersect) {
    BitSet toRet = (BitSet) bitset_.clone();
    toRet.and(toIntersect.bitset_);
    return new SetImmutable(toRet);
  }
  //Bitwise Or
  public final SetImmutable union(SetImmutable toUnion) {
    BitSet toRet = (BitSet) bitset_.clone();
    toRet.or(toUnion.bitset_);
    return new SetImmutable(toRet);
  }
  //Bitwise and!
  public final SetImmutable difference(SetImmutable toDiff) {
    BitSet toRet = (BitSet) bitset_.clone();
    toRet.andNot(toDiff.bitset_);
    return new SetImmutable(toRet);
  }
  //(A - B)UNION(B - A)
  public final SetImmutable symmetricDifference(SetImmutable toDiff) {
    return (difference(toDiff)).union((toDiff.difference(this)));
  }
  public final SetImmutable complement() {
    BitSet toRet = new BitSet(bitset_.size());
    toRet.set(0, bitset_.size(), true);
    toRet.xor(bitset_);
    return new SetImmutable(toRet);
  }
  public final int countingMeasure() {
    return bitset_.cardinality();
  }
  public final boolean equals(Object obj) {
    if (!(obj instanceof SetImmutable)) {
      return false;
    }
    return bitset_.equals(((SetImmutable) obj).bitset_);
  }
  public final boolean hasElement(int pos) {
    return bitset_.get(pos);
  }
  public final int getAnyObject() {
    return bitset_.nextSetBit(0);
  }
}
