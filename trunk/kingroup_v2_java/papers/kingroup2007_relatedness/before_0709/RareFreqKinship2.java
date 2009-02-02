package papers.kingroup2007_relatedness.before_0709;
import kingroup_v2.kinship.KinshipR2Mtrx;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/09/2006, Time: 16:58:24
 */
public class RareFreqKinship2 extends RareFreqKinship
{
  public void testSingleFamilyV2() {
    N_TRIALS = 1000;  // 10,000 paper
    POP_BUILDER_MODEL.setBuilderType(PopBuilderModel.SINGLE_FULL_SIB_FAMILY);
    N_ALLELES = 20;
    N_LOCI = 10;
    GROUP_SIZE = 100;
//    MIN_PROP = 10;
    calcByProportion("_QG2_TRI_NL" + N_LOCI + "_NA" + N_ALLELES);
  }

  protected void calcRMatrix(PopBuilderModel builderModel) {
    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
//    log.info("\nsysPop=\n" + sysPop);

//    log.info("Freq=\n" + sysPop.getFreq());
    SysAlleleFreq observedFreq = SysAlleleFreqFactory.makeFrom(sysPop, true);
//    log.info("observedFreq=\n" + observedFreq);
    sysPop.setFreq(observedFreq);

    KinshipR2Mtrx mtrx = new KinshipR2Mtrx(sysPop);
    mtrx.calc();
    loadKin(FS, 0.5, SysPopFactory.getFS(mtrx, sysPop));
    loadKin(HS, 0.25, SysPopFactory.getHS(mtrx, sysPop));
    loadKin(PO, 0.5, SysPopFactory.getPO(mtrx, sysPop));
    loadKin(NR, 0.0, SysPopFactory.getNR(mtrx, sysPop));
  }
}

