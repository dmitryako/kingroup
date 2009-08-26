package javax.utilx.log;

import javax.utilx.log.kiss.KissLog;
import java.util.HashMap;
import java.io.PrintStream;

/**
 * Created by Dmitry.A.Konovalov@gmail.com using IntelliJ, 20/08/2009, 10:02:48 AM
 */
public class Log extends KissLog {
  private static Log globalLog;
  private static HashMap<String, Log> map = new HashMap<String, Log>();
  public static Log getLog(Class c) {
    Log res = map.get(c.getName());
    if (res == null) {
      res = new Log(c);
      map.put(c.getName(), res);
    }
    return res;
  }
  private Log(Class cl) {
    super(cl);
  }
  public static Log getRootLog() {
    if (globalLog == null) globalLog = new Log(Log.class);
    return globalLog;
  }
  public static void setup() {
    getRootLog().add(System.out);
    getRootLog().add(System.err);
  }
  public static void addGlobal(PrintStream ps) {
    getRootLog().add(ps);
  }
}
// OLD way via java.uitl.log.Logger
