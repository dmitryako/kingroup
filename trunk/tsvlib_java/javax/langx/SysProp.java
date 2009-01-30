package javax.langx;
/** JKinship    Copyright (C) 2003  Dr.D.A.Konovalov
 */
import javax.langx.IOx;
import javax.swing.*;
import java.util.Properties;
public class SysProp {
  public static final String EOL = System.getProperty("line.separator");

  public static boolean assertJRE_1_5_PLUS(String caller)
  {
    System.out.println(caller + " [       Trying to getLine system properties ...]\n");
    try {
      Properties all = System.getProperties();
      System.out.println(caller + " [START: ALL SYSTEM PROPERTIES]\n");
      all.list(System.out);
      System.out.println(caller + " [FINISH: ALL SYSTEM PROPERTIES]\n");
      String errorMsg
        = "Java Runtime Environment (JRE) 1.5 or higher is required in order to run "
        + caller + ".\n"
        + "Below are your system properties:\n"
        + getSystemProperties() + EOL;
      System.out.println(caller + " [START: SYSTEM SUMMARY]\n");
      System.out.println(errorMsg);
      System.out.println(caller + " [FINISH: SYSTEM SUMMARY]\n");
      JOptionPane.showMessageDialog(null, errorMsg
        , "Java Runtime Environment (JRE)"
        , JOptionPane.ERROR_MESSAGE);
    }
    catch (Throwable ignore) {
      System.out.println(caller
        + " [ERROR occurred while trying to display system properties]\n");
    }
    System.out.println(caller + " [Shutting down ...]\n");
    System.exit(1);
    return false;
  }
  public static String getSystemProperties() {
    String[] keys = {
      "java.version"
      , "java.home"
      , "java.specification.version"
      , "java.vm.version"
      , "java.runtime.version"
//      , "java.class.path"
//      , "sun.boot.library.path"
//      , "sun.boot.class.path"
      , "os.name"
      , "user.dir"
      , "user.name"
    };
    String res = "";
    int MAX_LENGTH = 40;
    try {
      Properties all = System.getProperties();
      for (int i = 0; i < keys.length; i++) {
        String prop = all.getProperty(keys[i]);
        if (prop != null)
        {
          if (prop.length() >= MAX_LENGTH)
            prop = prop.substring(0, MAX_LENGTH);
          res += (EOL + keys[i] + " = [" + prop + "]");
        }
      }
    }
    catch (SecurityException e) {
      res = "Unable to get system properties due to security restrictions\n" + e;
    }
    return res;
  }
}
