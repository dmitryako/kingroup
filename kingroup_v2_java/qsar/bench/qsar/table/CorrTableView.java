package qsar.bench.qsar.table;
import pattern.mvc.MVCTableView;

import javax.iox.table.Table;
import javax.iox.table.TableDisplayOpt;
import javax.iox.table.TableView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/06/2007, 15:50:24
 */
public class CorrTableView extends MVCTableView {
  public CorrTableView(final int toColIdx, Table z, TableDisplayOpt opt) {
    z = z.getTableCols(0, opt.getMaxNumCols());
    double[] corrArr = z.calcColCorr(toColIdx);
    double[][] A = new double[1][0];
    A[0] = corrArr;

    TableDisplayOpt localOpt = new TableDisplayOpt();
    localOpt.setDigitsModel(opt.getDigitsModel());
    localOpt.setMaxNumRows(0);
    localOpt.setMaxNumCols(opt.getMaxNumCols());

    Table table = new Table(A);
    table.setName("Correlation");
    table.setColIds(z.getColIds());
    table.setRowIds(new String[]{"r"});
    MVCTableView tableView = new TableView(table, localOpt);
    assemble(tableView.getTableView());
  }
}

