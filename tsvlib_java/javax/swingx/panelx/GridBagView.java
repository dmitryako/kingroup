package javax.swingx.panelx;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 10, 2004, Time: 10:13:18 AM
 */
public class GridBagView extends JPanel {
  private GridBagLayout gridbag = new GridBagLayout();
  private GridBagConstraints startRow = new GridBagConstraints();
  private GridBagConstraints middleRow = new GridBagConstraints();
  private GridBagConstraints endRow = new GridBagConstraints();
  public GridBagView() {
    super();
    init(null);
  }
  public GridBagLayout getGridBagLayout() {
    return gridbag;
  }
  public GridBagConstraints getEndRow() {
    return endRow;
  }
  public GridBagConstraints getMiddleRow() {
    return middleRow;
  }
  public GridBagConstraints getStartRow() {
    return startRow;
  }
  public GridBagView(String title) {
    super();
    init(title);
  }
  private void init(String title) {
    super.setLayout(gridbag);

    // see  http://www.jakemiles.com/?page=gridBagLayout

    // int top, int left, int bottom, int right)
    //startRow_.insets = new Insets(4, 4, 4, 4);
    startRow.insets = new Insets(2, 3, 2, 3);
    startRow.fill = GridBagConstraints.BOTH;
    startRow.anchor = GridBagConstraints.NORTHEAST;

    middleRow.insets = startRow.insets;
    middleRow.fill = GridBagConstraints.BOTH;
    middleRow.anchor = GridBagConstraints.NORTHWEST;
    middleRow.weightx = 1;  // NOTE!!!

    endRow.gridwidth = GridBagConstraints.REMAINDER;
    endRow.insets = startRow.insets;
    endRow.fill = GridBagConstraints.BOTH;
    endRow.anchor = GridBagConstraints.NORTHWEST;

    if (title != null) {
      Border border = BorderFactory.createEtchedBorder();
      Border titled = BorderFactory.createTitledBorder(border, title);
      setBorder(titled);
    }
  }
  public void startRow(Component comp) {
    addToGridBag(comp, startRow);
  }
  public void middleRow(Component comp) {
    addToGridBag(comp, middleRow);
  }
  public void endRow(Component comp) {
    addToGridBag(comp, endRow);
  }
  public void addToGridBag(Component comp, GridBagConstraints c) {
    gridbag.setConstraints(comp, c);
    add(comp);
//    add(comp, c);
  }
}
