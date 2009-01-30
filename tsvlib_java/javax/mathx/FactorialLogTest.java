package javax.mathx;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 15/05/2005, Time: 12:53:56
 */
public class FactorialLogTest extends TestCase {
  private static final double EPS = 1e-13;
  public static Test suite() {
    return new TestSuite(FactorialLogTest.class);
  }
  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
  public void testCalc() {
/*
   job.addLine(GTestableDH(log(1.)),   log_fact[0],  "log_fact[0]=",  EPS18, true);
   job.addLine(GTestableDH(log(1.)),   log_fact[1],  "log_fact[1]=",  EPS18, true);
   job.addLine(GTestableDH(log(2.)),   log_fact[2],  "log_fact[2]=",  EPS18, true);
   job.addLine(GTestableDH(log(6.)),   log_fact[3],  "log_fact[3]=",  EPS18, true);
   job.addLine(GTestableDH(log(24.)),  log_fact[4],  "log_fact[4]=",  EPS18, true);
   job.addLine(GTestableDH(log(120.)), log_fact[5],  "log_fact[5]=",  EPS18, true);

   job.addLine(GTestableDH(1.),   exp(log_fact[0]),  "fact[0]=",  EPS18, true);
   job.addLine(GTestableDH(1.),   exp(log_fact[1]),  "fact[1]=",  EPS18, true);
   job.addLine(GTestableDH(2.),   exp(log_fact[2]),  "fact[2]=",  EPS18, true);
   job.addLine(GTestableDH(6.),   exp(log_fact[3]),  "fact[3]=",  EPS18, true);
   job.addLine(GTestableDH(24.),  exp(log_fact[4]),  "fact[4]=",  EPS18, true);
   job.addLine(GTestableDH(120.), exp(log_fact[5]),  "fact[5]=",  2e-16, true);
*/
    Factorial fact = new Factorial(2);
    assertEquals(1., fact.calc(0), EPS);
    assertEquals(1., fact.calc(1), EPS);
    assertEquals(2., fact.calc(2), EPS);
    assertEquals(6., fact.calc(3), EPS);
    assertEquals(24., fact.calc(4), EPS);
    assertEquals(120., fact.calc(5), EPS);
    FactorialLog factLog = new FactorialLog(2);
    assertEquals(Math.log(120.), factLog.calc(5), EPS);
  }
}
