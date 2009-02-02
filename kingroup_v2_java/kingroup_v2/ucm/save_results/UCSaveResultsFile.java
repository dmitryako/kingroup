package kingroup_v2.ucm.save_results;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import javax.iox.TextFile;
import kingroup_v2.kinship.KinshipFileWriter;
import pattern.ucm.UCController;

import javax.swing.*;
import java.io.File;
import tsvlib.project.ProjectLogger;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 15:44:24
 */
public class UCSaveResultsFile implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCSaveResultsFile.class.getName());
  public boolean run() {
    Kingroup bean = KinGroupV2Project.getInstance();
    String name = bean.getLastSavedFileName();
    File file = bean.makeFile(name);
    write(file);
    return true;
  }
  public void write(File file) {
    Kingroup project = KinGroupV2Project.getInstance();
    if (project.getFileType() == project.KINSHIP_FILE) {
      writeKinshipFile(file, project);
    }
    else if (project.getFileType() == project.CERVUS_FILE) {
      String error = "Not implemented. Please contact dmitry.konovalov@jcu.edu.au if you need this format.";
      log.severe(error);
      JFrame frame = KingroupFrame.getInstance();
      JOptionPane.showMessageDialog(frame, error);
    }
  }
  public void writeKinshipFile(File file, Kingroup project)
  {
//    KinshipFileFormat format = project.getKinshipFileFormat();
//    KinshipFileFormatView formatView = new KinshipFileFormatView(format);
//    ApplyDialogUI dlg = new UCShowImportFileUI(formatView
//      , "Save results to " + file.getName());
//    dlg.setApplyBttnText("Save");
//    dlg.setVisible(true);
//    if (!dlg.apply())
//      return;
//    formatView.loadTo(format);
//    project.saveProjectToDefaultLocation(); // remember user input

    // user may change parameters
    TextFile model = new TextFile();
    model.setFileName(file.getName());
    KinshipFileWriter.write(model, project);

//    TextFileView fileView = new TextFileView(model);
//    ApplyDialogUI dlg = new UCShowImportFileUI(fileView, "Save results to " + file.getName());
//    dlg.setApplyBttnText("Save");
//    dlg.setVisible(true);
//    if (!dlg.apply())
//      return;
//    formatView.loadTo(format);
//    project.saveProjectToDefaultLocation(); // remember user input

    JFrame frame = KingroupFrame.getInstance();
    model.write(file, frame);
  }
}

