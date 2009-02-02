package kingroup_v2.pop.sample.sys;
import kingroup.population.PopErrorFactory;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.pop.UserLocus;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.allele.freq.UserAlleleFinder;
import kingroup_v2.pop.allele.freq.UsrAlleleFreq;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.PopBuilder;
import kingroup_v2.pop.sample.builder.SysPopBuilderFactory;
import kingroup_v2.pop.sample.usr.UserGenotypeIdFinder;
import kingroup_v2.pop.sample.usr.UserPopGroupFinder;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.vec.Vec;
import javax.utilx.pair.IntPairSortedOLD;
import javax.vecmathx.matrix.MtrxReadI;
import java.util.ArrayList;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/09/2005, Time: 16:10:25
 */
public class SysPopFactory {
  private static ProjectLogger log = ProjectLogger.getLogger(SysPopFactory.class.getName());
  private static final String MAT_TYPE = "maternal";
  private static final String PAT_TYPE = "paternal";
//  private static final int N_DIP_ALLELES = 2;
  private static final int LOOP_BREAKER = 100;

  public static int[] loadAllAllelels(int L, SysPop pop) {
    int n = pop.size();
    int[] res = new int[2 * n];
    int idx = 0;
    for (int i = 0; i < n; i++) {
      int a = pop.getAllele(i, L, pop.PAT);
      if (a != -1)
        res[idx++] = a;

      int b = pop.getAllele(i, L, pop.MAT);
      if (b != -1)
        res[idx++] = b;
    }
    return res;
  }

  public static int findFirstIdxSLOW(SysPop pop, SysPop from, int idx) {
    int LAST_L = 0;
    for (int i = 0; i < pop.size(); i++) {
      int L = 0;
      for (; L <= LAST_L; L++) {
        IntPairSortedOLD find = new IntPairSortedOLD(from.getAllelePair(idx, L));
        IntPairSortedOLD in = new IntPairSortedOLD(pop.getAllelePair(i, L));
        if (find.a != in.a || find.b != in.b)
          break;
      }
      if (L > LAST_L)
        return i;
    }
    return -1; // not found
  }

  public static SysPop makeSysPopFrom(UsrPopSLOW usr) {
//    // log.info("pop formatLog=" + pop.toString());
    int nLoci = usr.getNumLoci();
    int n = usr.size();
    SysPop sys = new SysPop(usr.size(), usr.getNumLoci());
    sys.copyFrom(usr);
//    log.info("usr=\n" + usr);
//    log.info("sys=\n" + sys);

    UsrAlleleFreq usrFreq = usr.getFreq();
    SysAlleleFreq sysFreq = SysAlleleFreqFactory.makeSysAlleleFreqFrom(usrFreq);
//    log.info("usrFreq=\n" + usrFreq);
//    log.info("sysFreq=\n" + sysFreq);
    sys.setFreq(sysFreq);
    sys.setSize(n);
    UserAlleleFinder alleleFinder = new UserAlleleFinder(usrFreq);
    UserGenotypeIdFinder genoFinder = new UserGenotypeIdFinder(usr);
    UserPopGroupFinder groupFinder = new UserPopGroupFinder(usr);
    for (int i = 0; i < n; i++) {
      sys.setId(i, i);
//      UserGenotype geno = pop.getGenotype(i);
      if (usr.getHasGroupId())
        sys.setGroupId(i, groupFinder.getGroupIdx(usr.getGroupId(i)));
      if (!loadMatPatId(true, sys, i, usr, genoFinder)) // MATERNAL
        return null;
      if (!loadMatPatId(false, sys, i, usr, genoFinder)) // PATERNAL
        return null;
      for (int L = 0; L < nLoci; L++) {
        UserLocus locus = usr.getLocus(i, L);
        for (int a = 0; a < locus.size() && a < locus.MAX_SIZE; a++) {
          String alleleKey = locus.getAllele(a);
          if (alleleKey == null  || alleleKey.length() == 0) {   //[dak080331]
            sys.setAllele(i, L, a, (int) -1);
            continue;
          }

          int alleleIdx = alleleFinder.findAlleleIdx(L, alleleKey);
          if (alleleIdx == -1) {
            String error = "An allele from the population sample does not match an allele in the allele frequency block:"
              + "\n allele name [" + alleleKey + "]"
              + "\n genotype #" + (i + 1)
              + "\n locus #" + (L + 1);
            log.severe(error);
            JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance(), error);
            return null;
          }
          sys.setAllele(i, L, a, (int) alleleIdx);
        }
      }
    }
//    // log.info("sys formatLog=\n" + res.toString());
    return sys;
  }
  private static boolean loadMatPatId(boolean mat, SysPop res, int i
    , UsrPopSLOW pop
    , UserGenotypeIdFinder genoFinder
  ) {
    String type = mat ? MAT_TYPE : PAT_TYPE;
    String id = mat ? pop.getMatId(i) : pop.getPatId(i);
    if (id == null || id.length() == 0)
      return true;
    int idx = genoFinder.findIdIdx(id);
    if (idx == i) {
      String error = "Unable to import:\nIndividual #" + (i + 1) + " with id=["
        + pop.getId(i) + "]"
        + "\nhas " + type + " id as itself.";
      log.severe(error);
      JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance(), error);
      return false;
    }
    if (idx == -1) {
      String error = "Unable to import:\nIndividual #" + (i + 1) + " with id=["
        + pop.getId(i) + "]"
        + "\nhas " + type + " id=[" + id + "],"
        + "\nwhich does not match to any of the avialable individual ids.";
      log.severe(error);
      JOptionPane.showMessageDialog(KinGroupV2MainUI.getInstance(), error);
      return false;
    }
    if (mat)
      res.setMatId(i, idx);
    else
      res.setPatId(i, idx);
    return true;
  }
  public static SysPop[] makeGroupsFrom(SysPop pop) {
    if (pop == null)
      return null;
//    // log.info("from pop =\n" + pop.toString());
    SysPopGroupFinder finder = new SysPopGroupFinder(pop);
//    // log.info("finder = " + finder.toString());
    SysPop[] res = new SysPop[finder.size()];
    if (finder.size() == 1) {
      res[0] = pop;
      return res;
    }
    for (int g = 0; g < finder.size(); g++) {
      res[g] = new SysPop(finder.getGroupSize(g), pop.getNumLoci());
      res[g].setFreq(pop.getFreq());
      res[g].setParentIdSrc(pop);      // mid, pid may not belong to this group
    }
    for (int i = 0; i < pop.size(); i++) {
      int g = pop.getGroupId(i);
//      // log.info("res["+g+"]=\n"+res[g]);
      res[g].addGenotype(i, pop);
//      // log.info("res["+g+"]=\n"+res[g]);
    }
    return res;
  }
  public static void makeRandomDiploid(int idx, SysPop pop) {
    SysAlleleFreq freq = pop.getFreq();
    for (int L = 0; L < pop.getNumLoci(); L++) {
      int a = freq.getRandomAllele(L);
      int a2 = freq.getRandomAllele(L);
      pop.setAllelePair(idx, L, a, a2);
    }
  }
  public static int getRandomAllele(int a, int a2) {
    return RandomSeed.getInstance().nextInt(2) == 0 ? a : a2;
  }
  protected static int getIBDAllele(double R, int xa, int L
    , SysAlleleFreq freq) {
    if ((float)R == 0f)
      return freq.getRandomAllele(L);
    if ((float)R == 1f)
      return xa;
    double p = RandomSeed.getInstance().nextDouble();
    if (p <= R)
      return xa;
    return freq.getRandomAllele(L);
  }

  public static void makeFullSibs(int mat, int pat, SysPop from, SysPop pop)
  {
    for (int i = 0; i < pop.size(); i++)
    {
      for (int L = 0; L < pop.getNumLoci(); L++) {
        int m = from.getAllele(mat, L, pop.MAT);
        int m2 = from.getAllele(mat, L, pop.PAT);
        int p = from.getAllele(pat, L, pop.MAT);
        int p2 = from.getAllele(pat, L, pop.PAT);

        int a = getRandomAllele(m, m2);
        int a2 = getRandomAllele(p, p2);
        pop.setAllelePair(i, L, a, a2);
      }
    }
  }

  public static void appendFullSibs(int mat, int pat, SysPop pop, int groupSize)
  {
    int posIdx = pop.size();
    for (int i = 0; i < groupSize; i++)
    {
      for (int L = 0; L < pop.getNumLoci(); L++) {
        int m = pop.getAllele(mat, L, pop.MAT);
        int m2 = pop.getAllele(mat, L, pop.PAT);
        int p = pop.getAllele(pat, L, pop.MAT);
        int p2 = pop.getAllele(pat, L, pop.PAT);

        int a = getRandomAllele(m, m2);
        int a2 = getRandomAllele(p, p2);
        pop.setAllelePair(posIdx + i, L, a, a2);
        pop.setMatId(posIdx + i, mat);
        pop.setPatId(posIdx + i, pat);
        pop.setId(posIdx + i, posIdx + i);
      }
    }
    pop.setSize(posIdx + groupSize);
  }

  public static SysPop makeSysPopFrom(PopBuilderModel model)
  {
    SysAlleleFreq sysFreq = SysAlleleFreqFactory.makeSysAlleleFreq(model, null);
    sysFreq.normalize(1.0f, false);
    PopBuilder builder = SysPopBuilderFactory.makeBuilder(model);
    SysPop sysPop = builder.makeSysPop(model, sysFreq);
//    if (model.getAlleleFreqSource() == PopBuilderModelOLD.ALLELE_FREQ_CALC)
//    {
//      SysAlleleFreq newFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);
//      sysPop.setFreq(newFreq);
//    }
    return sysPop;
  }
  public static SysPop makeSysPopFrom(PopBuilderModel model, SysAlleleFreq sysFreq)
  {
    PopBuilder builder = SysPopBuilderFactory.makeBuilder(model);
    SysPop sysPop = builder.makeSysPop(model, sysFreq);

//    if (model.getAlleleFreqSource() == PopBuilderModelOLD.ALLELE_FREQ_CALC)
//    {
//      SysAlleleFreq newFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);
//      sysPop.setFreq(newFreq);
//    }
    return sysPop;
  }


  public static SysPop shuffle(SysPop from)
  {
    int[] rnd = IntVec.makeRandIdxArr(from.size());
    SysPop res = new SysPop(rnd.length, from.getNumLoci());
    res.setFreq(from.getFreq());
    for (int i = 0; i < rnd.length; i++)
    {
      res.addGenotype(rnd[i], from);
    }
    res.resetIdIdx();
    return res;
  }

  public static SysPop mutatePerLocus(SysPop from, float percentError)
  {
    int n = from.size();
    int nLoci = n * from.getNumLoci();
    int numMutations = PopErrorFactory.calcNumMutations(nLoci, percentError);

    SysPop res = makeDeepCopy(from);

    // mutate
    RandomSeed random = RandomSeed.getInstance();
    for (int i = 0; i < numMutations; i++) {
      int idx = random.nextInt(nLoci);

      // in columns: idx = c*nRows + r
      // e.g. nRows = 10
      // r = 0, c = 0, idx = 0
      // r = 1, c = 0, idx = 1
      // r = 0, c = 1, idx = nRows
      //
      // loci are columns, ids are rows:
      int L = idx / n;
      int id = idx % n;

      int type = RandomSeed.getInstance().nextInt(2) == 0 ? res.MAT : res.PAT;
      int allele = from.getAllele(id, L, type);
      int locusSize = res.getFreq().getLocusSize(L);
      int replace = random.nextInt(locusSize);
      while (replace == allele && locusSize > 1) {
        replace = random.nextInt(locusSize);
      }
      res.setAllele(id, L, type, (int)replace);
    }
    return res;
  }

  public static SysPop makeDeepCopy(SysPop from) {
    // make a deep copy
    int n = from.size();
    SysPop res = new SysPop(n, from.getNumLoci());
    res.setFreq(from.getFreq());
    for (int i = 0; i < n; i++)
    {
      res.addGenotype(i, from);
    }
    res.resetIdIdx();
    return res;
  }

  /**
   * Make SysPop from the given SysPop by mutating percentError-number of alleles
   * @param from
   * @param percentError
   * @return a newly created SysPop
   */
  public static SysPop makeWithAlleleError(SysPop from, float percentError)
  {
    int n = from.size();
    int nLoci = from.getNumLoci();
    int nAlleles = 2 * n * nLoci;
    int numMutations = PopErrorFactory.calcNumMutations(nAlleles, percentError);

    SysPop res = makeDeepCopy(from);
    if (numMutations == 0)
      return res;

    // mutate
    RandomSeed random = RandomSeed.getInstance();
    BitSet done = new BitSet();
    for (int i = 0; i < numMutations; i++) {
      int id;
      int L;
      int type;
      int count = 0;
      for (;;) { //select allele
        // in columns: idx = c*nRows + r
        // e.g. nRows = 10
        // r = 0, c = 0, idx = 0
        // r = 1, c = 0, idx = 1
        // r = 0, c = 1, idx = nRows
        //
        // loci are columns, ids are rows:
        id = random.nextInt(n);
        L = random.nextInt(nLoci);
        type = random.nextInt(2);
        int idx = id * 2 * nLoci + 2 * L + type;
        if (done.get(idx)) { // already was done
          if (count++ < LOOP_BREAKER)
            continue;
          log.severe("count > LOOP_BREAKER");
          System.exit(count);
        }
        done.set(idx);
        break;
      }

      int locusSize = res.getFreq().getLocusSize(L);
      int replace = random.nextInt(locusSize);
      res.setAllele(id, L, type, (int)replace);
    }

    int nErrors = countDiffAllelesStrict(res, from);
    log.info("num errors=" + nErrors);
    log.info("num errors[%]=" + (float)100.0*nErrors/nAlleles);
    return res;
  }

  public static void applyErrorClassI(SysPop pop, int percentError)
  {
    RandomSeed rand = RandomSeed.getInstance();
    for (int L = 0; L < pop.getNumLoci(); L++) {
      for (int i = 0; i < pop.size(); i++) {
        int m = pop.getAllele(i, L, pop.MAT);
        int p = pop.getAllele(i, L, pop.PAT);
        if (rand.makeRandomEventPercent(percentError)) {  // MAT -> PAT
          pop.setAllele(i, L, pop.PAT, m);
          continue;
        }
        if (rand.makeRandomEventPercent(percentError)) { // PAT -> MAT
          pop.setAllele(i, L, pop.MAT, p);
          continue;
        }
      }
    }
  }

  private static int countDiffAllelesStrict(SysPop pop, SysPop pop2)
  {
    int count = 0;
    for (int type = 0; type < pop.getNumTypes(); type++) {
      for (int id = 0; id < pop.size(); id++) {
        for (int L = 0; L < pop.getNumLoci(); L++)      {
          int a = pop.getAllele(id, L, type);
          int a2 = pop2.getAllele(id, L, type);
          if (a != a2)
            count++;
        }
      }
    }
    return count;
  }

  public static SysPop makeTestPopFrom(int[][] alleleArr)
  {
    int CAPACITY = alleleArr.length;
    int N_ALLELES = 10;
    int N_LOCI = 1;
    int LOC_IDX = 0;
    SysPop pop = new SysPop(CAPACITY, N_LOCI);
    SysAlleleFreq freq = SysAlleleFreqFactory.makeEquifrequent(N_LOCI, N_ALLELES);
    pop.setFreq(freq);

    for (int i = 0; i < CAPACITY; i++) {
      int[] alleles = alleleArr[i];
      int type = 0;
      pop.setAllele(i, LOC_IDX, type, (int)alleles[type]);
      type++;
      pop.setAllele(i, LOC_IDX, type, (int)alleles[type]);
    }
    return pop;
  }

  public static SysPop makeUnrelated(int n, SysAlleleFreq freq)
  {
    SysPop pop = new SysPop(n, freq.getNumLoci());
    pop.setFreq(freq);
    pop.setSize(n);
    for (int i = 0; i < n; i++) {
      SysPopFactory.makeRandomDiploid(i, pop);
    }
    return pop;
  }

  public static void setGroupIds(int idx, BitSet group, SysPop pop) {
    for (int i = 0; i < pop.size(); i++) {
      pop.setGroupId(i, group.get(i) ? idx : idx - 1);
    }
  }

  public static SysPop makeGroupFrom(int groupIdx, SysPop pop) {
    SysPop res = new SysPop(pop.size(), pop.getNumLoci());
    res.setFreq(pop.getFreq());
    res.setParentIdSrc(pop);      // mid, pid may not belong to this group
    for (int i = 0; i < pop.size(); i++) {
      if (pop.getGroupId(i) == groupIdx)
        res.addGenotype(i, pop);
    }
    return res;
  }

  public static double[] getPO(MtrxReadI mtrx, SysPop sysPop) {
    int n = sysPop.size();
    ArrayList<Double> list = new ArrayList<Double>();
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < r; c++) {
        if (SysPopFactory.arePO(sysPop, r, c))
          list.add(new Double(mtrx.get(r, c)));
      }
    }
    return Vec.asArray(list);
  }
  public static double[] getHS(MtrxReadI mtrx, SysPop sysPop) {
    int n = sysPop.size();
    ArrayList<Double> list = new ArrayList<Double>();
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < r; c++) {
        if (SysPopFactory.areHS(sysPop, r, c))
          list.add(new Double(mtrx.get(r, c)));
      }
    }
    return Vec.asArray(list);
  }
  public static double[] getFS(MtrxReadI mtrx, SysPop sysPop) {
    int n = sysPop.size();
    ArrayList<Double> list = new ArrayList<Double>();
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < r; c++) {
        if (SysPopFactory.areFS(sysPop, r, c))
          list.add(new Double(mtrx.get(r, c)));
      }
    }
    return Vec.asArray(list);
  }
  public static double[] getNR(MtrxReadI mtrx, SysPop sysPop) {
    int n = sysPop.size();
    ArrayList<Double> list = new ArrayList<Double>();
    for (int r = 0; r < n; r++) {
      for (int c = 0; c < r; c++) {
        if (SysPopFactory.areNR(sysPop, r, c))
          list.add(new Double(mtrx.get(r, c)));
      }
    }
    return Vec.asArray(list);
  }
  public static boolean areNR(SysPop sysPop, int i, int i2) {
    if (i == i2  ||  i == -1  ||  i2 == -1)
      return false;
    int m = sysPop.getMatId(i);
    int p = sysPop.getPatId(i);
    int m2 = sysPop.getMatId(i2);
    int p2 = sysPop.getPatId(i2);
    if (m == -1  &&  p == -1
    &&  m2 == -1 &&  p2 == -1) // both parents is unknown
      return true;
    if (areFS(sysPop, i, i2))
      return false;
    if (areHS(sysPop, i, i2))
      return false;
    if (arePO(sysPop, i, i2))
      return false;
    return true;
  }

  public static boolean areFS(SysPop sysPop, int i, int i2) {
    if (i == i2  ||  i == -1  ||  i2 == -1)
      return false;
    int m = sysPop.getMatId(i);
    int p = sysPop.getPatId(i);
    int m2 = sysPop.getMatId(i2);
    int p2 = sysPop.getPatId(i2);
    if (m == -1  ||  p == -1
    ||  m2 == -1 ||  p2 == -1) // one of the parents is unknown!!!
      return false;
    if ((m == m2  &&  p == p2)
    ||  (m == p2  &&  p == m2))
      return true;
    return false;
  }
  public static boolean areHS(SysPop sysPop, int i, int i2) {
    if (i == i2  ||  i == -1  ||  i2 == -1)
      return false;
    if (areFS(sysPop, i, i2))
      return false;
    int m = sysPop.getMatId(i);
    int p = sysPop.getPatId(i);
    int m2 = sysPop.getMatId(i2);
    int p2 = sysPop.getPatId(i2);

    // HS must have one common parent
    if ((m == -1  &&  p == -1)
    ||  (m2 == -1 &&  p2 == -1)) // both parents is unknown!!!
      return false;
    if (m != -1  &&  (m == m2  ||  m == p2))
      return true;
    if (p != -1  &&  (p == m2  ||  p == p2))
      return true;
    return false;
  }
  public static boolean arePO(SysPop sysPop, int i, int i2) {
    if (i == i2  ||  i == -1  ||  i2 == -1)
      return false;
    int m = sysPop.getMatId(i);
    int p = sysPop.getPatId(i);
    int m2 = sysPop.getMatId(i2);
    int p2 = sysPop.getPatId(i2);
    if (m == i2  ||  p == i2)
      return true;
    if (m2 == i  ||  p2 == i)
      return true;
    return false;
  }

  public static double[][] makePedigreeR(SysPop sysPop) {
    int n = sysPop.size();
    double r[][] = new double[n][n];

    for (int i = 0; i < n; i++) {
      r[i][i] = 1;
      for (int j = 0; j < i; j++) {
        double rij = 0;
        if (arePO(sysPop, i, j)  ||  areFS(sysPop, i, j))
          rij = 0.5;
        else if (areHS(sysPop, i, j))
          rij = 0.25;
        r[i][j] = rij;
        r[j][i] = rij;
      }
    }
    return r;
  }

}
