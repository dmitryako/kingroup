package qsar.bench.ucm.table;
import qsar.bench.QBenchMainUI;
import tsvlib.project.ProjectLogger;

import javax.iox.table.Table;
import javax.swing.*;
import javax.utilx.arrays.mtrx.Mtrx;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 6/06/2007, 13:41:55
 */
public class UCDelConstCols extends UCDelColsCommon  {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCDelConstCols.class);

  public boolean run() {
    log.trace("run()");
    QBenchMainUI ui = QBenchMainUI.getInstance();

    Table zTrain = ui.getZTable();
    if (zTrain == null   ||  zTrain.getNumRows() == 0) {
      log.trace("train == null   ||  train.getNumRows() == 0");
      ui.showLoadTrainTableFirst();
      return false;
    }
    int nCols = zTrain.getNumCols();
    BitSet cols = Mtrx.getNonConstCols(zTrain.getByRows());
    if (cols.cardinality() == nCols) {
      JOptionPane.showMessageDialog(ui, "There are no constant columns");
      return true;
    }
    return displayRes(zTrain, cols);

//    Table res = zTrain.getCols(cols);
//    QBench project = QBenchProject.getInstance();
//    TableView tableView = new TableView(res, project.getTableDisplayOpt());
//    ui.setZTableView(tableView);
//
//    BitSet constCols = new BitSet(nCols);
//    constCols.set(0, nCols);
//    constCols.xor(cols);
//    String[] constIds = StrVec.get(constCols, zTrain.getColIds());
//    String mssg = StrVec.toCSV(constIds);
//    if (mssg.length() > MAX_STR_LEN)
//      mssg = mssg.substring(0, MAX_STR_LEN);
//    JOptionPane.showMessageDialog(ui, "Deleted column ids:\n" + mssg + "...");
//    return true;
  }
}

