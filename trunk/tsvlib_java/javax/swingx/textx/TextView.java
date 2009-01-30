package javax.swingx.textx;
import javax.swing.*;
import javax.langx.SysProp;
import java.text.SimpleDateFormat;
import java.awt.*;
import java.util.Date;
/**
 * Copyright dmitry.konovalov@jcu.edu.au Date: 9/09/2008, Time: 16:56:26
 */
// see examples at
// http://java.sun.com/docs/books/tutorial/uiswing/examples/components/index.html#TextDemo
public class TextView extends JPanel {
  private JTextArea text;
  public TextView() {
    super(new BorderLayout());
    init();
    assemble();
  }
  private void assemble() {
    add(new JScrollPane(text), BorderLayout.CENTER);
//    setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
    setBorder(BorderFactory.createEmptyBorder());
  }
  private void init() {
    text = new JTextArea();
    text.setCaretPosition(0);
    text.setMargin(new Insets(5,5,5,5));
    text.setBorder(BorderFactory.createEmptyBorder());
    text.setEditable(false);
  }
  public void print(String str) {
    text.append(str);
    text.setCaretPosition(text.getDocument().getLength());
  }
  public void println(String str, double d) {
    println(str + (float)d);
  }
  public void println(String str, Object obj) {
    println(str + obj.toString());
  }
  public void println(String str) {
    SimpleDateFormat date = new SimpleDateFormat();
    try {
//      date.applyPattern("HH:mm  EEE MMM d yyyy");
      date.applyPattern("HH:mm:ss");
    }
    catch (IllegalArgumentException e) {
    }
    String res = date.format(new Date()) + "> ";
    text.append(res);
    text.append(str);
    text.append(SysProp.EOL);
    text.setCaretPosition(text.getDocument().getLength());
  }
}
