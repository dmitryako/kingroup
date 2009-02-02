package kingroup_v2.kinship.like.view;
import kingroup_v2.kinship.Kinship;
import pattern.ucm.UCController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swingx.panelx.GridBagView;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 3/10/2005, Time: 13:47:24
 */
public class KinshipRatioSimView extends GridBagView {
  private KinshipRatioSimOptView optView;
  private KinshipRatioSimTableView tableView;
  public KinshipRatioSimView(Kinship model) {
    init();
    loadFrom(model);
    assemble();
  }
  private void loadFrom(Kinship model) {
    this.optView = new KinshipRatioSimOptView(model);
    this.tableView = new KinshipRatioSimTableView(model);
  }
  private void init() {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, "Significance levels");
    setBorder(titled);
  }
  public void assemble() {
    removeAll();
    endRow(optView);
    endRow(tableView);
  }
  public void setPValueEnabled(boolean b) {optView.setPValueEnabled(b);}
  public KinshipRatioSimTableView getTableView() {
    return tableView;
  }
  public KinshipRatioSimOptView getOptionsView() {
    return optView;
  }
  public void loadTo(Kinship model) {
    optView.loadTo(model);
  }
  public void setTableView(KinshipRatioSimTableView view) {
    tableView = view;
    assemble();
  }
  public void onSigFlagChange(UCController uc) {
    optView.onSigFlagChange(uc);
  }
  public void onPValueChange(UCController uc) {
    optView.onPValueChange(uc);
  }
  public int getNumPairs() {
    return optView.getNumPairs();
  }
  public boolean getDisplayPValue() {
    return optView.getDisplayPValue();
  }
}
