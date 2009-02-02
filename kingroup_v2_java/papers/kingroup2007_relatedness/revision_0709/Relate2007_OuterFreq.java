package papers.kingroup2007_relatedness.revision_0709;
import kingroup_v2.kinship.KinshipRMtrxBiasPair;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.relatedness.PairwiseRMtrx;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/09/2007, Time: 10:49:02
 */
public class Relate2007_OuterFreq extends Relate2007_SingleFamily
{
  protected void calcRMatrix(PopBuilderModel builderModel) {
    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
//    log.info("\nsysPop=\n" + sysPop);

    PairwiseRMtrx mtrx = new KinshipRMtrxBiasPair(sysPop, sysPop, KINSHIP);
    mtrx.calc();
    loadKin(FS, 0.5, SysPopFactory.getFS(mtrx, sysPop));
    loadKin(HS, 0.25, SysPopFactory.getHS(mtrx, sysPop));
    loadKin(PO, 0.5, SysPopFactory.getPO(mtrx, sysPop));
    loadKin(NR, 0.0, SysPopFactory.getNR(mtrx, sysPop));
  }
}
