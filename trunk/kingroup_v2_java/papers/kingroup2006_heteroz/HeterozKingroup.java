package papers.kingroup2006_heteroz;
import kingroup_v2.cervus.AlleleAnalysisFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;

/**
 * Created by: jc1386591
 * Date: 28/06/2006. Time: 20:58:15
 */
public class HeterozKingroup extends HeterozNei
{
  public void testByNA() {
    calcByNA("_HegKon_TRI_NL" + N_LOCI + "_FGS" + MIN_GROUP_SIZE + "_NG" + N_GROUPS);
  }
  public void testAll() {
    testByNA();
    testByNG();
  }

  public void testByNG() {
    N_ALLELES = 10;
    N_LOCI = 10;
    calcByNG("_HegKon_TRI_NL" + N_LOCI + "_FGS" + MIN_GROUP_SIZE + "_NA" + N_ALLELES);
  }

  protected double calcHeteroz(PopBuilderModel builderModel) {
    SysPop sysPop = SysPopFactory.makeSysPopFrom(builderModel);
    double trueHet = AlleleAnalysisFactory.calcTrueHeterozAvr(sysPop.getFreq());
//    log.info("\nsysPop=\n" + sysPop);
    double het = AlleleAnalysisFactory.calcObservHeterozAvr(sysPop);
    return trueHet - het;
  }
}
