package kingroup_v2.partition.simpson.haplodip;
import kingroup.KinGroupError;
import kingroup_v2.partition.simpson.SibshipAlg;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.utilx.bitset.CompBitSet;
import javax.utilx.pair.IntPair;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 16/08/2005, Time: 16:44:28
 */
public class HaplodipSibship extends SibshipAlg
{
  protected int MIN_VALID_HAPLOD_GROUP = 1;
  private static final int MAX_HAPLOD_ALLELES = 3;
  private static final int MAX_HAPLOD_SET = 2;

  public boolean isSibGroupSLOW(SysPop pop, CompBitSet group)
  {
    assert(pop.getNumLoci() > 0);
    if (pop.getNumLoci() <= 0) {
      throw new KinGroupError("getNumLoci() <= 0");
    }
    if (group.cardinality() <= MIN_VALID_HAPLOD_GROUP)
      return true;
    for (int L = 0; L < pop.getNumLoci(); L++) {
      if (!isHaplodipSibLocusSLOW(pop, group, L))
        return false;
    }
    return true;
  }

  private boolean isHaplodipSibLocusSLOW(SysPop pop, CompBitSet g, int locusIdx) {
    TreeSet alleleSet = new TreeSet();   // set of different alleles
    TreeSet locusSet = new TreeSet(); // set of different loci
    loadAlleleAndLocusSetsSLOW(pop, locusSet, alleleSet, g, locusIdx);
    // HAPLODIPLOID Sib group rules:
    // parent => offsprings
    // A  c/d  =>  A/c, A/d  =>  alleles = 3
    // A  A/d  =>  A/A, A/d  =>  alleles = 2
    // A  A/A  =>  A/A       =>  alleles = 1
    if (alleleSet.size() > MAX_HAPLOD_ALLELES)
      return false;
    if (locusSet.size() > MAX_HAPLOD_SET)
      return false;
    return hasParentsSLOW(locusSet);
  }



  public static boolean hasParentsSLOW(TreeSet locusSet) {
    LinkedList pA = new LinkedList(); // parent A
    LinkedList pB = new LinkedList();
    for (Iterator it = locusSet.iterator(); it.hasNext();) {
      IntPair L = (IntPair) it.next(); // given get
      if (!makeParents(L, pA, pB))
        return false;
    }
    return true;
  }
  public static boolean makeParents(IntPair L, LinkedList pA, LinkedList pB) {
    if (pA.size() == 0) {       // first time
      pA.add(new Integer(L.a));            // (a) (b,?)
      pB.add(new IntPair(L.b, NOT_SET));
      if (L.a != L.b) {                    // (b) (a,?)
        pA.add(new Integer(L.b));
        pB.add(new IntPair(L.a, NOT_SET));
      }
      return true;
    }
    for (int i = 0; i < pA.size(); i++) {
      int A = ((Integer) pA.get(i)).intValue();
      IntPair B = (IntPair) pB.get(i);
      if (B.b == NOT_SET) { // second time
        if (A == B.a && L.a == L.b && A == L.a) { //A A/? + A/A
          continue;
        } else if (L.a != A && L.b != A) {
          pA.remove(i);
          pB.remove(i);
          i--;
        } else if (A == B.a) {
          if (A == L.a) // [A] [A]/? + A/d => parents A A/d
            B.b = L.b;
          else if (A == L.b) // [A] [A]/? + d/A => parents A A/d
            B.b = L.a;
          else {
            pA.remove(i);
            pB.remove(i);
            i--;
          }
        } else if (L.a == L.b) {
          if (A == L.b) // A B/? + [A/A] => parents A B/A
            B.b = L.b;
          else {
            pA.remove(i);
            pB.remove(i);
            i--;
          }
        } else if (A == L.a) {
          assert(B.a != L.b);
          B.b = L.b;  // [A] B/? + [A]/c => parents A B/c
        } else if (A == L.b) {
          assert(B.a != L.a);
          B.b = L.a;  // [A] B/? + c/[A] => parents A B/c
        }
        continue;
      } else if (!makeParents(L, A, B)) {
        pA.remove(i);
        pB.remove(i);
        i--;
      }
    }
//      LOG.report(null, "new parentA=" + pA);
//      LOG.report(null, "new parentB=" + pB);
    return (pA.size() != 0);
  }
  private static boolean makeParents(IntPair L, int A, IntPair B) {
    if (B.b == NOT_SET) {
      throw new KinGroupError("B.b == NOT_SET");
    }
    if (L.a == A && (L.b == B.a || L.b == B.b))
      return true;
    if (L.b == A && (L.a == B.a || L.a == B.b))
      return true;
    return false;
  }
}
