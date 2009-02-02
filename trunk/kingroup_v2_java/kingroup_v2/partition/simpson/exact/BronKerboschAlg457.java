package kingroup_v2.partition.simpson.exact;
import javax.iox.LOG;
import javax.utilx.arrays.IntVec;
import java.util.ArrayList;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 6/07/2005, Time: 14:22:10
 */
//An implementation of Bron-Kerbosch max. clique algorithm
// From Algorithm 457 of the Collected Algorithms from CACM
public class BronKerboschAlg457 {
  private int[] compsub;
  private int c;
  private boolean[][] connected;
  ArrayList res = new ArrayList();
  public ArrayList run(boolean[][] connected, int N) {
    if (N == 0)
      return res;
    if (N == 1) {
      int[] clique = new int[N];
      clique[0] = 0;
      res.add(clique);
      return res;
    }
    int[] ALL = new int[N];
    compsub = new int[N];
    this.connected = connected;
    for (int i = 0; i < N; i++) {
      ALL[i] = i;
    }
    extend_version_2(ALL, 0, N);
    return res;
  }
  private void extend_version_2(int[] old, int ne, int ce) {
    LOG.report(this, "extend_version_2(ne=" + ne + ", ce=" + ce);
    LOG.report(this, "old=" + IntVec.toString(old));
    int[] new_ = new int[ce];
    int nod = 0;
    int fixp = 0;
    int pos = 0;
    int s = 0;
    int minnod = ce;
    // DETERMINE EACH COUNTER VALUE AND LOOK FOR MINIMUM:
    for (int i = 0; i < ce && minnod != 0; i++) {
      int p = old[i];
      int count = 0;
      // COUNT DISCONNECTIONS:
      for (int j = ne; j < ce && count < minnod; j++) {
        if (!connected[p][old[j]]) {
          count++;
          pos = j; // SAVE POSITION OF POTENTIAL CANDIDATE:
        }
      }
      //TEST NEW MINIMUM
      if (count < minnod) {
        fixp = p;
        minnod = count;
        if (i < ne)
          s = pos;
        else {
          s = i;
          nod = 1; // PREINCR
        }
      } // NEW MINIMUM;
    } //i;
    // If fixed point initially chosen from candidates then
    // number of diconnections will be preincreased by one;
    // BACKTRACKCYCLE
    int newne = 0;
    int sel = 0;
    for (nod += minnod; nod >= 1; nod--) {
      // INTERCHANGE
      int p = old[s];
      old[s] = old[ne];  //dk: was [ne+1]
      sel = old[ne] = p; //dk: was [ne+1]
      // FILL NEW SET "not"
      newne = 0;
      for (int i = 0; i < ne; i++) { //dk: was i<=ne
        if (connected[sel][old[i]]) {
          newne++;
          new_[newne - 1] = old[i]; //dk: was [newne]
        }
      }
    }
    // FILL NEW SET "cand":
    int newce = newne;
    for (int i = ne + 1; i < ce; i++) {
      if (connected[sel][old[i]]) {
        newce++;
        new_[newce - 1] = old[i]; //dk: was [newce]
      }
    }
    //ADD TO "compsub":
    c++;
    compsub[c - 1] = sel;
    if (newce == 0) {
      LOG.report(this, "compsub=" + IntVec.toString(compsub, c));
      int[] clique = new int[c];
      System.arraycopy(compsub, 0, clique, 0, c);
      res.add(clique);
    } else if (newne < newce) {
      extend_version_2(new_, newne, newce);
    }
    // REMOVE FROM compsub:
    c--;
    //ADD TO not:
    ne++;
    if (nod > 1) {
      //Select a candidate disconnected to the fixed point:
      for (s = ne; connected[fixp][old[s]]; s++) {
      } // selection
    } // BACKTRACKCYCLE
  } // extend version 2;
}
