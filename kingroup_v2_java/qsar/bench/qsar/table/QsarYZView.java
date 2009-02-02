package qsar.bench.qsar.table;
import pattern.mvc.MVCTableView;
import qsar.bench.qsar.MLR.MlrRes;

import javax.iox.table.Table;
import javax.stats.StatsRes;
import javax.swing.*;
import javax.textx.FractionDigitsModel;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 11/03/2007, 11:38:52
 */
public class QsarYZView extends MVCTableView {
  private final Table table;
  private static final int SHIFT = 2;

  public QsarYZView(String name, double[] yp, MlrRes stats, Table z, FractionDigitsModel formater) {
    table = z;
    MVCTableView[] views = new MVCTableView[3];
    views[0] = new StatsTableView(name, stats, z, formater);
    if (stats.getMlrCoeffs() != null)
      views[1] = new MlrCoeffsView(SHIFT, "MLR coeff", stats.getMlrCoeffs(), formater);
    views[2] = new QsarYYpTableView(name, yp, z, formater);
    JTable t = makeTableView(views);
    assemble(t);
  }
  public QsarYZView(String name, double[] yp, StatsRes stats, Table z, FractionDigitsModel formater) {
    table = z;
    MVCTableView statsView = new StatsTableView(name, stats, z, formater);
    MVCTableView qsarView = new QsarYYpTableView(name, yp, z, formater);
    JTable t = makeTableView(statsView, qsarView);
    assemble(t);
  }
  public Table getTable() {
    return table;
  }
}

