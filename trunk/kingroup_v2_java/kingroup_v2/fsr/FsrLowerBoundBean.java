package kingroup_v2.fsr;
import kingroup_v2.pop.sample.PopBuilderEqualGroupsBean;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 5/02/2006, Time: 09:53:58
 */
public class FsrLowerBoundBean extends PopBuilderEqualGroupsBean
{
  public void loadDefaults() {
    setNumGroups(10);
    setGroupSize(10);
    setIncParents(false);
    setNumAlleles(10);
    setNumLoci(1);
    setParentAlleleFreqType(FREQ_EQUAL);
    setNumTrials(1000);
    setShuffled(false);
  }  
}

