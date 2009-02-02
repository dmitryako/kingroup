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
 * Created by IntelliJ IDEA.
 * User: eng-nbb
 * Date: 19/09/2005
 * Time: 14:53:43
 * To change this template use File | Settings | File Templates.
 */
public class JWAlg_v2_HapDipTest extends TestCase {
  private ButlerPopBuilderModel POP_MODEL = new ButlerPopBuilderModel();
  private final String eol = System.getProperty("line.separator");

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
    OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
    freq.normalize(1.0f, false);

    GenotypeFactory.init();
    POP_MODEL.setIncParents(false);
//         POP_MODEL.setIncludeParents(true);
    OldPop popA = new ButlerPopBuilder(freq).buildButler(POP_MODEL);

    SysPop simsPop = OldPopToSysPopFactory.makePopFrom(popA);

    System.out.println(new JWAlg_v2_HapDip(simsPop, freq).makeOutgoingData(new Date()));

  }

  public void testSingleTest() {

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

      KinGroupProjectV1.makeInstance("SimpsPopFactoryTest", "3");
      POP_MODEL.setFamilyType(ButlerFamilyData.BUTLER_6x2);
      OldAlleleFreq freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
      freq.normalize(1.0f, false);

    GenotypeFactory.init();
    POP_MODEL.setIncParents(false);
//         POP_MODEL.setIncludeParents(true);
    OldPop popA = new ButlerPopBuilder(freq).buildButler(POP_MODEL);

    SysPop simsPop = OldPopToSysPopFactory.makePopFrom(popA);
    JWAlg_v2_HapDip f = new JWAlg_v2_HapDip(simsPop,freq);
    f.setDebug(true);
    Partition partB = f.partition();
    System.out.println(partB);
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
}
