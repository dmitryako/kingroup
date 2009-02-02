package kingroup.algorithm.window;
import javax.utilx.bitset.CompBitSet;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/01/2006, Time: 15:27:41
 */
public abstract class AlgWinAccessOrder
{
  public static final int NOT_SET = -1;
  protected final int popSize;
  protected BitSet pool;

  public AlgWinAccessOrder(int size)
  {
    popSize = size;
  }

  public void setUnassignedPool(BitSet unassign) {
    pool = unassign;
  }
  public void setAssignedPool(BitSet done) {
    pool = new CompBitSet();
    pool.set(0, popSize, true);
    pool.andNot(done); //Clears all of the bits in this BitSet whose corresponding bit is set in the specified BitSet.
  }
  public abstract boolean hasNext();
  public abstract int nextIdx();
}
