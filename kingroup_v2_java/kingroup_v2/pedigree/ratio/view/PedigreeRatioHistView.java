package kingroup_v2.pedigree.ratio.view;
import javax.swing.*;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/03/2006, Time: 17:14:38
 */
public class PedigreeRatioHistView    extends JPanel {
//  private double[] data = new double[]
//          {55.5, 34.2, 47.4, 53.1, 69.9, 68.7, 81.1};
//  private DataPlot dataPlot;
//  private GraphicsPlotCanvas plotCanvas;
//
//  public PedigreeRatioHistView(PedigreeRatioHist hist) {
//    init();
//    loadFrom(hist);
//    assemble();
//  }
//
//  private void loadFrom(PedigreeRatioHist hist)
//  {
//    plotCanvas = createPlotCanvas(hist);
//
//    dataPlot = new DataPlot();
//    dataPlot.addElement(new DataCurve(""));
//    plotCanvas.connect(dataPlot);
//  }
//
//  private void assemble()
//  {
//    add(plotCanvas.getGraphicsCanvas(), BorderLayout.CENTER);
//  }
//
//  public void init() {
//    setLayout(new BorderLayout());
//  }
//
//  private GraphicsPlotCanvas createPlotCanvas(PedigreeRatioHist hist) {
//    Properties props = new Properties();
//    ConfigParameters config
//      = new ConfigParameters(new PropertiesBasedConfigData(props));
//    props.put("plot/legendVisible", "false");
//    props.put("plot/coordinateSystem/xAxis/minimum", Double.toString(hist.getMinR()));
//    props.put("plot/coordinateSystem/xAxis/maximum", Double.toString(hist.getMaxR()));
//    props.put("plot/coordinateSystem/xAxis/axisLabel", "like");
//    props.put("plot/coordinateSystem/xAxis/ticLabelFormat/className",
//      "jcckit.plot.TicLabelMap");
//    props.put("plot/coordinateSystem/xAxis/ticLabelFormat/map",
//      "0=Mo;1=Tu;2=We;3=Th;4=Fr;5=Sa;6=Su");
//    props.put("plot/coordinateSystem/yAxis/axisLabel", "frequency");
//    props.put("plot/coordinateSystem/yAxis/maximum", "100");
//    props.put("plot/coordinateSystem/yAxis/ticLabelFormat", "%d%%");
//    props.put("plot/curveFactory/definitions", "curve");
//    props.put("plot/curveFactory/curve/withLine", "false");
//    props.put("plot/curveFactory/curve/symbolFactory/className",
//      "jcckit.plot.BarFactory");
//    props.put("plot/curveFactory/curve/symbolFactory/attributes/className",
//      "jcckit.graphic.ShapeAttributes");
//    props.put("plot/curveFactory/curve/symbolFactory/attributes/fillColor",
//      "0xfe8000");
//    props.put("plot/curveFactory/curve/symbolFactory/attributes/lineColor",
//      "0");
//    props.put("plot/curveFactory/curve/symbolFactory/size", "0.08");
//    props.put("plot/initialHintForNextCurve/className",
//      "jcckit.plot.PositionHint");
//    props.put("plot/initialHintForNextCurve/position", "0 0.1");
//
//    return new GraphicsPlotCanvas(config);
//  }
//
//  private void animate() {
//    DataCurve curve = new DataCurve("");
//    for (int i = 0; i < data.length; i++) {
//      curve.addElement(new DataPoint(i, 0));
//    }
//    dataPlot.replaceElementAt(0, curve);
//
//    for (int i = 0; i < data.length; i++) {
//      double x = i;
//      double y = 0;
//      while (y < data[i]) {
//        try {
//          Thread.sleep(50);
//        } catch (InterruptedException e) {}
//        y = Math.min(data[i], y + 5);
//        curve.replaceElementAt(i, new DataPoint(x, y));
//      }
//    }
//  }
}

