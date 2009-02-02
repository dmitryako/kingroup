package kingroup_v2.pop.sample;
import kingroup_v2.kinship.KinshipIBD;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/09/2005, Time: 08:12:15
 */
public class PopBuilderModel extends PopBuilderEqualGroupsBean
{
  //private static int count = 0;
  public static final int FULL_SIB_BUILDER = 0;
  public static final int KINSHIP_IBD_BUILDER = 1;
  public static final int HALF_SIB_BUILDER = 2;
  public static final int PEDIGREE_BUILDER = 3;
  public static final int PEDIGREE_TRIANG_BUILDER = 4;
  public static final int SINGLE_FULL_SIB_FAMILY = 5;  
  public static final int SINGLE_FULL_SIB_FAMILY_BY_SIZE = 6;

  public static final int EQUAL_GROUPS = 0;
  public static final int MANUAL_GROUPS = 1;

  public static final int EQUAL_LOCI = 0;
  public static final int MANUAL_LOCI = 1;

  private int builderType;
  private int[] groupSizes;
  private int groupsBuilderIdx;
  private int groupSizeSkew;
  private int[] locusSizes;
  private int lociBuilderIdx;
  private float alleleErrorRate;

  private KinshipIBD kinshipIBD;
  private int numKinshipPairs;

  private int numSires;
  private int numDams;
  private int numOffspring;
  private boolean incMatPatIds;
  private int numUnrelated;

  public void loadDefaults() {
    setBuilderType(FULL_SIB_BUILDER);

    setNumUnrelated(0);
    setNumGroups(10);
    setGroupSize(10);
    setIncParents(false);
    setNumAlleles(10);
    setNumLoci(5);
    setParentAlleleFreqType(FREQ_EQUAL);
    setNumTrials(1000);
    setShuffled(false);
    setIncMatPatIds(false);
    setAlleleErrorRate(0f);

    KinshipIBD ibd = new KinshipIBD();
    ibd.setRm(0.5);
    ibd.setRp(0.5);
    setKinshipIBD(ibd);
    setNumKinshipPairs(100);

    setNumSires(2);
    setNumDams(3);
    setNumOffspring(4);
  }

  public int[] getGroupSizes()
  {
    return groupSizes;
  }

  public void setGroupSizes(int[] groupSizes)
  {
    this.groupSizes = groupSizes;
  }

  public int getGroupsBuilderIdx()
  {
    return groupsBuilderIdx;
  }

  public void setGroupsBuilderIdx(int groupsBuilderIdx)
  {
    this.groupsBuilderIdx = groupsBuilderIdx;
  }

  public int getGroupSizeSkew()
  {
    return groupSizeSkew;
  }

  public void setGroupSizeSkew(int groupSizeSkew)
  {
    this.groupSizeSkew = groupSizeSkew;
  }

  public int[] getLocusSizes()
  {
    return locusSizes;
  }

  public void setLocusSizes(int[] locusSizes)
  {
    this.locusSizes = locusSizes;
  }

  public int getLociBuilderIdx()
  {
    return lociBuilderIdx;
  }

  public void setLociBuilderIdx(int lociBuilderIdx)
  {
    this.lociBuilderIdx = lociBuilderIdx;
  }

  public float getAlleleErrorRate()
  {
    return alleleErrorRate;
  }

  public void setAlleleErrorRate(float alleleErrorRate)
  {
    this.alleleErrorRate = alleleErrorRate;
  }

  public KinshipIBD getKinshipIBD()
  {
    return kinshipIBD;
  }

  public void setKinshipIBD(KinshipIBD kinshipIBD)
  {
    this.kinshipIBD = kinshipIBD;
  }

  public int getNumKinshipPairs()
  {
    return numKinshipPairs;
  }

  public void setNumKinshipPairs(int numKinshipPairs)
  {
    this.numKinshipPairs = numKinshipPairs;
  }

  public int getBuilderType()
  {
    return builderType;
  }

  public void setBuilderType(int builderType)
  {
    this.builderType = builderType;
  }

  public int getNumSires()
  {
    return numSires;
  }

  public void setNumSires(int numSires)
  {
    this.numSires = numSires;
  }

  public int getNumDams()
  {
    return numDams;
  }

  public void setNumDams(int numDams)
  {
    this.numDams = numDams;
  }

  public int getNumOffspring()
  {
    return numOffspring;
  }

  public void setNumOffspring(int numOffspring)
  {
    this.numOffspring = numOffspring;
  }

  public void setIncMatPatIds(boolean incMatPatIds) {
    this.incMatPatIds = incMatPatIds;
  }

  public boolean getIncMatPatIds() {
    return incMatPatIds;
  }

  public void setNumUnrelated(int numUnrelated)
  {
    this.numUnrelated = numUnrelated;
  }

  public int getNumUnrelated()
  {
    return numUnrelated;
  }
}
