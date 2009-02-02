package multi_t.pcr;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/11/2006, Time: 15:44:42
 */
public class Primer
{
  private String seq;
  private String lbl;
  private int maxNumErrs;

  public Primer() {
    init();
  }

  private void init()
  {
    seq = new String();
    lbl = new String();
    maxNumErrs = 4;
  }

  public String getSeq()
  {
    return seq;
  }

  public void setSeq(String seq)
  {
    this.seq = seq;
  }

  public String getLbl()
  {
    return lbl;
  }

  public void setLbl(String lbl)
  {
    this.lbl = lbl;
  }

  public int getMaxNumErrs()
  {
    return maxNumErrs;
  }

  public void setMaxNumErrs(int maxNumErrs)
  {
    this.maxNumErrs = maxNumErrs;
  }

  public void loadDefault()
  {
    setMaxNumErrs(4);
    setLbl("primer");
    setSeq("missing");
  }
}
