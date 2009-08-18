package kingroup_v2.fsr.bound;
import tsvlib.project.ProjectLogger;

import javax.utilx.RandomSeed;
import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.matrix.LowTriangleMatrix;
import java.util.ArrayList;
import java.util.BitSet;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/11/2005, Time: 14:28:17
 */
public class FsrLBoundMonteCarlo extends FsrLBound
{
  private static ProjectLogger log = ProjectLogger.getLogger(FsrLBoundMonteCarlo.class.getName());

  public FsrLBoundMonteCarlo(int r, int nAlleles, int nLoci)
  {
    super(r, nAlleles, nLoci);
  }
  public double calcAccuracyError(int gs) {
    return runSimL(gs).getAccuracyError();
  }
  public FsrLBoundResults runSimOLD(int gs // group size
  )  {
    RandomSeed rand = RandomSeed.getInstance();
    BitSet boxes = new BitSet();
    int[] qm = IntVec.make(N_PHENOTYPES, 0);
    int count = 0;
    for (int i = 0; i < r; i++)
    {
      int x = rand.nextInt(na); // parent #1
      int x2 = rand.nextInt(na);
      int y = rand.nextInt(na); // parent #2
      int y2 = rand.nextInt(na);

      BitSet group = new BitSet();
      for (int j = 0; j < gs; j++) {
        int z = makeOffspringAllele(x, x2);
        int z2 = makeOffspringAllele(y, y2);
        int idx = idxFromAlleles(z, z2);
        if (boxes.get(idx))
          count++; // count errors
        group.set(idx);
      }
      int m = group.cardinality();
//      log.info("\n("+x+","+x2+") + ("+y+","+y2+") = "+ m);
      qm[m-1]++;
      boxes.or(group);
    }
    FsrLBoundResults res = new FsrLBoundResults();
    res.setNumErrors(count);
    res.setAccuracyError(count/(r*gs));
    res.setPhenotypeCount(qm);
    return res;
  }

  public FsrLBoundResults runSimL(int gs // group size
  )  {
    RandomSeed rand = RandomSeed.getInstance();
    ArrayList<int[]> boxes = new ArrayList<int[]>();
    int[] qm = IntVec.make(N_PHENOTYPES, 0);
    int count = 0;
    for (int i = 0; i < r; i++)
    {
      // BUILD GROUP
      int[][] group = new int[gs][L];
      for (int locus = 0; locus < L; locus++) // LOCUS
      {
        int x = rand.nextInt(na); // parent #1
        int x2 = rand.nextInt(na);
        int y = rand.nextInt(na); // parent #2
        int y2 = rand.nextInt(na);
        for (int j = 0; j < gs; j++) {
          int z = makeOffspringAllele(x, x2);
          int z2 = makeOffspringAllele(y, y2);
          int idx = idxFromAlleles(z, z2);
          group[j][locus] = idx;
        }
      }

      // COUNT ERRORS
      for (int j = 0; j < gs; j++) {
        if (find(group[j], boxes))
          count++;
      }

      // LOAD PHENOTYPES
      ArrayList<int[]> phenos = new ArrayList<int[]>();
      for (int j = 0; j < gs; j++) {
        if (find(group[j], phenos))
          continue;
        phenos.add(group[j]);
      }

      int m = phenos.size();
//      log.info("\n("+x+","+x2+") + ("+y+","+y2+") = "+ m);
      if (m <= N_PHENOTYPES)
        qm[m-1]++;

      // MERGE
      for (int j = 0; j < phenos.size(); j++) {
        if (find(phenos.get(j), boxes))
          continue;
        boxes.add(phenos.get(j));
      }
    }
    FsrLBoundResults res = new FsrLBoundResults();
    res.setNumErrors(count);
    res.setAccuracyError((double)count/(r*gs));
    res.setPhenotypeCount(qm);
    return res;
  }
//  private class Genotype {
//    public IntPairSymmKey[] loci = new IntPairSymmKey[L];
//  }

  private boolean find(int[] geno, ArrayList<int[]> boxes)
  {
    for (int i = 0; i < boxes.size(); i++)
    {
      int[] box = boxes.get(i);
      if (equals(box, geno))
        return true;
    }
    return false;
  }

  private boolean equals(int[] geno, int[] geno2)
  {
    for (int locus = 0; locus < geno.length; locus++) {
      if (geno[locus] != geno2[locus])
        return false;
    }
    return true;
  }

  public FsrLBoundResults runSim(int gs, int nTrials)
  {
    int[] qm = IntVec.make(N_PHENOTYPES, 0);
    int count = 0;
    for (int i = 0; i < nTrials; i++)
    {
//      FsrLBoundResults trial = runSimOLD(gs);
      FsrLBoundResults trial = runSimL(gs);
      count += trial.getNumErrors();
      IntVec.add(qm, trial.getPhenotypeCount());
    }
    FsrLBoundResults res = new FsrLBoundResults();
    res.setNumErrors(count);
    res.setAccuracyError((double)count/(nTrials*r*gs));

    double[] pm = Vec.makeArray(0., N_PHENOTYPES);
    Vec.add(pm, qm);
    Vec.mult(1./(nTrials*r), pm);
    res.setPhenotypeCount(qm);
    res.setPhenotypeProb(pm);
    return res;
  }

//  public int calcDist(int gs // group size
//  )  {
//    RandomSeed rand = RandomSeed.getInstance();
//    int[] box = IntVec.make(s, 0);
//    int count = 0;
//    for (int i = 0; i < r; i++)
//    {
//      int x = rand.nextInt(na); // parent #1
//      int x2 = rand.nextInt(na);
//      int y = rand.nextInt(na); // parent #2
//      int y2 = rand.nextInt(na);
//
//      int[] saved = IntVec.make(s, 0);
//      for (int g = 0; g < gs; g++) {
//        int z = makeOffspringAllele(x, x2);
//        int z2 = makeOffspringAllele(y, y2);
//        int idx = idxFromAlleles(z, z2);
//        if (box[idx] > 0)
//          count++; // count errors
//        saved[idx]++;
//      }
//      IntVec.add(box, saved);
//    }
//    return count;
//  }

  private int makeOffspringAllele(int x, int x2)
  {
    RandomSeed rand = RandomSeed.getInstance();
    return (rand.nextInt(2) == 0) ? x : x2;
  }

  public int idxFromAlleles(int a, int a2) {
    if (a == a2)
      return a;
    if (a > a2)
      return na + LowTriangleMatrix.lowerTriangleToIdx(a, a2, na);
    else
      return na + LowTriangleMatrix.lowerTriangleToIdx(a2, a, na);
  }
}
