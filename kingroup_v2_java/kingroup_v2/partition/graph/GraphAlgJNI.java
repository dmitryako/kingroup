package kingroup_v2.partition.graph;
import javax.iox.LOG;
import java.io.File;
import java.io.IOException;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 21/07/2005, Time: 11:12:53
 * <p/>
 * Build this java project first.
 * Run GraphAlgJNI.bat to create GraphAlgJNI.h
 * Copy GraphAlgJNI.h (e.g. by running GraphAlgJNI_copy.bat) to c++ project area
 */
public class GraphAlgJNI {
  static {
//      System.loadLibrary("family_finder_vc7");
    File file = new File(".");
    try {
      String name = file.getCanonicalPath();
      name += File.separator + "family_finder_vc7.dll";
      LOG.report(null, "file name=" + name);
//          System.load("/Users/david/IdeaProjects/kingroup_050707/libfamilyFinder.dynlib");
//          System.load("C:\\Documents and Settings\\jc152844\\Desktop\\kingroup\\kingroup_050707\\ff_DB.dll");
      System.load(name);
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  }
  public native void partitionTestHelloWorld();
  public native int[] partitionTestIntArray(int[] array);
  public native void partition(int[][] array);
}
