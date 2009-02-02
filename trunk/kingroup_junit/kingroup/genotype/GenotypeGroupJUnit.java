package kingroup.genotype;

import javax.iox.LOG;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/03/2005, Time: 09:07:57
 */
public class GenotypeGroupJUnit extends TestCase  {
   static public Allele A = new Allele("A", 0.5);
   static public Allele B = new Allele("B", 0.1);
   static public Allele C = new Allele("C", 0.6);
   static public Locus L = new Locus();
   static public Locus L2 = new Locus();
   static public Locus L3= new Locus();
   static public Locus L4= new Locus();
   static public Locus L5= new Locus();
   static public Locus L6= new Locus();

   public void setUp() {
      L.setId("L1");
      L.add(A);
      L.add(A);

      L2.setId("L2");
      L2.add(A);
      L2.add(B);

      L3.setId("L3");
      L3.add(B);
      L3.add(A);

      L4.setId("L4");
      L4.add(A);
      L4.add(B);

      L5.setId("L5");
      L5.add(A);

      L6.setId("L6");
      L6.add(A);
      L6.add(C);
   }
   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      return new TestSuite(GenotypeGroupJUnit.class);
   }

   public void testCountUnique() {
      LOG.setTrace(true);

      GenotypeFactory.init();

      Genotype g = GenotypeFactory.getInstance().makeGenotype();
      g.add(L); //A/A
      g.add(L2); //A/B

      Genotype g2 = GenotypeFactory.getInstance().makeGenotype();
      g2.add(L); //A/A
      g2.add(L3); //B/A

      Genotype g3 = GenotypeFactory.getInstance().makeGenotype();
      g3.add(L); //A/A
      g3.add(L3); //B/A

      Genotype g4 = GenotypeFactory.getInstance().makeGenotype();
      g4.add(L2); //A/B
      g4.add(L3); //B/A

      GenotypeGroup group = new GenotypeGroup();
      group.addGenotype(g);
      group.addGenotype(g2);
      group.addGenotype(g3);
      assertEquals(1, group.countUnique());

      group.addGenotype(g4);
      assertEquals(2, group.countUnique());

      LOG.setTrace(false);
   }
}