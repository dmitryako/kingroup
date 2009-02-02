package kingroup_v2.pop.allele.freq;
import kingroup_v2.KingroupFrame;
import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
import javax.swing.*;
import javax.utilx.arrays.vec.Vec;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 10/09/2005, Time: 16:18:51
 */
final public class SysAlleleFreq
{
  private static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private double[] idendixWeight; // sum(p_i^2)
  private SysAlleleFreqRandomFactory randomFactory;

  private int maxNumAlleles;
  private final int nLoci;
  private final double[][] af; // allelic frequencies
  public SysAlleleFreq(int nLoci, int nAlleles) {
    assert(nAlleles > 0);
    assert(nLoci > 0);
    af = new double[nLoci][nAlleles]; // NOTE getNumLoci is first!!!
    this.maxNumAlleles = nAlleles;
    this.nLoci = nLoci;
  }
  public SysAlleleFreq(int nLoci) {
    this(nLoci, 0);
  }
  public void init() {
    randomFactory = null;
    idendixWeight = null;
  }
  final public void setFreq(int locIdx, int i, double f) {
    if (i == -1)
      return;
    init();
    af[locIdx][i] = f;
  }
  final public double getFreq(int L, int a) {
    if (a < 0)
      return 0;
    if (L < 0 || L >= af.length) {
      String error = "invalid getFreq(L=" + L + "); must be 0<L<" + (af.length-1);
      log.severe(error);
      throw new RuntimeException(error);
    }
    if (a >= af[L].length) {
      String error = "invalid getFreq(L=" + L + ", a="+a+"); must be 0<a<" + (af[L].length-1);
      log.severe(error);
      throw new RuntimeException(error);
    }
    return af[L][a];
  }
  final public double[] getLocFreq(int L) {
    return af[L];
  }
  final public double[] getLocFreqCopy(int L) {
    return Vec.copy(af[L]);
  }
  final public int getLocusSize(int L) {
    return af[L].length;
  }
  final public void setLocusFreq(int L, double[] locus) {
    init();
    int oldSize = af[L].length;
    int newSize = locus.length;
    af[L] = locus;
    if (newSize > oldSize) {
      if (maxNumAlleles < newSize) {
        maxNumAlleles = newSize;
      }
    }
    if (newSize < oldSize) {
      maxNumAlleles = calcMaxNumAlleles();
    }
  }
  private int calcMaxNumAlleles() {
    int maxSize = 0;
    for (int L = 0; L < getNumLoci(); L++) {
      double[] locus = getLocFreq(L);
      if (maxSize < locus.length) {
        maxSize = locus.length;
      }
    }
    return maxSize;
  }
  final public int getMaxNumAlleles() {
    return maxNumAlleles;
  }
  final public int getNumLoci() {
    return nLoci;
  }
  /**
   * Normalizes all frequencies (within each get) to val
   *
   * @param val            normaly 1.0
   * @param displayWarning set to TRUE if you want to report that the original normaisation values are not VAL.
   *                       For example, Needed for when reading from a file and reporting that the sum of all frequencies is not 1.
   */
  final public void normalize(double val, boolean displayWarning) {
    init();
    for (int L = 0; L < nLoci; L++) {
      double[] freq = getLocFreq(L);
      double sum = Vec.sum(freq);
      if (displayWarning && (float) sum != (float) val) {
        JOptionPane.showMessageDialog(KingroupFrame.getInstance()
          , "Allele frequencies for Locus#" + (L + 1) + SysProp.EOL
          + "is currently normalized to " + sum + "\n\n"
          + "These frequencies will be renormalized to " + val);
      }
      if ((float) sum == 0f || (float) sum == (float) val)
        continue;
      Vec.mult(val / sum, freq);
    }
  }
  public String toString() {
    String spacer = ", ";
    StringBuffer buff = new StringBuffer();
    for (int a = 0; a < maxNumAlleles; a++) {
      buff.append(Integer.toString(a)).append(spacer);
      for (int L = 0; L < getNumLoci(); L++) {
        int locusSize = getLocusSize(L);
        if (a < locusSize) {
          buff.append(Float.toString((float) getFreq(L, a)));
        }
        buff.append(spacer);
      }
      buff.append(SysProp.EOL);
    }
    return buff.toString();
  }

  public int getRandomAllele(int locIdx)
  {
    if (randomFactory == null)
      randomFactory = new SysAlleleFreqRandomFactory(this);
    return randomFactory.getRandomAllele(locIdx);
  }
  public double getIdentixWeight(int locIdx)
  {
    if (idendixWeight == null)
      idendixWeight = SysAlleleFreqFactory.calcIdendixWeight(this);
    return idendixWeight[locIdx];
  }

  public double mse(SysAlleleFreq from)
  {
    double res = 0;
    for (int L = 0; L < nLoci; L++) {
      double[] f = getLocFreq(L);
      double[] f2 = from.getLocFreq(L);
      double mse = Vec.distL2(f, f2) / f.length;
      res += mse;
    }
    res /= nLoci;
    log.debug("mse=", res);
    return res;
  }
}
