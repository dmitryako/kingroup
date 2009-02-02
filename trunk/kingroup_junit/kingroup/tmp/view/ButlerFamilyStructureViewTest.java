package kingroup.tmp.view;

import javax.swing.*;
import java.awt.*;

import javax.swingx.ApplyDialogUI;
import kingroup.papers.butler2004.ButlerPopBuilderModel;
import kingroup.papers.butler2004.family.ButlerFamilyStructureView;

/**
 * Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 */
public class ButlerFamilyStructureViewTest {
   public static void main(String[] args) {
      ButlerFamilyStructureViewTest test = new ButlerFamilyStructureViewTest();
      System.exit(0);
   }

   public ButlerFamilyStructureViewTest()    {
      JFrame f = new JFrame();
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      JPanel panel = new JPanel(new GridLayout(12, 1));

      ButlerPopBuilderModel model = new ButlerPopBuilderModel();
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));
      panel.add(new ButlerFamilyStructureView(model));

      ApplyDialogUI dlg = new ApplyDialogUI(panel, f, true);
      dlg.setVisible(true);
      f.dispose();
   }

}
