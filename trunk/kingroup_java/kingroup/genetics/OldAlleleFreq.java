package kingroup.genetics;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
/* Allele frequencies
*/
import kingroup.genotype.Allele;
import kingroup.genotype.Loci;
import kingroup.genotype.Locus;
import kingroup.gui.MainGui;
import kingroup.model.KinshipFileModelV1;
import kingroup.project.KinGroupProjectV1;
import kingroup.util.FastId;
import kingroup.util.FastIdArray;

import javax.iox.DataSourceModel;
import javax.langx.SysProp;
import javax.swing.*;
import java.util.StringTokenizer;
public class OldAlleleFreq extends Loci
{
//  private static String ALLELE_PREFIX = "a";
  private static String ALLELE_PREFIX = "";
  public OldAlleleFreq() {
    super();
  }
//   public OldAlleleFreq(int _size) {super(_size);}
  final public OldAlleleFreq duplicateAlleleFreq() {
    OldAlleleFreq res = new OldAlleleFreq();
    res.copyFastIdFrom(this);
    for (int i = 0; i < getNumLoci(); i++)
      res.add(getLocus(i).duplicate());
    return res;
  }
  public boolean findAll(FastIdArray locusIds) {
    if (!findAllFrom(locusIds))
      return false;
    return findAllIn(locusIds);
  }
  public boolean findAllFrom(FastIdArray locusIds) {
    for (int i = 0; i < locusIds.size(); i++) {
      FastId id = locusIds.get(i);
      if (find(id) == null)
        return false;
    }
    return true;
  }
  public boolean findAllIn(FastIdArray locusIds) {
    for (int i = 0; i < getNumLoci(); i++) {
      Locus locus = getLocus(i);
      if (!locusIds.find(locus))
        return false;
    }
    return true;
  }
//   public boolean findAll(FastIdArray locusIds) {
//      for (int i = 0; i < locusIds.size(); i++) {
//         FastId getId = locusIds.get(i);
//         if (findFirstIdxSLOW(getId) == null)
//            return false;
//      }
//      return true;
//   }
  // PRECOND: none
  // [040124-common] why: If UsrPopFactory block contains unused loci, simulation becomes
  //             complicated, e.g. everytime you need to locate correct locus - slow and messy
  public OldAlleleFreq removeUnused(FastIdArray locusIds) { // list of get ids
    int num_loci = locusIds.size();
    OldAlleleFreq res = new OldAlleleFreq();
    for (int i = 0; i < num_loci; i++) {
      FastId locusId = locusIds.get(i); // get getId
      Locus locus = find(locusId);
      res.add(locus);
    }
    return res;
  }
  // fetching getAllele frequencies from file
  public boolean loadFreqFrom(DataSourceModel file, KinshipFileModelV1 format) {
    if (file == null)
      return false;
    boolean endOfFreqBlock = false;
    boolean firstValidLine = true;
    for (int i = 0; i < file.size(); i++) {
      file.setStartIdx(i);
      String line = file.get(i);
      if (format.ignoreInputLine(line))
        continue;
      if (firstValidLine) {
        firstValidLine = false;
        String[] res = format.importFreqLocusIdsFrom(line);
        put(res);
        if (res == null)  // first line is the header
          return false;
        continue;
      }
      if (format.isEndOfAlleleFreqs(line.trim())) {
        file.setStartIdx(i + 1);
        endOfFreqBlock = true;
        break; // end of all getAllele frequencies detected
      }
      if (!loadFrom(line))
        return false; // error
      file.setStartIdx(i + 1);
    }
    return (getNumLoci() > 0) && endOfFreqBlock;
  }
  /**
   * Normalizes all frequencies (within each get) to val
   *
   * @param val            normally 1.0
   * @param displayWarning set to TRUE if you want to report that the original normaisation values are not VAL.
   *                       For example, Needed for when reading from a file and reporting that the sum of all frequencies is not 1.
   */
  final public void normalize(double val, boolean displayWarning) {
    for (int locusIdx = 0; locusIdx < getNumLoci(); locusIdx++) {
      Locus locus = getLocus(locusIdx);
      double sum = 0;
      for (int alleleIdx = 0; alleleIdx < locus.size(); alleleIdx++)
        sum += locus.get(alleleIdx).getProb();
      if (displayWarning && (float) sum != (float) val) {
        JOptionPane.showMessageDialog(MainGui.getInstance()
          , "Allele frequencies for Locus#" + (locusIdx + 1) + SysProp.EOL
          + "is currently normalized to " + sum + "\n\n"
          + "These frequencies will be renormalized to " + val);
      }
      if ((float) sum == 0f || (float) sum == (float) val)
        continue;
      locus.multiplyProb(val / sum);
    }
  }
  final public void generateLocusIds(int num) {
    for (int i = 0; i < num; i++)
      put("L" + (i + 1));
  }
  final public void generateAlleles(int num) {
    for (int i = 0; i < getNumLoci(); i++) {
      Locus locus = getLocus(i);
      for (int a = 0; a < num; a++) {
        Allele allele = new Allele();
//            getAllele.setId(ALLELE_PREFIX + (a + 1) + "@" + get.getId());
//        allele.setId(locus.getId() + ALLELE_PREFIX + (a + 1));
        allele.setId(Integer.toString(a + 1));
        locus.add(allele);
      }
    }
  }
  private boolean loadFrom(String s) {
//      LOG.trace(this, "importGenotypeGroupDataFrom(", s);
    KinshipFileModelV1 format = KinGroupProjectV1.getInstance().getKinshipFormatModel();
    boolean limitNumLoci = format.getHasMaxNumLoci();
    StringTokenizer st = new StringTokenizer(s, format.getColumnDelimiters(), true);//returnDelims==true
    int locusIdx = -1;
    String delim = null;
    while (st.hasMoreTokens()) {
      locusIdx++;  // starts with 0
      String alleleId = st.nextToken().trim();

      // BE CAREFUL HERE! This code must work with empty inputs like ,,,,,,,
      if (alleleId.length() == 1                        // case: >,<
        && format.isColumnDelimiter(alleleId.charAt(0))) // CHECK for delimiters
        alleleId = "";
      else {                                          // case: >something,<
        if (st.hasMoreTokens())
          delim = st.nextToken().trim();          // MUST be a delim!!!
      }
      String freq = null;
      if (st.hasMoreTokens()) {
        freq = st.nextToken().trim();
        if (freq.length() == 1                        // case: >,<
          && format.isColumnDelimiter(freq.charAt(0))) // CHECK for delimiters
          freq = "";
        else {                                       // case: >something,<
          if (st.hasMoreTokens())
            delim = st.nextToken().trim();       // MUST be a delim!!!
        }
      }
      if (alleleId.length() < 1 || freq == null || freq.length() < 1)
        continue;  // ignore bad data
      if (locusIdx >= getNumLoci()) { // ignore passed valid header
//            LOG.trace(this, "locusIdx=", locusIdx);
        break;
      }
      Locus locus = getLocus(locusIdx);
      double val = 0;
      try {
        val = Double.parseDouble(freq);
      }
      catch (NumberFormatException e) {
        val = 0;
      }
      Allele allele = locus.put(alleleId);
      allele.setProb(val);
    }
    return true;
  } //importGenotypeGroupDataFrom
}
