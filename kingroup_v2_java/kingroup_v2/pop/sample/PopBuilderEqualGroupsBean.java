package kingroup_v2.pop.sample;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/02/2006, Time: 09:57:50
 */
public class PopBuilderEqualGroupsBean
{
  public static final int FREQ_EQUAL = 0;
  public static final int FREQ_RANDOM = 1;
  public static final int FREQ_TRIANGULAR = 2;
  public static final int FREQ_CALC_SAMPLE_FREQ = 3;
  public static final int FREQ_CALC_KON_HEG_2008 = 4;
  public static final int FREQ_DIERKES_2005 = 5;
  public static final int FREQ_ONE_COMMON = 6;

  private int parentAlleleFreqType;
  private int numGroups;
  private int groupSize;
  private int numFullSibs;    // percentage
  private boolean incParents;
  private int numLoci;
  private int numTrials;
  private int numAlleles;
  private boolean shuffled;

  public void loadDefaults() {
    numGroups = 2;
    groupSize = 5;
    incParents = false;
    numLoci = 1;
    numAlleles = 10;
    parentAlleleFreqType = FREQ_EQUAL;
    numTrials = 1000;
    shuffled = false;
  }

  public void setNumLoci(int numLoci) {
    this.numLoci = numLoci;
  }
  public int getNumLoci() {
    return numLoci;
  }
  public void setParentAlleleFreqType(int parentAlleleFreqType) {
    this.parentAlleleFreqType = parentAlleleFreqType;
  }
  public int getParentAlleleFreqType() {
    return parentAlleleFreqType;
  }
  public int getNumGroups() {
    return numGroups;
  }
  public void setNumGroups(int numGroups) {
    this.numGroups = numGroups;
  }
  public int getGroupSize() {
    return groupSize;
  }
  public void setGroupSize(int groupSize) {
    this.groupSize = groupSize;
  }
  public boolean getIncParents() {
    return incParents;
  }
  public void setIncParents(boolean incParents) {
    this.incParents = incParents;
  }

  public int getNumTrials()
  {
    return numTrials;
  }

  public void setNumTrials(int numTrials)
  {
    this.numTrials = numTrials;
  }

  public void setNumAlleles(int numAlleles)
  {
    this.numAlleles = numAlleles;
  }

  public int getNumAlleles()
  {
    return numAlleles;
  }

  public boolean getShuffled()
  {
    return shuffled;
  }

  public void setShuffled(boolean shuffled)
  {
    this.shuffled = shuffled;
  }

  public int getNumFullSibs()
  {
    return numFullSibs;
  }

  public void setNumFullSibs(int numFullSibs)
  {
    this.numFullSibs = numFullSibs;
  }
}
