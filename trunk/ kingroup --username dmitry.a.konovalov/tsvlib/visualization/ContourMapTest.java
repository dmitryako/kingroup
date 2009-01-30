package tsvlib.visualization;

import junit.framework.TestCase;
import algorithm.Func2DI;

import java.awt.*;

/**
 * Copyright KinGroup Team.
 * User: eng-nbb, Date: 10/05/2006, Time: 16:16:14
 */

/**
 * <code>ContourMapTest</code>
 * <p/>
 * Date: 10/05/2006
 * Time: 16:16:14
 *
 * @author Nigel Blair
 */
public class ContourMapTest extends TestCase {
  public void testContourMap() {
    Func2DI f = new Func2DI() {
      public double calc(double x, double y) {
        return Math.sin(x)+Math.sin(y);
      }
    };
    int size = 100;

    double[][] data = new double[size][size];
    for(int i = 0; i < size; i++)
    {
      for(int j = 0; j < size; j++)
      {
        data[i][j] = f.calc(-1d+(((double)i*2)/size),-1d+(((double)j*2)/size));
      }
    }
    Contour[] s = new ContourMap(5).constructContour(data,-1,-1,1,1);
    System.out.println(s.length);
  }
}
