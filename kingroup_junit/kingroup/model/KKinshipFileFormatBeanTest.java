package kingroup.model;

import javax.utilx.arrays.ArraysX;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import kingroup.genotype.Genotype;
import kingroup.genotype.GenotypeFactory;
import kingroup.genotype.Locus;
import kingroup.model.KinshipFileModelV1;
import kingroup.util.FastIdArray;

/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 21, 2004, Time: 8:16:17 AM
 */
public class KKinshipFileFormatBeanTest extends TestCase {
   public static void main(String[] args) { junit.textui.TestRunner.run(suite()); }
   public static Test suite() { return new TestSuite(KKinshipFileFormatBeanTest.class); }

   public void testImportLocusIdsFrom_csv() {
      String[] src = {
         ", "   // "L1", "L2"
         , " , "
         , " ,  "
         , ","     // "L1"
         , " "     // "L1"
         , "A1, A2"
         , "B1,B2"
         , "B1, B2 "
         , " B1 , B2 "
      };
      String[] freq = {
         ",,, " // "L1", "L2"
         , ",,,"
         , ",, "
         , ",," // "L1"
         , ",," // "L1"
         , "A1, , A2,"
         , "B1,,B2,"
         , "B1, ,B2 , "
         , " B1 , , B2 ,"
      };
      String[][] test = {
         {"L1", "L2"}
         , {"L1", "L2"}
         , {"L1", "L2"}
         , {"L1"}
         , {"L1"}
         , {"A1", "A2"}
         , {"B1", "B2"}
         , {"B1", "B2"}
         , {"B1", "B2"}
      };
      String[] src_null = {
         ""
      };
      KinshipFileModelV1 format = new KinshipFileModelV1();
      format.loadDefaults();
      format.setColumnDelimiters(",");
      format.setHasMaxNumLoci(false);
      format.setLocusColumn(1);
      assertImportLocusIdsFrom(format, test, src);
      assertImportLocusIdsFrom_null(format, src_null);
      assertImportFreqLocusIdsFrom(format, test, freq);
   }

   public void testImportLocusIdsFrom_tab() {
      String[] src = {
         "\t\tA1\tA2"
         , "\t\t\t "
         , " \t \t \t "
         , " \t \t \t" // "L1"
         , " \t \tB1\tB2"
         , "\t\t B1 \t B2 "
         , " \t \t B1 \t B2 \t"
      };
      String[][] test = {
         {"A1", "A2"}
         , {"L1", "L2"}
         , {"L1", "L2"}
         , {"L1"}
         , {"B1", "B2"}
         , {"B1", "B2"}
         , {"B1", "B2"}
      };
      String[] src_null = {
         ""
      };
      KinshipFileModelV1 format = new KinshipFileModelV1();
      format.loadDefaults();
      format.setColumnDelimiters(",\t");
      format.setHasMaxNumLoci(true);
      format.setMaxNumLoci(2);
      format.setLocusColumn(3);
      assertImportLocusIdsFrom(format, test, src);
      assertImportLocusIdsFrom_null(format, src_null);
   }

   public void testImportLocusFrom() {
      String[] src = {
         "A1/A2 "
         , " B1 / B2 "
         , "B1 / B2/C3/"
         , "  / B2 "
         , "/B2"
         , " B1"
         , " B1/"
      };
      String[][] test = {
         {"A1", "A2"}
         , {"B1", "B2"}
         , {"B1", "B2", "C3"}
         , {"B2"}
         , {"B2"}
         , {"B1"}
         , {"B1"}
      };
      String[] src_null = {
         " /"
         , " "
      };
      KinshipFileModelV1 format = new KinshipFileModelV1();
      format.loadDefaults();
      format.setAlleleDelimiters("/");
      assertImportLocusFrom(format, test, src);
      assertImportLocusFrom_null(format, src_null);
   }

   public void testImportGenotypeFrom() {
      String[] locusIdsStr = {"L#1", "L#2", "L#3"};
      FastIdArray locusIds = new FastIdArray(locusIdsStr);
      String[] locusStrA = {"A1", "A2"};
      String[] locusStrB = {"B1", "B2"};
      Locus locusA = new Locus(locusStrA);
      Locus locusB = new Locus(locusStrB);
      Locus locusEmpty = new Locus();
      Genotype test = GenotypeFactory.getInstance().makeGenotype();

      KinshipFileModelV1 format = new KinshipFileModelV1();
      format.loadDefaults();
      format.setAlleleDelimiters("/");
      format.setColumnDelimiters(",");
      format.setHasIdColumn(true);
      format.setHasGroupIdColumn(true);
      format.setIdColumn(1);
      format.setGroupIdColumn(2);
      format.setLocusColumn(3);
      format.setHasMaxNumLoci(false);

      String src = "i1, gr1, A1/A2 ";
      test.setId("i1");
      test.setGroupId("gr1");
      test.add(locusA);
      Genotype res = format.importGenotypeFrom(src, locusIds);
      assertEqualGenotypes(test, res);

      src = "i1, gr1, , B1/B2 ";
      test.add(locusEmpty);
      test.add(locusB);
      res = format.importGenotypeFrom(src, locusIds);
      assertEqualGenotypes(test, res);

      //todo: need more geometryx
   }

   private void assertEqualGenotypes(Genotype test, Genotype res) {
      assertEquals(test.getId(), res.getId());
      assertEquals(test.getGroupId(), res.getGroupId());
      assertEquals(test.getNumLoci(), res.getNumLoci());
      for (int L = 0; L < test.getNumLoci(); L++)
         assertEquals(true, test.getLocus(L).isEqualLocus(res.getLocus(L)));
   }

   private void assertImportLocusFrom(KinshipFileModelV1 format
                                       , String[][] test, String[] src) {
      for (int i = 0; i < src.length; i++) {
         String[] res = format.importLocusFrom(src[i]);
         assertEquals(true, ArraysX.equals(test[i], res));
      }
   }
   private void assertImportLocusFrom_null(KinshipFileModelV1 format
                                       , String[] src) {
      for (int i = 0; i < src.length; i++) {
         String[] res = format.importLocusFrom(src[i]);
         assertEquals(true, res == null);
      }
   }
   private void assertImportLocusIdsFrom(KinshipFileModelV1 format
                                       , String[][] test, String[] src) {
      for (int i = 0; i < src.length; i++) {
         String[] res = format.importLocusIdsFrom(src[i]);
         assertEquals(true, ArraysX.equals(test[i], res));
      }
   }
   private void assertImportLocusIdsFrom_null(KinshipFileModelV1 format
                                       , String[] src) {
      for (int i = 0; i < src.length; i++) {
         String[] res = format.importLocusIdsFrom(src[i]);
         assertEquals(true, res == null);
      }
   }
   private void assertImportFreqLocusIdsFrom(KinshipFileModelV1 format
                                       , String[][] test, String[] src) {
      for (int i = 0; i < src.length; i++) {
         String[] res = format.importFreqLocusIdsFrom(src[i]);
         assertEquals(true, ArraysX.equals(test[i], res));
      }
   }
   private void assertImportFreqLocusIdsFrom_null(KinshipFileModelV1 format
                                       , String[] src) {
      for (int i = 0; i < src.length; i++) {
         String[] res = format.importFreqLocusIdsFrom(src[i]);
         assertEquals(true, res == null);
      }
   }
}
