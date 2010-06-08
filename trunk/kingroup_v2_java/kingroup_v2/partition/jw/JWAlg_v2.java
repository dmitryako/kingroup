package kingroup_v2.partition.jw;
import kingroup.genetics.OldAlleleFreq;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.pop.sample.sys.SysPop;
import tsvlib.project.ProjectLogger;

import javax.utilx.bitset.CompBitSet;
import javax.utilx.pair.IntPair2;
import java.io.*;
import java.util.Date;

/**
 * <code>JWAlg_v2</code>
 * <p/>
 * Date: Aug 9, 2005
 * Time: 8:59:04 AM
 *
 * @author Nigel Blair
 */
public class JWAlg_v2
{
  private static ProjectLogger log = ProjectLogger.getLogger(JWAlg_v2.class.getName());

  private final SysPop pop;
  private final OldAlleleFreq freq;
  private String exec_name;
  protected final String eol = System.getProperty("line.separator");
  private int haploDiploid_Diploid = 0;
  private int haplo_Diplo_PolygamousSex = 0;
//  private int randomSeed = 55;
  public boolean isDebug() {
    return debug;
  }
  public void setDebug(boolean debug) {
    this.debug = debug;
  }
  private boolean debug = false;
  public JWAlg_v2(SysPop pop, OldAlleleFreq freq) {
    this.pop = pop;
    this.freq = freq;
    String os = System.getProperty("os.name");
    if (os.indexOf("Windows") != -1) {
      exec_name = ".\\kingroup_v2_native\\jw_v2\\colony12.exe";
    } else if (os.indexOf("Linux") != -1) {
      exec_name = "./kingroup_v2_native/jw_v2/colony12";
    } else {
      throw new RuntimeException("Operating System Not Supported.");
    }
  }
  public Partition partition()
  {
    StringBuffer buf = new StringBuffer();
    try {
      Process p = Runtime.getRuntime().exec(exec_name + (debug ? " -debug" : ""));
      OutputStream out = p.getOutputStream();
      InputStream in = p.getInputStream();
      Date runtime = new Date();
      String f = makeOutgoingData(runtime);
//      log.info("JW input=\n" + f);
      if (debug) {
        FileOutputStream fo = new FileOutputStream(new File("JW-INPUT_" + runtime.getTime() + ".DAT"));
        fo.write(f.getBytes());
        fo.flush();
        fo.close();
      }
      out.write(f.getBytes());
      out.flush();
      out.close();
      int g = 0;
      while ((g = in.read()) != -1) {
        buf.append((char) g);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    String[] bits = buf.toString().split("\\b");
    int[] vals = new int[pop.size()];
    String temp;
    int count = 0;
    for (int i = 0; i < bits.length; i++) {
      temp = bits[i].trim();
      if (temp.length() > 0) {
//        System.out.println(temp);
        vals[count++] = Integer.parseInt(temp);
      }
    }
    int max = 1;
    boolean done = false;
    CompBitSet toAdd;
    Partition res = new Partition();
    for (int j = 0; j < max; j++) {
      toAdd = new CompBitSet();
      for (int i = 0; i < vals.length; i++) {
        int val = vals[i];
        if (!done && val > max) {
          max = val;
        }
        if (val == (j + 1)) {
          toAdd.flip(i);
        }
      }
      res.add(toAdd);
      done = true;
    }

//    System.out.println(res);
    return res;
  }
  protected String makeOutgoingData(Date runtime) {
    StringBuffer buf = new StringBuffer();
//1
    buf.append(" \"Population\"").append(eol);

//2
    //FName = file_name
    //PRINT *, FName
    buf.append(" \"JW-OUTPUT_" + runtime.getTime() + ".OUT\"").append(eol);

//3 - set in parse_population_array
    //RawSmplSize
    buf.append(" ").append(pop.size()).append(eol);

//4 - set in parse_population_arra
    //NumLociy
    buf.append(" ").append(pop.getNumLoci()).append(eol);

//5
//  I_SEED(1)= 55//seed
    buf.append(" ").append((int)getRandomSeed()).append(eol);

//6
    //>0 integer for updating getAllele frequency
    // every specified number of successful moves. -1 for no updating
//  UpdateAlleleFre = 1000//-1
    buf.append(" ").append(1000).append(eol);

//7
    //Indicator: 1/0=HaploDiploid/Diploid
//  HaploDiploid = 0
    buf.append(" ").append(getHaploDiploid_Diploid()).append(eol);

//8
    //Indicator: 1/0=Haplo/Diploid of polygamous sex, or of females for
    // singly mating only. Valid only when HaploDiploid=1
//  HaploDiploid_Sex1 = 0
    buf.append(" ").append(getHaplo_Diplo_PolygamousSex()).append(eol);

//9
    //1/0=FS Family/Nested halfSib family in ESTIMATION
//  FSFamilyOnly_in_Est = 1
    buf.append(" ").append(1).append(eol);

//10 - set in parse_frequency_array
    //1/0=known frequencies/unknown frequencies
    //PopAlleleFreKnownQ = 1
    buf.append(" ").append(1).append(eol);

//11 - set in parse_frequency_array
    buf.append(" ").append(makeNumAllelesLine()).append(eol);

//12 - set in parse_frequency_array
    buf.append(makeFreqString());

//13
//  Drop_in_Data = 0
    buf.append(" ").append(0).append(eol);
    // Indicator: 1/0=with/without allelic dropout errors in data

//      IF(Drop_in_Data==1)THEN
//14
//        ALLOCATE(RawDropRate(NumLoci))
    //READ(10,*,IOSTAT=IOS,Err=9,End=9)(RawDropRate(I),I=1,NumLoci)
//    buf.append(" ").append(eol);

//15
//  Drop_in_Est = 0
    // Indicator: 1/0=with/without allelic dropout errors
    //  accounted for in estimation
//      ENDIF
//    buf.append(" ").append(eol);

//16
//  OtherTypeError_in_Data = 0
    // Indicator: 1/0=with/without non-dropout errors in genotypes in data
    buf.append(" ").append(0).append(eol);

//      IF(OtherTypeError_in_Data==1)THEN
//17
//        ALLOCATE(RawOtherErrorRate(NumLoci))
    //READ(10,*,IOSTAT=IOS,Err=9,End=9) (RawOtherErrorRate(I),I=1,NumLoci)
//    buf.append(" ").append(eol);
//18
//  OtherTypeError_in_Est = 0
    // Indicator: 1/0=with/without non-dropout errors in genotypes
    //  accounted for in estimation
//      ENDIF
//ommited not removed
//      buf.append(" ").append(0).append(eol);

//19 - set in parse_population_array
    buf.append(makeGenotypeString());

//20 - Not Using
//    buf.append(" ").append(eol);
//21
//  NumThread = 1
    buf.append(" ").append(1).append(eol);
//    buf.append(" ").append(eol);
//    buf.append(" ").append(eol);
    return buf.toString();
  }
  protected String makeFreqString() {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < freq.getNumLoci(); i++) {
      buf.append(" ").append(makeAFList(i)).append(eol);
      buf.append(" ").append(makeAlleleList(i)).append(eol);
    }
    return buf.toString();
  }
  public SysPop getPop() {
    return pop;
  }
  public OldAlleleFreq getFreq() {
    return freq;
  }
  private String makeAFList(int l) {
    StringBuffer buf = new StringBuffer();
    String del = "";
    for (int i = 0; i < freq.getLocus(l).size(); i++) {
      buf.append(del).append(i + 1/*UsrPopFactory.get(l).locus(i).getId()*/);
      del = ",";
    }
    return buf.toString();
  }
  private String makeAlleleList(int l) {
    StringBuffer buf = new StringBuffer();
    String del = "";
    for (int i = 0; i < freq.getLocus(l).size(); i++) {
      buf.append(del).append(freq.getLocus(l).get(i).getProb());
      del = ",";
    }
    return buf.toString();
  }
  protected String makeNumAllelesLine() {
    StringBuffer buf = new StringBuffer();
    String del = "";
    for (int i = 0; i < freq.getNumLoci(); i++) {
      buf.append(del).append(freq.getLocus(i).size());
      del = " ";
    }
    return buf.toString();
  }
  protected String makeGenotypeString() {
    IntPair2 p = null;
    StringBuffer buf = new StringBuffer();
    int width = new Integer(pop.size()).toString().length();
    for (int i = 0; i < pop.size(); i++) {
      buf.append(rightAlignNums(width + 1, i + 1));
      if (getHaploDiploid_Diploid() == 1) {
        buf.append("    ").append(getGCount(pop, i));
      }
      buf.append("    -1  -1");
      for (int j = 0; j < pop.getNumLoci(); j++) {
        // [dk20060509] changed IntPairSorted to Int2
        p = pop.getAllelePair(i, j);
        buf.append("   ").append(p.a + 1).append(" ").append(p.b + 1);
      }
      buf.append(eol);
    }
    return buf.toString();
  }
  private int getGCount(SysPop pop, int i) {
    return 2;
  }
  String rightAlignNums(int width, int toAlign) {
    String ali = new Integer(toAlign).toString();
    int toadd = width - ali.length();
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < toadd; i++) {
      buf.append(" ");
    }
    buf.append(ali);
    return buf.toString();
  }
  protected int getHaploDiploid_Diploid() {
    return haploDiploid_Diploid;
  }
  protected int getHaplo_Diplo_PolygamousSex() {
    return haplo_Diplo_PolygamousSex;
  }
  /**
   * Indicator: 1/0=HaploDiploid/Diploid
   *
   * @param haploDiploid_Diploid default = 0, i.e. Dioploids
   */
  protected void setHaploDiploid_Diploid(int haploDiploid_Diploid) {
    this.haploDiploid_Diploid = haploDiploid_Diploid;
  }
  /**
   * Indicator: 1/0=Haplo/Diploid of polygamous sex, or of females for
   * singly mating only. Valid only when HaploDiploid=1
   *
   * @param haplo_Diplo_PolygamousSex default = 0
   */
  protected void setHaplo_Diplo_PolygamousSex(int haplo_Diplo_PolygamousSex) {
    this.haplo_Diplo_PolygamousSex = haplo_Diplo_PolygamousSex;
  }
  public long getRandomSeed() {
    return 55;
//    return RandomSeed.getInstance().getSeed();
  }
//  public void setRandomSeed(int randomSeed) {
//    this.randomSeed = randomSeed;
//  }
}
