package kingroup_v2.ucm.tools;

import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.cervus.CervusFileFormat;
import kingroup_v2.cervus.CervusFileReader;
import kingroup_v2.cervus.tools.CervusParentageData;
import kingroup_v2.cervus.tools.CervusParentageFileFormat;
import kingroup_v2.cervus.tools.CervusParentageFormatView;
import kingroup_v2.cervus.tools.CervusParentageReader;
import kingroup_v2.io.FileIO;
import tsvlib.project.ProjectLogger;

import javax.iox.TextFile;
import javax.iox.TextFileView;
import javax.stats.Stats;
import javax.stats.StatsRes;
import javax.swing.*;
import javax.swingx.ApplyDialogUI;
import javax.swingx.UCShowImportFileUI;
import java.io.File;
import java.text.NumberFormat;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 29/01/2006, Time: 10:47:30
 */
public class UCReadCervusParentageFile
 {
  private static ProjectLogger log = ProjectLogger.getLogger(UCReadCervusParentageFile.class.getName());
  public void read(File[] files) {
    Kingroup project = KinGroupV2Project.getInstance();
    JFrame frame = KingroupFrame.getInstance();
    if (frame != null
      && files != null
      && files.length != 0
      && files[0] != null
    ) {
      frame.setTitle(project.getAppName() + " "
        + project.getAppVersion()
        + "  [CERVUS Parentage file: " + files[0].getName() + "]");
    }
    else
      return;
    File file = files[0];
    CervusParentageFileFormat format = project.getCervus().getParentageFormat();
    CervusFileFormat cervus = new CervusFileFormat();
    cervus.setColumnDelim(format.getColumnDelim());

    TextFile fileModel = new TextFile();
    fileModel.setFileName(file.getName());
    fileModel.read(file, frame);
    CervusFileReader.separateDelimiters(fileModel, cervus); // MUST BE DONE!!!

    // CONFIGURE CERVUS FORMAT
    TextFileView fileView = new TextFileView(fileModel);
    CervusParentageFormatView formatView = new CervusParentageFormatView(format);
    ApplyDialogUI dlg = new UCShowImportFileUI(FileIO.combine(formatView, fileView)
      , frame, "Import " + file.getName());
    dlg.setApplyBttnText("Import");
    dlg.setVisible(true);
    if (!dlg.apply())
      return;
    formatView.loadTo(format);
    project.saveProjectToDefaultLocation(); // remember user input

    // SHOW INPUT FILE
    CervusParentageData parents = CervusParentageReader.importFile(fileModel, format);
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    ui.resetAll();
    fileView = new TextFileView(parents.getInputData());
    ui.setImportFileView(fileView);

    String mssg = "Found progeny assignment errors:  ";
    double[] arr = countErrors(files);
    StatsRes stats = new Stats(arr);
    double nErrors = stats.getMean();
    double accError = 100.*nErrors/format.getNumProgenies();

    NumberFormat nf = NumberFormat.getNumberInstance();
    nf.setMaximumFractionDigits(4);
    mssg += (nf.format(nErrors) + " (or "+nf.format(accError)+"%)");
    mssg += (",\naveraged over " + arr.length + " files.");
    mssg += "\n\nASSUMED:\nError = one or two incorrect parents.";
    mssg += "\nEach file has the same number of progenies (" +format.getNumProgenies() +").";
    log.severe(mssg);
    JOptionPane.showMessageDialog(frame, mssg);
  }
  private double[] countErrors(File[] files)
  {
    Kingroup project = KinGroupV2Project.getInstance();
    JFrame frame = KingroupFrame.getInstance();
    CervusParentageFileFormat format = project.getCervus().getParentageFormat();
    CervusFileFormat cervus = new CervusFileFormat();
    cervus.setColumnDelim(format.getColumnDelim());

    double[] res = new double[files.length];
    for (int i = 0; i < res.length; i++) {
      File file = files[i];
      TextFile fileModel = new TextFile();
      fileModel.setFileName(file.getName());
      fileModel.read(file, frame);
      CervusFileReader.separateDelimiters(fileModel, cervus); // MUST BE DONE!!!
      CervusParentageData parents = CervusParentageReader.importFile(fileModel, format);

      res[i] = parents.countErrors();
    }
    return res;
  }
}

