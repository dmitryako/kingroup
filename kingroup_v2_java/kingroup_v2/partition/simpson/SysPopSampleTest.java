package kingroup_v2.partition.simpson;
import junit.framework.Test;
import junit.framework.TestSuite;
import kingroup.genetics.OldAlleleFreq;
import kingroup.genetics.OldAlleleFreqFactory;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeFactory;
import kingroup.genotype.Locus;
import kingroup.population.OldPop;
import kingroup.population.OldPopToSysPopFactory;
import kingroup.project.KinGroupProjectV1;
import kingroup_v2.partition.simpson.genotype_dist.GenotypeDistLocusAvr;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.iox.LOG;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 11/05/2005, Time: 16:22:12
 */
public class SysPopSampleTest extends SimpsPopFactoryTest {
  OldAlleleFreq freq;
  GenotypeFactory genoMaker;
  SibshipAlg sibship = new DiploidSibship();

  public Locus LAA = new Locus(new String[]{"A", "A"});
  public Locus LAB = new Locus(new String[]{"A", "B"});
  public Locus LAC = new Locus(new String[]{"A", "C"});
  public Locus LAD = new Locus(new String[]{"A", "D"});
  public Locus LBA = new Locus(new String[]{"B", "A"});
  public Locus LBB = new Locus(new String[]{"B", "B"});
  public Locus LBC = new Locus(new String[]{"B", "C"});
  public Locus LBD = new Locus(new String[]{"B", "D"});
  public Locus LCA = new Locus(new String[]{"C", "A"});
  public Locus LCB = new Locus(new String[]{"C", "B"});
  public Locus LCC = new Locus(new String[]{"C", "C"});
  public Locus LCD = new Locus(new String[]{"C", "D"});
  public Locus LDA = new Locus(new String[]{"D", "A"});
  public Locus LDB = new Locus(new String[]{"D", "B"});
  public Locus LDC = new Locus(new String[]{"D", "C"});
  public Locus LDD = new Locus(new String[]{"D", "D"});
  private static final double EPS = 1e-10;
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public static Test suite() {
    return new TestSuite(SimpsPopFactoryTest.class);
  }
  public void setUp() {
    super.setUp();
    LAA.setId("LAA");
    LAB.setId("LAB");
    LAC.setId("LAC");
    LAD.setId("LAD");
    LBA.setId("LBA");
    LBB.setId("LBB");
    LBC.setId("LBC");
    LBD.setId("LBD");
    LCA.setId("LCA");
    LCB.setId("LCB");
    LCC.setId("LCC");
    LCD.setId("LCD");
    LDA.setId("LDA");
    LDB.setId("LDB");
    LDC.setId("LDC");
    LDD.setId("LDD");
    KinGroupProjectV1.makeInstance("SysPopSampleTest", "1");
    POP_MODEL.setNumLoci(1);
    freq = OldAlleleFreqFactory.makeAlleleFreq(POP_MODEL);
    freq.normalize(1.0f, false);
    genoMaker = GenotypeFactory.getInstance();
  }
  public void testCalcLocusDistance() {
    LOG.setTrace(true);
    assertDistance(0, LAA, LAA);
    assertDistance(1, LAA, LAB);
    assertDistance(0, LAB, LAB);
    assertDistance(0, LAB, LBA);
    assertDistance(2, LAA, LBB);
    assertDistance(1, LAB, LAC);
    assertDistance(2, LAB, LCD);
    LOG.setTrace(false);
  }
  public void testIsSibGroup() {
    LOG.setTrace(true);

    // Test A/A
    OldPop pop = new OldPop(freq);
    Genotype geno = genoMaker.makeGenotype();
    geno.add(LAA);
    pop.addGenotype(geno);
    LOG.trace(this, "pop=", pop.toString());
    SysPop sysPop = OldPopToSysPopFactory.makePopFrom(pop);
    assertEquals(true, sibship.isSibGroupSLOW(sysPop, OldPopToSysPopFactory.makeOneGroup(pop)));
    MSAlgGroup group = new MSAlgGroup();
    for (int id = 0; id < sysPop.size(); id++) {
      group.set(id);
      assertEquals(true, sibship.isSibGroupSLOW(sysPop, group));
    }
    assertPop(true, LAA, LAA);// Test A/A A/A
/*
from
Genetics, Vol. 158, 1329-1338, July 2001, Copyright © 2001
Accurate Partition of Individuals Into Full-Sib Families From Genetic Data Without Parental Information
Bruce R. Smitha, Christophe M. Herbingerb, and Heather R. Merrya

Table 1
1. AA p4A + 4p3ApX(1/2)n + 4p2Ap2X(1/4)n
2. AB 2[p2Ap2B + (1/2)nAB 2(p3ApB +p2ApBpX + pAp3B + pAp2BpX) + (1/4)nAB 4(p2ApBpX + pAp2BpX + pApBp2X)] + (1/2)nAB 4p2Ap2B
3. AA BB 4p2Ap2B(1/4)n
4. AA AB 4p3ApB(1/2)n + 4p2Ap2B(1/4)nAA(1/2)nAB + 8p2ApBpX(1/2)n
5. AA AB BB 4p2Ap2B (1/4)nAA+nBB (1/2)nAB
6. AA BC 8p2ApBpC(1/4)n
7. AB AC 4p2ApBpC(1/2)n + 8p2ApBpC(1/4)n + 8pApBpCpX(1/4)n
8. AA AB AC 8p2ApBpC(1/4)n
9. AA AB BC 8p2ApBpC(1/4)n
10. AB AC BC 8pApBpC(1 - pX)(1/4)n
11. AA AB AC BC 8p2ApBpC(1/4)n
12. AC BD 8pApBpCpD(1/4)n
13. AD AC BC 8pApBpCpD(1/4)n
14. AC AD BC BD 8pApBpCpD(1/4)n
*/
    assertPop(true, LAA, LBB);// Test 3. A/A B/B
    assertPop(true, LAA, LBB, LBB);
    assertPop(true, LAA, LBB, LBB, LAA);
    assertPop(true, LAA, LAB);// Test 4. A/A A/B
    assertPop(true, LAA, LBA);// Test 4'. A/A B/A
    assertPop(true, LAA, LAB, LBB);// Test 5. AA AB BB
    assertPop(true, LAA, LBA, LBB);// Test 5'. AA BA BB
    assertPop(true, LAA, LBA, LBB, LAB);
    assertPop(true, LAA, LBC);// Test 6. AA BC
    assertPop(true, LAA, LCB);// Test 6'. AA CB
    assertPop(true, LAB, LAC);// Test 7. AB AC
    assertPop(true, LBA, LAC);// Test 7b. BA AC
    assertPop(true, LAB, LCA);// Test 7c. AB CA
    assertPop(true, LBA, LCA);// Test 7d. BA CA
    assertPop(true, LAA, LAB, LAC);// Test 8. AA AB AC
    assertPop(true, LAA, LBA, LAC);// Test 8b. AA BA AC
    assertPop(true, LAA, LAB, LCA);// Test 8c. AA AB CA
    assertPop(true, LAA, LBA, LCA);// Test 8d. AA BA CA //#15
    assertPop(true, LAA, LAB, LBC);// Test 9. AA AB BC
    assertPop(true, LAA, LBA, LBC);// Test 9b. AA BA BC
    assertPop(true, LAA, LAB, LCB);// Test 9c. AA AB CB
    assertPop(true, LAA, LBA, LCB);// Test 9d. AA BA CB
    assertPop(true, LAB, LAC, LBC);// Test 10. AB AC BC
    assertPop(true, LAB, LCA, LBC);// Test 10b. AB CA BC
    assertPop(true, LAB, LAC, LCB);// Test 10c. AB AC CB
    assertPop(true, LAB, LCA, LCB);// Test 10d. AB CA CB
    assertPop(true, LBA, LAC, LBC);// Test 10_. BA AC BC
    assertPop(true, LBA, LCA, LBC);// Test 10_b. BA CA BC
    assertPop(true, LBA, LAC, LCB);// Test 10_c. BA AC CB
    assertPop(true, LBA, LCA, LCB);// Test 10_d. BA CA CB
    assertPop(true, LAA, LAB, LAC, LBC);// Test 11. AA AB AC BC
    assertPop(true, LAA, LAB, LCA, LBC);// Test 11b. AA AB CA BC
    assertPop(true, LAA, LAB, LAC, LCB);// Test 11c. AA AB AC CB
    assertPop(true, LAA, LAB, LCA, LCB);// Test 11d. AA AB CA CB
    assertPop(true, LAA, LBA, LAC, LBC);// Test 11_. AA AB AC BC
    assertPop(true, LAA, LBA, LCA, LBC);// Test 11_b. AA BA CA BC
    assertPop(true, LAA, LBA, LAC, LCB);// Test 11_c. AA BA AC CB
    assertPop(true, LAA, LBA, LCA, LCB);// Test 11_d. AA BA CA CB /#15+20=35
    assertPop(true, LAC, LBD);// Test 12. AC BD
    assertPop(true, LCA, LBD);// Test 12b. CA BD
    assertPop(true, LAC, LDB);// Test 12c. AC DB
    assertPop(true, LCA, LDB);// Test 12d. CA DB
    assertPop(true, LCA, LDB, LAC, LBD);
    assertPop(true, LAD, LAC, LBC);// Test 13. AD AC BC
    assertPop(true, LAD, LCA, LBC);// Test 13b. AD CA BC
    assertPop(true, LAD, LAC, LCB);// Test 13c. AD AC CB
    assertPop(true, LAD, LCA, LCB);// Test 13d. AD CA CB
    assertPop(true, LDA, LAC, LBC);// Test 13_. DA AC BC
    assertPop(true, LDA, LCA, LBC);// Test 13_b. DA CA BC
    assertPop(true, LDA, LAC, LCB);// Test 13_c. DA AC CB
    assertPop(true, LDA, LCA, LCB);// Test 13_d. DA CA CB
    assertPop(true, LAC, LAD, LBC, LBD);// Test 14. AC AD BC BD
    assertPop(true, LAC, LAD, LCB, LBD);// Test 14b. AC AD CB BD
    assertPop(true, LAC, LAD, LBC, LDB);// Test 14c. AC AD BC DB
    assertPop(true, LAC, LAD, LCB, LDB);// Test 14d. AC AD CB DB
    assertPop(true, LAC, LDA, LBC, LBD);// Test 14_. AC DA BC BD
    assertPop(true, LAC, LDA, LCB, LBD);// Test 14_b. AC DA CB BD
    assertPop(true, LAC, LDA, LBC, LDB);// Test 14_c. AC DA BC DB
    assertPop(true, LAC, LDA, LCB, LDB);// Test 14_d. AC DA CB DB /#35+20=55
    assertPop(true, LCA, LAD, LBC, LBD);// Test 14x_. CA AD BC BD
    assertPop(true, LCA, LAD, LCB, LBD);// Test 14x_b. CA AD CB BD
    assertPop(true, LCA, LAD, LBC, LDB);// Test 14x_c. CA AD BC DB
    assertPop(true, LCA, LAD, LCB, LDB);// Test 14x_d. CA AD CB DB
    assertPop(true, LCA, LDA, LBC, LBD);// Test 14__. CA DA BC BD
    assertPop(true, LCA, LDA, LCB, LBD);// Test 14__b. CA DA CB BD
    assertPop(true, LCA, LDA, LBC, LDB);// Test 14__c. CA DA BC DB
    assertPop(true, LCA, LDA, LCB, LDB);// Test 14__d. CA DA CB DB /#55+8=63

    //********************
    // INVALID SIB GROUPS!!!!
    //********************
    assertPop(false, LAA, LBB, LCC);
    assertPop(false, LAA, LBB, LDC);
    assertPop(false, LAA, LBB, LAC);
    assertPop(false, LAA, LAB, LCC);
    assertPop(false, LAA, LBA, LCC);
    assertPop(false, LAA, LAB, LBB, LCA);
    assertPop(false, LAA, LAB, LBB, LCC);
    assertPop(false, LAA, LAB, LBB, LBC);
    assertPop(false, LAA, LBC, LBB);
    assertPop(false, LAA, LBC, LCC);
    assertPop(false, LAA, LBC, LAD);
    assertPop(false, LAA, LAB, LBC, LCC);
    assertPop(false, LAB, LAC, LBC, LDD);
    assertPop(false, LAB, LAC, LBC, LCC);
    assertPop(true, LBA, LAC, LBC, LBB);//TRUE!!!
    assertPop(false, LBA, LAC, LBC, LCC);
    assertPop(false, LBA, LAC, LBC, LAD);
    assertPop(false, LAA, LAB, LAC, LBC, LAD);
    assertPop(false, LAA, LAB, LAC, LBC, LBB);
    assertPop(false, LAA, LAB, LAC, LBC, LCC);
    assertPop(false, LAC, LBD, LAA);
    assertPop(true, LAC, LBD, LAB); // TRUE!
    assertPop(true, LAC, LBD, LAD); // TRUE!
    assertPop(false, LAC, LBD, LAD, LAB);
    assertPop(false, LAD, LAC, LBC, LAB);
    assertPop(false, LDA, LAC, LBC, LDC);
    assertPop(false, LAC, LAD, LBC, LBD, LAA);
    assertPop(false, LAC, LAD, LBC, LBD, LAB);
    assertPop(false, LAC, LAD, LBC, LBD, LDC);
  }
  public OldPop makePop(Locus L, Locus L2) {
    OldPop pop = new OldPop(freq);
    Genotype geno = genoMaker.makeGenotype();
    geno.add(L);
    pop.addGenotype(geno);
    geno = genoMaker.makeGenotype();
    geno.add(L2);
    pop.addGenotype(geno);
    return pop;
  }
  public OldPop makePop(Locus L, Locus L2, Locus L3) {
    OldPop pop = makePop(L, L2);
    Genotype geno = genoMaker.makeGenotype();
    geno.add(L3);
    pop.addGenotype(geno);
    return pop;
  }
  public OldPop makePop(Locus L, Locus L2, Locus L3, Locus L4) {
    OldPop pop = makePop(L, L2, L3);
    Genotype geno = genoMaker.makeGenotype();
    geno.add(L4);
    pop.addGenotype(geno);
    return pop;
  }
  public OldPop makePop(Locus L, Locus L2, Locus L3, Locus L4, Locus L5) {
    OldPop pop = makePop(L, L2, L3, L4);
    Genotype geno = genoMaker.makeGenotype();
    geno.add(L5);
    pop.addGenotype(geno);
    return pop;
  }
  public void assertPop(boolean isSib, Locus L, Locus L2) {
    OldPop pop = makePop(L, L2);
    LOG.trace(this, "pop=", pop.toString());
    SysPop simsPop = OldPopToSysPopFactory.makePopFrom(pop);
    assertEquals(isSib, sibship.isSibGroupSLOW(simsPop, OldPopToSysPopFactory.makeOneGroup(pop)));
    if (isSib) {
      MSAlgGroup group = new MSAlgGroup();
      for (int id = 0; id < simsPop.size(); id++) {
        group.set(id);
        assertEquals(isSib, sibship.isSibGroupSLOW(simsPop, group));
      }
    }
  }
  public void assertDistance(int dist, Locus L, Locus L2) {
    OldPop pop = makePop(L, L2);
    LOG.trace(this, "pop=", pop.toString());
    SysPop simsPop = OldPopToSysPopFactory.makePopFrom(pop);

    GenotypeDistLocusAvr alg = new GenotypeDistLocusAvr();
//    assertEquals(dist, simsPop.calcGenotypeDistance((short) 0, (short) 1), EPS);
    assertEquals(dist, alg.calcGenotypeDistance(simsPop, 0, 1), EPS);
  }
  public void assertPop(boolean isSib, Locus L, Locus L2, Locus L3) {
    OldPop pop = makePop(L, L2, L3);
    LOG.trace(this, "pop=", pop.toString());
    SysPop simsPop = OldPopToSysPopFactory.makePopFrom(pop);
    assertEquals(isSib, sibship.isSibGroupSLOW(simsPop, OldPopToSysPopFactory.makeOneGroup(pop)));
    if (isSib) {
      MSAlgGroup group = new MSAlgGroup();
      for (int id = 0; id < simsPop.size(); id++) {
        group.set(id);
        assertEquals(isSib, sibship.isSibGroupSLOW(simsPop, group));
      }
    }
  }
  public void assertPop(boolean isSib, Locus L, Locus L2, Locus L3, Locus L4) {
    OldPop pop = makePop(L, L2, L3, L4);
    LOG.trace(this, "pop=", pop.toString());
    SysPop simsPop = OldPopToSysPopFactory.makePopFrom(pop);
    assertEquals(isSib, sibship.isSibGroupSLOW(simsPop, OldPopToSysPopFactory.makeOneGroup(pop)));
    if (isSib) {
      MSAlgGroup group = new MSAlgGroup();
      for (int id = 0; id < simsPop.size(); id++) {
        group.set(id);
        assertEquals(isSib, sibship.isSibGroupSLOW(simsPop, group));
      }
    }
  }
  public void assertPop(boolean isSib
    , Locus L, Locus L2, Locus L3, Locus L4, Locus L5) {
    OldPop pop = makePop(L, L2, L3, L4, L5);
    LOG.trace(this, "pop=", pop.toString());
    SysPop simsPop = OldPopToSysPopFactory.makePopFrom(pop);
    assertEquals(isSib, sibship.isSibGroupSLOW(simsPop, OldPopToSysPopFactory.makeOneGroup(pop)));
    if (isSib) {
      MSAlgGroup group = new MSAlgGroup();
      for (int id = 0; id < simsPop.size(); id++) {
        group.set(id);
        assertEquals(isSib, sibship.isSibGroupSLOW(simsPop, group));
      }
    }
  }
}