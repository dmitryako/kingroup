package kingroup_v2.lynch;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 4/04/2007, 13:05:49
 */
public class RMtrx_LR_NN extends RMtrx_LR
{
  public RMtrx_LR_NN(SysPop pop)
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
