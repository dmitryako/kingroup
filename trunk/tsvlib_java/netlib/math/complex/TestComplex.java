/* File: TestComplex.java
 *                                          -- Tester for Complex class.
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
/**
 * Tester for class <tt>Complex</tt>.
 * <p/>
 * <p/>
 * <pre>
 * Last change:  ALM  23 Mar 2001    9:13 pm
 * </pre>
 */
public class
  TestComplex {
  static final String MODULE = TestComplex.class.getName();
  static final String VERSION = "1.0.1";
  static final String DATE = "Fri 23-Mar-2001 9:13 pm";
  static final String AUTHOR = "sandy@almide.demon.co.uk";
  static final String REMARK = "Tester for Complex class";
  /**
   * Used for testing class <tt>Complex</tt>.
   * <p/>
   * <p/>
   * <dl>
   * <dt> <b>Usage:</b>
   * <dd> <pre><b>java Complex</b> <i>method</i>  [<i>a</i>] [<i>bi</i>]  [<i>c</i>] [<i>di</i>]</pre>
   * </dl>
   * <p/>
   * <hr>
   * <dd><b>Help us <blink>BETA</blink> test class
   * <tt>Complex</tt>...</b></dd>
   * <p/>
   * <dd>But first, <b><i>enable your browser's Java Console</i></b>.  On
   * Netscape, it's the <b>Options</b> menu, then the <b>Show Java Console</b>
   * item.
   * <p/>
   * <p/>
   * <applet
   * code="ComplexTestApplet.class"
   * width=300
   * height=24
   * alt="ComplexTest Applet"
   * align=texttop
   * >
   * Make yours a Java enabled browser and OS!
   * </applet>
   * </p>
   * <p/>
   * <dd>Feel free to test out <i>any</i> method in the <tt>Complex</tt>
   * documentation.  For instance, to test out <i>sqrt(-1)</i> you'd type
   * <p/>
   * <pre>
   *     sqrt -1 0
   * </pre>
   * <p/>
   * and hit the <b>Test!</b> button.  You'll see the results appearing on the
   * Java Console.  Please mail me, <a
   * href="mailto:Alexander Anderson <sandy@almide.demon.co.uk>">Sandy Anderson</a>
   * in England, and give me your observations.
   * </dd>
   * <hr>
   * <p/>
   */

  /*
      System.out is static but NOT final, so it is OK to do

      PrintStream redirect = new PrintStream(someDataOutputStream);
      System.out = redirect;

      Instead, however, I  generally do something like

      PrintStream out;
      if (someCondition)
        out = new PrintStream(...);
      else
        out = System.out;

      Then I use out.println instead of System.out.println.
  */
  public static void
    main(String[] args) {
    // debug =  false; // !!!
    final String nanStr = "NaN";
    final String negInfStr = "-Infinity";
    final String posInfStr = "Infinity";
    final String traceFlagStr = "-trace";
    boolean setDebug = false;
    int maxArg = 6;
    String method = "unknown";
    double[]  doubleArgs = {0.0, 0.0, 0.0, 0.0, 0.5, 999, 999}; //!!! epsilon woz ere
    boolean noArgs = false;
    boolean inputError = false;
    double scalar = 100.0;
    double tolerance;
    String complexClassName;
    Complex z1 = null;
    Complex z2;
    if (args.length == 0) {
      inputError = true;
      noArgs = true;
    } else {
      method = args[0];
      for (int i = 1; ((i < args.length)); i++) {                      // good enough...
        try {
          if (args[i].equalsIgnoreCase(traceFlagStr)) {
            setDebug = true;
          } else {
            if (i >= maxArg) break;
            String argsI = args[i];
            if (argsI.equalsIgnoreCase(nanStr)) {
              doubleArgs[i - 1] = Double.NaN;
            } else if (argsI.equalsIgnoreCase(posInfStr)) {
              doubleArgs[i - 1] = Double.POSITIVE_INFINITY;
            } else if (argsI.equalsIgnoreCase(negInfStr)) {
              doubleArgs[i - 1] = Double.NEGATIVE_INFINITY;
            } else {
              doubleArgs[i - 1] = Double.valueOf(args[i]).doubleValue();
            }//endif
          }//endif
        } catch (NumberFormatException e) {
          inputError = true;
        }//endtry
      }//endfor
    }//endif
    z1 = Complex.cart(doubleArgs[0], doubleArgs[1]);
    z2 = Complex.cart(doubleArgs[2], doubleArgs[3]);
    tolerance = doubleArgs[4];
    complexClassName = z1.getClass().getName();

    // Complex.objectCount =  0;                                              // !!!
    System.out.println();

    // if (setDebug) debug =  true; // !!!
    if (!noArgs) {
      System.out.println("z1         ==  " + z1);
      System.out.println("z2         ==  " + z2);
      System.out.println("tolerance  ==  " + tolerance);
      // System.out.println("trace      ==  " + debug); // !!!
      System.out.println();
      if (method.equals("real")) {
        System.out.println(complexClassName + "." + method + "(" + z1.re() + ")  ==  " + Complex.real(z1.re()));
      } else if (method.equals("cart")) {
        System.out.println(complexClassName + "." + method + z1 + "  ==  " + Complex.cart(z1.re(), z1.im()));
      } else if (method.equals("polar")) {
        System.out.println(complexClassName + "." + method + z1 + "  ==  " + Complex.polar(z1.re(), z1.im()));
      } else if (method.equals("pow")) {
        System.out.println(complexClassName + "." + method + "(" + z1.re() + ", " + z2 + ")  ==  " + Complex.pow(z1.re(), z2));
        System.out.println(complexClassName + "." + method + "(" + z1 + ", " + z2.re() + ")  ==  " + Complex.pow(z1, z2.re()));
        System.out.println(complexClassName + "." + method + "(" + z1 + ", " + z2 + ")  ==  " + Complex.pow(z1, z2));
      } else if (method.equals("isInfinite")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.isInfinite());
        System.out.println(z1 + ".isNaN()  ==  " + z1.isNaN());
      } else if (method.equals("isNaN")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.isNaN());
        System.out.println(z1 + ".isInfinite()  ==  " + z1.isInfinite());
      } else if (method.equals("equals")) {
        System.out.println(z1 + "." + method + "(" + z2 + ", " + tolerance + ")  ==  " + z1.equals(z2, tolerance));
      } else if (method.equals("clone")) {
        System.out.println(z1 + "." + method + "()  ==  " + (Complex) z1.clone());
      } else if (method.equals("re")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.re());
      } else if (method.equals("im")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.im());
      } else if (method.equals("norm")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.norm());
      } else if (method.equals("abs")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.abs());
      } else if (method.equals("arg")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.arg());
      } else if (method.equals("neg")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.neg());
      } else if (method.equals("conj")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.conj());
      } else if (method.equals("scale")) {
        System.out.println(z1 + "." + method + "(" + z2.re() + ")  ==  " + z1.scale(z2.re()));
      } else if (method.equals("addLine")) {
        System.out.println(z1 + "." + method + z2 + "  ==  " + z1.add(z2));
      } else if (method.equals("sub")) {
        System.out.println(z1 + "." + method + z2 + "  ==  " + z1.sub(z2));
      } else if (method.equals("mul")) {
        System.out.println(z1 + "." + method + z2 + "  ==  " + z1.mul(z2));
      } else if (method.equals("div")) {
        System.out.println(z1 + "." + method + z2 + "  ==  " + z1.div(z2));
      } else if (method.equals("sqrt")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.sqrt());
      } else if (method.equals("exp")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.exp());
      } else if (method.equals("log")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.log());

//          } else if (method.equals("log10")) {
//              System.out.println(z1 + "." + method + "()  ==  " + z1.log10());
      } else if (method.equals("sin")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.sin());
      } else if (method.equals("cos")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.cos());
      } else if (method.equals("tan")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.tan());
      } else if (method.equals("cosec")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.cosec());
      } else if (method.equals("sec")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.sec());
      } else if (method.equals("cot")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.cot());
      } else if (method.equals("sinh")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.sinh());
      } else if (method.equals("cosh")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.cosh());
      } else if (method.equals("tanh")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.tanh());
      } else if (method.equals("asin")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.asin());
      } else if (method.equals("acos")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.acos());
      } else if (method.equals("atan")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.atan());
      } else if (method.equals("asinh")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.asinh());
      } else if (method.equals("acosh")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.acosh());
      } else if (method.equals("atanh")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.atanh());
      } else if (method.equals("toString")) {
        System.out.println(z1 + "." + method + "()  ==  " + z1.toString());
      } else {
        System.out.println("Does the method '" + method + "' have the correct spelling?");
        System.out.println();
        System.out.println("(Run TestComplex with no args to getLine a list of method-names.)");
        inputError = true;
      }//endif
    }//endif
    if (inputError) {
      if (noArgs) {
        System.out.println("Tester...");
        System.out.println("    Module : " + MODULE);
        System.out.println("    Version: " + VERSION);
        System.out.println("    Date   : " + DATE);
        System.out.println("    Author : " + AUTHOR);
        System.out.println("    Remark : " + REMARK);
        System.out.println("Testing...");
        System.out.println("    Module : " + complexClassName);
        System.out.println("    Version: " + Complex.VERSION);
        System.out.println("    Date   : " + Complex.DATE);
        System.out.println("    Author : " + Complex.AUTHOR);
        System.out.println("    Remark : " + Complex.REMARK);
        System.out.println();
        manInfo();
      }//endif
      System.out.println();
      System.out.print("usage: ");
      System.out.println("java " + MODULE + " method-name [a] [bi] [c] [di]");
    }//endif

    // debug =  false; // !!!

    // this caused more headaches than all of class Complex put together!
  }//end main(String[])
  static void
    manInfo() {
    System.out.println("The following is a list of Complex methods:");
    System.out.println();
    System.out.println("    method-name        arguments    -- remark");
    System.out.println("    -------------------------------------------------------------------");
    System.out.println("    addLine sub mul div    a b  c d");
    System.out.println();
    System.out.println("    arg                a b          -- angle (in radians)");
    System.out.println("    abs                a b          -- length, or magnitude");
    System.out.println("    norm               a b          -- square of magnitude");
    System.out.println("    scale              a b  scalar  -- scale complex by a real number");
    System.out.println("    neg                a b          -- scale by minus 1");
    System.out.println("    conj               a b          -- complex conjugate");
    System.out.println();
    System.out.println("    sqrt               a b          -- square root");
    System.out.println("    exp                a b          -- raise e to a complex power");
    System.out.println("    log                a b          -- natural logarithm");
    System.out.println("    pow                a b  c d     -- raise (a+bi) to the power (c+di)");
    System.out.println();
    System.out.println("    sin   cos   tan    a b          --");
    System.out.println("    cosec sec   cot    a b          --");
    System.out.println("    sinh  cosh  tanh   a b          --    trigonometry");
    System.out.println("    asin  acos  atan   a b          --");
    System.out.println("    asinh acosh atanh  a b          --");
    System.out.println();
    System.out.println("    polar              r theta      -- convert polar to cartesian");
    System.out.println();
    System.out.println("    -- More method-names ...");
    System.out.println("    cart               a b");
    System.out.println("    clone              a b");
    System.out.println("    equals             a b  c d  tolerance");
    System.out.println("    isInfinite isNaN   a b");
    System.out.println("    re im              a b");
    System.out.println("    real               x");
    System.out.println("    toString           a b");
  }//end manInfo()
}//end TestComplex

/*             C A U T I O N   E X P L O S I V E   B O L T S
--                     REMOVE BEFORE ENGAGING REPLY
//
// Kelly and Sandy Anderson <kelsan@explosive-alma-services-bolts.co.uk>
// (alternatively            kelsan_odoodle at ya who period, see oh em)
// Alexander (Sandy)  1B5A DF3D A3D9 B932 39EB  3F1B 981F 4110 27E1 64A4
// Kelly              673F 6751 6DBA 196F E8A8  6D87 4AEC F35E E9AD 099B
// Homepages             http://www.explosive-alma-services-bolts.co.uk/
*/
