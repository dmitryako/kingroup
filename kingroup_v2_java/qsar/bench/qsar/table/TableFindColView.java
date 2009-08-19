package qsar.bench.qsar.table;

import javax.swingx.panelx.GridBagView;
import javax.swingx.text_fieldx.IntField;
import javax.swing.*;
import javax.swing.border.Border;
import javax.iox.TableFormat;
import java.awt.*;

/**
 * Created by Dmitry.A.Konovalov@gmail.com using IntelliJ, 19/08/2009, 4:21:56 PM
 */
public class TableFindColView extends GridBagView {
  private static int FIELD_SIZE = 20;
  private JTextField colName;
  private JLabel colNameLbl;

  public TableFindColView(TableFormat model, String mssg) {
    init();
    loadFrom(model);
    assemble(mssg);
  }
  protected void assemble(String mssg) {
    getStartRow().anchor = GridBagConstraints.NORTHWEST;
    startRow(makePanel(mssg));
  }
  protected void init() {
    colNameLbl = new JLabel("column name ");
    colName = new JTextField(FIELD_SIZE);

    setBorder(BorderFactory.createLoweredBevelBorder());
  }
  private JPanel makePanel(String mssg) {
    GridBagView panel = new GridBagView();
    Border etched = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(etched, mssg);
    panel.setBorder(titled);
    panel.startRow(colNameLbl);
    panel.endRow(colName);
    return panel;
  }
  public void loadFrom(TableFormat model) {
    colName.setText(model.getColName());
  }
  public void loadTo(TableFormat model) {
    model.setColName(colName.getText());
  }
}


