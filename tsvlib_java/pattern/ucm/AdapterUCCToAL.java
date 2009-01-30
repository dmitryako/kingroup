package pattern.ucm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 20/04/2007, 13:44:45
 */
public class AdapterUCCToAL implements ActionListener {
  private UCController uc;
  public AdapterUCCToAL(UCController uc) {
    this.uc = uc;
  }
  public void actionPerformed(ActionEvent e) {
    if (uc != null)
      uc.run();
//    System.gc(); // cleanup
  }
}

