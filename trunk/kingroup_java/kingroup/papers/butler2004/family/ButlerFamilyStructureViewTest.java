package kingroup.papers.butler2004.family;
import kingroup.papers.butler2004.ButlerPopBuilderModel;

import javax.swing.*;
import javax.swingx.ApplyDialogUI;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 27, 2004, Time: 4:22:46 PM
 */
public class ButlerFamilyStructureViewTest extends JFrame {
  public static void main(String[] args) {
    ButlerFamilyStructureViewTest test = new ButlerFamilyStructureViewTest();
    System.exit(0);
  }
  public ButlerFamilyStructureViewTest() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ButlerPopBuilderModel model = new ButlerPopBuilderModel();
    model.loadDefaults();
    ButlerFamilyStructureView view = new ButlerFamilyStructureView(model);
    ApplyDialogUI dlg = new ApplyDialogUI(view, this, true);
    dlg.setVisible(true);
  }
}