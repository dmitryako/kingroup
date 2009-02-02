package kingroup_v2.relatedness.qg;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 4/04/2007, 13:10:12
 */
public class RMtrx_QG_NN extends RMtrx_QG  // Non-negative
{
  public RMtrx_QG_NN(SysPop pop)
  {
    super(pop);
  }
  public double get(int r, int c) {
    double v = super.get(r, c);
    if (v < 0)
      return 0;
    return v;
  }
}

