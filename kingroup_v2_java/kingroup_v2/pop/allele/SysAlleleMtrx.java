package kingroup_v2.pop.allele;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/05/2005, Time: 07:37:52
 */
public class SysAlleleMtrx
{
  private static ProjectLogger log = ProjectLogger.getLogger(SysAlleleMtrx.class.getName());

  public final static int NOT_SET = -1;
  private final int[][] ai;  // getAllele index
  private final int capacity;
  private final int nLoci;
  private int size;
  public SysAlleleMtrx(int capacity, int nLoci) {
    assert(capacity > 0);
    assert(nLoci > 0);
    ai = new int[capacity][nLoci];
    this.capacity = capacity;
    this.size = 0;      // NOTE!!!  size is ZERO!!
    this.nLoci = nLoci;
    init();
  }
  private void init() {
    for (int i = 0; i < capacity; i++) {
      for (int L = 0; L < nLoci; L++) {
        setAllele(i, L, NOT_SET);
      }
    }
  }

//    http://doc.novsu.ac.ru/oreilly/java/javanut/ch02_06.htm
//   byte signed integer 0 8 bits -128, 127
//   short signed integer 0 16 bits -32768, 32767
  public final int getAllele(int idIdx //max=32767
    , int locusIdx //max=127
  ) {
    return ai[idIdx][locusIdx];
  }
  public final void setAllele(int idIdx
    , int locusIdx
    , int alleleIdx
  ) {
    ai[idIdx][locusIdx] = alleleIdx;
  }
  public void setSize(int newPopSize) {
    assert (capacity >= newPopSize);
    if (capacity < newPopSize) {
      throw new RuntimeException("capacity < newPopSize");
    }
    size = newPopSize;
  }
  public int size() {
    return size;
  }
  public int getNumLoci() {
    return nLoci;
  }
  public void add(SysAlleleMtrx mtrx) {
    int oldSize = size();
    for (int i = 0; i < mtrx.size(); i++) {
      ai[oldSize + i] = mtrx.getAlleles(i);
    }
    setSize(oldSize + mtrx.size());
  }
  private int[] getAlleles(int idx) {
    return ai[idx];
  }
}
