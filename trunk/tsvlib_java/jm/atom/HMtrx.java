package jm.atom;
import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import jamax.MatrixX;
import jm.shell.ConfigArr;
import stlx.valarrayx.SymmetricMatrix;

import javax.utilx.arrays.vec.Vec;
import javax.vecmathx.function.FuncVec;
/**
 * Copyright (C) 2004  KinGroup Development Team.
 * See www.kingroup.org for more details.
 * User: jc138691, Date: Dec 3, 2004, Time: 4:16:17 PM
 */
// HamiltonianMatrix
public class HMtrx extends SymmetricMatrix {
  private final Atom atom;
  private final ConfigArr basis;
  public HMtrx(ConfigArr basis, final Atom atom) {
    super(basis.size());
    this.atom = atom;
    this.basis = basis;
    load();
  }
  public EigenvalueDecomposition eig() {
    double[][] full = getFullArray();
    Matrix m = new Matrix(full);
    EigenvalueDecomposition res = m.eig();
    m = null;    // gc
    full = null; // gc
    return res;
  }
  private void load() {
    for (int r = 0; r < basis.size(); r++) {
      for (int c = r; c < basis.size(); c++) {
        // Atomic system should know how to calculate itself
        Energy res = atom.calcConfigEnergy(basis.getConfig(r), basis.getConfig(c));
        set(r, c, res.kin + res.pot);
      }
    }
  }
  public Energy calcEnergy(EigenvalueDecomposition eig, int col) {
    Matrix m = eig.getV();
    Energy res = new Energy();
    for (int r = 0; r < m.getRowDimension(); r++) {
      double val = m.get(r, col);  // BY ROW is correct!   see HydrogenJUnit.test_2s
      for (int r2 = 0; r2 < m.getRowDimension(); r2++) {
        double val2 = m.get(r2, col);  // BY ROW is correct!   see HydrogenJUnit.test_2s
        Energy configE = atom.calcConfigEnergy(basis.getConfig(r), basis.getConfig(r2));
        res.kin += (configE.kin * val * val2);
        res.pot += (configE.pot * val * val2);
      }
    }
    return res;
  }
  public FuncVec calcDensity(EigenvalueDecomposition eig, int col) {
    Matrix m = eig.getV();
    FuncVec res = null;
    for (int r = 0; r < m.getRowDimension(); r++) {
      double val = m.get(r, col);  // BY ROW is correct!   see HydrogenJUnit.test_2s
      for (int r2 = 0; r2 < m.getRowDimension(); r2++) {
        double val2 = m.get(r2, col);  // BY ROW is correct!   see HydrogenJUnit.test_2s
        FuncVec conf = atom.calcConfigDensity(basis.getConfig(r), basis.getConfig(r2));
        if (conf == null)
          continue;
        conf.scale(val * val2);
        if (res == null) {
          res = new FuncVec(conf);
        } else
          res.addSafe(conf);
      }
    }
    return res;
  }
  public String toStringSorted(EigenvalueDecomposition eig, int col) {
    StringBuffer buff = new StringBuffer();
    buff.append("e[" + col + "]=" + (float) (eig.getRealEigenvalues()[col]));
    Matrix m = eig.getV();
    double[] v = MatrixX.getColCopy(m, col);
    int[] idx = Vec.sortIdxByAbs(v, false);
    for (int r = 0; r < m.getRowDimension(); r++) {
      int sortedIdx = idx[r];
      buff.append("\n\tv[" + sortedIdx + "]=\t");
      buff.append((float) m.get(sortedIdx, col)).append(" * ");
      buff.append(basis.getConfig(sortedIdx).toString());
    }
    return buff.toString();
  }
  public String toString(EigenvalueDecomposition eig, int col) {
    StringBuffer buff = new StringBuffer();
    buff.append("e[" + col + "]=" + (float) (eig.getRealEigenvalues()[col]));
    Matrix m = eig.getV();
    double norm = 0;
    for (int r = 0; r < m.getRowDimension(); r++) {
      buff.append("\n\tv[" + r + "]=\t");
      double val = m.get(r, col);  // BY ROW is correct!   see HydrogenJUnit.test_2s
//         double val = m.getLine(col, r);  // this is WRONG!!!
      norm += val * val;
      buff.append((float) val).append(" * ");
      buff.append(basis.getConfig(r).toString());
    }
    buff.append("\n\tnorm=" + norm);
    return buff.toString();
  }
//   public double calcMaxNormErrorByColumn(EigenvalueDecomposition eig) {
//      double maxError = 0;
//      Matrix m = eig.getV();
//      for (int c = 0; c < m.getColumnDimension(); c++) {
//         double[] v = MatrixX.getColumn(m, c);
//         for (int c2 = 0; c2 <= c; c2++) {
//            double[] v2 = MatrixX.getColumn(m, c2);
//            double res = DoubleArr.dotSLOW(v, v2);
//            LOG.trace(this, "<" + c + "|" + c2 + ">=" + res);
//            if (c == c2)
//               res -= 1;
//            res = Math.abs(res);
//            if (maxError < res);
//               maxError = res;
//         }
//      }
//      return maxError;
//   }
//   public double calcMaxNormErrorByRow(EigenvalueDecomposition eig) {
//      double maxError = 0;
//      Matrix m = eig.getV();
//      for (int r = 0; r < m.getRowDimension(); r++) {
//         double[] v = MatrixX.getRow(m, r);
//         for (int r2 = 0; r2 <= r; r2++) {
//            double[] v2 = MatrixX.getRow(m, r2);
//            double res = DoubleArr.dotSLOW(v, v2);
//            LOG.trace(this, "<" + r + "|" + r2 + ">=" + res);
//            if (r == r2)
//               res -= 1;
//            res = Math.abs(res);
//            if (maxError < res);
//               maxError = res;
//         }
//      }
//      return maxError;
//   }
}
