package javax.awtx;
/**
 * Copyright (C) 2004  Dr. Dmitry Konovalov.
 * This code is licensed under the GPL license (see www.gnu.org) for academic,
 * not-for-profit use or for use within other Open Source software (see www.opensource.org).
 * See www.kingroup.org for more details.
 * User: dmitry
 * Date: May 10, 2004, Time: 3:12:52 PM
 */
public class GraphFactory {
  /**
   * Makes a grid with equal steps: equally spaced
   */
  public static double[] makeGrid(double first, double last, int size) {
    if (size <= 0)
      return null;
    double[] g = new double[size];
    g[0] = first;
    if (size == 1)
      return g;
    double step = (last - first) / (size - 1);
    for (int i = 1; i < size; i++)
      g[i] = first + i * step;
    return g;
  }
  /**
   * y(x) = a*x + b
   */
  public static FuncPlot make_AX_B(double a, double b, int size) {
    if (size <= 0)
      return null;
    double[] y = new double[size];
    for (int x = 0; x < y.length; x++)
      y[x] = a * x + b;
    FuncPlot graph = new FuncPlot(y, 0, y.length - 1);
    return graph;
  }
  /**
   * y(x) = a*x*x + b*x + c
   */
  public static FuncPlot make_AX2_BX_C(double a, double b, double c, double[] x) {
    if (x.length <= 0)
      return null;
    double[] y = new double[x.length];
    for (int i = 0; i < y.length; i++)
      y[i] = a * x[i] * x[i] + b * x[i] + c;
    FuncPlot graph = new FuncPlot(x, y);
    return graph;
  }
}
