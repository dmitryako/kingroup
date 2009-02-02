package kingroup_v2.kinship;
import kingroup_v2.KinGroupV2MainUI;
import kingroup_v2.Kingroup;
import kingroup_v2.cervus.view.CervusAlleleAnalysisView;
import kingroup_v2.cervus.view.CervusMainUI;
import kingroup_v2.fsr.FsrAlgUI;
import kingroup_v2.fsr.FsrAlgView;
import kingroup_v2.io.ImportPopOptions;
import kingroup_v2.kinship.like.view.KinshipRatioOptView;
import kingroup_v2.kinship.like.view.KinshipRatioSimView;
import kingroup_v2.kinship.like.view.KinshipRatioView;
import kingroup_v2.kinship.view.*;
import kingroup_v2.like.thompson.Thompson;
import kingroup_v2.like.thompson.view.ThompsonIBDView;
import kingroup_v2.like.thompson.view.ThompsonRatioOptView;
import kingroup_v2.like.thompson.view.ThompsonRatioView;
import kingroup_v2.pedigree.ratio.view.PedigreeRatioOptView;
import kingroup_v2.pedigree.ratio.view.PedigreeRatioView;
import kingroup_v2.pop.allele.freq.KonHegFreqAlg;
import kingroup_v2.pop.allele.freq.UsrAlleleFreqView;
import kingroup_v2.pop.sample.sys.SysPop;
import kingroup_v2.pop.sample.usr.UsrPopSLOW;
import kingroup_v2.pop.sample.usr.UsrPopView;
import tsvlib.project.ProjectLogger;

import javax.iox.FileX;
import javax.iox.TextFile;
import javax.swing.*;
import javax.swingx.tablex.TableViewI;
import javax.swingx.tablex.TableWithApplyUI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 30/09/2005, Time: 16:09:49
 */
public class KinshipFileWriter {
  private static ProjectLogger log = ProjectLogger.getLogger(KinshipFileWriter.class.getName());
  private static final boolean AS_COMMENT = true;
  private static final boolean WITH_HEADER = true;

  public static void write(TextFile to, Kingroup project)
  {
    KinshipFileFormat format = project.getKinshipFileFormat();
    KinshipFileWriter.writeFileHeader(to, project);

    KinGroupV2MainUI ui = KinGroupV2MainUI.getInstance();
    KinshipFileWriter.comment(to, "Population", format);
    UsrAlleleFreqView usrFreqView = ui.getUsrAlleleFreqView();
    KinshipFileWriter.writeTable(to, "ALLELE FREQUENCY BLOCK"
      , usrFreqView, format, WITH_HEADER, !AS_COMMENT); // FREQ
    if (usrFreqView != null)
      to.addLine(format.getFreqBlockEndMarker());

    KinshipFileWriter.writeTable(to, "INDIVIDUAL GENOTYPES BLOCK"
      , ui.getUsrPopView(), format, WITH_HEADER, !AS_COMMENT);  // SAMPLE

    SysPop[] sysGroups = ui.getSysGroups();
    if (sysGroups != null  &&  sysGroups.length > 1 && format.getFreqSource() == ImportPopOptions.FREQ_SOURCE_BIAS)
    {
      String delim = format.getColumnDelimStr() + " ";
      UsrPopSLOW[] usrGroups = ui.getUsrGroups();
      for (int i = 0; i < usrGroups.length; i++) {
        UsrPopSLOW usrPop = usrGroups[i];
        UsrPopView popView = new UsrPopView(usrPop);
        usrFreqView = new UsrAlleleFreqView(usrPop.getFreq(), delim);

        KinshipFileWriter.comment(to, " ", format);
        KinshipFileWriter.comment(to
          , "Group #" + (i+1) + ": " + usrPop.getName(), format);
        KinshipFileWriter.writeTable(to, "Group allele frequencies"
          , usrFreqView, format, WITH_HEADER, AS_COMMENT); // FREQ
        if (usrFreqView != null)
          comment(to, format.getFreqBlockEndMarker(), format);
        KinshipFileWriter.writeTable(to, "Group genotypes"
          , popView, format, WITH_HEADER, AS_COMMENT);
      }
    }

    KinshipFileWriter.writeCERVUSView(to, "ALLELE ANALYSIS [as per CERVUS]"
      , ui.getCervusMainUI(), format);
    KinshipFileWriter.writePairwiseRView(to, ui.getPairwiseRView(), format);
    KinshipFileWriter.commentLikeView(to, "PRIMARY HYPOTHESIS LIKELIHOODS"
      , ui.getKinshipPrimView(), format); // PRIMARY
    KinshipFileWriter.commentLikeView(to, "NULL HYPOTHESIS LIKELIHOODS"
      , ui.getKinshipNullView(), format); // NULL
    KinshipFileWriter.commentRatioView(to, "KINSHIP PRIMARY/NULL RATIO"
      , ui.getKinshipRatioView(), format);
    KinshipFileWriter.commentRatioView(to, "KINSHIP PRIMARY/NULL RATIO: Likelihoods from " + Kinship.REFERENCE
      , ui.getPedigreeRatioView(), format);
    KinshipFileWriter.commentRatioView(to, "k-coefficents PRIMARY/NULL RATIO: Likelihoods from " + Thompson.REFERENCE
      , ui.getThompsonRatioView(), format);
    KinshipFileWriter.commentTableView(to, KonHegFreqAlg.REFERENCE
      , ui.getKonHegFreqView(), format);
    KinshipFileWriter.commentFSRPartitionView(to, ui.getFsrView(), format);
  }

  private static void writePairwiseRView(TextFile to, PairwiseRView view, KinshipFileFormat format)
  {
    if (view == null)
      return;
    KinshipFileWriter.writeTable(to, view.getReference()
      , view, format, !WITH_HEADER, AS_COMMENT);
  }
  private static void writeCERVUSView(TextFile to, String title, CervusMainUI ui, KinshipFileFormat format)
  {
    if (ui == null)
      return;
    CervusAlleleAnalysisView view = ui.getAlleleAnalysisView();
    if (view == null)
      return;
    JTable table = view.getTableView();
    if (table == null)
      return;
    comment(to, " ", format);
    KinshipFileWriter.writeTable(to, title, view, format, WITH_HEADER, AS_COMMENT);
  }

  private static void writeFileHeader(TextFile to, Kingroup project)
  {
    KinshipFileFormat format = project.getKinshipFileFormat();
    SimpleDateFormat date = new SimpleDateFormat();
    try {
      date.applyPattern("HH:mm  EEE MMM d yyyy");
    }
    catch (IllegalArgumentException e) {
    }
    KinshipFileWriter.comment(to, "This is the results file [in KINSHIP format] of " + project.getAppName() + " " + project.getAppVersion(), format);
    KinshipFileWriter.comment(to, "Saved at " + date.format(new Date()), format);
    KinshipFileWriter.comment(to, "Column delimiter: " + format.getUserColumnDelimName(), format);
    int SHOW_LEVELS = 3;
    String line = "Last saved file: " + FileX.getShortFileName(project.getLastSavedFileName(), SHOW_LEVELS);
    KinshipFileWriter.comment(to, line, format);
    line = "Last imported file: " + FileX.getShortFileName(project.getLastImportedFileName(), SHOW_LEVELS);
    KinshipFileWriter.comment(to, line, format);
  }

  private static void commentFSRPartitionView(TextFile to, FsrAlgUI fsrView, KinshipFileFormat format)
  {
    if (fsrView == null)
      return;
    comment(to, " ", format);
    comment(to, "TAB: FSR - Full Sibship Reconstruction", format);
    comment(to, "MENU: FSR -> Partition", format);
    FsrAlgView view = fsrView.getDRView();
    writeTable(to, "DR algorithm", view, format, WITH_HEADER, AS_COMMENT);
    view = fsrView.getSDRView();
    writeTable(to, "SDR algorithm", view, format, WITH_HEADER, AS_COMMENT);
    view = fsrView.getSIMPSView();
    writeTable(to, "SIMPSON algorithm", view, format, WITH_HEADER, AS_COMMENT);
//    view = fsrView.getDRView();
//    writeTable(to, "DR algorithm", view, format, WITH_HEADER, AS_COMMENT);
//    view = fsrView.getDRView();
//    writeTable(to, "DR algorithm", view, format, WITH_HEADER, AS_COMMENT);
  }
  private static void writeTable(TextFile to
    , String title , TableViewI view , KinshipFileFormat format
    , boolean withHeader , boolean asComment
  )
  {
    if (view == null)
      return;
    JTable table = view.getTableView();
    if (table == null)
      return;
    comment(to, " ", format);
    comment(to, title, format);
    write(to, table, format, withHeader, asComment);
  }

  public static void commentLikeView(TextFile to, String title
    , KinshipLikeView view, KinshipFileFormat format) {
    if (view == null)
      return;
    JTable table = view.getTableView();
    if (table == null)
      return;
    comment(to, " ", format);
    if (view.getOptionsView().getDisplayLog())
      comment(to, title + " [base 10 log]", format);
    else
      comment(to, title, format);
    KinshipLikeOptView opt = view.getOptionsView();
    comment(to, opt.getIBDView(), format);
    write(to, table, format, !WITH_HEADER, AS_COMMENT);
  }
  public static void commentTableView(TextFile to, String title
    , TableWithApplyUI view, KinshipFileFormat format) {
    if (view == null)
      return;
    JTable table = view.getTableView();
    if (table == null)
      return;
    comment(to, title, format);
    write(to, table, format, !WITH_HEADER, AS_COMMENT);
  }
  public static void commentRatioView(TextFile to, String title
    , KinshipRatioView view
    , KinshipFileFormat format) {
    if (view == null)
      return;
    JTable table = view.getTableView();
    if (table == null)
      return;
    comment(to, " ", format);
    if (view.getOptView().getDisplayLog())
      comment(to, title + " [base 10 log]", format);
    else
      comment(to, title, format);
    KinshipRatioOptView opt = view.getOptView();
    comment(to, "Primary", format);
    comment(to, opt.getPrimIBDView(), format);
    comment(to, "Null", format);
    comment(to, opt.getNullIBDView(), format);
    comment(to, opt.getRatioSimView(), format);
    write(to, table, format, !WITH_HEADER, AS_COMMENT);
  }
  public static void commentRatioView(TextFile to, String title
    , PedigreeRatioView view
    , KinshipFileFormat format) {
    if (view == null)
      return;
    JTable table = view.getTableView();
    if (table == null)
      return;
    comment(to, " ", format);
    if (view.getOptView().getDisplayLog())
      comment(to, title + " [base 10 log]", format);
    else
      comment(to, title, format);
    PedigreeRatioOptView opt = view.getOptView();
    comment(to, "Primary hypothesis", format);
    comment(to, opt.getPrimIBDView(), format);
    writeTable(to, "Null hypothesis", opt.getNullIBDView(), format, true, true);
    comment(to, opt.getRatioSimView(), format);
    write(to, table, format, !WITH_HEADER, AS_COMMENT);
  }
  public static void commentRatioView(TextFile to, String title
    , ThompsonRatioView view
    , KinshipFileFormat format) {
    if (view == null)
      return;
    JTable table = view.getTableView();
    if (table == null)
      return;
    comment(to, " ", format);
    if (view.getOptView().getDisplayLog())
      comment(to, title + " [base 10 log]", format);
    else
      comment(to, title, format);
    ThompsonRatioOptView opt = view.getOptView();
    comment(to, "Primary hypothesis", format);
    comment(to, opt.getPrimIBDView(), format);
    writeTable(to, "Null hypothesis", opt.getNullIBDView(), format, true, true);
    comment(to, opt.getRatioSimView(), format);
    write(to, table, format, !WITH_HEADER, AS_COMMENT);
  }
  private static void comment(TextFile to, KinshipRatioSimView view, KinshipFileFormat format) {
    int nPairs = view.getNumPairs();
    comment(to, "" + nPairs + " simulated pairs used to perform significance test.", format);
    write(to, view.getTableView().getTable(), format, WITH_HEADER, AS_COMMENT);
    if (view.getDisplayPValue())
      comment(to, "p-values [%]", format);
  }
  private static void comment(TextFile to
    , KinshipIBDComplexView ibd, KinshipFileFormat format) {
    String DELIM = format.getUserColumnDelim();
    StringBuffer buff = new StringBuffer();
    buff.append(format.getCommentLineMarker()).append(DELIM);
//    KinshipIBDComplexView ibd = opt.getIBDView();
    if (ibd.isComplex()) {
      buff.append("Complex hypothesis");
      to.addLine(buff.toString());
      buff.setLength(0);
      buff.append(format.getCommentLineMarker()).append(DELIM);
      buff.append("Rm from ").append(ibd.getRmView());
      buff.append(" to ").append(ibd.getRmsView());
      buff.append(" with ").append(ibd.getNumRmsView());
      buff.append(" search intervals.");
      to.addLine(buff.toString());
      buff.setLength(0);
      buff.append(format.getCommentLineMarker()).append(DELIM);
      buff.append("Rp from ").append(ibd.getRpView());
      buff.append(" to ").append(ibd.getRpsView());
      buff.append(" with ").append(ibd.getNumRpsView());
      buff.append(" search intervals.");
      to.addLine(buff.toString());
    } else {
      buff.append("Rm = ").append(ibd.getRmView());
      buff.append("  Rp = ").append(ibd.getRpView());
      to.addLine(buff.toString());
    }
  }
  private static void comment(TextFile to
    , KinshipIBDView ibd, KinshipFileFormat format) {
    String DELIM = format.getUserColumnDelim();
    StringBuffer buff = new StringBuffer();
    buff.append(format.getCommentLineMarker()).append(DELIM);
    buff.append("Rm = ").append(ibd.getRmView());
    buff.append("  Rp = ").append(ibd.getRpView());
    to.addLine(buff.toString());
  }
  private static void comment(TextFile to
    , ThompsonIBDView ibd, KinshipFileFormat format) {
    String DELIM = format.getUserColumnDelim();
    StringBuffer buff = new StringBuffer();
    buff.append(format.getCommentLineMarker()).append(DELIM);
    buff.append("k = (k0 k1 k2) = (").append(ibd.getView(0));
    buff.append(" ").append(ibd.getView(1));
    buff.append(" ").append(ibd.getView(2));
    buff.append(")");
    to.addLine(buff.toString());
  }
  public static void write(TextFile to
    , JTable table, KinshipFileFormat format
    , boolean header, boolean asComment) {
    if (table == null)
      return;
    String DELIM = format.getUserColumnDelim();
    StringBuffer buff = new StringBuffer();
    if (header) {
      buff.setLength(0);
      if (asComment)
        buff.append(format.getCommentLineMarker()).append(DELIM);
      for (int c = 0; c < table.getColumnCount(); c++) {
        buff.append(table.getColumnName(c));
        if (c != table.getColumnCount() - 1)
          buff.append(DELIM);
      }
      to.addLine(buff.toString());
    }
    for (int r = 0; r < table.getRowCount(); r++) {
      buff.setLength(0);
      if (asComment)
        buff.append(format.getCommentLineMarker()).append(DELIM);
      for (int c = 0; c < table.getColumnCount(); c++) {
        String cell = (String) table.getValueAt(r, c);
        buff.append(cell);
        if (c != table.getColumnCount() - 1)
          buff.append(DELIM);
      }
      to.addLine(buff.toString());
    }
  }
  public static void comment(TextFile to, String txt
    , KinshipFileFormat format) {
    String DELIM = format.getUserColumnDelim();
    StringBuffer buff = new StringBuffer();
    buff.append(format.getCommentLineMarker());
    buff.append(DELIM).append(txt);
    to.addLine(buff.toString());
  }
}
