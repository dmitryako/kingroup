package tomsk.ucm.world;
import pattern.ucm.UCController;
import tomsk.TomskMainUI;
import tomsk.domain.view.PDBTableView;
import tomsk.io.pdb.PDBModel;
import tomsk.io.pdb.PDBReader;
import tomsk.project.Tomsk;
import tomsk.project.TomskProject;
import tomsk.view.j3dx.PDB3dView;
import tsvlib.project.ProjectFrame;
import tsvlib.project.ProjectLogger;

import javax.iox.TextFile;
import javax.iox.TextFileView;
import javax.swing.*;
import javax.swingx.ApplyDialogUI;
import javax.swingx.UCShowImportFileUI;
import java.io.File;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: jc138691
 * Date: Sep 22, 2004, Time: 10:58:06 AM
 */
public class UCImportPDBFile implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCImportPDBFile.class);
  private File file;

  public UCImportPDBFile(File file) {
    this.file = file;
  }
  public boolean run()
  {
    log.trace("read");
    Tomsk project = TomskProject.getInstance();
    JFrame frame = ProjectFrame.getInstance();
    if (frame != null && file != null)
      frame.setTitle(project.getAppName() + " " + project.getAppVersion()
        + " [PDB file: " + file.getName() + "]");

    TextFile from = new TextFile();
    from.setFileName(file.getName());
    from.read(file, frame);
    from.trim();
    log.trace("from = \n", from);

    TextFileView fileView = new TextFileView(from);

    ApplyDialogUI dlg = new UCShowImportFileUI(fileView, frame
      , "Import " + file.getName());
    dlg.setApplyBttnText("Import");
    dlg.setVisible(true);
    if (!dlg.apply())
      return false;

    project.saveProjectToDefaultLocation(); // remember user input

    TomskMainUI ui = TomskMainUI.getInstance();
    ui.setImportFileView(fileView);

    PDBModel model = PDBReader.makeFromFile(from, true);
    if (model == null) {
      String error = "Unable to import from file \n"
        + from.getFileName();
      log.severe(error);
      JOptionPane.showMessageDialog(frame, error);
      return false;
    }

    PDBTableView pdbView = new PDBTableView(model, project.getDigitsModel());
    ui.setPdbTableView(pdbView);

    PDB3dView view3d = new PDB3dView(model, project.getTomsk3dModel());
    ui.setPdb3dView(view3d);

    return true;
  }
}
