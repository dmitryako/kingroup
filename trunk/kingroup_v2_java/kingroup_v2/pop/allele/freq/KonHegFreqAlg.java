package kingroup_v2.pop.allele.freq;
import kingroup.partition.bitsets.Partition;
import kingroup_v2.cervus.AlleleAnalysisFactory;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.pop.sample.sys.SysPopView;
import kingroup_v2.relatedness.RMtrxOutbredKonHeg;
import pattern.mvc.MVCTableView;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.ProgressWnd;
import javax.swingx.tablex.JTableFactory;
import javax.swingx.tablex.TableViewWithOpt;
import javax.utilx.RandomSeed;
import javax.utilx.bitset.CompBitSet;
import java.text.DecimalFormat;
import java.util.BitSet;

/**
 * Created by: jc1386591
 * Date: 14/07/2006. Time: 14:12:23
 */
public class KonHegFreqAlg {
  public static final String REFERENCE = "Konovalov&Heg(2008) ESTIMATION OF POPULATION ALLELE FREQUENCIES FROM SAMPLES CONTAINING MULTIPLE GENERATIONS, APBC2008, 321-331";
  private ProgressWnd progress;
  private int GROUP_IDX = 1;
  private static final double K_B = -1. / Math.log(0.5);
  private TableViewWithOpt updateView;
  private BitSet bestC;
  private int n;

  protected final static ProjectLogger log = ProjectLogger.getAnonymousLogger();
  private static final double MIN_COST = 1e-7;

  public SysAlleleFreq calc(SysPop givenPop, int nIter, TableViewWithOpt updateView)          {
    this.updateView = updateView;
    n = givenPop.size();                        log.debug("\ngivenPop=\n", givenPop);
    SysAlleleFreq givenAlleleCount = SysAlleleFreqFactory.makeFrom(givenPop, false);
    log.debug("\ngivenAlleleCount=\n", givenAlleleCount);

    SysPop pop = SysPopFactory.makeDeepCopy(givenPop);

//    int MAX_COUNT = 50000;
//    int MAX_COUNT = 100 * n;
    int MAX_COUNT = nIter;
    BitSet currC = new BitSet();

    // STEP 1
    currC.set(0, n);   // Include all
    SysPop currPop = makeSubPop(currC, pop);
    double currZ = calcCost(currPop, pop);      log.debug("currZ=", currZ);
    double bestZ = currZ;
    bestC = currC;

    for (int i = 0; i < MAX_COUNT; i++) {
      if (progress != null
        && progress.isCanceled(i, 0, MAX_COUNT)) {
        break;
      }

      // STEP 2.
      BitSet newC = makeNewC(currC, n);       log.debug("newC = ", newC);
      SysPop newPop = makeSubPop(newC, pop);
      if (!valid(newPop, givenAlleleCount))
        continue;
      double newZ = calcCost(newPop, pop);      log.debug("newZ = ", newZ);

      // STEP 3.
      if (i == 0  ||  newZ < bestZ) {
        bestZ = newZ;                log.debug("\nbestZ = ", bestZ);
        bestC = newC;                log.debug("\nbestC = ", bestC);
        showTable(newPop, pop);
        if (bestZ < MIN_COST)  {
          log.info("MIN_COST > bestZ=" +(float)bestZ);
          break;
        }
      }
      if (newZ <= currZ  ||  acceptNew(newZ, currZ, i, MAX_COUNT)) {
        currC = newC;
      }
    }
    if (progress != null)
      progress.close();
    SysPopFactory.setGroupIds(GROUP_IDX, bestC, pop); // need ids for viewing
    SysPop bestPop = SysPopFactory.makeGroupFrom(GROUP_IDX, pop);          log.info("bestPop=\n", bestC);
    SysAlleleFreq bestFreq = SysAlleleFreqFactory.makeFrom(bestPop, true); log.debug("bestFreq=\n", bestFreq);
    return bestFreq;
  }

  private boolean valid(SysPop newPop, SysAlleleFreq givenAlleleCount)
  {
    SysAlleleFreq newFreq = newPop.getFreq();
    boolean ok = SysAlleleFreqFactory.hasAllAlleles(newFreq, givenAlleleCount);   //Check that all alleles are present
    if (!ok) {
      log.debug("\nnewFreq MISSING ALLELE=\n", newFreq);
      log.debug("\ngivenAlleleCount=\n", givenAlleleCount);
    }
    return ok;
  }

  private BitSet makeNewC(BitSet currC, int n)
  {
    RandomSeed rand = RandomSeed.getInstance();
    BitSet newC = new BitSet();
    newC.set(0, n, false);
    newC.or(currC);
    newC.flip(rand.nextInt(n));    log.debug("\nnewC = ", newC);
    return newC;
  }

  private SysPop makeSubPop(BitSet from, SysPop pop) {               log.debug("calcCost(set = ", from);
    SysPopFactory.setGroupIds(GROUP_IDX, from, pop);               log.debug("pop = \n", pop);
    SysPop newPop = SysPopFactory.makeGroupFrom(GROUP_IDX, pop);  log.debug("setPop = \n", newPop);
    SysAlleleFreq newFreq = SysAlleleFreqFactory.makeFrom(newPop, false);
    newPop.setFreq(newFreq);
    return newPop;
  }

  private double calcCost(SysPop newPop, SysPop pop) {
//    SysPopFactory.setGroupIds(GROUP_IDX, set, pop);               log.debug("pop = \n", pop);
//    SysPop newPop = SysPopFactory.makeGroupFrom(GROUP_IDX, pop);  log.debug("setPop = \n", newPop);
    RMtrxOutbredKonHeg R = new RMtrxOutbredKonHeg(newPop);
    R.calc();
    double sum = 0;
    int n = newPop.size();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < i; j++) {
        sum += Math.abs(R.get(i, j));
//        sum += R.get(i, j);
      }
    }                                                                log.debug("sum =", sum);
    double h = AlleleAnalysisFactory.calcObservHeterozAvr(newPop);   log.debug("h=", h);
    sum *= h;                                                        log.debug("sum *= h =", sum);
    sum /= ((2.*n - 1.) * n);                                        log.debug("sum /=", sum);
//    return sum;
    return Math.abs(sum);
  }

  private boolean acceptNew(double newL, double currL, int i, int maxCount)
  {
    double diffL = (newL - currL) / newL;       log.debug("diffL=", diffL);
    double T = (double)(maxCount - i) / maxCount;       log.debug("T=", T);
    double pr = Math.exp(- diffL / (K_B * T));  log.debug("Pr=", pr);
    return RandomSeed.getInstance().makeRandomEvent(pr);
  }

  public void setProgress(ProgressWnd progress) {
    this.progress = progress;
  }

  private void showTable(SysPop newGroup, SysPop pop) {
    if (updateView == null) {
      return;
    }

//    KinGroupV2MainUI mainUI = KinGroupV2MainUI.getInstance();
//    SysPop givenPop = mainUI.getSysPop();
//    SysAlleleFreq givenFreq = givenPop.getFreq();

//    double[] obsHet = AlleleAnalysisFactory.calcObservHeteroz(givenPop);
//    log.info("\nobsHet=" + Vec.toString(obsHet));
//    double obsHetAvr = Vec.avr(obsHet);
//

    int N_TABLES = 2;
    int FREQ_TABLE = 1;
    int POP_TABLE = 0;
    JTable[] tables = new JTable[N_TABLES];
    int[] ROW_SHIFT = {1, 2}; // shift rows
    int[] COL_SHIFT = {0, 4}; // shift columns

    MVCTableView groupFreqView = new SysAlleleFreqView(newGroup.getFreq());
    MVCTableView popView = new SysPopView(pop);
    JTable groupFreqTable = groupFreqView.getTableView();
    JTable popTable = popView.getTableView();
    tables[FREQ_TABLE] = groupFreqTable;
    tables[POP_TABLE] = popTable;

//      JTable table = JTableFactory.assemble(ROW_SHIFT, COL_SHIFT, tables, img);
    JTable table = JTableFactory.assemble(ROW_SHIFT, COL_SHIFT, tables, null);

    int colIdx = COL_SHIFT[FREQ_TABLE];
    int rowIdx = ROW_SHIFT[FREQ_TABLE];
    JTableFactory.copyColumnNames(table, rowIdx-2, colIdx, groupFreqTable);
//    JTableFactory.copyColumnNames(table, 0, colIdx, groupFreqTable);
//    JTableFactory.copyColumnNames(table, 0, colIdx, groupFreqTable);
//    table.setValueAt("AvrOverLoci", 0, colIdx-1);
//    table.setValueAt("Heteroz from", 0, colIdx-2);
    table.setValueAt("SubPop[1]", rowIdx-2, colIdx-3);
    table.setValueAt("mat", rowIdx-2, colIdx-2);
    table.setValueAt("pat", rowIdx-2, colIdx-1);

//    table.setValueAt("Total# in ref pop", rowIdx-3, colIdx-1);
//    int[] alleles = IntVec.makeIdxArray(pop.getFreq().getMaxNumAlleles());
//    JTableFactory.writeColumn(table, rowIdx-3, colIdx, alleles);
//
//    table.setValueAt("Total# in sub pop", rowIdx-2, colIdx-1);
//    alleles = IntVec.makeIdxArray(newGroup.getFreq().getMaxNumAlleles());
//    JTableFactory.writeColumn(table, rowIdx-2, colIdx, alleles);

    //DecimalFormat nf = new DecimalFormat("0.###E0");
    DecimalFormat nf = new DecimalFormat("0.###");

//    rowIdx -= 5;
//    table.setValueAt("TRUE freqs", rowIdx, colIdx-2);
//    table.setValueAt(nf.format(trueHetAvr), rowIdx, colIdx-1);
//    JTableFactory.writeRow(table, rowIdx, colIdx, trueHet, nf);
//
//    rowIdx++;
//    table.setValueAt("GROUP freqs", rowIdx, colIdx-2);
//    table.setValueAt(nf.format(estHetAvr), rowIdx, colIdx-1);
//    JTableFactory.writeRow(table, rowIdx, colIdx, estHet, nf);
//
//    rowIdx++;
//    table.setValueAt("ObsRefPop", rowIdx, colIdx-2); // observed from reference pop
//    table.setValueAt(nf.format(obsHetAvr), rowIdx, colIdx-1);
//    JTableFactory.writeRow(table, rowIdx, colIdx, obsHet, nf);

    if (updateView != null) {
//      table.setBackground(new Color(0,0,0));
//      table.setForeground(new Color(150,255,150));
      updateView.setTableView(table);
      updateView.assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
    }
  }

  public Partition getPartition()
  {
    Partition res = new Partition();
    CompBitSet set = new CompBitSet(bestC);
    res.add(set);
    set = new CompBitSet(bestC);
    set.flip(0, n);
    res.add(set);
    res.setSampleSize(n);
    return res;
  }
}
