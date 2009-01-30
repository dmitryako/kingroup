package tomsk;
import tomsk.domain.view.PDBTableView;
import tomsk.view.j3dx.PDB3dView;

import javax.iox.TextFileView;
import javax.swing.*;
import javax.swingx.JTabbedPaneX;
import java.awt.*;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Aug 26, 2004, Time: 3:41:38 PM
 */
public class TomskMainUI extends JPanel {
  private static TomskMainUI instance = null;
  private JTabbedPaneX tabbedPane;

  private TextFileView importFileView;
  private int importFileIdx = -1;
  private JRadioButton importFileFocus;

  private PDBTableView pdbTableView;
  private int pdbTableIdx = -1;
  private JRadioButton pdbTableFocus;

  private PDB3dView pdb3dView;
  private int pdb3dIdx = -1;
  private JRadioButton pdb3dFocus;

  public static TomskMainUI getInstance() {
    if (instance == null)
      instance = new TomskMainUI();
    return instance;
  }
  public TomskMainUI() {
    init();
  }
  public void init() {
    importFileFocus = new JRadioButton();
    pdbTableFocus = new JRadioButton();
    pdb3dFocus = new JRadioButton();
    ButtonGroup group = new ButtonGroup();
    group.add(importFileFocus);
    group.add(pdbTableFocus);
    group.add(pdb3dFocus);

    importFileFocus.setSelected(true);
    tabbedPane = new JTabbedPaneX(JTabbedPane.TOP);
    setLayout(new BorderLayout());
    add(tabbedPane, BorderLayout.CENTER);
  }

  public void addWorldView(JApplet view) {
    this.removeAll();
    add("Center", view);
    validate();
    repaint();
  }

  public void setImportFileView(TextFileView view) {
    this.importFileView = view;
    importFileFocus.setSelected(true);
    rebuild();
  }

  private void rebuild() {
    importFileIdx = tabbedPane.processView(importFileView, importFileIdx, importFileFocus.isSelected()
      , "Imported File", "Last Imported File");

    pdbTableIdx = tabbedPane.processView(pdbTableView, pdbTableIdx, pdbTableFocus.isSelected()
      , "PDB", "Last Imported PDB structure");

    pdb3dIdx = tabbedPane.processView(pdb3dView, pdb3dIdx, pdb3dFocus.isSelected()
      , "3D", "Last Imported PDB structure");

    JFrame frame = TomskFrame.getInstance();
    if (frame != null) {
      frame.validate();
      frame.repaint();
    }
    validate();
    repaint();
  }

  public void setPdbTableView(PDBTableView molView)
  {
    this.pdbTableView = molView;
    pdbTableFocus.setSelected(true);
    rebuild();
  }

  public void setPdb3dView(PDB3dView molView)
  {
    this.pdb3dView = molView;
    pdb3dFocus.setSelected(true);
    rebuild();
  }
}
