package qsar.bench.qsar.cv.mccv;
import pattern.mvc.MVCTableView;
import qsar.bench.qsar.table.StatsTableView;

import javax.iox.table.Table;
import javax.iox.table.TableDisplayOpt;
import javax.iox.table.TableView;
import javax.stats.StatsRes;
import javax.swing.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 24/04/2007, 14:32:49
 */
public class MccvZView extends MVCTableView {
  private final Table table;

  public MccvZView(String name, StatsRes stats, Table z, TableDisplayOpt model) {
    table = z;
    MVCTableView statsView = new StatsTableView(name, stats, z, model.getDigitsModel());
    if (stats.getGraph() != null) {
      double[][] arr = stats.getGraphAsArr();
      Table graphTable = new Table(arr);
      graphTable.setName("Graph");
//      graphTable.setColIds(new String[]{"x", "y"});
      graphTable.setColIds(new String[]{"y", "x"});
      MVCTableView graphView = new TableView(graphTable, model);
      JTable combView = makeTableView(statsView, graphView);
      assemble(combView);
//      statsView.assemble(combView);
    }
    else {
      MVCTableView tableView = new TableView(z, model);
      JTable t = makeTableView(statsView, tableView);
      assemble(t);
    }
  }
  public Table getTable() {
    return table;
  }
}

