package multi_t.ucm.settings;
import javax.iox.TextFile;
import multi_t.MultiT;
import multi_t.MultiTFrame;
import multi_t.MultiTProject;
import multi_t.fasta.FastaPrimerReader;
import multi_t.pcr.Primer;
import multi_t.project.ProjectSettingsUI;

import javax.iox.FileX;
import javax.swing.*;
import java.io.File;
import tsvlib.project.ProjectLogger;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 20/11/2006, Time: 17:19:44
 */
public class UCReadPrimerFile extends UCSelectFile {
  private final static ProjectLogger log = ProjectLogger.getLogger(UCSelectPrimerFile.class.getName());
  public UCReadPrimerFile(ProjectSettingsUI view)  {
    super(view);
  }
  public boolean run() {
    MultiT project = MultiTProject.getInstance();
    String name = project.getPrimerFileName();
    File file = project.makeFile(name);
    if (file == null)
      return false;

    TextFile fileModel = new TextFile();
    fileModel.setFileName(file.getName());
    fileModel.read(file, MultiTFrame.getInstance());
    FastaPrimerReader.read(fileModel, project);
    Primer fwd = project.getFwdPrimer();
    if (fwd == null
      || fwd.getSeq() == null
      || fwd.getSeq().length() == 0) {
      String err = "Primers are not found in the following FASTA file:\n" + FileX.getFileName(file);
      file = null;
      System.gc();
      log.severe(err);
      JOptionPane.showMessageDialog(MultiTFrame.getInstance(), err);
      return false;
    }
    project.setPrimerFileName(FileX.getFileName(file));
    
    project.saveProjectToDefaultLocation();
    if (parentView != null)  {
      parentView.loadFrom(project);
      parentView.repaint();
    }
    file = null;
    System.gc();
    return true;
  }
}
