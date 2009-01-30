package pattern.ucm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Copyright KinGroup Team.
 * User: jc138691, Date: 13/09/2005, Time: 13:05:54
 */
public class AdapterUCCToALThread implements ActionListener {
  private UCController uc;
  public AdapterUCCToALThread(UCController uc) {
    this.uc = uc;
  }
  public void actionPerformed(ActionEvent e) {
    Runnable runnable = new Runnable() {
      public void run() {
        if (uc != null)
          uc.run();
        System.gc(); // cleanup
      }
    };
    new Thread(runnable).start();
  }
}
