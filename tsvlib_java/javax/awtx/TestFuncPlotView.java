package javax.awtx;
import javax.swing.*;
import javax.swingx.ApplyDialogUI;
import javax.swingx.panelx.GridBagView;
import java.awt.*;
import java.util.Random;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 10, 2004, Time: 1:17:48 PM
 */
public class TestFuncPlotView extends JFrame {
  public static void main(String[] args) {
    TestFuncPlotView test = new TestFuncPlotView();
    JPanel panel;
    //panel = test.showTestMinSize(10, 1);
    //panel = test.showTestTwoGraphs(100, 1);
    //panel = test.showTestThreeGraphs(100);
    panel = test.showAll();
    ApplyDialogUI dlg = new ApplyDialogUI(panel, test, true);
    javax.iox.LOG.setTrace(true);
    dlg.setVisible(true);
    System.exit(0);
  }
  public TestFuncPlotView() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  /**
   * Example for (4, 1.)
   * // 1234
   * // 1-34
   * // 12-4
   * // 1234
   */
  public JPanel showTestMinSize(int minSize, float a) {
    JPanel panel = new JPanel();
    FuncPlotView view = new FuncPlotView(GraphFactory.make_AX_B(a, 0, minSize));
    view.setMinSizeLen(minSize);
    panel.add(view);
    return panel;
  }
  public JPanel showTestTwoGraphs(int minSize, float a) {
    JPanel panel = new JPanel();
    FuncPlot graph = GraphFactory.make_AX_B(a, 0, minSize);
    graph.addGraph(GraphFactory.make_AX_B(-a, 0, minSize));
    FuncPlotView view = new FuncPlotView(graph);
    view.setMinSizeLen(minSize);
    panel.add(view);
    return panel;
  }
  public JPanel showTestThreeGraphs(int minSize) {
    JPanel panel = new JPanel();
    double[] x = GraphFactory.makeGrid(-1, 1, 100);
    Random rand = new Random(0);
    FuncPlot graph = GraphFactory.make_AX2_BX_C(2 * rand.nextFloat() - 1
      , 2 * rand.nextFloat() - 1
      , 2 * rand.nextFloat() - 1, x);
    graph.addGraph(GraphFactory.make_AX2_BX_C(2 * rand.nextFloat() - 1
      , 2 * rand.nextFloat() - 1
      , 2 * rand.nextFloat() - 1, x));
    graph.addGraph(GraphFactory.make_AX2_BX_C(2 * rand.nextFloat() - 1
      , 2 * rand.nextFloat() - 1
      , 2 * rand.nextFloat() - 1, x));
    FuncPlotView view = new FuncPlotView(graph);
    view.setShowX(true);
    view.setShowY(true);
    view.setMinSizeLen(minSize);
    panel.add(view);
    return panel;
  }
  public JPanel showAll() {
    //JPanel panel = new GridBagView();
    GridBagView gridbag = new GridBagView();
    gridbag.getStartRow().fill = GridBagConstraints.NONE;
    gridbag.getEndRow().fill = GridBagConstraints.NONE;
    gridbag.startRow(showTestMinSize(4, 1));
    gridbag.startRow(showTestMinSize(5, 1));
    gridbag.endRow(showTestMinSize(6, 1));
    gridbag.startRow(showTestMinSize(4, -1));
    gridbag.startRow(showTestMinSize(5, -1));
    gridbag.endRow(showTestMinSize(6, -1));
    gridbag.startRow(showTestMinSize(100, 1));
    gridbag.endRow(showTestMinSize(100, -1));
    gridbag.startRow(showTestTwoGraphs(100, -1));
    gridbag.endRow(showTestThreeGraphs(100));
    return gridbag;
  }
}
