package kingroup.view;
import kingroup.model.KinshipIBDModelV1;
import kingroup.model.KnownPedigreeModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.utilx.arrays.ArraysX;
import java.awt.event.ActionListener;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: Jul 5, 2004, Time: 11:01:45 AM
 */
public class PedigreeView extends JPanel {
  private String pedigrees_[] = new String[0]; // NOTE!!!! NOT STATIC!!
  private float relRm_[] = new float[0];
  private float relRp_[] = new float[0];
  private JComboBox pedigreeCombo_;
  protected PedigreeView() {
  }
  public PedigreeView(String title, int type) {
    init(title);
    addType(type);
  }
  public void addType(int type) {
    if (!KnownPedigreeModel.isValidType(type))
      return;
//    pedigrees_ = ArraysX.addString(KnownPedigreeModel.getPedigreeName(type)
//      , pedigrees_);
    relRm_ = ArraysX.addFloat(KnownPedigreeModel.getPedigreeRm(type), relRm_);
    relRp_ = ArraysX.addFloat(KnownPedigreeModel.getPedigreeRp(type), relRp_);
    pedigreeCombo_ = new JComboBox(pedigrees_);
    assemble();
  }
  public float getRm() {
    int idx = pedigreeCombo_.getSelectedIndex();
    if (idx < 0 || idx >= relRm_.length)
      return 0.0f;
    return relRm_[idx];
  }
  public float getRp() {
    int idx = pedigreeCombo_.getSelectedIndex();
    if (idx < 0 || idx >= relRp_.length)
      return 0.0f;
    return relRp_[idx];
  }
  public void loadTo(KinshipIBDModelV1 model) {
    model.setRm(getRm());
    model.setRp(getRp());
  }
  private void init(String title) {
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, title);
    setBorder(titled);
  }
  protected void assemble() {
    removeAll();
    add(pedigreeCombo_);
    if (getParent() != null)
      getParent().validate();
  }
  protected void assembleWithTitle(String title) {
    removeAll();
    Border border = BorderFactory.createEtchedBorder();
    Border titled = BorderFactory.createTitledBorder(border, title);
    setBorder(titled);
    add(pedigreeCombo_);
    if (getParent() != null)
      getParent().validate();
  }
  public void addActionListener(ActionListener act) {
    pedigreeCombo_.addActionListener(act);
  }
  final public void setType(int pedigree) {
    if (!KnownPedigreeModel.isValidType(pedigree))
      return;
    pedigreeCombo_.setSelectedIndex(pedigree);
  }
  final public int getType() {
    return pedigreeCombo_.getSelectedIndex();
  }
  public void setEnabled(boolean b) {
    super.setEnabled(b);
    pedigreeCombo_.setEnabled(b);
  }
  public boolean isEnabled() {
    int idx = pedigreeCombo_.getSelectedIndex();
    // NOTE idx==0 is manual input
    if (idx <= 0 || idx >= relRm_.length)
      return true;
    return false;
  }
  public void loadFrom(float rm, float rp) {
    int idx = matchPedigrees(rm, rp);
    if (idx >= 0 && idx < pedigrees_.length)
      pedigreeCombo_.setSelectedIndex(idx);
  }
  private int matchPedigrees(float rm, float rp) {
    for (int i = 0; i < relRm_.length && i < relRp_.length; i++)
      if ((relRm_[i] == rm && relRp_[i] == rp)
        || (relRm_[i] == rp && relRp_[i] == rm))
        return i; // // NOTE: 0 is for manual input
    return 0; // NOTE: 0 is for manual input
  }
}
