package papers.kingroup2006_cervus.accuracy;
import junit.framework.TestCase;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionFactory;
import kingroup_v2.cervus.CervusFileFormat;
import kingroup_v2.cervus.CervusFileWriter;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.sample.PopBuilderModel;
import kingroup_v2.pop.sample.builder.PopBuilder;
import kingroup_v2.pop.sample.builder.SysPopBuilderFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import tsvlib.project.ProjectLogger;

import javax.iox.LOG;
import javax.iox.TextFile;
import javax.utilx.DoubleArrayByInt;
import javax.utilx.RandomSeed;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 25/01/2006, Time: 11:49:37
 */
public class CervusInput extends CervusFigure1
{
  private static ProjectLogger log = ProjectLogger.getLogger(CervusInput.class.getName());
  private static long CERVUS_SEED = -1;
  private static int MUTATION = 0;

  DoubleArrayByInt E0 = new DoubleArrayByInt(); // equal, 0% mutation
  DoubleArrayByInt E5 = new DoubleArrayByInt();
  DoubleArrayByInt E10 = new DoubleArrayByInt();
  DoubleArrayByInt R0 = new DoubleArrayByInt(); // random, 0% mutation
  DoubleArrayByInt R5 = new DoubleArrayByInt();
  DoubleArrayByInt R10 = new DoubleArrayByInt();

//  public static void main(String[] args) {
//    CervusInput test = new CervusInput();
//    System.exit(0);
//  }
  public void makeCervusInput()
  {
    MIN_N_LOCI = 4;
    MAX_N_LOCI = 10;
    for (int L = MIN_N_LOCI; L <= MAX_N_LOCI; L++)
      runLocus(L);
  }
  public void runLocus(int nLoci)
  {
    N_LOCI = nLoci;
    CERVUS_SEED = N_LOCI;

    String dirName = DIR + (File.separator + "cervus_NL" + CERVUS_SEED);

    N_ALLELES = 10;
    N_TRIALS = 10; // 100-paper

    BUILDER_MODEL.setNumAlleles(N_ALLELES);
    BUILDER_MODEL.setNumLoci(N_LOCI);

    BUILDER_MODEL.setGroupsBuilderIdx(PopBuilderModel.EQUAL_GROUPS);
    POP_SIZE = 100;
    BUILDER_MODEL.setNumGroups(10);
    BUILDER_MODEL.setGroupSize(8);
    BUILDER_MODEL.setIncParents(true);
    BUILDER_MODEL.setShuffled(false);

    RandomSeed.getInstance().setSeed(CERVUS_SEED);
    MUTATION = 0;
    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    makeFile("EQUAL", E0, dirName);
    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    makeFile("RANDOM", R0, dirName);

    RandomSeed.getInstance().setSeed(CERVUS_SEED);
    MUTATION = 5;
    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    makeFile("EQUAL", E5, dirName);
    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    makeFile("RANDOM", R5, dirName);

    RandomSeed.getInstance().setSeed(CERVUS_SEED);
    MUTATION = 10;
    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_EQUAL);
    makeFile("EQUAL", E10, dirName);
    BUILDER_MODEL.setParentAlleleFreqType(PopBuilderModel.FREQ_RANDOM);
    makeFile("RANDOM", R10, dirName);
  }

  void makeFile(String tag, DoubleArrayByInt arrByL, String dirName)
  {
    allDist = arrByL;

    PopBuilder builder = SysPopBuilderFactory.makeBuilder(BUILDER_MODEL);

    String extName2 = "CERVUS_N" + POP_SIZE  // NAME without num loci
      + "_NA" + BUILDER_MODEL.getNumAlleles()
      + "_" + builder.toString(BUILDER_MODEL);
    String dirName2 = dirName;
    dirName2 += (File.separator + extName2);
    dirName2 += (File.separator + "MUTATION" + MUTATION);
    dirName2 += (File.separator + tag);
    File dir2 = new File(dirName2);
    dir2.mkdirs();

    String algFileName = ALG_NAME + ".csv";

    String extName = "CERVUS_N" + POP_SIZE
      + "_NA" + BUILDER_MODEL.getNumAlleles()
      + "_NL" + BUILDER_MODEL.getNumLoci()           // num of loci
      + "_" + builder.toString(BUILDER_MODEL);
    dirName += (File.separator + extName);
    dirName += (File.separator + "MUTATION" + MUTATION);
    dirName += (File.separator + tag);
    File dir = new File(dirName);
    dir.mkdirs();

    for (int i = 0; i < N_TRIALS; i++)
    {
      SysAlleleFreq sysFreq = SysAlleleFreqFactory.makeSysAlleleFreq(BUILDER_MODEL, null);
      sysFreq.normalize(1.0f, false);
      SysPop sysPop = builder.makeSysPop(BUILDER_MODEL, sysFreq);
      SysPop mutPop = SysPopFactory.makeWithAlleleError(sysPop, MUTATION);
      UsrPopSLOW usrPop = builder.makeUsrPop(mutPop, BUILDER_MODEL);
//      usrPop = UsrPopFactory.shuffle(usrPop);

      TestCase.assertEquals(POP_SIZE, usrPop.size());

      log.info("usrPop=\n" + usrPop);

      String fileName = "set" + (i+1) + ".csv";
      String fullName = dirName + File.separator + fileName;
      File file = new File(fullName);

      TextFile to = new TextFile();
      CervusFileFormat format = new CervusFileFormat();
      CervusFileWriter.write(to, usrPop, format);
      to.setFileName(file.getName());
      to.write(file, null);

      // partition
      Partition partA = PartitionFactory.makePartitionFrom(sysPop);
      log.info("partA=" + partA);
      Partition partB = partitionSysPop(sysPop);
      log.info("partB=" + partB);
      double dist = new LitowDistance().distance(partA, partB);
      allDist.add(N_LOCI, 100. * dist / POP_SIZE);
    }
    LOG.saveToFile(allDist, dirName2, algFileName);
    LOG.saveToFile(allDist, dirName2 + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, algFileName);
    LOG.saveBoxplot(allDist, dirName2 + File.separator + "boxplot", algFileName);
    LOG.saveBoxplot(allDist, dirName2 + File.separator + "boxplot" + File.separator + "P" + POP_SIZE + "_NT" + N_TRIALS, algFileName);
  }

}
