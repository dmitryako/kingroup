package papers.kingroup2006_cervus.ratio;
import kingroup_v2.kinship.Kinship;
import kingroup_v2.kinship.KinshipIBDFactory;
import kingroup_v2.kinship.like.KinshipRatioSim;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import papers.kingroup2006_cervus.CervusCommon;
import papers.kingroup2006_cervus.accuracy.CervusFigure1;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.utilx.arrays.vec.Vec;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 24/02/2006, Time: 15:48:18
 */
public class CervusRatio extends CervusCommon
{
  private static ProjectLogger log = ProjectLogger.getLogger(CervusFigure1.class.getName());
  public static void main(String[] args) {
    new CervusRatio().run();
    System.exit(0);
  }
  public CervusRatio()
  {
    DIR += (File.separator + "ratio");
    N_ALLELES = 10;
    N_LOCI = 1; // 1-paper
    N_TRIALS = 100; // 100-paper
  }
  public void run() {
    String fileName = "kinship_NL" + N_LOCI + "_NA" + N_ALLELES;
    String primName = fileName + "_prime.csv";
    String nullName = fileName + "_null.csv";
    Kinship kinship = new Kinship();
    kinship.loadDefault();
    kinship.setComplexPrimIBD(KinshipIBDFactory.makeParentOffspring());
    kinship.setComplexNullIBD(KinshipIBDFactory.makeUnrelated());
    kinship.setNumSimPairs(N_TRIALS);

    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    BUILDER_MODEL.setNumAlleles(N_ALLELES);
    BUILDER_MODEL.setNumLoci(N_LOCI);
    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    POP_SIZE = 2;
    BUILDER_MODEL.setNumGroups(1);
    BUILDER_MODEL.setGroupSize(2);
    BUILDER_MODEL.setIncParents(false);
    BUILDER_MODEL.setShuffled(false);
    SysPop sysPop = SysPopFactory.makeSysPopFrom(BUILDER_MODEL);

    double[] bins = Vec.makeByStep(0, 0.5, 10);

    KinshipRatioSim sim = new KinshipRatioSim(kinship.getComplexNullIBD(), kinship);

    sim.calc(sysPop.getFreq(), "null");
    double[] hist = Vec.histL(sim.getLogs(), bins);
    LOG.saveToFile(Vec.toCSV(hist), DIR, nullName);
//    LOG.saveToFile(DoubleArr.toCSV(sim.getLogs()), DIR, nullName);

    sim = new KinshipRatioSim(kinship.getComplexPrimIBD(), kinship);
    sim.calc(sysPop.getFreq(), "prime");
    hist = Vec.histL(sim.getLogs(), bins);
    LOG.saveToFile(Vec.toCSV(hist), DIR, primName);
  }
}
