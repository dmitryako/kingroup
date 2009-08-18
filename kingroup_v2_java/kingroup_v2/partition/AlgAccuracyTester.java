package kingroup_v2.partition;

import junit.framework.TestCase;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionFactory;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.PopBuilder;
import kingroup_v2.pop.sample.builder.SysPopBuilderFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.langx.SystemX;
import javax.utilx.DoubleArrayByInt;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/11/2005, Time: 13:35:04
 */
public abstract class AlgAccuracyTester
{
  private static ProjectLogger log = ProjectLogger.getLogger(AlgAccuracyTester.class.getName());

  public static final long TIME_SCALE = 1000000000;
  protected DoubleArrayByInt allDist = new DoubleArrayByInt();
  protected DoubleArrayByInt allTimes = new DoubleArrayByInt();

  protected String DIR = "output";
  protected String ALG_NAME = "na";
  public int N_ALLELES = 10;
  protected int MIN_N_LOCI = 4;  //4-paper
  protected int MAX_N_LOCI = 8; // 24-paper
  public int MIN_GROUP_SIZE = 2; // 2-paper
  public int MAX_GROUP_SIZE = 20;
  public int N_GROUPS = 5;
  public int N_LOCI = 5;
  public int GROUP_SIZE = 1;// for counting the partition space size//
  public int GROUP_SIZE_STEP = 5;
  public int MIN_N_GROUPS = 2;
  public int MAX_N_GROUPS = 30;
  public int N_GROUPS_STEP = 10;
  protected int N_TRIALS = 10;
  protected int POP_SIZE = 50;
  protected PopBuilderModel BUILDER_MODEL;

  protected AlgAccuracyTester()
  {
    BUILDER_MODEL = new PopBuilderModel();
  }
  public Partition partitionSysPop(SysPop sysPop)
  {
    throw new RuntimeException("must overwrite partitionSysPop");
  }

  protected String makeFileNameByL(String algName)
  {
    PopBuilder builder = SysPopBuilderFactory.makeBuilder(BUILDER_MODEL);
    StringBuffer buff = new StringBuffer();
    buff.append(algName);
    buff.append("_N").append(POP_SIZE);
    buff.append("_NA").append(BUILDER_MODEL.getNumAlleles());
    buff.append("_").append(builder.toString(BUILDER_MODEL));
    buff.append(".csv");
    return buff.toString();
  }
  public void calcAccuracyByLoci(String dirName, String fileName) {
    allDist = new DoubleArrayByInt();
    for (N_LOCI = MIN_N_LOCI; N_LOCI <= MAX_N_LOCI; N_LOCI++) {
      BUILDER_MODEL.setNumLoci(N_LOCI);
      log.info("N_LOCI = " + N_LOCI);
      for (int i = 0; i < N_TRIALS; i++)
      {
        BUILDER_MODEL.setNumLoci(N_LOCI);
        SysPop sysPop = SysPopFactory.makeSysPopFrom(BUILDER_MODEL);
        TestCase.assertEquals(POP_SIZE, sysPop.size());

//        log.info("sysPop=\n" + sysPop);
        sysPop = SysPopFactory.shuffle(sysPop);
//        log.severe("NOTE SHUFFLE IS OFF!!!");
//        log.info("sysPop=\n" + sysPop);


        Partition partA = PartitionFactory.makePartitionFrom(sysPop);
//    log.info("partA=" + partA);
        Partition partB = partitionSysPop(sysPop);

//        log.info("partB=" + partB);
        double dist = new LitowDistance().distance(partA, partB);
//      LOG.trace(this, "D(A,B)=", Integer.toString(dist));

        allDist.add(N_LOCI, 100. * dist / POP_SIZE);
      }
    }
    LOG.saveToFile(allDist, dirName, fileName);
    LOG.saveToFile(allDist, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
    LOG.saveBoxplot(allDist, dirName + File.separator + "boxplot", fileName);
    LOG.saveBoxplot(allDist, dirName + File.separator + "boxplot" + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, fileName);
  }
  public void calcAccuracyByNGroups(String dirName, String distFileName, String timeFileName) {
    allTimes = new DoubleArrayByInt();
    allDist = new DoubleArrayByInt();
    for (N_GROUPS = MIN_N_GROUPS; N_GROUPS <= MAX_N_GROUPS; N_GROUPS += N_GROUPS_STEP) {
//      int[] groups = IntVec.make(N_GROUPS, GROUP_SIZE);
      BUILDER_MODEL.setGroupSize(GROUP_SIZE);
      BUILDER_MODEL.setNumGroups(N_GROUPS);
      BUILDER_MODEL.setNumLoci(N_LOCI);
      BUILDER_MODEL.setNumAlleles(N_ALLELES);
      POP_SIZE = N_GROUPS * GROUP_SIZE;

      log.info("popSize = " + POP_SIZE + ", nGroups = " + N_GROUPS);
      for (int i = 0; i < N_TRIALS; i++) {
        SysPop sysPop = SysPopFactory.makeSysPopFrom(BUILDER_MODEL);
        TestCase.assertEquals(POP_SIZE, sysPop.size());

//        log.info("sysPop=\n" + sysPop);
        sysPop = SysPopFactory.shuffle(sysPop);
//        log.severe("NOTE SHUFFLE IS OFF!!!");
//        log.info("sysPop [shuffled]=\n" + sysPop);

        Partition partA = PartitionFactory.makePartitionFrom(sysPop);
//        log.info("partA=" + partA);

        long time = SystemX.time();
        Partition partB = partitionSysPop(sysPop);
        double dtime = (double) (SystemX.time() - time) / TIME_SCALE;

//        log.info("partB=" + partB);
        double dist = new LitowDistance().distance(partA, partB);
//      LOG.trace(this, "D(A,B)=", Integer.toString(dist));

        allTimes.add(POP_SIZE, dtime);
        allDist.add(POP_SIZE, 100. * dist / POP_SIZE);
      }
    }
    LOG.saveToFile(allTimes, dirName, timeFileName);
    LOG.saveToFile(allDist, dirName, distFileName);
    LOG.saveToFile(allTimes, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, timeFileName);
    LOG.saveToFile(allDist, dirName + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, distFileName);
  }
}
