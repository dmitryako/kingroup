package papers.kingroup2007_relatedness.revision_0709;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/09/2007, Time: 10:45:43
 */
public class Relate2007_TrueFreq extends Relate2007_SingleFamily
{
  protected SysPop makeSysPopFrom(PopBuilderModel builderModel) {
    SysPop res = SysPopFactory.makeSysPopFrom(builderModel);   // TRUE FREQS!!!!!!!!!!
//    log.info("\nsysPop=\n" + sysPop);
    return res;
  }
}
