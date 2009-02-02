package qsar.bench.descriptors.LFER;
import pattern.ucm.UCController;
import pattern.ucm.AdapterUCCToALThread;
import qsar.bench.QBench;
import qsar.bench.view.QBenchViewI;
import tsvlib.project.ProjectLogger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.textx.FractionDigitsView;
import java.awt.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 30/03/2007, 14:15:44
 */
public class LferPlattsOptView extends GridBagView
  implements QBenchViewI
//  , TableViewI
{
  private static ProjectLogger log = ProjectLogger.getLogger(LferPlattsOptView.class.getName());
//  protected TableView tableView;
  protected FractionDigitsView digitsView;
  protected JCheckBox showLferTable;

  public LferPlattsOptView()
  {
    init();
  }
  public LferPlattsOptView(QBench project)
  {
    init();
    initFrom(project);
    assemble();
  }
  protected void init()
  {
    getStartRow().anchor = GridBagConstraints.NORTHWEST;
    getEndRow().anchor = GridBagConstraints.NORTHWEST;

    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Display");
    setBorder(titled);

    showLferTable = new JCheckBox("Fragments");
    showLferTable.setToolTipText("contributing groups");
  }

  public void loadTo(QBench model) {
    digitsView.loadTo(model.getDigitsModel());
    model.getLferPlatts().setShowLferTable(showLferTable.isSelected());
  }
  private void initFrom(QBench model) {
    digitsView = new FractionDigitsView(model.getDigitsModel());

    LferPlatts platts = model.getLferPlatts();
    showLferTable.setSelected(platts.getShowLferTable());
//    tableView = new TableView(platts.getLferTable(), model.getDigitsModel());
  }
  public void loadFrom(QBench model) {
    digitsView.loadFrom(model.getDigitsModel());

    LferPlatts platts = model.getLferPlatts();
    showLferTable.setSelected(platts.getShowLferTable());
//    tableView.loadFrom(platts.getLferTable(), model.getDigitsModel());
  }
  protected void assemble() {
    endRow(showLferTable);
    endRow(digitsView);
  }
  public void setRunOnDigitChange(UCController uc) {
    digitsView.setRunOnChange(uc);
  }
  public void setRunOnDisplayChange(UCController uc) {
    showLferTable.addActionListener(new AdapterUCCToALThread(uc));
  }

//  public JTable getTableView()
//  {
//    return tableView.getTableView();
//  }
}

