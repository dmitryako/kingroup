package kingroup_v2.pop.sample.sys;
import kingroup_v2.pop.allele.SysAlleleMtrx;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.sample.PopParentId;

import javax.langx.SysProp;
import javax.utilx.MapIntToIdx;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.IntVec;
import javax.utilx.bitset.CompBitSet;
import javax.utilx.pair.IntPair2;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/09/2005, Time: 16:08:45
 */
public class SysPop extends PopParentId
{
  public static final int DIPLOID_SIZE = 2;

  private SysAlleleFreq freq;
  private SysAlleleMtrx[] alleles;
  private int[] matIds;
  private int[] patIds;
  private int[] groupIds;
  private int[] idIdx;    // index into the parent population
  private SysPop parentIdSrc;
  public static final int NOT_SET = -1;

  public SysPop(int capacity, int nLoci) {
    parentIdSrc = this;
    alleles = new SysAlleleMtrx[DIPLOID_SIZE];
    alleles[MAT] = new SysAlleleMtrx(capacity, nLoci);
    alleles[PAT] = new SysAlleleMtrx(capacity, nLoci);
    matIds = new int[capacity];
    patIds = new int[capacity];
    groupIds = new int[capacity];
    idIdx = new int[capacity];
    IntVec.set(matIds, -1);
    IntVec.set(patIds, -1);
    IntVec.set(groupIds, -1);
    IntVec.set(idIdx, -1);
  }
  public SysPop() {

  }

  public IntPair2 getAllelePair(int idIdx, int L) {
    return new IntPair2(getAllele(idIdx, L, MAT)
      , getAllele(idIdx, L, PAT));
  }
  public void setAllelePair(int idIdx, int locusIdx, int a, int b) {
    if (RandomSeed.getInstance().nextInt(2) == 0) {
      setAllele(idIdx, locusIdx, MAT, a);
      setAllele(idIdx, locusIdx, PAT, b);
    } else {
      setAllele(idIdx, locusIdx, MAT, b);
      setAllele(idIdx, locusIdx, PAT, a);
    }
  }

  public void shallowCopyFrom(SysPop from) {
    super.copyFrom(from);

    freq = from.freq;
    alleles = from.alleles;
    matIds = from.matIds;
    patIds = from.patIds;
    groupIds = from.groupIds;
    idIdx = from.idIdx;
    parentIdSrc = from.parentIdSrc;
  }

  public void setParentIdSrc(SysPop src) { // when breaking up the origial pop into groups
    parentIdSrc = src;
  }
  public void add(SysPop from) {
    System.arraycopy(from.matIds, 0, matIds, size(), from.matIds.length);
    System.arraycopy(from.patIds, 0, patIds, size(), from.patIds.length);
    System.arraycopy(from.groupIds, 0, groupIds, size(), from.groupIds.length);
    System.arraycopy(from.idIdx, 0, idIdx, size(), from.idIdx.length);
    alleles[MAT].add(from.alleles[MAT]);
    alleles[PAT].add(from.alleles[PAT]);
  }
  public SysAlleleFreq getFreq() {
    return freq;
  }
  public void setFreq(SysAlleleFreq freq) {
    this.freq = freq;
  }
  public void setAllele(int idIdx, int locusIdx, int slotId, int a) {
    alleles[slotId].setAllele(idIdx, locusIdx, a);
  }
  public int getAllele(int idIdx, int locusIdx, int typeIdx) {
    return alleles[typeIdx].getAllele(idIdx, locusIdx);
  }
  public int getMatAllele(int idIdx, int locusIdx, int typeIdx) {
    int midIdx = getMatId(idIdx);
    return parentIdSrc.alleles[typeIdx].getAllele(midIdx, locusIdx);
  }
  public int getPatAllele(int idIdx, int locusIdx, int slotIdx) {
    int pidIdx = getPatId(idIdx);
    return parentIdSrc.alleles[slotIdx].getAllele(pidIdx, locusIdx);
  }
  public int size() {
    return Math.min(alleles[MAT].size(), alleles[PAT].size());
  }
  public void setSize(int newPopSize) {
    alleles[MAT].setSize(newPopSize);
    alleles[PAT].setSize(newPopSize);
  }
  public int getNumLoci() {
    return Math.min(alleles[MAT].getNumLoci(), alleles[PAT].getNumLoci());
  }
  public String toString() {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size(); i++) {
      buff.append(toString(i));
      buff.append(SysProp.EOL);
    }
    return buff.toString();
  }
  public String toCSV() {
    StringBuffer buff = new StringBuffer();
    for (int i = 0; i < size(); i++) {
      buff.append(toCSV(i));
      buff.append(SysProp.EOL);
    }
    return buff.toString();
  }
  public String toString(int i) {
    String tab = ",\t";
    StringBuffer buff = new StringBuffer();
    buff.append("[" + i + "]=");
//    buff.append("id=").append(getIdIdx(i)).append(tab);
    buff.append(getIdIdx(i)).append(tab);
    buff.append("grp=").append(getGroupId(i)).append(tab);
    buff.append("mid=").append(getMatId(i)).append(tab);
    buff.append("pid=").append(getPatId(i)).append(tab);
    for (int L = 0; L < getNumLoci(); L++) {
      buff.append(getAllele(i, L, MAT));
      buff.append(ALLELE_DELIM);
      buff.append(getAllele(i, L, PAT));
      if (L != getNumLoci() - 1)
        buff.append(tab);
    }
    return buff.toString();
  }
  public String toCSV(int i) {
    String tab = ",";
    StringBuffer buff = new StringBuffer();
    buff.append("[" + i + "]=");
//    buff.append("id=").append(getIdIdx(i)).append(tab);
    buff.append(getIdIdx(i)).append(tab);
    buff.append("grp=").append(getGroupId(i)).append(tab);
    buff.append("mid=").append(getMatId(i)).append(tab);
    buff.append("pid=").append(getPatId(i)).append(tab);
    for (int L = 0; L < getNumLoci(); L++) {
      buff.append(getAllele(i, L, MAT) + 1);
      buff.append(ALLELE_DELIM);
      buff.append(getAllele(i, L, PAT) + 1);
      if (L != getNumLoci() - 1)
        buff.append(tab);
    }
    return buff.toString();
  }
  public void setMatId(int idx, int mid) {
    matIds[idx] = mid;
  }
  public int getMatId(int idx) {
    return matIds[idx];
  }
  public void setPatId(int idx, int pid) {
    patIds[idx] = pid;
  }
  public int getPatId(int idx) {
    return patIds[idx];
  }
  public void setGroupId(int idx, int groupid) {
    groupIds[idx] = groupid;
  }
  public int getGroupId(int idx) {
    return groupIds[idx];
  }
  public int[] getGroupIds() {
    return groupIds;
  }
  public void setGroupIds(int[] ids) {
    groupIds = ids;
  }
  public void addGenotype(int fromIdx, SysPop from) {
    int toIdx = size();
    int newSize = toIdx;
    setSize(++newSize);
    setId(toIdx, from.getIdIdx(fromIdx));
    setGroupId(toIdx, from.getGroupId(fromIdx));
    setMatId(toIdx, from.getMatId(fromIdx));
    setPatId(toIdx, from.getPatId(fromIdx));
    for (int L = 0; L < getNumLoci(); L++) {
      setAllele(toIdx, L, MAT, from.getAllele(fromIdx, L, MAT));
      setAllele(toIdx, L, PAT, from.getAllele(fromIdx, L, PAT));
    }
  }
  public void setId(int idx, int id) {
    idIdx[idx] = id;
  }
  public int getIdIdx(int idx) {
    return idIdx[idx];
  }
  public void resetIdIdx()  {
    for (int i = 0; i < idIdx.length; i++)
      idIdx[i] = i;
  }
  public void resetGroupIdx() {
    MapIntToIdx map = new MapIntToIdx();
    int runningIdx = 0;
    for (int i = 0; i < idIdx.length; i++)
    {
      int idx = map.getIdx(groupIds[i]);
      if (idx == -1) {
        map.put(groupIds[i], runningIdx);
        groupIds[i] = runningIdx++;
      }
      else
        groupIds[i] = idx;
    }
  }
  public String toString(CompBitSet group) {
    StringBuffer buff = new StringBuffer();
    short idx = -1;
    for (; ;) {
      idx = (short) group.nextSetBit(idx + 1); // for all members of this group
      if (idx == -1)
        break;
      buff.append(toString(idx)).append(SysProp.EOL);
    }
    return buff.toString();
  }

  public SysPop get(int idxFirst, int idxLast)
  {
    SysPop res = new SysPop(size(), getNumLoci());
    res.setFreq(getFreq());
    res.setParentIdSrc(this);      // mid, pid may not belong to this group
    for (int i = idxFirst; i < idxLast; i++) {
      res.addGenotype(i, this);
    }
    return res;
  }
}
