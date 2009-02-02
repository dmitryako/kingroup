package kingroup_v2.pop.sample.sys;
import junit.framework.TestCase;

/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 2/03/2006, Time: 10:34:56
 */
public class SysPopFactoryTest extends TestCase
{

  public void testAreFS() {
    int n = 10;
    int nLoci = 1;
    SysPop pop = new SysPop(n, nLoci);

    int idx = 0;
    int p = idx++;
    pop.setMatId(p, -1);
    pop.setPatId(p, -1);

    int p2 = idx++;
    pop.setMatId(p2, -1);
    pop.setPatId(p2, -1);
    assertEquals(false, SysPopFactory.areFS(pop, p, p2));

    int p_p2 = idx++;
    pop.setMatId(p_p2, p);
    pop.setPatId(p_p2, p2);
    assertEquals(false, SysPopFactory.areFS(pop, p, p_p2));
    assertEquals(false, SysPopFactory.areFS(pop, p_p2, p2));

    int p_p2_2 = idx++;
    pop.setMatId(p_p2_2, p);
    pop.setPatId(p_p2_2, p2);
    assertEquals(true, SysPopFactory.areFS(pop, p_p2_2, p_p2));

    int p3 = idx++;
    pop.setMatId(p3, -1);
    pop.setPatId(p3, -1);
    int p_p3 = idx++;
    pop.setMatId(p_p3, p);
    pop.setPatId(p_p3, p3);
    assertEquals(false, SysPopFactory.areFS(pop, p_p2_2, p_p3));
    assertEquals(true, SysPopFactory.areHS(pop, p_p2_2, p_p3));

    int p4 = idx++;
    pop.setMatId(p4, -1);
    pop.setPatId(p4, -1);
    int p4_p3 = idx++;
    pop.setMatId(p4_p3, p4);
    pop.setPatId(p4_p3, p3);
    assertEquals(false, SysPopFactory.areFS(pop, p_p2, p4_p3));
    assertEquals(true, SysPopFactory.areHS(pop, p4_p3, p_p3));
  }
}
