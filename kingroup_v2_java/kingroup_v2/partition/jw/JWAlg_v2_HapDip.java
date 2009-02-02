package kingroup_v2.partition.jw;
import kingroup.genetics.OldAlleleFreq;
import kingroup_v2.pop.sample.sys.SysPop;

/**
 * Created by IntelliJ IDEA.
 * User: eng-nbb
 * Date: 7/09/2005
 * Time: 11:20:55
 * To change this template use File | Settings | File Templates.
 */
public class JWAlg_v2_HapDip extends JWAlg_v2 {
  public JWAlg_v2_HapDip(SysPop pop, OldAlleleFreq freq) {
    super(pop, freq);
    setHaplo_Diplo_PolygamousSex(0);
    setHaploDiploid_Diploid(1);
  }
}
