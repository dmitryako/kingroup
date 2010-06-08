package kingroup_v2.partition.simpson;
import kingroup.KinGroupError;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.utilx.bitset.CompBitSet;
import javax.utilx.pair.Int2;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/05/2005, Time: 10:30:40
 */
public class DiploidSibship extends SibshipAlg
{
  private static final int MAX_DIPLOID_ALLELES = 4;
  private static final int MAX_DIPLOID_SET = 4;
  protected int MIN_VALID_DIPLOID_GROUP = 2;

  public boolean isSibGroupSLOW(SysPop pop, CompBitSet group)
  {
    assert(pop.getNumLoci() > 0);
    if (pop.getNumLoci() <= 0) {
      throw new KinGroupError("getNumLoci() <= 0");
    }
    if (group.cardinality() <= MIN_VALID_DIPLOID_GROUP)
      return true;
    for (int L = 0; L < pop.getNumLoci(); L++) {
      if (!isSibLocusSLOW(pop, group, L))
        return false;
    }
    return true;
  }
  private boolean isSibLocusSLOW(SysPop pop, CompBitSet g, int locusIdx) {
    TreeSet alleleSet = new TreeSet();   // set of different alleles
    TreeSet locusSet = new TreeSet(); // set of different loci
    loadAlleleAndLocusSetsSLOW(pop, locusSet, alleleSet, g, locusIdx);
    // DIPLOID Sib group rules:
    // parent => offsprings
    // A/B  c/d  =>  A/c, A/d, B/c, B/d  =>  alleles = 4
    // A/B  A/d  =>  A/A, A/d, B/A, B/d  =>  alleles = 4
    // A/B  A/B  =>  A/A, A/B, B/B       =>  alleles = 3
    // A/A  A/B  =>  A/A, A/B            =>  alleles = 2
    // A/A  b/c  =>  A/b, A/c            =>  alleles = 2
    // A/A  A/A  =>  A/A                 =>  alleles = 1
    if (alleleSet.size() > MAX_DIPLOID_ALLELES)
      return false;
    if (locusSet.size() > MAX_DIPLOID_SET)
      return false;
    return hasParentsSLOW(locusSet);
  }


  public static boolean hasParentsSLOW(TreeSet locusSet) {
    LinkedList pA = new LinkedList(); // parent A
    LinkedList pB = new LinkedList();
    for (Iterator it = locusSet.iterator(); it.hasNext();) {
      Int2 L = (Int2) it.next(); // given get
      if (!makeParents(L, pA, pB))
        return false;
    }
    return true;
  }
  public static boolean makeParents(Int2 L, LinkedList pA, LinkedList pB) {
//      LOG.report(this, "+" + L);
    if (pA.size() == 0) {       // first time
      Int2 A = new Int2(L.a, NOT_SET);
      pA.add(A);
      Int2 B = new Int2(L.b, NOT_SET);
      pB.add(B);
      return true;
    }
    for (int i = 0; i < pA.size(); i++) {
      Int2 A = (Int2) pA.get(i);
      Int2 B = (Int2) pB.get(i);
      if (A.b == NOT_SET && B.b == NOT_SET) { // second time
        assert(!(A.a == B.a && L.a == L.b && A.a == L.a)); // NOTE A/? A/? + A/A is not possible!!!
        if (A.a == B.a) {
          if (A.a == L.a) // A/? A/? + A/d => parents A/? A/d
            B.b = L.b;
          else if (A.a == L.b) // A/? A/? + d/A => parents A/? A/d
            B.b = L.a;
          else {
            A.b = L.a; // A/? A/? + c/d => parents A/c A/d
            B.b = L.b;
          }
        } else if (L.a == L.b) {
          if (A.a == L.b) // A/? B/? + A/A => parents A/? B/A
            B.b = L.b;
          else if (B.a == L.b) {// A/? B/? + B/B => parents A/B B/?
            int Aa = A.a; // convert to B/? A/B
            A.a = L.b;
            B.a = Aa;
            B.b = L.b;
          } else {
            A.b = L.a; // A/? B/? + c/c => parents A/c A/c
            B.b = L.b;
          }
        } else if (A.a == L.a) {
          assert(B.a != L.b);
          B.b = L.b;  // A/? B/? + A/c => parents A/? B/c
          i++;
          pA.add(i, new Int2(A.a, L.b)); // A/? B/? + A/c => parents A/c B/A
          pB.add(i, new Int2(B.a, L.a));
        } else if (A.a == L.b) {
          assert(B.a != L.a);
          B.b = L.a;  // A/? B/? + c/A => parents A/? B/c
          i++;
          pA.add(i, new Int2(A.a, L.a)); // A/? B/? + c/A => parents A/c B/A
          pB.add(i, new Int2(B.a, L.b));
        } else if (B.a == L.a) {// x/? y/? + y/z => parents x/z y/?
          assert(A.a != L.b);
          i++; // must be done first since A and B will be swapped later
          pA.add(i, new Int2(A.a, L.a)); // x/? y/? + y/z => parents x/y y/z
          pB.add(i, new Int2(B.a, L.b));
          int Aa = A.a; // convert to y/? x/z
          A.a = B.a;
          B.a = Aa;
          B.b = L.b;
        } else if (B.a == L.b) {// x/? y/? + z/y => parents x/z y/?
          assert(A.a != L.a);
          i++; // must be done first since A and B will be swapped later
          pA.add(i, new Int2(A.a, L.b)); // x/? y/? + z/y => parents x/y y/z
          pB.add(i, new Int2(B.a, L.a));
          int Aa = A.a; // convert to y/? x/z
          A.a = B.a;
          B.a = Aa;
          B.b = L.a;
        } else {
          assert(A.a != B.a);
          assert(A.a != L.a && A.a != L.b);
          assert(B.a != L.a && B.a != L.b);
          assert(L.a != L.b);
          A.b = L.a;  // A/? B/? + c/d => parents A/c B/d
          B.b = L.b;
          i++;
          pA.add(i, new Int2(A.a, L.b)); // A/? B/? + c/d => parents A/d B/c
          pB.add(i, new Int2(B.a, L.a));
        }
        continue;
      } else if (A.b == NOT_SET && (A.a != L.a && A.a != L.b)
        && ((L.a == B.a && L.b == B.b) || (L.a == B.b && L.b == B.a))) {
        A.b = L.a;  // x/? y/z + y/z => x/y y/z
        i++;
        pA.add(i, new Int2(A.a, L.b)); // x/? y/z + y/z => x/z + y/z
        pB.add(i, new Int2(B));
      } else if (!makeParents(L, A, B)) {
        pA.remove(i);
        pB.remove(i);
        i--;
      }
    }
//      LOG.report(this, "new parentA=" + pA);
//      LOG.report(this, "new parentB=" + pB);
    return (pA.size() != 0);
  }
  private static boolean makeParents(Int2 L, Int2 A, Int2 B) {
    assert(!(A.b == NOT_SET && B.b == NOT_SET));
    assert(B.b != NOT_SET);
    if (A.b == NOT_SET) {
      if (L.a == A.a && (L.b == B.a || L.b == B.b))
        return true;
      if (L.b == A.a && (L.a == B.a || L.a == B.b))
        return true;
      A.b = L.a;   // try a new parent
      if (makeParents(L, A, B))
        return true;
      A.b = L.b; // try the second choice
      return makeParents(L, A, B);
    }
    if ((L.a == A.a || L.a == A.b) && (L.b == B.a || L.b == B.b))
      return true;
    if ((L.b == A.a || L.b == A.b) && (L.a == B.a || L.a == B.b))
      return true;
    return false;
  }
}
