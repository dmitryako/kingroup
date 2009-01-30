package pattern.ucm;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 17/04/2007, 10:07:01
 */
public class UCClearView implements UCController
{
  private ClearableViewI view;
  public UCClearView(ClearableViewI view) {
    this.view = view;
  }
  public boolean run()
  {
    view.clear();
    return true;
  }
}
