package qsar.bench.qsar.cv.mcvs.ga;
import qsar.bench.QBench;
import qsar.bench.qsar.MLR.QsarAlgFactory;
import qsar.bench.qsar.cv.mcvs.McvsItem;
import qsar.bench.qsar.cv.mcvs.sa.McvsSaAlg;
import tsvlib.project.ProjectLogger;

import javax.swingx.tablex.TableViewWithOpt;
import javax.utilx.RandomSeed;
import javax.utilx.arrays.IntVec;
import javax.utilx.bitset.BitSetFactory;
import java.util.BitSet;
import java.util.Collection;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/11/2007, Time: 11:59:02
 */
public class McvsGaAlg extends McvsSaAlg
{
  private final static ProjectLogger log = ProjectLogger.getLogger(McvsGaAlg.class);
  public static final String REFERENCE = "MCVS_GA";
  private static final double MUTATION_RATE = 0.5;

  public McvsGaAlg(QBench model, QsarAlgFactory algFactory, TableViewWithOpt updateView)
  {
    super(model, algFactory, updateView);
  }

  public void calcStats(double[][] z, boolean showProgress)
  {
    initCalc(z, showProgress);
    int countUpdate = 0;

    // STEP 1.
    McvsItem currJ = makeRandom(k);
    itemArr.add(currJ);
    loadItemArr(k);

    for (int i = 0; i < N; i++) {
      if (progress != null
        && progress.isCanceled(i, 0, N)) {
        cleanup();
        return;
      }

      // STEP 2.
      int[] currI = IntVec.makeRandIdxArr(n);
      McvsItem newJ = crossRandom();
      if (rand.makeRandomEvent(MUTATION_RATE))
        newJ = swapRandom(newJ);
      newJ = itemArr.add(newJ); // NOTE: newJ may have been already created
      calcBestArr(currI);
      markBestForP();
      itemArr.removeExcess();

      // STEP 3.
      countUpdate++;
      if (countUpdate >= vs.getNumUpdatesPerView()) {
        countUpdate = 0; // reset count
        updateView();
      }
    }
    calcF();
    updateView();

    if (progress != null)
      progress.close();
    progress = null;
  }

  private McvsItem crossRandom() {
    Collection<McvsItem> col =  itemArr.values();
    Object[] arr = col.toArray();
    RandomSeed rand = RandomSeed.getInstance();
    McvsItem p = (McvsItem)arr[rand.nextInt(arr.length)]; log.debug("p=", p);
    McvsItem p2 = (McvsItem)arr[rand.nextInt(arr.length)]; log.debug("p2=", p2);


    BitSet pool = new BitSet();
    pool.or(p);
    pool.or(p2); log.debug("parental pool=", p);
    pool.set(0, kFixed + 1, false); // ignore fixed

    BitSet newSet = BitSetFactory.getRandomSet(p.cardinality() - kFixed - 1, pool);
    log.debug("newSet (excl fixed)=", newSet);
    newSet.set(0, kFixed + 1, true); // restore fixed
    log.debug("newSet (inc fixed)=", newSet);
    return new McvsItem(newSet);
  }

}

