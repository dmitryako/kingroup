package kingroup_v2.ucm.import_sample;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.KinGroupV2Project;
import kingroup_v2.Kingroup;
import kingroup_v2.KingroupFrame;
import kingroup_v2.cervus.CervusFileFormat;
import kingroup_v2.cervus.CervusFileReader;
import kingroup_v2.cervus.view.CervusFileFormatView;
import kingroup_v2.io.FileIO;
import kingroup_v2.io.ImportPopOptions;
import kingroup_v2.kinship.KinshipFileFormat;
import kingroup_v2.kinship.KinshipFileReader;
import kingroup_v2.kinship.view.KinshipFileFormatView;
import kingroup_v2.pop.allele.freq.*;
import kingroup_v2.pop.sample.PopGroupView;
import kingroup_v2.pop.sample.PopView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.sys.SysPopFactory;
import kingroup_v2.pop.sample.sys.SysPopView;
import kingroup_v2.pop.sample.usr.UsrPopFactory;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import kingroup_v2.pop.sample.usr.UsrPopView;
import pattern.ucm.UCController;
import tsvlib.project.ProjectLogger;

import javax.iox.TextFile;
import javax.iox.TextFileView;
import javax.swing.*;
import javax.swingx.ApplyDialogUI;
import javax.swingx.UCShowImportFileUI;
import java.io.File;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/09/2005, Time: 16:57:33
 */
public class UCImportFile implements UCController {
  private static ProjectLogger log = ProjectLogger.getLogger(UCImportFile.class);
  public boolean run() {
    Kingroup bean = KinGroupV2Project.getInstance();
    String name = bean.getLastImportedFileName();
    File file = bean.makeFile(name);
    read(file);
    return true;
  }
  public void read(File file) {
    Kingroup project = KinGroupV2Project.getInstance();
    if (project.getFileType() == project.KINSHIP_FILE) {
      readKinshipFile(file, project);
    }
    else if (project.getFileType() == project.CERVUS_FILE) {
      readCervusFile(file, project);
    }
  }
  public void readKinshipFile(File file, Kingroup project) {
    JFrame frame = KingroupFrame.getInstance();
    if (frame != null && file != null) {
      frame.setTitle(project.getAppName() + " "
        + project.getAppVersion()
        + "  [KINSHIP input file: " + file.getName() + "]");
    }
    KinshipFileFormat format = project.getKinshipFileFormat();
    TextFile fileModel = new TextFile();
    fileModel.setFileName(file.getName());
    fileModel.read(file, frame);
//    KinshipFileReader.replaceTabDelims(fileModel, format); // MUST BE DONE!!!
    KinshipFileReader.separateDelimiters(fileModel, format); // MUST BE DONE!!!

    //    todo: can it be made expadable to max size?
    // CONFIGURE KINSHIP FORMAT
    TextFileView fileView = new TextFileView(fileModel);
    KinshipFileFormatView formatView = new KinshipFileFormatView(format);
    ApplyDialogUI dlg = new UCShowImportFileUI(FileIO.combine(formatView, fileView)
      , frame, "Import " + file.getName());
    dlg.setApplyBttnText("Import");
    dlg.setVisible(true);
    if (!dlg.apply())
      return;
    formatView.loadTo(format);
    project.saveProjectToDefaultLocation(); // remember user input

    // SHOW INPUT FILE
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    ui.resetAll();
    ui.setImportFileView(fileView);
    UsrPopSLOW usrPop = KinshipFileReader.importFile(fileModel, format);
    if (usrPop == null) {
      String error = "Unable to import.";
      log.severe(error);
      JOptionPane.showMessageDialog(frame, error);
      return;
    }
    commonFinish(ui, usrPop, format);
    setupGroups(ui, format);
  }

  private void setupGroups(KinGroupV2MainUI ui, KinshipFileFormat format) {
    if (!format.getHasGroupId())
      return;
    JFrame frame = KingroupFrame.getInstance();
    UsrPopSLOW usrPop = ui.getUsrPop();
    UsrPopSLOW[] usrGroups = UsrPopFactory.toGroupArray(usrPop);
    SysPop sysPop = ui.getSysPop();
    SysPop[] sysGroups = SysPopFactory.makeGroupsFrom(sysPop);
    if (usrGroups == null  || usrGroups.length == 0)   {
      String mssg = "Internal error occurred while trying to set up groups:\nusrGroups == null  || usrGroups.length == 0.";
      log.severe(mssg);
      JOptionPane.showMessageDialog(frame, mssg);
      return;
    }
    if (usrGroups.length == 1)   {
      String mssg = "Note: Column of group ids contains only one group.";
      log.severe(mssg);
      JOptionPane.showMessageDialog(frame, mssg);
      return;
    }
    if (usrGroups.length != sysGroups.length)   {
      String mssg = "Internal error. usrGroups.length != sysGroups.length";
      log.severe(mssg);
      JOptionPane.showMessageDialog(frame, mssg);
      return;
    }

    if (format.getFreqSource() == format.FREQ_SOURCE_BIAS)
    {
      UsrAlleleFreq freq = UsrAlleleFreqFactory.calcFrom(usrPop);
      for (int g = 0; g < usrGroups.length; g++) {
        UsrPopSLOW usrGroup = usrGroups[g];
        UsrAlleleFreq groupFreq = UsrAlleleFreqFactory.calcFrom(usrGroup);
        UsrAlleleFreq newFreq = UsrAlleleFreqFactory.subtract(freq, groupFreq);

        SysAlleleFreq sysFreq = SysAlleleFreqFactory.makeSysAlleleFreqFrom(newFreq);
        if (format.getFreqUserNorm())
          SysAlleleFreqFactory.copyToUserFreq(newFreq, sysFreq);

        usrGroup.setFreq(newFreq);
        sysGroups[g].setFreq(sysFreq);
      }
    }

    PopGroupView popGroupView = new PopGroupView();
    popGroupView.load(usrGroups, sysGroups, format);
    PopView popView = ui.getPopView();
    ui.setPopGroupView(popGroupView);
    popView.setPopFocus();
  }

  public void readCervusFile(File file, Kingroup project) {     //log.setDebug();
    JFrame frame = KingroupFrame.getInstance();
    if (frame != null && file != null) {
      frame.setTitle(project.getAppName() + " "
        + project.getAppVersion()
        + "  [CERVUS input file: " + file.getName() + "]");
    }
    CervusFileFormat format = project.getCervus().getFileFormat();
    TextFile fileModel = new TextFile();
    fileModel.setFileName(file.getName());
    fileModel.read(file, frame);                              log.debug("input file\n", fileModel);
    CervusFileReader.separateDelimiters(fileModel, format);   log.debug("separateDelimiters\n", fileModel);

    // CONFIGURE CERVUS FORMAT
    TextFileView fileView = new TextFileView(fileModel);
    CervusFileFormatView formatView = new CervusFileFormatView(format);
    ApplyDialogUI dlg = new UCShowImportFileUI(FileIO.combine(formatView, fileView)
      , frame, "Import " + file.getName());
    dlg.setApplyBttnText("Import");
    dlg.setVisible(true);
    if (!dlg.apply())
      return;
    formatView.loadTo(format);
    project.saveProjectToDefaultLocation(); // remember user input

    // SHOW INPUT FILE
    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    ui.resetAll();
    ui.setImportFileView(fileView);
    UsrPopSLOW usrPop = CervusFileReader.importFile(fileModel, format);   log.debug("usrPop\n", usrPop);
    if (usrPop == null) {
      String error = "Unable to import.";
      log.severe(error);
      JOptionPane.showMessageDialog(frame, error);
      return;
    }
    commonFinish(ui, usrPop, format);
  }
  private void commonFinish(KinGroupV2MainUI ui
                            , UsrPopSLOW usrPop
                            , ImportPopOptions format)
  {
    PopView popView = new PopView();
    ui.setPopView(popView);

    String delim = format.getColumnDelimStr() + " ";
    UsrAlleleFreq usrFreq = usrPop.getFreq();
    if (usrFreq != null) {
      SysAlleleFreq sysFreq = SysAlleleFreqFactory.makeSysAlleleFreqFrom(usrFreq);
      if (format.getFreqUserNorm())
        SysAlleleFreqFactory.copyToUserFreq(usrFreq, sysFreq);

      AlleleFreqView freqView = new AlleleFreqView();
      freqView.setUserAlleleFreqView(new UsrAlleleFreqView(usrFreq, delim));
      freqView.setSysAlleleFreqView(new SysAlleleFreqView(sysFreq));
      popView.setAlleleFreqView(freqView);
      freqView.setUsrFocus();
    }

    SysPop sysPop = SysPopFactory.makeSysPopFrom(usrPop);
    if (sysPop == null) {
      return;
    }

    SysPopView sysView = new SysPopView(sysPop);
    popView.setSysPopView(sysView);

    UsrPopView usrView = new UsrPopView(usrPop);
    popView.setUserPopView(usrView);

    popView.setPopFocus();
  }
}

