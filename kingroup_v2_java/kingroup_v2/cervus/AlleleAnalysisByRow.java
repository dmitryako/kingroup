package kingroup_v2.cervus;
import kingroup_v2.KingroupFrame;
import kingroup_v2.heterozygosity.GuoThompson1992;
import kingroup_v2.pop.sample.sys.HWEquiAlg;
import kingroup_v2.pop.sample.sys.SysPop;

import javax.swingx.ProgressWnd;
import javax.utilx.arrays.IntVec;
import java.text.NumberFormat;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/03/2006, Time: 11:34:56
 */
public class AlleleAnalysisByRow
{
  private static int START_ROW = 1;
  private static int numCols = 8;
  private static final String[] colNames = {"loc", "k", "N", "Hets", "Homs", "H(O)", "H(E)", "pHW"
    , "PIC", "Excl(1)", "Excl(2)", "HW", "NullFreq"};
  private static final String[] colTips = {
    "Locus id"
    , "Number of alleles at the locus."
    , "Number of individuals typed at the locus [as per CERVUS]."
    , "Observed number of heterozygotes [as per CERVUS]."
    , "Observed number of homozygotes [as per CERVUS]."
    , "Observed heterozygosity [as per CERVUS]."
    , "Expected heterozygosity [as per CERVUS]."
    , "P-value of deviation from Hardy-Weinberg ["+ GuoThompson1992.REFERENCE + "]"
    , "Polymorphic information content."
    , "Average exclusion probability without information on one parent."
    , "Average exclusion probability given information on known parent."
    , "Significance of deviation from Hardy-Weinberg equilibrium."
    , "Estimated null allele frequency."};

  int[] aL;
  int[] aK;
  int[] aN;
  int[] aHets;
  int[] aHoms;
  double[] aHO;
  double[] aHE;
  double[] pHW;
  private int numRows;
  private static int idxEnum = 0;
  private static final int L_IDX = idxEnum++;
  private static final int K_IDX = idxEnum++;
  private static final int N_IDX = idxEnum++;
  private static final int HETS_IDX = idxEnum++;
  private static final int HOMS_IDX = idxEnum++;
  private static final int HO_IDX = idxEnum++;
  private static final int HE_IDX = idxEnum++;
  private static final int PHW_IDX = idxEnum++;
  private static final int PIC_IDX = idxEnum++;
  private static final int EXCL1_IDX = idxEnum++;
  private static final int EXCL2_IDX = idxEnum++;
  private static final int HW_IDX = idxEnum++;
  private static final int NULL_IDX = idxEnum++;

  private NumberFormat FORMATTER_3;
  private static final int N_DIGITS = 3;

  public AlleleAnalysisByRow()
  {
    init();
  }
  public AlleleAnalysisByRow(SysPop pop, Cervus cervus)
  {
    init();
    int nLoci = pop.getNumLoci();
    numRows = nLoci;
    aL = IntVec.makeIdxArr(nLoci + 1);
    aK = AlleleAnalysisFactory.countAlleles(pop);
    aN = AlleleAnalysisFactory.countGenotypes(pop);
    aHets = AlleleAnalysisFactory.countHeterozygotes(pop);
    aHoms = AlleleAnalysisFactory.countHomozygotes(pop);
    aHO = AlleleAnalysisFactory.calcHeterozygosity(aHets, aN);
    aHE = AlleleAnalysisFactory.calcNeiHeteroz(pop);

    HWEquiAlg hw = new HWEquiAlg();
    ProgressWnd progress = new ProgressWnd(KingroupFrame.getInstance(), "p-value for Hardy-Weinberg");
    hw.setProgress(progress);
    pHW = hw.calc(pop);
  }

  private void init()
  {
    FORMATTER_3 = NumberFormat.getNumberInstance();
    FORMATTER_3.setMaximumFractionDigits(N_DIGITS);
  }

  public int getNumRows()  {    return numRows + START_ROW;  }
  public int getNumCols()  {    return getNumVals();  }
  public int getNumVals()  {    return numCols;  }
  public void setNumRows(int numRows)  {    this.numRows = numRows;  }
  public void setNumCols(int numCols)  {    this.numCols = numCols;  }
  public String getValName(int i)  {    return colNames[i];  }
  public String getColName(int i)  {    return getValName(i);  }
  public String getRowName(int i)  {    return Integer.toString(aL[i]);  }
  public static String getValTip(int i)
  {
    if (i < 0 ||  i >= colTips.length)
      return null;
    return colTips[i];
  }

  public String makeStringFor(int r, int c)
  {
    if (c == L_IDX)
      return Integer.toString(aL[r]);
    if (r == 0)
      return getValName(c);
    r -= START_ROW;
    if (c == K_IDX)
      return Integer.toString(aK[r]);
    if (c == N_IDX)
      return Integer.toString(aN[r]);
    if (c == HETS_IDX)
      return Integer.toString(aHets[r]);
    if (c == HOMS_IDX)
      return Integer.toString(aHoms[r]);
    if (c == HO_IDX)
      return FORMATTER_3.format(aHO[r]);
    if (c == HE_IDX)
      return FORMATTER_3.format(aHE[r]);
    if (c == PHW_IDX)
      return FORMATTER_3.format(pHW[r]);
    return " ";
  }
}
