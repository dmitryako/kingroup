package kingroup.genotype;

import javax.iox.LOG;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.genotype.Allele;
import kingroup.genotype.Locus;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 8/03/2005, Time: 08:48:21
 */
public class LocusJUnit extends TestCase  {
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
      return new TestSuite(LocusJUnit.class);
   }

   public void testEqualsByAllelesTo() {
      LOG.setTrace(true);

      assertEquals(false, L.equalsByAllelesTo(L2));
      assertEquals(false, L.equalsByAllelesTo(L3));
      assertEquals(false, L.equalsByAllelesTo(L5));
      assertEquals(false, L.equalsByAllelesTo(L6));

      assertEquals(true, L2.equalsByAllelesTo(L2));
      assertEquals(true, L2.equalsByAllelesTo(L3));
      assertEquals(true, L2.equalsByAllelesTo(L4));

      LOG.setTrace(false);
   }
}