package papers.kingroup2005c_ms2;
import tsvlib.project.ProjectLogger;

import javax.langx.SysProp;
import javax.mathx.MathX;
import javax.utilx.RandomSeed;
import java.util.Random;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 19/10/2005, Time: 08:29:11
 */
public class AccuracyErrorLimit
{
  private static ProjectLogger log = ProjectLogger.getLogger(AccuracyErrorLimit.class.getName());

  public static void main(String[] args) {
    new AccuracyErrorLimit().run();
  }
  public void run() {
    int L = 1; // num of loci
    int n = 10; // num of alleles
    int nTrails = 1000000;
    int groupSize = 1;


    double n2 = n * n;
    double n3 = n2 * n;

    double mu1 = 4.*(n-1.)*(n-2.)/(n2*n);
    double mu2 = 2.*(n-1.)/(n2*n);
    double eta1 = 2.*mu1 *mu1 *MathX.pow((n-2.)/n, 4);
    double eta2 = 2.*mu1 *mu1 *(2.*mu1 *MathX.pow((n-2.)/n, 2) + mu1 *mu1);
    double eta3 = 2.*mu1 *mu2 *MathX.pow((n-2.)/n, 4);
    log.info("mu = " + (float)mu1
      + "\neta1 = " + (float)eta1
      + "\neta2 = " + (float)eta2
      + "\neta3 = " + (float)eta3
    );
//    double gamma = 0.25*MathX.pow(1.+1./n, 2);
    double gamma = 0.25;
    double m = gamma*(eta1 + eta2);
    double xi = 0.5 * m;

    int count = 0;
    int x_eq_y = 0;
    int a_eq_b = 0;
    int a_neq_b = 0;
    int x_has_allele_in_y = 0;
    int x_has_one_allele = 0;
    int xx_has_allele_in_yy = 0;
    for (int i = 0; i < nTrails; i++) {
      Genotype x = new Genotype(n);
      Genotype x2 = new Genotype(n);
      Group g = new Group(x, x2, groupSize);
//      log.info("x  = " + x
//        + "\nx2 = " + x2
//        + "\ngroup =\n" + g);

      Genotype y = new Genotype(n);
      Genotype y2 = new Genotype(n);
      Group g2 = new Group(y, y2, groupSize);
//      log.info("y  = " + y
//        + "\ny2 = " + y2
//        + "\ngroup2 =\n" + g2);

      if (x.equals(y))
        x_eq_y++;
      if (x.hasAllele(y))
        x_has_allele_in_y++;
      if (x.hasOneAllele(y))
        x_has_one_allele++;
      if (x.hasAllele(y) && x2.hasAllele(y2))
        xx_has_allele_in_yy++;
      if (x.a[0] == x.a[1])
        a_eq_b++;
      if (x.a[0] != x.a[1])
        a_neq_b++;

      count += g.count(g2);
    }
    double error = (double)count /nTrails / (2. * groupSize);
//    double p = Math.sqrt(m);
//    log.info("m = " + (float)m + ", p = " + (float)p);
//    log.info("(1-p)^3 = " + (float)MathX.pow(1-p, 3));

    log.info("count = " + count + ", error = " + (float)error
      + ", (theory) = " + (float)xi);
    log.info("countXeqY = " + x_eq_y
      + ", countXeqY/nTrails = " + (float)x_eq_y /nTrails);
    log.info("x_has_allele_in_y = " + x_has_allele_in_y + ", x_has_allele_in_y/nTrails = "
      + (float)x_has_allele_in_y /nTrails);

    double tmp = 4. * (n-1.)*(n-2.)/n3;
    log.info("x_has_one_allele/nTrails = " + (float)x_has_one_allele / nTrails
    + " theory=" + (float)tmp);

    log.info("xHasAlleleInY/nTrails (theory)= " + (float)mu1);
    log.info("xx_has_allele_in_yy = " + xx_has_allele_in_yy + ", xx_has_allele_in_yy/nTrails = "
      + (float)xx_has_allele_in_yy /nTrails);
//    log.info("a_eq_b = " + a_eq_b
//      + ", a_eq_b/nTrails = " + (float)a_eq_b /nTrails);
//    log.info("a_neq_b = " + a_neq_b
//      + ", a_neq_b/nTrails = " + (float)a_neq_b /nTrails);
  }
  private class Genotype {
    int DIPLOID = 2;
    public int[] a = new int[DIPLOID];

    public Genotype(int nAlleles)
    {
      Random rand = RandomSeed.getInstance();
      for (int i = 0; i < a.length; i++) {
        a[i] = rand.nextInt(nAlleles);
      }
    }
    public Genotype(Genotype p, Genotype p2)
    {
      Random rand = RandomSeed.getInstance();
      int idx = rand.nextInt(DIPLOID);
      int idx2 = rand.nextInt(DIPLOID);
      a[0] = p.a[idx];
      a[1] = p2.a[idx2];
    }
    public String toString() {
      return ""+a[0]+"/"+a[1];
    }
    public boolean equals(Genotype geno) {
      return ((a[0] == geno.a[0]  &&  a[1] == geno.a[1])
        || (a[0] == geno.a[1]  &&  a[1] == geno.a[0]));
    }

    public boolean hasAllele(Genotype y)
    {
      return  a[0] == y.a[0]    ||  a[0] == y.a[1]
        || a[1] == y.a[0] || a[1] == y.a[1];
    }
    public boolean hasOneAllele(Genotype y)
    {
      if (a[0] == a[1])
        return false;
      if (y.a[0] == y.a[1])
        return false;
      return  (a[0] == y.a[0]  &&  a[1] != y.a[1])
        || (a[0] != y.a[0]  &&  a[1] == y.a[1])
        || (a[0] == y.a[1]  &&  a[1] != y.a[0])
        || (a[0] != y.a[1]  &&  a[1] == y.a[0])
        ;
    }
  }

  private class Group {
    public Genotype[] arr;
    public Group(Genotype p, Genotype p2, int groupSize)
    {
      arr = new Genotype[groupSize];
      for (int i = 0; i < arr.length; i++) {
        arr[i] = new Genotype(p, p2);
      }
    }
    public String toString() {
      StringBuffer buff = new StringBuffer();
      for (int i = 0; i < arr.length; i++) {
        buff.append(arr[i]).append(SysProp.EOL);
      }
      return buff.toString();
    }

    public int count(Group g2)
    {
//      return Math.min(countEquals(g2), g2.countEquals(this));
      return countEquals(g2);
    }
    public int countEquals(Group g2)
    {
      int res = 0;
      for (int i = 0; i < arr.length; i++) {
        for (int k = 0; k < g2.arr.length; k++) {
          if (arr[i].equals(g2.arr[k])) {
            res++;
            break;
          }
        }
      }
      return res;
    }
  }
}
