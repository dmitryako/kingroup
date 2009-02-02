package kingroup_v2.ucm.pop;

import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.cervus.AlleleAnalysisFactory;
import kingroup_v2.pop.allele.freq.SysAlleleFreq;
import kingroup_v2.pop.allele.freq.SysAlleleFreqFactory;
import kingroup_v2.pop.allele.freq.SysAlleleFreqView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.pop.sample.sys.SysPopView;
import kingroup_v2.view.KingroupViewI;
import pattern.mvc.MVCTableView;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swingx.tablex.JTableFactory;
import javax.swingx.tablex.TableViewWithOpt;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.IntVec;
import javax.utilx.arrays.vec.Vec;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.BitSet;

/**
 * Created by IntelliJ IDEA.
 * User: ESHFreeUser
 * Date: 7/06/2006
 * Time: 13:30:47
 * To change this template use File | Settings | File Templates.
 */
public class UCPopFreqCalcFreq_dev implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCPopFreqCalcFreq_dev.class.getName());
  private TableViewWithOpt updateView;
  protected KingroupViewI optView;

  public UCPopFreqCalcFreq_dev(KingroupViewI optView) {
    this.optView = optView;
  }
  public boolean run() {
//    ImageIcon img = ProjectFrame.loadImageIcon("images/matrix.jpg");
//    ImageIcon img = ProjectFrame.loadImageIcon("images/see_matrix.jpg");

    // LOAD FROM OPTIONS VIEW
    Kingroup project = KinGroupV2Project.getInstance();
    //Kinship kinship = project.getKinship();
    optView.loadTo(project);
    project.saveProjectToDefaultLocation();

/*    if (!isValid())
      return false;
*/
    KinGroupV2MainUI mainUI = KinGroupV2MainUI.getInstance();
    SysPop givenPop = mainUI.getSysPop();

    SysAlleleFreq givenFreq = givenPop.getFreq();

    SysPop pop = SysPopFactory.makeDeepCopy(givenPop);
    int n = pop.size();
//    UsrPopView popView = mainUI.getUsrPopView();

    //LinkedList<Double> bestList = new LinkedList<Double>();
    //int MAX_LIST_SIZE = n;
    int MAX_COUNT = 10;
    int GROUP_IDX = 1;
    BitSet bestConfig = new BitSet();
    BitSet currConfig = new BitSet();
    BitSet newConfig = new BitSet();
    double bestZ = 0;
    double currZ = 0;

    double[] obsHet = AlleleAnalysisFactory.calcObservHeteroz(givenPop);
    log.info("\nobsHet=" + Vec.toString(obsHet));
    double obsHetAvr = Vec.avr(obsHet);

    RandomSeed rand = RandomSeed.getInstance();
    for (int i = 0; i < MAX_COUNT; i++) {
      if (i == 0) {
        newConfig.set(0, n);   // Include all
      }
      else {
        newConfig.set(0, n, false);
        newConfig.or(currConfig);
        newConfig.flip(rand.nextInt(n));
      }
      log.info("\nnewConfig = " + newConfig);
      //TODO: Check that all allele are present

      SysPopFactory.setGroupIds(GROUP_IDX, newConfig, pop); // need ids for viewing
      SysPop newGroup = SysPopFactory.makeGroupFrom(GROUP_IDX, pop);

      SysAlleleFreq newGroupFreq = SysAlleleFreqFactory.makeFrom(newGroup, true);
      newGroup.setFreq(newGroupFreq);

      double[] newHet = AlleleAnalysisFactory.calcTrueHeteroz(newGroup.getFreq());
      double newHetAvr = Vec.avr(newHet);
      double newZ = calcCost(obsHetAvr, newHetAvr);

      if (i == 0  ||  newZ < bestZ) {
        bestZ = newZ;
        bestConfig.set(0, n, false);
        bestConfig.or(newConfig);

        showTable(newGroup, pop);
      }
      if (i == 0  ||  newZ < currZ) {
        currZ = newZ;
        currConfig.set(0, n, false);
        currConfig.or(newConfig);
      }
      else {
        // TODO: Simulated annealing
        continue;
      }
    }
    return true;
  }

  private void showTable(SysPop newGroup, SysPop pop) {
    KinGroupV2MainUI mainUI = KinGroupV2MainUI.getInstance();
    SysPop givenPop = mainUI.getSysPop();
    SysAlleleFreq givenFreq = givenPop.getFreq();

    double[] trueHet = AlleleAnalysisFactory.calcTrueHeteroz(givenPop.getFreq());
    double trueHetAvr = Vec.avr(trueHet);

    double[] obsHet = AlleleAnalysisFactory.calcObservHeteroz(givenPop);
    log.info("\nobsHet=" + Vec.toString(obsHet));
    double obsHetAvr = Vec.avr(obsHet);

    //double[] obsArr = SimilarityIndex.calcFromPop(newGroup);
    //double obs = SimilarityIndex.averageOverLoci(obsArr);

    //double[] estArr = SimilarityIndex.calcUnbiasedFromFreq(newGroup);
    //double est = SimilarityIndex.averageOverLoci(estArr);
    double[] estHet = AlleleAnalysisFactory.calcTrueHeteroz(newGroup.getFreq());
    log.info("\nestHet=" + Vec.toString(obsHet));
    double estHetAvr = Vec.avr(obsHet);

//    double[] biasedArr = SimilarityIndex.calcFromFreq(newGroupFreq);
//    double biased = SimilarityIndex.averageOverLoci(biasedArr);

    int N_TABLES = 2;
    int FREQ_TABLE = 0;
    int POP_TABLE = 1;
    JTable[] tables = new JTable[N_TABLES];
    int[] ROW_SHIFT = {6, 2}; // shift rows
    int[] COL_SHIFT = {4}; // shift columns

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
    JTableFactory.copyColumnNames(table, rowIdx-1, colIdx, groupFreqTable);
    JTableFactory.copyColumnNames(table, 0, colIdx, groupFreqTable);
    table.setValueAt("AvrOverLoci", 0, colIdx-1);
    table.setValueAt("Heteroz from", 0, colIdx-2);
    table.setValueAt("allele\\freqs", rowIdx-1, colIdx-1);
    table.setValueAt("Group", rowIdx-1, colIdx-2);

    int[] alleles = IntVec.makeIdxArr(givenFreq.getMaxNumAlleles());
    JTableFactory.writeColumn(table, rowIdx, colIdx-1, alleles);

    //DecimalFormat nf = new DecimalFormat("0.###E0");
    DecimalFormat nf = new DecimalFormat("0.###");

    rowIdx -= 5;
    table.setValueAt("TRUE freqs", rowIdx, colIdx-2);
    table.setValueAt(nf.format(trueHetAvr), rowIdx, colIdx-1);
    JTableFactory.writeRow(table, rowIdx, colIdx, trueHet, nf);

//    rowIdx++;
//    table.setValueAt("Group freqs", rowIdx, colIdx-2);
//    table.setValueAt(nf.format(biased), rowIdx, colIdx-1);
//    JTableFactory.writeRow(table, rowIdx, colIdx, biasedArr, nf);

    rowIdx++;
    table.setValueAt("GROUP freqs", rowIdx, colIdx-2);
    table.setValueAt(nf.format(estHetAvr), rowIdx, colIdx-1);
    JTableFactory.writeRow(table, rowIdx, colIdx, estHet, nf);

    rowIdx++;
    table.setValueAt("ObsRefPop", rowIdx, colIdx-2); // observed from reference pop
    table.setValueAt(nf.format(obsHetAvr), rowIdx, colIdx-1);
    JTableFactory.writeRow(table, rowIdx, colIdx, obsHet, nf);

    if (updateView != null) {
      table.setBackground(new Color(0,0,0));
      table.setForeground(new Color(150,255,150));
      updateView.setTableView(table);
      updateView.assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
    }
  }
  private double calcCost(double s, double s2) {
    return (s - s2) * (s - s2);
  }

  public void setViewForUpdate(TableViewWithOpt tableView) {
    this.updateView = tableView;
  }
}

