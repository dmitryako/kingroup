package kingroup_v2.kinship;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 12/03/2006, Time: 11:35:38
 */
public class KinshipAlleleFreqOpt
{
  private boolean calcFreq;
  private boolean exclBias;
  private boolean exclGroup;
  private boolean allowZeroFreq;

  public boolean getCalcFreq()
  {
    return calcFreq;
  }

  public void setCalcFreq(boolean calcFreq)
  {
    this.calcFreq = calcFreq;
  }

  public boolean getExclBias()
  {
    return exclBias;
  }

  public void setExclBias(boolean exclBias)
  {
    this.exclBias = exclBias;
  }

  public boolean getExclGroup()
  {
    return exclGroup;
  }

  public void setExclGroup(boolean correctByGroups)
  {
    this.exclGroup = correctByGroups;
  }

  public void loadDefault()
  {
    setExclBias(false);
    setExclGroup(false);
    setCalcFreq(false);
    setAllowZeroFreq(false);
  }

  public boolean getAllowZeroFreq()
  {
    return allowZeroFreq;
  }

  public void setAllowZeroFreq(boolean allowZeroFreq)
  {
    this.allowZeroFreq = allowZeroFreq;
  }
}
