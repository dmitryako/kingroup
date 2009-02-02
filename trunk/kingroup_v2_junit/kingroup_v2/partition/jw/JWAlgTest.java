package kingroup_v2.partition.jw;

import junit.framework.TestCase;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.genotype.GenotypeFactory;
import kingroup.papers.butler2004.ButlerPopBuilder;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.papers.butler2004.family.ButlerFamilyData;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup.project.KinGroupProjectV1;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * <code>JWAlgTest</code>
 * <p/>
 * Date: Jul 18, 2005
 * Time: 11:17:33 AM
 *
 * @author Nigel Blair
 */
public class JWAlgTest extends TestCase {
  protected ButlerPopBuilderModel POP_MODEL = new ButlerPopBuilderModel();
  

  public void testJWAlg() {

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

    new JWAlg(simsPop,freq).partition();

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

    JWAlg g = new JWAlg(simsPop,freq);
    for(int i = 0; i < 10; i++){
    g.partition();}

  }


  public static void main(String[]  args)
  {
    new JWAlgTest().testRepeatTest();
  }
}
