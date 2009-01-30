package tsvlib.project;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 17/04/2007, 10:39:18
 */
public class ProjectLogger
{
  private Logger logger;
  public static final int MAX_N_ROWS_TO_STRING = 10;
  public static final int MAX_LEN_TO_STRING = 100;
  public static final int MAX_N_COLS_TO_STRING = 20;
  private boolean debug;
  private boolean info;

  public static ProjectLogger getAnonymousLogger()
  {
    return new ProjectLogger(//Logger.getRootLogger()
    );
  }
  public static ProjectLogger getRootLogger()
  {
    return new ProjectLogger(//Logger.getRootLogger()
    );
  }
  public static ProjectLogger getLogger(String name) {
    return new ProjectLogger(name);
  }
  public static ProjectLogger getLogger(Class aClass) {
    return new ProjectLogger(aClass.getName());
  }
  public static void configure() {
//    BasicConfigurator.configure();
  }

  public ProjectLogger(String name) {
    if (name == null || name.length() == 0)
      logger = Logger.getAnonymousLogger();
//    logger = Logger.getRootLogger();
    else
      logger = Logger.getLogger(name);
  }
  public ProjectLogger() {
//    logger = Logger.getAnonymousLogger();
    logger = Logger.getLogger("");
  }

  final public void addAppender() {
//    logger.addAppender();  TODO: STOPPED HERE 070419
  }

  /**
   * @return old level
   */
  final public Level setAll() {
    Level oldLevel =  logger.getLevel();
    logger.setLevel(Level.ALL);
    return oldLevel;
  }

  final public void setTrace() {
    logger.setLevel(Level.FINEST);
  }
  final public void setDebug() {
    setDebug(true);
  }
  final public void setInfo() {
    logger.setLevel(Level.INFO);  }
  final public void setOff() {
    logger.setLevel(Level.OFF);  }

  final public void setThresholdAll() {   //logger.getLoggerRepository().setThreshold(Level.ALL);
  }
  final public void setThresholdTrace() { //logger.getLoggerRepository().setThreshold(Level.TRACE);
  }
  final public void setThresholdDebug() { //logger.getLoggerRepository().setThreshold(Level.DEBUG);
  }
  final public void setThresholdInfo() {  //logger.getLoggerRepository().setThreshold(Level.INFO);
  }
  final public void setThresholdOff() {   //logger.getLoggerRepository().setThreshold(Level.OFF);
  }

  final public void info(String m) {
    logger.info(m);
  }
  final public void info(Object m, Object m2) {
    if (hasInfo()) info("" + m + m2);
  }
  final public void info(Object m, Object m2, Object m3) {
    if (hasInfo()) info("" + m + m2 + m3);
  }
  final public void info(Object m, Object m2, Object m3, Object m4) {
    if (hasInfo()) info("" + m + m2 + m3 + m4);
  }

  final public void debug(String m) {
//    logger.fine(m);
    System.out.println(m);
  }
  final public void debug(Object m, Object m2) {
    if (!hasDebug())
      return;
    if (m2 == null)  {
      debug(m.toString() + "null");
    }
    else {
      debug(m.toString() + m2.toString());
    }
  }
  final public void debug(Object m, boolean v) {
    if (hasDebug()) debug("" + m + v);
  }
  final public void debug(Object m, int v) {
    if (hasDebug()) debug("" + m + v);
  }
  final public void debug(Object m, double v) {
    if (hasDebug()) {
      debug("" + m + v);
    }
  }
  final public void debug(Object m, Object m2, Object m3) {
    if (hasDebug()) debug("" + m + m2 + m3);
  }
  final public void debug(Object m, Object m2, Object m3, Object m4) {
    if (hasDebug()) debug("" + m + m2 + m3 + m4);
  }

  final public void trace(String m) {    logger.finest(m);
  }
  final public void trace(Object m, Object m2) {
//    System.out.println(mssg);
    //if (logger.isTraceEnabled()) logger.trace("" + m + m2);
  }
  final public void trace(Object m, Object m2, Object m3) {
    //if (logger.isTraceEnabled()) logger.trace("" + m + m2 + m3);
  }
  final public void trace(Object m, Object m2, Object m3, Object m4) {
    //if (logger.isTraceEnabled()) logger.trace("" + m + m2 + m3 + m4);
  }

  final public void error(Object m, Throwable e) {    //logger.error(m, e);
  }
//  final public void error(Throwable e) {    logger.error(e);  }

  public void start(String appName) {
    String mssg = " starting " + appName + " ...";
    System.out.println(mssg);
    ProjectLogger.configure();

//    Logger.getRootLogger().setLevel(Level.INFO);
//    Logger.getRootLogger().setLevel(Level.OFF);
//    logger.setLevel(Level.OFF);

    logger.info(mssg);
  }

  public void severe(Object mssg) {
//    logger.error(mssg);
  }

//  private ProjectLogger() {
//    lineStream = new LineStream();
//    lineStreamView = new LineStreamView(lineStream);
//
//    log.debug("starting ProjectLogger");
//
//
////    Logger.getRootLogger();
////
//////    outputStream = new FixedSizeStringStream();
////
////    //http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html
////    StreamHandler sh = new StreamHandler(lineStreamView, new SimpleFormatter());
//////    sh.setLevel(Level.FINEST);
////    sh.setLevel(Level.INFO);
////    Logger.getLogger("").addHandler(sh);  //javax.swingx
////    Logger.getLogger("").setLevel(Level.INFO);
////
////    Logger.getLogger(LineStreamView.class.getName()).setLevel(Level.INFO);
////    Logger.getLogger(LineStreamView.class.getName()).addHandler(sh);
//////    Logger.getLogger("javax.swingx").addHandler(sh);  //
////
//////    Logger.getLogger("").setLevel(Level.FINEST);
////
//////    LogManager.getLogManager().setLevel("", );
////
//////    Handler fh = new FileHandler("%t/wombat.log");
//////    Logger.getLogger("").addHandler(fh);
//////    Logger.getLogger("com.wombat").setLevel("com.wombat", Level.FINEST);
//
//  }

//  public OutputStream getLineStream()
//  {
//    return lineStream;
//  }
//  public LineStreamView getLineStreamView()
//  {
//    return lineStreamView;
//  }

//  public void setLevel(Level level)
//  {
//    logger.setLevel(level);
//  }

  public void setLevel(Level level)
  {
    logger.setLevel(level);
  }

  public boolean hasDebug()
  {
    return debug;
  }

  public boolean hasInfo()
  {
    return info;
  }

  public void setDebug(boolean b)
  {
    this.debug = b;
    if (b) {
      logger.setLevel(Level.FINE);
      logger.info("debug=true, logger.getName()=" + logger.getName());
    }
  }
  public void setInfo(boolean b)
  {
    this.info = b;
    if (b) {
      logger.setLevel(Level.INFO);
      logger.info("info=true, logger.getName()=" + logger.getName());
    }
  }
}
