package kingroup_v2.kinship;
import junit.framework.TestCase;
import kingroup_v2.pop.UserGenotype;
import kingroup_v2.pop.UserLocus;
import kingroup_v2.pop.allele.freq.UsrAlleleFreq;
import kingroup_v2.pop.sample.usr.UserPopFormat;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;

import javax.iox.TextFile;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 17/09/2005, Time: 06:49:54
 */
public class KinshipFileReaderTest extends TestCase  {
  KinshipFileFormat format;
  protected void setUp() {
    format = new KinshipFileFormat();
    format.loadDefault();
    format.setColumnDelim(',');
  }
  public void testRemoveComments() {
    TextFile file = new TextFile();
    file.addLine("*comment1");
    file.addLine("");
    file.addLine(" ");
    file.addLine("not comment");
    TextFile res = KinshipFileReader.removeComments(file, format);
    assertEquals(1, res.size());
  }
  public void testGetFreqBlock() {
    TextFile file = new TextFile();
    file.addLine("freq1, ");
    file.addLine("end");
    file.addLine("pop, ");
    file.addLine("pop, ");
    TextFile res = KinshipFileReader.getFreqBlock(file, format);
    assertEquals(1, res.size());
  }
  public void testImportFreqLocusIdsFrom() {
    format.setNumLoci(1);
    String line = "loc1";
    String[] res = KinshipFileReader.importFreqLocusIdsFrom(line, format, null);
    assertEquals(true, res[0].equals("loc1"));
    assertEquals(1, res.length);

    line = "loc1 ,";
    res = KinshipFileReader.importFreqLocusIdsFrom(line, format, null);
    assertEquals(true, res[0].equals("loc1"));
    assertEquals(1, res.length);

    format.setNumLoci(2);
    line = "loc1, , loc2";
    res = KinshipFileReader.importFreqLocusIdsFrom(line, format, null);
    assertEquals(true, res[0].equals("loc1"));
    assertEquals(true, res[1].equals("loc2"));
    assertEquals(2, res.length);
  }
  public void testMakeFreqFrom() {
    format.setNumLoci(2);
    TextFile file = new TextFile();
    file.addLine("loc1, , loc2");
    file.addLine("a, 0.1,  b, 0.2");
    file.addLine("a2, 0.3, b2, 0.4");
    UsrAlleleFreq res = KinshipFileReader.makeFreqFrom(file, format, null);
    assertEquals(true, res.getLocusId(0).equals("loc1"));
    assertEquals(true, res.getLocusId(1).equals("loc2"));
  }
  public void testRemoveFreqBlock() {
    TextFile file = new TextFile();
    file.addLine("freq1, ");
    file.addLine("end");
    file.addLine("pop, ");
    file.addLine("pop, ");
    TextFile res = KinshipFileReader.removeFreqBlock(file, format);
    assertEquals(2, res.size());
  }
  public void testImportLocusIdsFrom() {
    UserPopFormat pop = new UserPopFormat();
    format.setNumLoci(1);
    format.setFirstLocusColumn(1);
    String line = "loc1";
    KinshipFileReader.importLocusIdsFrom(pop, line, format, null);
    String[] res = pop.getLocusIds();
    assertEquals(true, res[0].equals("loc1"));
    assertEquals(1, res.length);

    line = "loc1 ,";
    KinshipFileReader.importLocusIdsFrom(pop, line, format, null);
    res = pop.getLocusIds();
    assertEquals(true, res[0].equals("loc1"));
    assertEquals(1, res.length);

    format.setNumLoci(2);
    line = "loc1 , loc2";
    KinshipFileReader.importLocusIdsFrom(pop, line, format, null);
    res = pop.getLocusIds();
    assertEquals(true, res[0].equals("loc1"));
    assertEquals(true, res[1].equals("loc2"));
    assertEquals(2, res.length);

    format.setNumLoci(1);
    format.setFirstLocusColumn(2);
    line = ",loc1";
    String nice = KinshipFileReader.separateColumnAndAlleleDelimiters(line, " ", format);
    KinshipFileReader.importLocusIdsFrom(pop, nice, format, null);
    res = pop.getLocusIds();
    assertEquals(true, res[0].equals("loc1"));
    assertEquals(1, res.length);

    line = " ,loc1 ,";
    KinshipFileReader.importLocusIdsFrom(pop, line, format, null);
    res = pop.getLocusIds();
    assertEquals(true, res[0].equals("loc1"));
    assertEquals(1, res.length);

    format.setNumLoci(2);
    line = " ,loc1 , loc2";
    KinshipFileReader.importLocusIdsFrom(pop, line, format, null);
    res = pop.getLocusIds();
    assertEquals(true, res[0].equals("loc1"));
    assertEquals(true, res[1].equals("loc2"));
    assertEquals(2, res.length);
  }

  public void testLoadLocusFrom() {
    String line = "a/b";
    UserLocus res = KinshipFileReader.loadLocusFrom(line, format);
    assertEquals(2, res.size());
    assertEquals(true, res.getAllele(0).equals("a"));
    assertEquals(true, res.getAllele(1).equals("b"));

    line = "a/ ";
    res = KinshipFileReader.loadLocusFrom(line, format);
    assertEquals(1, res.size());
    assertEquals(true, res.getAllele(0).equals("a"));

    line = "aa";
    res = KinshipFileReader.loadLocusFrom(line, format);
    assertEquals(1, res.size());
    assertEquals(true, res.getAllele(0).equals("aa"));

    line = " /b";
    res = KinshipFileReader.loadLocusFrom(line, format);
    assertEquals(1, res.size());
    assertEquals(true, res.getAllele(0).equals("b"));
  }

  public void testLoadGenotypeFrom() {
    format.setNumLoci(2);
    format.setHasGroupId(true);
    format.setGroupIdColumn(1);
    format.setHasId(true);
    format.setIdColumn(2);
    format.setHasMatId(true);
    format.setMatIdColumn(3);
    format.setHasPatId(true);
    format.setPatIdColumn(4);
    format.setFirstLocusColumn(5);
    String line = "groupId, id, matId, patId, A/B, C/D";
    UserGenotype res = KinshipFileReader.loadGenotypeFrom(line, format);
    assertEquals(2, res.nLoci());
    assertEquals(true, res.getGroupId().equals("groupId"));
    assertEquals(true, res.getId().equals("id"));
    assertEquals(true, res.getMatId().equals("matId"));
    assertEquals(true, res.getPatId().equals("patId"));
    assertEquals(true, res.get(0).getAllele(0).equals("A"));
    assertEquals(true, res.get(0).getAllele(1).equals("B"));
    assertEquals(true, res.get(1).getAllele(0).equals("C"));
    assertEquals(true, res.get(1).getAllele(1).equals("D"));


    format.setNumLoci(1);
    format.setFirstLocusColumn(4);
    UserGenotype res2 = KinshipFileReader.loadGenotypeFrom(line, format);
    assertEquals(1, res2.nLoci());
    assertEquals(true, res2.getGroupId().equals("groupId"));
    assertEquals(true, res2.getId().equals("id"));
    assertEquals(true, res2.getMatId().equals("matId"));
    assertEquals(true, res2.getPatId().equals("patId"));
    assertEquals(true, res2.get(0).getAllele(0).equals("patId"));
  }

  public void testMakePopSampleFrom() {
    format.setNumLoci(2);
    format.setFirstLocusColumn(2);
    TextFile file = new TextFile();
    file.addLine(" , loc1, loc2");
    file.addLine(" ,a/c, b, 0.2");
    file.addLine(" ,a2/c, 0.3, b2, 0.4");
    UsrPopSLOW res = KinshipFileReader.makePopSampleFrom(file, format, null);
    assertEquals(true, res.getLocusId(0).equals("loc1"));
    assertEquals(true, res.getLocusId(1).equals("loc2"));
//    assertEquals(true, res.get(0));
  }
}
