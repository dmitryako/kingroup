package qsar.bench.ucm;
import qsar.bench.QBench;
import qsar.bench.QBenchFrame;
import qsar.bench.QBenchProject;
import kingroup_v2.ucm.import_sample.SelectImportFormatView;

import javax.swingx.ApplyDialogUI;

/**
 * Copyright www.DmitryKonovalov.org
 * User: jc138691, Date: 6/03/2007, Time: 11:54:29
 */
public class UCSelectTableFormat
{
  public static boolean select() {
    QBench bean = QBenchProject.getInstance();
//    SelectImportFormatView panel = new SelectImportFormatView(bean);
    SelectImportFormatView panel = null;
    ApplyDialogUI dlg = new ApplyDialogUI(panel, QBenchFrame.getInstance(), true);
    dlg.setTitle("File");
    dlg.center();
    dlg.setFocusOnApply();
    dlg.setApplyBttnText("Ok");
    dlg.setVisible(true);
    if (!dlg.apply()) {
      return false;
    }
//    panel.loadTo(bean);
    bean.saveProjectToDefaultLocation();
    return true;
  }
}
