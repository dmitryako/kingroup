package kingroup.papers.butler2004.freq;
import kingroup.papers.butler2004.ButlerPopBuilderModel;

import javax.swing.*;
import javax.swingx.ApplyDialogUI;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 28, 2004, Time: 2:32:34 PM
 */
public class ButlerAllelicFreqViewTest extends JFrame {
  public static void main(String[] args) {
    ButlerAllelicFreqViewTest test = new ButlerAllelicFreqViewTest();
    System.exit(0);
  }
  public ButlerAllelicFreqViewTest() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ButlerPopBuilderModel model = new ButlerPopBuilderModel();
    model.loadDefaults();
    ButlerAllelicFreqView view = new ButlerAllelicFreqView(model);
    ApplyDialogUI dlg = new ApplyDialogUI(view, this, true);
    dlg.setVisible(true);
  }
}
