package qsar.bench.qsar.table;
import javax.iox.TableFormat;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntField;
import java.awt.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 5/07/2007, 11:23:43
 */
public class TableSelectColsView  extends GridBagView {
  private static int FIELD_SIZE = 2;
  private IntField first;
  private JLabel firstLbl;
  private IntField last;
  private JLabel lastLbl;
  private static final int LAST_COL = 100;

  public TableSelectColsView(TableFormat model, String mssg) {
    init();
    loadFrom(model);
    assemble(mssg);
  }
  protected TableSelectColsView() {
  }
  protected void assemble(String mssg) {
    getStartRow().anchor = GridBagConstraints.NORTHWEST;
    startRow(makePanel(mssg));
  }
  protected void init() {
    firstLbl = new JLabel("first #");
    first = new IntField(FIELD_SIZE, 1, LAST_COL-1);
    lastLbl = new JLabel("last #");
    lastLbl.setToolTipText("set 0 to select the last available column");
    last = new IntField(FIELD_SIZE, 0, LAST_COL);
    last.setToolTipText(lastLbl.getToolTipText());

    setBorder(BorderFactory.createLoweredBevelBorder());
  }
  private JPanel makePanel(String mssg) {
    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, mssg);
    panel.setBorder(titled);
    panel.startRow(firstLbl);
    panel.endRow(first);
    panel.startRow(lastLbl);
    panel.endRow(last);
    return panel;
  }
  public void loadFrom(TableFormat model) {
    first.setValue(model.getFirstCol());
    last.setValue(model.getLastCol());
  }
  public void loadTo(TableFormat model) {
    model.setFirstCol(first.getInput());
    model.setLastCol(last.getInput());
  }
}


