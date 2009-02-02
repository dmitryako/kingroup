package kingroup_v2.partition.simpson;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 28/11/2005, Time: 17:53:01
 */
public class SIMPS2AlgModel
{
  private int numIters;
  private SibshipAlg sibshipAlg;
  private int loopBreaker;

  public SIMPS2AlgModel()
  {
    sibshipAlg = new DiploidSibship();
    loopBreaker = -1;
  }
  public int getNumIters()
  {
    return numIters;
  }

  public void setNumIters(int numIters)
  {
    this.numIters = numIters;
  }

  public SibshipAlg getSibshipAlg()
  {
    return sibshipAlg;
  }

  public void setSibshipAlg(SibshipAlg sibshipAlg)
  {
    this.sibshipAlg = sibshipAlg;
  }

  public int getLoopBreaker()
  {
    return loopBreaker;
  }

  public void setLoopBreaker(int loopBreaker)
  {
    this.loopBreaker = loopBreaker;
  }
}
