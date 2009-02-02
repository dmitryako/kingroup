package kingroup_v2.partition.jw;

import junit.framework.TestCase;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.genotype.GenotypeFactory;
import kingroup.papers.butler2004.ButlerPopBuilder;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PopToPartitionFactory;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup.project.KinGroupProjectV1;
import kingroup_v2.partition.distance.LitowDistance;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;
import java.util.Date;

/**
 * <code>JWAlg_v2_Test</code>
 * <p/>
 * Date: Aug 9, 2005
 * Time: 9:02:13 AM
 *
 * @author Nigel Blair
 */
public class JWAlg_v2_Test extends TestCase {
  protected ButlerPopBuilderModel POP_MODEL = new ButlerPopBuilderModel();

  private final String eol = System.getProperty("line.separator");

  public void testSingleTest() {
    LOG.setTrace(true);
    int numAlleles = 10;
    int numLoci = 5;
    POP_MODEL.loadDefaults();

    POP_MODEL.setIncParents(false);
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.EQUAL_FREQ);
    POP_MODEL.setNumLoci(numLoci);
    POP_MODEL.setNumAlleles(numAlleles);
    KinGroupProjectV1.makeInstance("SimpsPopFactoryJUnit_2", "2");
    POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_50x1);
    POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_6x2);
    OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
    freq.normalize(1.0f, false);

    GenotypeFactory.init();
    POP_MODEL.setIncParents(false);
    OldPop popA = new ButlerPopBuilder(freq).buildButler(POP_MODEL);
    System.out.println("Generated OldPop: "+popA);
    SysPop simsPop = OldPopToSysPopFactory.makePopFrom(popA);
    System.out.println("SysPop: "+simsPop);
    JWAlg_v2 f = new JWAlg_v2(simsPop, freq);
    f.setDebug(true);
    Partition partB = f.partition();
    System.out.println("Partitioned Population: "+partB);

    PopToPartitionFactory factory = PopToPartitionFactory.makeInstance(popA); // must be called here, since we created a new population
    Partition partA = factory.makeFromPopulation(popA);

    int dist = new LitowDistance().distance(partA, partB);
    if (dist != 0) {
      LOG.report(this, "A="+partA);
      LOG.report(this, "B="+partB);
      LOG.report(this, "D(A,B)="+Integer.toString(dist));
      dist = new LitowDistance().distance(partA, partB);
    }
    assertEquals(0, dist);
  }

  public void testDebug() {
    int POP_SIZE = 50;
    int numAlleles = 10;
    int numLoci = 10;
    POP_MODEL.loadDefaults();
    POP_MODEL.setIncParents(true);
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.EQUAL_FREQ);
    POP_MODEL.setSize(POP_SIZE);
    POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_50x1);

    POP_MODEL.setNumLoci(numLoci);
    POP_MODEL.setNumAlleles(numAlleles);

    KinGroupProjectV1.makeInstance("SimpsPopFactoryTest", "1");
    POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_50x1);
    POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_6x2);
    OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
    freq.normalize(1.0f, false);

    GenotypeFactory.init();
    POP_MODEL.setIncParents(false);
//         POP_MODEL.setIncParents(true);
    OldPop popA = new ButlerPopBuilder(freq).buildButler(POP_MODEL);

    SysPop simsPop = OldPopToSysPopFactory.makePopFrom(popA);

    System.out.println(new JWAlg_v2(simsPop,freq).makeOutgoingData(new Date()));

  }

  public void testRepeatTest() {
    int POP_SIZE = 50;
    int numAlleles = 10;
    int numLoci = 2;
    POP_MODEL.loadDefaults();
    POP_MODEL.setIncParents(true);
    POP_MODEL.setAllelicFreqType(ButlerFamilyData.EQUAL_FREQ);
    POP_MODEL.setSize(POP_SIZE);
    POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_50x1);

    POP_MODEL.setNumLoci(numLoci);
    POP_MODEL.setNumAlleles(numAlleles);

    KinGroupProjectV1.makeInstance("SimpsPopFactoryTest", "1");
    POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_2x5);
    OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
    freq.normalize(1.0f, false);

    GenotypeFactory.init();
    POP_MODEL.setIncParents(false);
//         POP_MODEL.setIncParents(true);
    OldPop popA = new ButlerPopBuilder(freq).buildButler(POP_MODEL);

    SysPop simsPop = OldPopToSysPopFactory.makePopFrom(popA);

    JWAlg_v2 g = new JWAlg_v2(simsPop,freq);
    for(int i = 0; i < 10; i++){
      g.partition();}

  }

  public void testResultParsing() {
    String ans ="           4      \r\n    19  \n\r        34  \n        28           2           5          34           7           4          12          15          22          16          29          32          33          10          24          14          31          22           8           6          35          30          13          20          11          15           9          14           3          19          12          23          30          18          27          17           7           5          21          27          25          26          29          24           1          10          25";
    String[] bits = ans.split("\\b");//ans.replaceAll(eol,"").replaceAll(" *"," ").trim().split(" ");
    System.out.println("Length: "+bits.length);
    int[] vals = new int[50];
    String temp;
    int count = 0;
    for (int i = 0; i < bits.length; i++) {
      temp = bits[i].trim();
      if(temp.length() > 0)
      {
        System.out.println(temp);
        vals[count++] = Integer.parseInt(temp);

      }

    }

  }


  public static void main(String[]  args)
  {
    new JWAlg_v2_Test().testSingleTest();
  }
}
