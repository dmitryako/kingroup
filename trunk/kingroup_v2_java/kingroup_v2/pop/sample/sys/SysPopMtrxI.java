package kingroup_v2.pop.sample.sys;
import javax.vecmathx.matrix.MtrxReadI;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/04/2006, Time: 17:04:42
 */
public interface SysPopMtrxI extends MtrxReadI
{
  public SysPop getPop();
  public int getId(int idx);
  public void setName(String name);
  public String getName();
}
