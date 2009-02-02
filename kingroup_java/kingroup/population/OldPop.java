package kingroup.population;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genetics.OldAlleleFreq;
import kingroup.genotype.Genotype;
import kingroup.gui.MainGui;
import kingroup.model.KinshipFileModelV1;

import javax.iox.DataSourceModel;
import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;

// Population is a collection of groups (each group is stored in PopGroup)
public class OldPop extends PopGroup {
  public final static int UNKNOWN = -1;
  public final static int KINSHIP_FILE = 0;
  public final static int GENERATED = 1;
  private int sourceType_ = UNKNOWN;
  private TreeMap mapGroupIdToGroup_ = new TreeMap();
  private PopGroup[] groups_ = new PopGroup[0];
  public OldPop() {
  }
  final public int getSourceType() {
    return sourceType_;
  }
  final public void setSourceType(int t) {
    sourceType_ = t;
  }
  // TODO: Move to KPopulationView
  public static void showMessageLoadPopulationFirst(Component view) {
    JOptionPane.showMessageDialog(view
      , "Load or generate population first via Menu | Population |");
  }
  public OldPop(OldAlleleFreq from) {
    initPop(from);
  }
  public OldPop(OldPop from) {
    super(from);
    initPop(from.getAlleleFreq());
    mapGroupIdToGroup_ = (TreeMap) from.mapGroupIdToGroup_.clone(); // ok to remove
    groups_ = new PopGroup[from.groups_.length];
    PopulationGroupArrays.arraycopy(from.groups_, groups_, groups_.length);
  }
  final public void initPop(OldAlleleFreq freq) {
    setSourceType(OldPop.GENERATED);
    setAlleleFreq(freq);
    setLocusIds(freq.getLocusIds());
  }
  public PopGroup getGroupByName(String id) {
    return (PopGroup) mapGroupIdToGroup_.get(id);
  }
  public PopGroup getGroupByIdx(int idx) {
    return groups_[idx];
  }
  /**
   * @param group - new group to be added together with its members
   *              TODO [common 040426]: Why all group memebers are also added to the population?
   *              TODO: This looks wrong! What if some or all of them are already in that population?
   *              PRE:  none
   *              POST: 1. Group and EACH group individual is added to this population
   *              2. Group's LocusIds is set to the population's
   */
  public void addGroup(PopGroup group) {
    String groupId = group.getId();
    mapGroupIdToGroup_.put(groupId, group);
    groups_ = PopulationGroupArrays.add(group, groups_);
    group.setLocusIds(getLocusIds());
    for (int i = 0; i < group.size(); i++) {
      Genotype g = group.getGenotype(i);
      addGenotype(g);
    }
  }
  public int getGroupCount() {
    if (mapGroupIdToGroup_ == null)
      return 1;
    return mapGroupIdToGroup_.size();
  }
  public Object[] getGroupNames() {
    if (mapGroupIdToGroup_ == null)
      return null;
    Object[] a = mapGroupIdToGroup_.keySet().toArray();
    return a;
  }
  public boolean importPopulationFrom(DataSourceModel file, KinshipFileModelV1 format) {
    if (!super.importGenotypeGroupDataFrom(file, format))
      return false;

    // assign EACH geno to its group
    for (int i = 0; i < size(); i++) { // for each geno
      Genotype geno = getGenotype(i);
      String groupId = geno.getGroupId();
      PopGroup group = (PopGroup) mapGroupIdToGroup_.get(groupId);
      if (group == null) {
        group = new PopGroup();
        group.setId(groupId);
        group.setLocusIds(getLocusIds());
        mapGroupIdToGroup_.put(groupId, group);
        groups_ = PopulationGroupArrays.add(group, groups_);
      }
      group.addGenotype(geno);
    }
    setSourceType(KINSHIP_FILE);
    if (!setupAlleleFreqs(format))
      return false;
    trimGenotypesToLocusIds();
    return true;
  }
  private void trimGenotypesToLocusIds() {
    int maxSize = getLocusIds().size();
    for (int i = 0; i < size(); i++) { // for each geno
      Genotype geno = getGenotype(i);
      geno.limitSize(maxSize);
    }
  }
  public boolean setupAlleleFreqs(KinshipFileModelV1 format) {
    switch (format.getAlleleFreqs()) {
      case KinshipFileModelV1.ALLELE_FREQ_FILE:
        return setupAlleleFreqsFromFile(format);
      case KinshipFileModelV1.ALLELE_FREQ_BIAS:
        return setupAlleleFreqsWithBias();
      default:
        return setupAlleleFreqsFromPop();
    }
  }
  public boolean setupAlleleFreqsFromPop() {
    shareFreqAlleles(); // only one set alleles is needed (shared between UsrPopFactory and genos)
    calculateAlleleFreqs();
    getAlleleFreq().normalize(1.0f, false);
    for (int i = 0; i < groups_.length; i++) {
      PopGroup group = groups_[i];
      group.setAlleleFreq(getAlleleFreq());
      group.setLocusIds(getLocusIds());
    }
    return true;
  }
  public boolean setupAlleleFreqsFromFile(KinshipFileModelV1 format) {
    // must findFirstIdxSLOW all get ids in the frequency
    if (!getAlleleFreq().findAll(getLocusIds())) {
      JOptionPane.showMessageDialog(MainGui.getInstance()
        , "KINSHIP file format ERROR:\nFrequency block locus ids are\n"
        + getAlleleFreq().getLocusIds().toStringIds()
        + "\n\n"
        + "while Genotype block has\n"
        + getLocusIds().toString()
      );
      return setValid(false, "Locus ids must be the same in frequency and getGenotype blocks");
    }
    shareFreqAlleles(); // only one set alleles is needed (shared between UsrPopFactory and genos)
    OldAlleleFreq newFreq = getAlleleFreq().removeUnused(getLocusIds());
    setAlleleFreq(newFreq);
    getAlleleFreq().normalize(1.0f, true);  // display renomalize warning when reading from file
    if (format.getHasMaxNumLoci()) {
      if (newFreq.getLocusIds().size() != getLocusIds().size()) {
        String mssg = "Number of Locus ids are different\n"
          + "between Frequency and Genotype blocks.\n"
          + "\n    Freq:" + newFreq.getLocusIds().toStringIds()
          + "\nGenotype:" + getLocusIds().toStringIds()
          + "\nCheck:\n"
          + "Limit number of loci to ["
          + format.getMaxNumLoci()
          + "?]\n\n"
          + "Do you still wish to proceed with this import?";
        if (JOptionPane.showConfirmDialog(MainGui.getInstance()
          , mssg, "Warning"
          , JOptionPane.YES_NO_OPTION
          , JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION)
          return setValid(false, "Import was canceled.");
      }
    }
    for (int i = 0; i < groups_.length; i++) {
      PopGroup group = groups_[i];
      group.setAlleleFreq(getAlleleFreq());
      group.setLocusIds(getLocusIds());
    }
    return true;
  }
  public boolean setupAlleleFreqsWithBias() {
    shareFreqAlleles(); // only one set alleles is needed (shared between UsrPopFactory and genos)
    calculateAlleleFreqs();
    //LOG.trace(this, "AFTER calculateAlleleFreqs: popFreq=", getAlleleFreq());
    Object[] groupIds = getGroupNames();
    if (groupIds.length <= 1)
      return setValid(false
        , "Bias correction is only supported for a population with more than one group.");
    // DO NOT use groups_
    for (int i = 0; i < groupIds.length; i++) { // scan all groups
      String groupId = (String) groupIds[i];
      PopGroup group = (PopGroup) mapGroupIdToGroup_.get(groupId);

      // VERY IMPORTANT!!!
      // when using bias corrected frequencies, each group
      // must have its own frequency block AND alleles
      group.setAlleleFreq(getAlleleFreq().duplicateAlleleFreq());
      group.shareFreqAlleles();
      group.setLocusIds(getLocusIds());
      group.calculateAlleleFreqs();
      OldAlleleFreq freq = group.getAlleleFreq();
      freq.setId(groupId); // label UsrPopFactory based on its group
//         LOG.trace(this, "UsrPopFactory [AFTER calculateAlleleFreqs for group=", groupId
//            , "]=\n", UsrPopFactory);
    }
    calcBiasCorrection();
    return true;
  }
  // from Kinship manual 1.2, section "Allele frequencies and group ID"
  /*
  Allele frequencies and group ID
  As discussed in Queller & Goodnight (1989),
  when performing relatedness calculations using population getAllele frequencies obtained from the same data set as the individuals being measured, a bias correction must be applied to those frequencies. The same consideration applies to the likelihood calculations performed by Kinship.
  Basically, any individuals which by hypothesis are relatives of the individual(s) under consideration will be expected to have getAllele frequencies closer to those individuals than the true population mean. Their inclusion in the limited sample of a data set thus biases its measure of pop frequencies in that direction.
  The solution is to exclude from background frequency calculations all individuals who might be relatives of the current individual.
  Like our earlier program Relatedness,  Kinship  uses a “Group ID” variable to identify sets of such individuals. As it performs calculations on a given pair of individuals, Kinship  will use getAllele frequencies obtained from the data set excluding  the group(s) to which the current pair belong.
  Unlike Relatedness 4.2,  Kinship’s  group ID variable is optional. If no group ID is loaded (or if it is loaded but has only one value, an equivalent situation), then Kinship  can only bias-correct population frequencies by excluding the 2 individuals in the current pair. Also, if an getAllele frequencies block is included in the data file then Kinship  will assume that the frequencies provided are correct and will not attempt any corrections— it will use exactly the frequencies read from the file for all calculations.
  Because of this function of the group ID, it is important for the accuracy of results to use this variable correctly. If your population can be clearly divided into sets of related individuals, you should designate this division with a group variable (even if you intend to do comparisons across the whole pop). Likewise, if your pop is more mixed, with no clear sets of relatives, you should not use a group ID.
  If you want to use a variable to subdivide your comparisons but it is not appropriate to use it for this bias correction, then you can include an getAllele frequencies block in your data set along with loading the variable as a group ID. Since having a frequencies block overrides the bias correction, the group ID will only function to subdivide the pairwise comparisons.
  */
  private void calcBiasCorrection() {
    OldAlleleFreq popFreq = getAlleleFreq();

//      Object[] groupIds = getGroupNames();
//      for (int i = 0; i < groupIds.length; i++) { // scan all groups
//         String groupId = (String)groupIds[i];
//         LOG.trace(this, "Perform bias correction for group [", groupId);
//         PopGroup group = (PopGroup)mapGroupIdToGroup_.get(groupId);
    for (int i = 0; i < groups_.length; i++) {
      PopGroup group = groups_[i];
      if (group == null)
        continue;
      OldAlleleFreq freq = group.getAlleleFreq();

      // NOTE: all freqs must have the same structure!!!
      freq.subtractProbs(popFreq);
      freq.multiplyProb(-1.0f);
//         LOG.trace(this, "group=", groupId, " UsrPopFactory [AFTER subtract(UsrPopFactory, popFreq)]=\n", UsrPopFactory);
      freq.normalize(1.0f, false);
//         LOG.trace(this, "group ", groupId, " UsrPopFactory [AFTER normalize(1.0)]=\n", UsrPopFactory);
    }
    popFreq.normalize(1.0f, false);
    // DO NOT load freqs into population!!!
    // alleles are different between groups BUT
    // genos are the same in the population and groups!!!
    // Remember groups are just regrouping of the individuals in the population
    // DO NOT CALL    shareFreqAlleles();
    // as it will point all alleles to the population feq alleles!
  }
}

