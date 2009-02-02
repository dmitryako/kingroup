package kingroup_v2.partition.distance.AlmudevarField1999;
import kingroup.partition.bitsets.Partition;
import kingroup.partition.bitsets.PartitionFactory;
import kingroup.partition.bitsets.RandomGroupSizeFactory;

import javax.swing.*;
import javax.swingx.ApplyDialogUI;
import javax.swingx.panelx.GridBagView;
import javax.utilx.arrays.vec.Vec;
import java.util.ArrayList;

/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Oct 15, 2004, Time: 2:06:53 PM
 */
public class TestBitSetPartitionDistanceView extends JFrame {
  public static void main(String[] args) {
    final int POP_SIZE = 200; // population size == universe
    final int SIM_SIZE = 1000;
    TestBitSetPartitionDistanceView test = new TestBitSetPartitionDistanceView();
    GridBagView panel = new GridBagView("TestBitSetPartitionFactoryView");
    PartitionFactory gen = new RandomGroupSizeFactory(POP_SIZE);
    JPanel groupsBySize = test.runSimulation(gen, SIM_SIZE, "AlmudevarFieldDistance");
    panel.startRow(groupsBySize);

    //gen = new RandomGroupNumFactory(POP_SIZE);
    //JPanel groupsByNum = test.runSimulation(gen, SIM_SIZE, "RandomGroupNum");
    //panel.endRow(groupsByNum);
    ApplyDialogUI dlg = new ApplyDialogUI(panel, test, true);
    dlg.setVisible(true);
    System.exit(0);
  }
  private JPanel runSimulation(PartitionFactory gen
    , int simSize
    , String title) {
    //javax.iox.LOG.setTrace(true);
    AlmudevarFieldDistance dist = new AlmudevarFieldDistance();
    ArrayList arr = new ArrayList();
    long time;
    for (int i = 0; i < simSize; i++) {
      Partition partA = gen.makeRandom();
      Partition partB = gen.makeRandom();
//         javax.iox.LOG.trace(this, "partA=", partA.toString());
//         javax.iox.LOG.trace(this, "partB=", partB.toString());
      time = System.currentTimeMillis();
      int v = dist.distance(partA, partB);
      dist.distance(partA, partB);
      dist.distance(partA, partB);
      dist.distance(partA, partB);
      dist.distance(partA, partB);
      dist.distance(partA, partB);
      dist.distance(partA, partB);
      dist.distance(partA, partB);
      dist.distance(partA, partB);
      dist.distance(partA, partB);
      time = System.currentTimeMillis() - time;
      arr.add(new Double(time));
    }
    double[] vals = Vec.asArray(arr);
//    HistogramInputData data = new HistogramInputData(vals);
//    data.setPreferredViewSize(new Dimension(300, 400));
//      return new HistogramView(title, data);
    return null;
  }
  public TestBitSetPartitionDistanceView() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}

