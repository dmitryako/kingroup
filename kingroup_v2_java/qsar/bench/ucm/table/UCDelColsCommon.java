package qsar.bench.ucm.table;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchMainUI;
import qsar.bench.QBenchProject;

import javax.iox.table.Table;
import javax.iox.table.TableView;
import javax.utilx.arrays.StrVec;
import java.util.BitSet;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 19/06/2007, 15:31:06
 */
public abstract class UCDelColsCommon implements UCController
{
  private static final int MAX_STR_LEN = 65;

  protected boolean displayRes(Table zTrain, BitSet cols) {
    QBenchMainUI ui = QBenchMainUI.getInstance();
    int nCols = zTrain.getNumCols();
    Table res = zTrain.getCols(cols);
    QBench project = QBenchProject.getInstance();
    TableView tableView = new TableView(res, project.getTableDisplayOpt());
    ui.setZTableView(tableView);

    BitSet delCols = new BitSet(nCols);
    delCols.set(0, nCols);
    delCols.xor(cols);
    String[] constIds = StrVec.get(delCols, zTrain.getColIds());
    String mssg = StrVec.toCSV(constIds);
    if (mssg.length() > MAX_STR_LEN)
      mssg = mssg.substring(0, MAX_STR_LEN) + ", ...";
    ui.setStatus("Deleted "+ delCols.cardinality() + " columns: " + mssg);
    return true;
  }
}
