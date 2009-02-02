package kingroup_v2.partition.dr;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genetics.GenotypePair;
import kingroup.genetics.KinshipRatioMtrxV1;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeGroup;
import kingroup.partition.PartitionMethod;

import javax.utilx.arrays.vec.Vec;

public class DescRatioMethod extends PartitionMethod {
  public static String REFERENCE = "Konovalov et al (2004) MolEcologyNotes 4, p779-782";
  public final static int LIMIT_ATTEMPTS = 10;
  protected Genotype pair_ = null;
  public void init() {
    pair_ = null;
  }
  public Genotype start(GenotypeGroup pool, KinshipRatioMtrxV1 pr) {
    return getNextGenotype(pool, pr);
  }
  public int getNumAttempts(GenotypeGroup pool) {
    return Math.min(LIMIT_ATTEMPTS, pool.size());
  }
  public Genotype getNextGenotype(GenotypeGroup pool, KinshipRatioMtrxV1 pr) {
    if (pool.size() == 1)
      return pool.getGenotype(0);
    Genotype res = pair_;
    if (pair_ != null) {
      pair_ = null;
      return res;
    }
    GenotypePair pair = findBestRatioAll(pool, pr);
    res = random(pair.first(), pair.second());
    pair_ = pair.pair(res);
    return res;
  }
  // PRIVATE -
  private Genotype random(Genotype g, Genotype g2) {
    //Returns a pseudorandom,
    //uniformly distributed int value between 0 (inclusive)
    //and the specified value (exclusive),
    if (random_.nextInt(2) == 0) // Which one to addSimulation? Make a random choice.
      return g;
    else
      return g2;
  }
  protected GenotypePair findBestRatioAll(GenotypeGroup pool
    , KinshipRatioMtrxV1 pr) {
    if (pool.size() == 2)
      return new GenotypePair(pool.getGenotype(0), pool.getGenotype(1));
    double[] arr = new double[pool.size() - 1];
    double[] arr2 = new double[pool.size() - 1];
    Genotype[] pairs = new Genotype[pool.size() - 1];
    int OFFSET = 1;
    for (int r = OFFSET; r < pool.size(); r++) { // row // NOTE r=1
      Genotype geno = pool.getGenotype(r);
      for (int c = 0; c < r; c++) //  column < row  // lower triangle by rows
        arr2[c] = pr.getLog(geno, pool.getGenotype(c));
      int idx2 = Vec.maxIdx(arr2, r);
      pairs[r - OFFSET] = pool.getGenotype(idx2);
      arr[r - OFFSET] = pr.getLog(geno, pool.getGenotype(idx2));
    }
    int idx = Vec.maxIdx(arr, arr.length);
    return new GenotypePair(pairs[idx], pool.getGenotype(idx + OFFSET));
  }
}