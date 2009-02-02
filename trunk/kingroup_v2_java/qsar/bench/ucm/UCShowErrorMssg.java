package qsar.bench.ucm;
import javax.swing.*;
import java.awt.*;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 19/03/2007, 13:59:11
 */
public class UCShowErrorMssg
{
  public static void showMessageDialog(Component parentComponent,
        Object message) {
    JOptionPane.showMessageDialog(parentComponent, "ERROR:\n" + message);
  }
}
