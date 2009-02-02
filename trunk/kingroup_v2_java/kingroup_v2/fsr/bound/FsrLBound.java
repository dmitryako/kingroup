package kingroup_v2.fsr.bound;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 14/11/2005, Time: 16:09:04
 */
public class FsrLBound
{
  public int N_PHENOTYPES = 4;
  protected final int na; // num of alleles
  protected final int L; // num of loci
  protected final double ina; //1/na
  protected final int s;  // number of boxes
  protected final int r;  // number of balls

  public FsrLBound(int r, int nAlleles, int nLoci) {
    na = nAlleles;
    ina = 1. / na;
    L = nLoci;
    this.r = r;
    int a = (nAlleles * (nAlleles + 1)) / 2;
    s = (int)Math.pow(a, nLoci);
  }
}
