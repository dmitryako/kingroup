package kingroup.genetics;
/* Copyright (C) 2003-2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.genotype.Genotype;
import kingroup.model.HypothesisModel;
import kingroup.population.GenotypeBuilder;
import kingroup.population.OldPop;
import kingroup.population.PopGroup;
import kingroup.project.KinGroupProjectV1;

import javax.iox.LOG;
import javax.utilx.arrays.ArraysX;
import javax.swingx.ProgressWnd;
import java.util.Arrays;
public class RatioSimulation extends KinPairSimModel {
  private HypothesisModel hypoPrim_ = null;
  private HypothesisModel hypoNull_ = null;
  private double simRm = 0;
  private double simRp = 0;
  private OldPop pop_;
  private GenotypeBuilder ar_;
  private PopGroup data_;
  private OldPop simPop_;
  private int ratioIdx_ = 0;
  private ProgressWnd progress = null;
  private boolean usePaternal_ = true;
  private double[] ratios = null;
  private void cleanup() {
    pop_ = null;
    ar_ = null;
    simPop_ = null;
    hypoPrim_ = null;
    hypoNull_ = null;
    setSimulationRatios(null);
    if (progress != null)
      progress.close();
    progress = null;
  }
  public HypothesisModel getPrimModel() {
    return hypoPrim_;
  }
  public double[] getRatios_() {
    return ratios;
  }
  private void setup(HypothesisModel hypoPrim
    , HypothesisModel hypoNull
    , OldPop pop) {
    pop_ = pop;
    ar_ = new GenotypeBuilder(pop_.getAlleleFreq());
    simPop_ = new OldPop(pop_);
    simPop_.removeGenotypes();
    hypoPrim_ = hypoPrim;
    hypoNull_ = hypoNull;

    // [040126-common] NOTE! Simulation is based on getAllele frequency block.
    // That is why getAllele freqs must be kept consistent with the population data.
    // Initial code was based on maxIdx num of loci in the population, however
    // that approach contains major bug, e.g.
    // 1. UsrPopFactory block has 10 loci
    // 2. population is loaded with one get but not the first!
    // 3. Simulation will take the first get of UsrPopFactory block as the offset is not
    //    known. So here is the problem: simulation will be based on the WRONG set of
    //    frequencies!
    ratioIdx_ = 0;
    setSimulationRatios(new double[getNumRuns()]);
    KinPairSimModel kssb = KinGroupProjectV1.getInstance().getSimulationModel();
    usePaternal_ = kssb.getPaternal();
  }
  public RatioSimulation(HypothesisModel hypoPrim
    , HypothesisModel hypoNull
    , HypothesisModel hypoSim
    , KinPairSimModel model
    , OldPop pop) {
    super(model);
    setup(hypoPrim, hypoNull, pop);
    simRm = hypoSim.getRm();
    simRp = hypoSim.getRp();
  }
  // Contains Ratio data results
  private void setSimulationRatios(double[] v) {
    ratios = v;
  }
  public OldPop getSimulatedPopulation() {
    return simPop_;
  }
  // Calculate Primary or NUll
  // Generates Individuals
  // Calculates The Hypothesis
  // Extract the results from a table
  // Send the results data to the Statistics class and
  // calculate confidence intervals
  public void performSim(boolean showProgress) {
    progress = null;
    if (showProgress)
      progress = ProgressWnd.getInstance();
    ratioIdx_ = 0;
    diploidSim();
    Arrays.sort(ratios);
    if (progress != null)
      progress.close();
  }
  // Type I error
  // DEF: Rejection of the null hypothesis when it is true called a TYPE I(one) ERROR
  // The probability of committing a type I error, also called the level of significance
  //
  // RETURN: sl - significance level
  // e.g. p=0.05 will produce sl < {r}, 5% will lay above sl
  // NOTE "<" is used!!!!!!!!!!!!!!!!!!!********************************
  public Double calcSigLevels(double pValue) {
    if (pValue > 1 || pValue < 0
      || ratios == null || ratios.length == 0)
      return null;

    // significance level p=0.05 corresponds to 0.95
    double d = excludeBelowPercent(1.0 - pValue, ratios);
    return new Double(d);
  }
  // PRECOND: 1. array of at least 1 element
  //          2. array is sorted in acsending order
  //          3. v belongs to [0,1]
  // RETURNS: the point of exclusion
  //  e.g. <= 95% of all {r}
  // NOTE "<=" is used!!!!!!!!!!!!!!!!!!!!!!!!********************************
  public double excludeBelowPercent(double v, double[] a) {
    // Examples:
    // v = 1.0 (sl=0.0)
    //   means exclude 100%, must return LAST element in the array
    //   idxF = 1.0 * a.length - 1 ;
    //   idx = a.length - 1 ; // LAST
    //
    // v = 0.0 (sl=1.0)
    //   means exclude 0%, must return LESS THAN the FIRST element in the array
    //   since 100% would have to lay ABOVE
    //   CURRENTLY will return a[0]
    //   idxF = 0.0 * a.length - 1 ;
    //   idx = -1 ; // FIRST
    //
    // v = 0.95 and a[100]
    //   means exclude 95%, must return 94th element in the array of 100
    //   idxF = 0.95 * 100 - 1 = 94.0;
    //   idx = 94;
    //
    // v = 0.999 and a[100]
    //   means exclude 99.9%, must return 99th element in the array of 100
    //   idxF = 0.999 * 100 - 1 = 98.9;
    //   idx = 99;
    //
    // v = 0.5 and a={0.19, 0.2, 0.3, 0.4}
    //   means exclude 50%, must return a[1]
    //   idxF = 0.5 * 4 - 1 = 1.0;
    //   idx = 1;
    //
    // v = 0.6 and a={0.19, 0.2, 0.3}
    //   means exclude 60%, must return the best fit, a[1] which excludes 66.66%
    //   idxF = 0.6 * 3.0  - 1 = 0.8;
    //   idx = round(0.8) = 1;
    float idxF = (float) (v * a.length - 1);
    int idx = Math.round(idxF);  // (int)Math.floor(a + 0.5f)
    if (idx < 0)
      return a[0];
    return a[idx];
  }
  // Type II error
  // DEF: Acceptance of the null hypothesis when it is false is called a TYPE II(two) ERROR
  public Double calcTypeTwoError(double ci) {
    if (ratios == null || ratios.length == 0)
      return null;
    int i = 0;
    for (i = 0; i < ratios.length; i++) {
      if (ratios[i] <= ci)
        continue;
      break;
    }
    double d = (double) i / ratios.length;
    return new Double(d);
  }
  // Type I error
  // DEF: Rejection of the null hypothesis when it is true called a TYPE I(one) ERROR
  // The probability of committing a type I error, also called the level of significance
  public Double calcTypeOneError(double r) {
    if (ratios == null || ratios.length == 0)
      return null;
    int i = ArraysX.binarySearchGE(ratios, r);
    if (i == ArraysX.NOT_FOUND)
      i = ratios.length;
    double d = (double) (ratios.length - i) / ratios.length;
    return new Double(d);
  }

  // PAIRS CONSIST OF TWO INDIVIDUALS, X AND Y
  // collects all possible alleles and their freqencies
  // These will be stored under tables, where the colum of the tables relate
  // to loci, gain access to OldAlleleFreq via Population
  // X- HAPLOIDS AND Y - HAPLOIDS
  private void haploidSim() {
    for (int i = 0; i < getNumRuns(); i++) {
      if (progress != null
        && progress.isCanceled(i, 0, getNumRuns())) {
        cleanup();
        break;
      }
      Genotype x = generateXHapInd();
      Genotype y = generateYHapIndFrom(x);
      data_ = createGroup("G" + (i + 1), x, y);
      calculateRatio(data_);
    }
  }
  private PopGroup createGroup(String groupId, Genotype gen, Genotype gen2) {
    data_ = new PopGroup();
    data_.setId(groupId);
    gen.setGroupId(groupId);
    gen.setId("s1");
    data_.addGenotype(gen);
    gen2.setGroupId(groupId);
    gen2.setId("s2");
    data_.addGenotype(gen2);
    LOG.trace(this, "createGroup()=", data_);
    return data_;
  }
  // X- HAPLOIDS AND  Y - DIPLOIDS
  private void haploDiploidSim() {
    for (int i = 0; i < getNumRuns(); i++) {
      if (progress != null
        && progress.isCanceled(i, 0, getNumRuns())) {
        cleanup();
        break;
      }
      Genotype x = generateXHapInd();
      Genotype y = generateYIndFrom(x);
      data_ = createGroup("G" + (i + 1), x, y);
      calculateRatio(data_);
    }
  }
  // X - DIPLOIDS  Y - HAPLOIDS
  private void haploDiploidSim2() {
    for (int i = 0; i < getNumRuns(); i++) {
      if (progress != null
        && progress.isCanceled(i, 0, getNumRuns())) {
        cleanup();
        break;
      }
      Genotype x = generateXInd();
      Genotype y = generateYHapIndFrom(x);
      data_ = createGroup("G" + (i + 1), x, y);
      calculateRatio(data_);
    }
  }
  // X - DIPLOIDS AND Y - DIPLOIDS
  private void diploidSim() {
    for (int i = 0; i < getNumRuns(); i++) {
      if (progress != null
        && progress.isCanceled(i, 0, getNumRuns())) {
        cleanup();
        break;
      }
      Genotype x = generateXInd();
      Genotype y = generateYIndFrom(x);
      data_ = createGroup("G" + (i + 1), x, y);
      calculateRatio(data_);
    }
  }
  private Genotype generateXHapInd() {
    Genotype geno = ar_.makeRandomGenotypeInOrder(1); // generate haploid!
    LOG.trace(this, "generateXHapInd()=", geno);
    return geno;
  }
  private Genotype generateXInd() {
    Genotype geno = ar_.makeRandomGenotypeInOrder(2); // generate diploid
    LOG.trace(this, "generateXInd()=", geno);
    return geno;
  }
  // For each Locus in the Loci, Assign the Maternal getAllele from the coressponding
  // X individual to the maternal Allele for the Y individual with probability Rm
  // (calls the getYMaternalAllele() function for this. Otherwise
  // generate the getAllele randomly, calling the getRanddomAllele() function. Do the same for
  // the paternal getAllele of Y. This will fill the get with the two maternal and paternal
  // getAllele
  // Then addSimulation the get to the Genotype class,contains Loci array of Klocus.
  // When finsihed the loop return indivdual
  private Genotype generateYIndFrom(Genotype x) {
    Genotype geno = ar_.getRandomGenotypeFrom(x
      , simRm, simRp, usePaternal_, 2); // 2-generate diploid
    LOG.trace(this, "generateYIndFrom()=", geno);
    return geno;
  }
  private Genotype generateYHapIndFrom(Genotype x) {
    Genotype geno = ar_.getRandomGenotypeFrom(x
      , simRm, simRp, usePaternal_, 1); // 1-generate haploid!
    LOG.trace(this, "generateYHapIndFrom()=", geno);
    return geno;
  }

  //**** END OF MANUAL SIMULATOR *************************************************************

  //*******************************************************************
  // HYPOTHESIS CALCULATIONS
  //*******************************************************************
  // I need the indivudals generated in an earlier operation.
  // To use the current setup, I will need submit a single PopGroup and
  // formatLog bean, which will have to be assigned values rather than from the gui
  // Obtain most of the code from KinshipLikeView and KinshipLikeMtrxV1
  public void calculateRatio(PopGroup kgd) {
    simPop_.addGroup(kgd);
    KinshipRatioMtrxV1 r = new KinshipRatioMtrxV1(kgd, hypoPrim_, hypoNull_);
    if (!r.calculateRatios())
      return;
    if (ratioIdx_ >= getNumRuns())
      ratioIdx_ = 0;
    ratios[ratioIdx_] = r.getLog(0, 1);
    LOG.traceArrayAt(this, "ratios_", ratioIdx_, ratios);
    ratioIdx_++;
  }
}// end of RatioSimulation