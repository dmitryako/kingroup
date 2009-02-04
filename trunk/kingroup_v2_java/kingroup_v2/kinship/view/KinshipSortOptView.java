package kingroup_v2.kinship.view;
import kingroup_v2.kinship.Kinship;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 27/03/2006, Time: 16:33:56
 */
public class KinshipSortOptView extends GridBagView
{
  private JCheckBox showHeaders;
  private JCheckBox sort;
  private JRadioButton desc;
  private JRadioButton ascend;
  private JCheckBox sortByID;

  public KinshipSortOptView(Kinship model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void assemble() {
    endRow(sort);
    endRow(desc);
    endRow(ascend);
    endRow(sortByID);
    endRow(showHeaders);
  }
  private void init() {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Sort");
    setBorder(titled);

    // CALC/CURRENT
    desc = new JRadioButton("Descending", false);
//    desc.setToolTipText("Use currently loaded");

    ascend = new JRadioButton("Ascending", false);
//    ascending.setToolTipText("Calculate from loaded population sample.");

    ButtonGroup group = new ButtonGroup();
    group.add(desc);
    group.add(ascend);
    desc.setSelected(true); // default

    sort = new JCheckBox("sort");
    sortByID = new JCheckBox("sort by id");
    showHeaders = new JCheckBox("headers");
    showHeaders.setToolTipText("show column headers");
//    showHeaders = new JCheckBox("save to file");
//    showHeaders.setToolTipText("to save and then view large data sets");

    sort.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)      {
        enableOptions();
      }});
  }
  private void loadFrom(Kinship model)
  {
    desc.setSelected(!model.getDisplayAscending());
    ascend.setSelected(model.getDisplayAscending());
    sort.setSelected(model.getDisplaySorted());
    sortByID.setSelected(model.getDisplaySortedById());
    showHeaders.setSelected(model.getShowColumnHeaders());
    enableOptions();
  }

  private void enableOptions()
  {
    desc.setEnabled(sort.isSelected());
    ascend.setEnabled(sort.isSelected());
    sortByID.setEnabled(sort.isSelected());
    showHeaders.setEnabled(sort.isSelected());
  }

  public void loadTo(Kinship model) {
    model.setDisplayAscending(ascend.isSelected());
    model.setDisplaySorted(sort.isSelected());
    model.setDisplaySortedById(sortByID.isSelected());
    model.setShowColumnHeaders(showHeaders.isSelected());
  }
}

