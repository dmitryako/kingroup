package kingroup_v2.fsr.bound;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.IntVec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 4/11/2005, Time: 09:31:19
 */
public class FsrUnrelatedEmpirical extends FsrLBoundApproxGSOne
{
  public FsrUnrelatedEmpirical(int r, int nAlleles, int nLoci)
  {
    super(r, nAlleles, nLoci);
  }

  public double calcEmpiricalDist()
  {
    RandomSeed rand = RandomSeed.getInstance();
    int[] box = IntVec.makeArray(s, 0);
    int count = 0;
    for (int i = 0; i < r; i++)
    {
      int idx = rand.nextInt(s);
      if (box[idx] > 0)
        count++; // count errors
      box[idx]++;
    }
    return count;
  }
}
