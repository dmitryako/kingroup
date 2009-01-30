package jm.shell;
import javax.iox.LOG;
import javax.vecmathx.function.FuncArr;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 5:14:01 PM
 */
public class ConfigArrFactory {
  public static ConfigArr makeTwoElec(ShellLS LS, int N, int L, FuncArr from) {
    return makeTwoElecFrom(null, LS, N, L, from);
  }
//   public static ConfigArr makeTwoElecNL(ShellLS ShellLS, int N, int maxL, FuncArr from) {
//   }
  public static ConfigArr makeTwoElecFrom(ConfigArr from, ShellLS LS, int N, int L
    , FuncArr fromArr) {
    ConfigArr res = new ConfigArr(from);
//      if (from == null)
//         from = new ConfigArr();
    for (int n = 0; n < N; n++) {
      Shell sh = new Shell(n, fromArr.get(n), 2, L, LS);
      if (sh.isValid()) {
        ShellConfig fc = new TwoEShells(sh);
        //         LOG.trace(fromArr, "config(n=n2)=", fc.toString());
        res.add(fc);
      }
      sh = new Shell(n, fromArr.get(n), L);
      for (int n2 = n + 1; n2 < N; n2++) {
        Shell sh2 = new Shell(n2, fromArr.get(n2), L);
        if (!sh2.isValid())
          continue;
        ShellConfig fc = new TwoEShells(sh).addShell(sh2, LS);
//            LOG.trace(fromArr, "config(n2>n)=", fc.toString());
        res.add(fc);
      }
    }
    LOG.trace(fromArr, "from=\n", res.toString());
    return res;
  }
  public static ConfigArr makeTwoElecSameN(ShellLS LS, int N, FuncArr from) {
    ConfigArr res = new ConfigArr();
    for (int n = 0; n < N; n++) {
      Shell sh = new Shell(n, from.get(n), 2, 0, LS);
      ShellConfig c = new TwoEShells(sh);
      res.add(c);
    }
    LOG.trace(from, "res=\n", res.toString());
    return res;
  }
  public static ConfigArr makeOneElec(int N, int L, FuncArr from) {
    ConfigArr res = new ConfigArr();
    for (int n = 0; n < N; n++) {
      Shell sh = new Shell(n, from.get(n), L);
      ShellConfig c = new ShellConfig(sh);
      res.add(c);
    }
    return res;
  }
}
