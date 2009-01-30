package javax.swingx;
import javax.swing.*;
import javax.utilx.pair.IntStringPair;
public class IntStringPairComboBox extends JComboBox {
  private IntStringPair[] pairs_;
  public IntStringPairComboBox(IntStringPair[] pairs) {
    super(pairs);
    pairs_ = pairs;
  }
  public void setByInt(int id) {
    for (int i = 0; i < pairs_.length; i++) {
      if (pairs_[i].getInt() == id)
        setSelectedItem(pairs_[i]);
    }
  }
}
