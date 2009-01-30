/* File: ComplexBenchmark.java
 *                                     -- Benchmarker for Complex class.
 *
 *
 * Copyright (c) 1997 - 2001, Alexander Anderson.
 *
 * This  program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published  by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be  useful,  but
 * WITHOUT   ANY   WARRANTY;   without  even  the  implied  warranty  of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR  PURPOSE.   See  the  GNU
 * General Public License for more details.
 *
 * You  should  have  received  a copy of the GNU General Public License
 * along  with  this  program;  if  not,  write  to  the  Free  Software
 * Foundation,  Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA.
 */
package netlib.math.complex;
import java.text.DecimalFormat;
import java.util.Date;

/*
  Last change:  ALM  23 Mar 2001    9:58 pm
*/
public class
  ComplexBenchmark {
  static final String MODULE = ComplexBenchmark.class.getName();
  static final String VERSION = "1.0.4";
  static final String DATE = "Fri 23-Mar-2001 9:58 pm";
  static final String AUTHOR = "sandy@almide.demon.co.uk";
  static final String REMARK = "Benchmarker for Complex class";
  static final char MARK_CHAR = '-';
  static final DecimalFormat stopwatch = new DecimalFormat("###0.00");
  static int iterations = 1000000;
  public static void
    main(String[] args) {
    Date start, finish;
    double dummy;
    boolean bool;
    if (args.length > 0) {
      try {
        iterations = Integer.valueOf(args[0]).intValue();
        if (iterations < 0) {
          throw new NumberFormatException();
        }//endif
      } catch (NumberFormatException e) {
        System.out.println();
        versionInfo();
        System.out.println();
        System.out.print("usage:  ");
        System.out.println("java " + MODULE + " [loop iterations]");
        System.exit(1);
      }//endtry
    }//endif
    System.out.println();
    System.out.println("    This tests on your system, the execution speed of the Complex");
    System.out.println("    functions by repeatedly calling each function " + iterations + " times.");
    System.out.println();
    System.out.println("                      Alexander Anderson <sandy@almide.demon.co.uk>");
    System.out.println();
    Complex z;
    Complex c = new Complex(0.1234, 0.5678);
    System.getProperties().list(System.out);
    System.out.println();
    versionInfo();
    System.out.println();
    System.out.println("Benchmark " + iterations + " started! ...");
    System.out.println();
    System.out.println("(Please be patient.  Each \"" + MARK_CHAR + "\" is one second.)");
    System.out.println();
    System.out.print("Empty Loop   ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      // skip
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("Constructor  ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = new Complex(0.1234, 5.6789);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("polar()      ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = Complex.polar(0.1234, 5.6789);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("pow(r, r)    ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      dummy = Math.pow(0.1234, 5.6789);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("pow(r, C)    ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = Complex.pow(0.1234, c);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("pow(C, r)    ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = Complex.pow(c, 0.1234);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("pow(C, C)    ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = Complex.pow(c, c);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
/*
        System.out.print("equals()     ");
        start =  new Date();
        for (int i =  1; i <= iterations; i++) {
            bool =  c.equals(c);
        }//endfor
        finish =  new Date();
        System.out.println(stars(start, finish));;
*/

    System.out.print("clone()      ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = (Complex) c.clone();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("re()         ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      dummy = c.re();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("norm()       ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      dummy = c.norm();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("abs()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      dummy = c.abs();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("arg()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      dummy = c.arg();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("neg()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.neg();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("conj()       ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.conj();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("scale()      ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.scale(1.234);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("addLine()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.add(c);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("sub()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.sub(c);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("mul()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.mul(c);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("div()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.div(c);
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("sqrt()       ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.sqrt();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("exp()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.exp();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("log()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.log();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
/*
        System.out.print("log10()      ");
        start =  new Date();
        for (int i =  1; i <= iterations; i++) {
            z =  c.log10();
        }//endfor
        finish =  new Date();
        System.out.println(stars(start, finish));;
*/
    System.out.print("sin()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.sin();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("cos()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.cos();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("tan()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.tan();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("cosec()      ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.cosec();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("sec()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.sec();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("cot()        ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.cot();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("sinh()       ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.sinh();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("cosh()       ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.cosh();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("tanh()       ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.tanh();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("asin()       ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.asin();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("acos()       ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.acos();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("atan()       ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.atan();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("asinh()      ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.asinh();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("acosh()      ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.acosh();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.print("atanh()      ");
    start = new Date();
    for (int i = 1; i <= iterations; i++) {
      z = c.atanh();
    }//endfor
    finish = new Date();
    System.out.println(stars(start, finish));
    ;
    System.out.println();
    System.out.println("... Benchmark " + iterations + " finished.");
  }//end main(String[])
  static String
    stars(Date start, Date finish) {
    double starSeconds = (finish.getTime() - start.getTime()) / 1000.0;
    StringBuffer starString = new StringBuffer();
    for (int i = 1; i <= Math.round(starSeconds); i++) {
      starString = starString.append(MARK_CHAR);
    }//endfor
    starString.append(" ").append(stopwatch.format(starSeconds));
    return starString.toString();
  }//end stars(Date,Date)
  static void
    versionInfo() {
    System.out.println("Benchmarker...");
    System.out.println("    Module : " + MODULE);
    System.out.println("    Version: " + VERSION);
    System.out.println("    Date   : " + DATE);
    System.out.println("    Author : " + AUTHOR);
    System.out.println("    Remark : " + REMARK);
    System.out.println("Benchmarking...");
    System.out.println("    Module : " + Complex.class.getName());
    System.out.println("    Version: " + Complex.VERSION);
    System.out.println("    Date   : " + Complex.DATE);
    System.out.println("    Author : " + Complex.AUTHOR);
    System.out.println("    Remark : " + Complex.REMARK);
  }//end versionInfo()
}//end ComplexBenchmark

/*             C A U T I O N   E X P L O S I V E   B O L T S
--                     REMOVE BEFORE ENGAGING REPLY
//
// Kelly and Sandy Anderson <kelsan@explosive-alma-services-bolts.co.uk>
// (alternatively            kelsan_odoodle at ya who period, see oh em)
// Alexander (Sandy)  1B5A DF3D A3D9 B932 39EB  3F1B 981F 4110 27E1 64A4
// Kelly              673F 6751 6DBA 196F E8A8  6D87 4AEC F35E E9AD 099B
// Homepages             http://www.explosive-alma-services-bolts.co.uk/
*/
