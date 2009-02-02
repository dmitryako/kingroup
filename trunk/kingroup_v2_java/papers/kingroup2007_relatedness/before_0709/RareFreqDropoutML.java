package papers.kingroup2007_relatedness.before_0709;
import kingroup_v2.kinship.KinshipRMaxLikeMtrx;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/09/2006, Time: 09:31:27
 */
public class RareFreqDropoutML  extends RareFreqKinship
{
  public void testSingleFamilyV2() {
    N_TRIALS = 1000;  // 10,000 paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    N_ALLELES = 20;
    N_LOCI = 10;
    GROUP_SIZE = 100;
//    MIN_PROP = 10;
    calcByProportion("_ML_TRI_DROPOUT_NL" + N_LOCI + "_NA" + N_ALLELES);
  }

  protected void calcRMatrix(PopBuilderModel builderModel) {
    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
//    log.info("\nsysPop=\n" + sysPop);

    SysPopFactory.applyErrorClassI(sysPop, CLASS_I_ERROR);

//    log.info("Freq=\n" + sysPop.getFreq());
    SysAlleleFreq observedFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);
//    log.info("observedFreq=\n" + observedFreq);
    sysPop.setFreq(observedFreq);

    KinshipRMaxLikeMtrx mtrx = new KinshipRMaxLikeMtrx(sysPop);
    mtrx.calc();
    loadKin(FS, 0.5, SysPopFactory.getFS(mtrx, sysPop));
    loadKin(HS, 0.25, SysPopFactory.getHS(mtrx, sysPop));
    loadKin(PO, 0.5, SysPopFactory.getPO(mtrx, sysPop));
    loadKin(NR, 0.0, SysPopFactory.getNR(mtrx, sysPop));
  }
}
