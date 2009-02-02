package kingroup.population;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genetics.OldAlleleFreq;
import kingroup.genotype.Allele;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeGroup;
import kingroup.genotype.Locus;
import kingroup.model.KinshipFileModelV1;
import kingroup.util.FastIdArray;

import javax.iox.DataSourceModel;
import javax.langx.SysProp;
// PopGroup is an array of Genotype's put size
public class PopGroup extends GenotypeGroup {
  private String error_;
  private boolean validState_ = false;
  private OldAlleleFreq freq_ = new OldAlleleFreq(); // getAllele frequencies
  private FastIdArray locusIds_ = new FastIdArray();  // is used for to store the list of get ids
  private static final String MISSING_GROUP_ID = "missing_group_id";
  private static final String MISSING_ID = "missing_id_";
  public PopGroup() {
  }
  public PopGroup(PopGroup from) {
    super(from);
    validState_ = from.validState_;
    freq_ = from.freq_;
  }
  final public void setLocusIds(FastIdArray h) {
    locusIds_ = h;
  }
  final public FastIdArray getLocusIds() {
    return locusIds_;
  }
  final public int getNumLoci() {
    return locusIds_.size();
  }
  final public OldAlleleFreq getAlleleFreq() {
    return freq_;
  }
  final public void setAlleleFreq(OldAlleleFreq to) {
    freq_ = to;
  }
  final public String getError() {
    return error_;
  }
  final public boolean setValid(boolean b, String error) {
    validState_ = b;
    if (b)
      error_ = "";
    else
      error_ = error;
    return b;
  }
  final public boolean isValid() {
    return validState_;
  }
  public boolean importGenotypeGroupDataFrom(DataSourceModel file, KinshipFileModelV1 format) {
    if (file == null)
      return setValid(false, "Input file is missing");
    if (format.getAlleleFreqs() == format.ALLELE_FREQ_FILE) { // this file has getAllele frequencies
      if (!freq_.loadFreqFrom(file, format)) // load getAllele frequencies from file
        return setValid(false
          , "Unable to load Allele Frequency Block from the input file");
    } else {
      // trying to help...
      // Allele UsrPopFactory should not be present, but if they are present, ignore them
      if (!freq_.loadFreqFrom(file, format)) {
        freq_ = new OldAlleleFreq(); // remove any rubbish that may have been loaded
        file.setStartIdx(0); //
      }
    }

    // NOTE: getStartIdx() - OldAlleleFreq.importGenotypeGroupDataFrom() may set the index to past where it read
    for (int i = file.getStartIdx(); i < file.size(); i++) {
      String line = file.get(i); // NOTE!!! do not trim > , ...< could be a missing group getId
      if (format.ignoreInputLine(line))
        continue;
      if (getLocusIds().size() == 0) { // Header is before the actual data
        String[] ids = format.importLocusIdsFrom(line);
        setLocusIds(new FastIdArray(ids));
        continue;
      }
      Genotype g = format.importGenotypeFrom(line, getLocusIds());
      if (g != null)
        addGenotype(g);
    }
    setValid(size() > 0, "Zero size population");
    if (isValid())
      replaceBlankIds(format);
    return isValid();
  }
  public void replaceBlankIds(KinshipFileModelV1 format) {
    for (int i = 0; i < size(); i++) {
      Genotype g = getGenotype(i);
      if (g.getGroupId() == null || g.getGroupId().length() == 0) {
        if (format.getHasGroupIdColumn())
          g.setGroupId(MISSING_GROUP_ID);
        else
          g.setGroupId(format.DEFAULT_GROUP_ID);
      }
      if (g.getId() == null || g.getId().length() == 0) {
        if (format.getHasIdColumn())
          g.setId(MISSING_ID + (i + 1));
        else
          g.setId("i" + (i + 1));
      }
    }
  }
  // POST: genotypes will point to the same getAllele as the UsrPopFactory's
  public void shareFreqAlleles() {
    for (int i = 0; i < size(); i++) { // for all getGenotype data sets
      Genotype geno = getGenotype(i);
      for (int L = 0; L < geno.getNumLoci(); L++) { // for all loci in a given getGenotype
        Locus locus = geno.getLocus(L);
        for (int a = 0; a < locus.size(); a++) { // for all alleles in a given get
          Allele allele = locus.get(a);
          Allele freqAllele = freq_.find(locus, allele);
          if (freqAllele == null)
            freq_.replaceSharedAllele(locus, allele); // missing in UsrPopFactory
          else
            locus.replace(a, freqAllele); // replace by UsrPopFactory's getAllele
        }
      }
    } // for
  }
  protected void calculateAlleleFreqs() {
    freq_.initProb();
    for (int i = 0; i < size(); i++) { // for all getGenotype data sets
      Genotype geno = getGenotype(i);
      for (int L = 0; L < geno.getNumLoci(); L++) { // for all loci in a given getGenotype
        Locus locus = geno.getLocus(L);
        for (int a = 0; a < locus.size(); a++) { // for all alleles in a given get
          Allele allele = locus.get(a);
          allele.addProb(1.0f); // THIS IS CORRECT! using prob as a count
        }
      }
    }
  }
  public String toString() {
    StringBuffer res = new StringBuffer();
    if (freq_ == null)
      res.append("UsrPopFactory=null");
    else
      res.append("UsrPopFactory=").append(freq_.toString());
    res.append(SysProp.EOL);
    if (locusIds_ == null)
      res.append("locusIds=null");
    else
      res.append("locusIds=").append(locusIds_.toString());
    res.append(SysProp.EOL);
    res.append(super.toString());
    return res.toString();
  }
}
