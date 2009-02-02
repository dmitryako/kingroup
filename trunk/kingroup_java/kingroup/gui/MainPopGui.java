package kingroup.gui;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.population.OldPop;

import javax.iox.DataSourceModel;
import javax.swing.*;
import javax.swingx.tablex.JTableFactory;
import java.awt.*;
public class MainPopGui extends JPanel {
  private JList popFile_;
  private Object[][] data = {
    {" ", "12"},
    {" ", "22"},
  };
  private String[] emptyFile = {
    "Load or generate Population using:"
    , "   Menu | Population | Import Kinship File ..."
    , "   Menu | Population | Generate ..."
  };
  private String[] emptyFreqs = {"Empty Allele Frequencies"};
  private String[] emptyGenotypes = {"Empty Population Data"};
  private DataSourceModel inputData_ = null;
  public DataSourceModel getInputFile() {
    return inputData_;
  }
  public MainPopGui(DataSourceModel file) {
    setLayout(new BorderLayout());
    inputData_ = file;
    popFile_ = new JList(emptyFile);
    if (inputData_ != null) {
      DataSourceModel dataView = new DataSourceModel(inputData_);
      dataView.replace('\t', "  Tab  ");
      popFile_.setModel(dataView.getDefaultListModel());
      popFile_.setToolTipText("Loaded input file: " + inputData_.getFileName());
    }
//    assembleWithFileView(null);
  }
  public MainPopGui() {
    setLayout(new BorderLayout());
    inputData_ = null;
    assemblePopData();
  }

//   public MainPopGui(MainPopGui from) {
//      setLayout(new BorderLayout());
//      inputData_ = from.inputData_;
//      assemblePopData();
//   }
  private void assemblePopData() {
    removeAll();
    JPanel popDataView = preparePopulationDataView();
    add(popDataView);
    invalidate();
  }
  private JPanel preparePopulationDataView() {
    JTable alleleFreq = new JTable(data, emptyFreqs);// view 2.1
    JTable popData = new JTable(data, emptyGenotypes);// view 2.2
    MainGui mainGui = MainGui.getInstance();
    OldPop pop = mainGui.getPopulation();
    if (pop != null) {
//      GenotypeGroupDataView popView = new GenotypeGroupDataView(pop);
//      popData.setModel(popView.getDefaultTableModel());
      JTableFactory.initColumnSizes(popData);
//      alleleFreq.setModel(popView.getAlleleFreqsModel());
      JTableFactory.initColumnSizes(alleleFreq);
    }
    JPanel popDataView = new JPanel(new BorderLayout());      // == view 2
    // ALLELE FREQUENCIES
    popDataView.removeAll();
    JScrollPane freqScrollPane = new JScrollPane(alleleFreq);
    JScrollPane dataScrollPane = new JScrollPane(popData);
    JSplitPane freqSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT
      , freqScrollPane, dataScrollPane);
    freqSplitPane.setOneTouchExpandable(true);
    freqSplitPane.setDividerLocation(150);
    popDataView.add(freqSplitPane, BorderLayout.CENTER);
    return popDataView;
  }
//  private void assembleWithFileView(KinshipFileFormatViewV1 formatView) {
//    JPanel fileView = new JPanel(new BorderLayout());
//    removeAll();
//    Dimension fileFormatDim = null;
//    fileView.removeAll();
//    JScrollPane listScrollPane = new JScrollPane(popFile_);
//    if (formatView == null) {
//      fileView.add(listScrollPane, BorderLayout.CENTER);
//    } else {
//      fileFormatDim = formatView.getPreferredSize();
//      JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT
//        , formatView, listScrollPane);
//      splitPane.setOneTouchExpandable(true);
//      splitPane.setResizeWeight(0);
//      formatView.setMinimumSize(formatView.getPreferredSize());
//      formatView.setMaximumSize(formatView.getMinimumSize());
//      fileView.add(splitPane, BorderLayout.CENTER);
//    }
//    JPanel popDataView = preparePopulationDataView();
//    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT
//      , fileView, popDataView);
//    splitPane.setOneTouchExpandable(true);
//    if (fileFormatDim == null)
//      splitPane.setDividerLocation(250);
//    else
//      splitPane.setDividerLocation(fileFormatDim.width
//        + splitPane.getDividerSize());
//    splitPane.resetToPreferredSizes(); // it doesn't work at this point?
//    add(splitPane);
//    invalidate();
//    repaint();
//  }
//  public void addFileFormatView(KinshipFileFormatViewV1 view) {
//    removeAll();
//    assembleWithFileView(view);
//    view.setFocusOnImport();
//  }
}