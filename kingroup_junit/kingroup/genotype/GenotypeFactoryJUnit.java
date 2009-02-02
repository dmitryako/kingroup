package kingroup.genotype;

import javax.iox.LOG;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.genotype.Allele;
import kingroup.genotype.Locus;
import kingroup.genotype.GenotypeFactory;
import kingroup.genotype.Genotype;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 16, 2004, Time: 3:48:18 PM
 */
public class GenotypeFactoryJUnit extends TestCase  {
   static public Allele A_05 = new Allele("A_05", 0.5);
   static public Allele A_01 = new Allele("A_01", 0.1);
   static public Allele B_06 = new Allele("B_06", 0.6);
   static public Locus L = new Locus();
   static public Locus L2 = new Locus();
   static public Locus L3= new Locus();

   public void setUp() {
      L.setId("L");
      L.add(A_05);
      L.add(A_01);

      L2.setId("L2");
      L2.add(A_05);
      L2.add(B_06);

      L3.setId("L3");
      L3.add(A_05);
      L3.add(A_05);
   }
   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
   public static Test suite() {
      return new TestSuite(GenotypeFactoryJUnit.class);
   }

   public void testMakeGenotype() {
      LOG.setTrace(true);
      GenotypeFactory.init();
      GenotypeFactory fact = GenotypeFactory.getInstance();

      int numLoci = 2;
      Genotype g = fact.makeGenotype();
      g.add(L);
      g.add(L2);
      LOG.trace(this, "Genotype g=", g);

      Genotype gB = fact.replicateGenotype(g, "newG");
      LOG.trace(this, "replicated=", gB);

      Genotype g2 = fact.makeGenotype();
      g2.add(L3);
      LOG.trace(this, "Genotype g=", g2);

      Genotype g2B = fact.replicateGenotype(g2, "newG");
      LOG.trace(this, "replicated=", g2B);

      LOG.setTrace(false);
   }
}