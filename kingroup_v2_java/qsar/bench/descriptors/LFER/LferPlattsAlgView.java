package qsar.bench.descriptors.LFER;
import pattern.ucm.UCController;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.view.QBenchViewI;

import javax.iox.table.TableView;
import javax.swing.*;
import javax.swingx.tablex.TableWithApplyUI;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 1/04/2007, 12:49:24
 */
public class LferPlattsAlgView extends TableWithApplyUI
  implements QBenchViewI
{
  private final LferPlattsOptView optView;
  public LferPlattsAlgView(LferPlattsOptView optView, QBench project, UCController runOnApply) {
    super(optView, runOnApply);
    setFrame(QBenchFrame.getInstance());
    this.optView = optView;
    this.setButtonText("Calculate");

    loadFrom(project);
  }

  public void assemble(JPanel v) {
    setOptView(v);

    setTableView(new JTable());
    assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
    setFocusOnApply();
  }

  public void loadTo(QBench model)
  {
    optView.loadTo(model);
  }

  public void loadFrom(QBench model)
  {
// NOTE!!! Do not call optView.loadFrom() since it is the source of action
//    optView.loadFrom(model);

    LferPlatts platts = model.getLferPlatts();
    if (platts.getShowLferTable()) {
      TableView tableView = new TableView(platts.getLferTable(), model.getTableDisplayOpt());
      setTableView(tableView.getTableView());
    }
    else {
      setTableView(new JTable());
    }
    assembleWithOpt(JSplitPane.HORIZONTAL_SPLIT);
    setFocusOnApply();
  }
}

