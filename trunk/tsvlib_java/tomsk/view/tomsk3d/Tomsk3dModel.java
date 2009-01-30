package tomsk.view.tomsk3d;
import javax.utilx.arrays.StrVec;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 14/05/2007, 12:56:24
 */
public class Tomsk3dModel
{
//  public static final int BOX_SIZE = 100;

  private int numRotationSecs;
  private int minRotationSecs;
  private int maxRotationSecs;
  private boolean enableRotation;

  private static final String[] BACKGROUNDS = {"White", "Gray", "Black", "Sky", "Space", "Lab", "Chess", "Townsville"};
  private int backgroundIdx;
  private static int bgrdCount = 0;
  public static final int BACKGROUND_WHITE = bgrdCount++;
  public static final int BACKGROUND_GRAY = bgrdCount++;
  public static final int BACKGROUND_BLACK = bgrdCount++;
  public static final int BACKGROUND_SKY = bgrdCount++;
  public static final int BACKGROUND_SPACE = bgrdCount++;
  public static final int BACKGROUND_LAB = bgrdCount++;
  public static final int BACKGROUND_CHESS = bgrdCount++;
  public static final int BACKGROUND_TSV = bgrdCount++;


  private int boxSize;

  public Tomsk3dModel() {
    init();
  }

  private void init() {
    loadDefault();
  }

  public Tomsk3dModel clone() {
    Tomsk3dModel res = new Tomsk3dModel();
    res.setBackgroundIdx(this.getBackgroundIdx());
    res.setEnableRotation(this.getEnableRotation());
    res.setNumRotationSecs(this.getNumRotationSecs());
    return res;
  }

  public void loadDefault()
  {
    setEnableRotation(false);
    setMinRotationSecs(5);
    setMaxRotationSecs(100);
    setNumRotationSecs(10);

    setBackgroundIdx(0);
    setBoxSize(100);
  }

  public String toString() {
    return "\nnumRotationSecs=" + numRotationSecs
      + "\nminRotationSecs=" + minRotationSecs
      + "\nmaxRotationSecs=" + maxRotationSecs
      + "\nenableRotation=" + enableRotation
      + "\nBACKGROUNDS=" + StrVec.toCSV(BACKGROUNDS)
      + "\nbackgroundIdx=" + backgroundIdx
      ;
  }
  public int getNumRotationSecs()
  {
    return numRotationSecs;
  }

  public void setNumRotationSecs(int numRotationSecs)
  {
    this.numRotationSecs = numRotationSecs;
  }

  public int getMinRotationSecs()
  {
    return minRotationSecs;
  }

  public void setMinRotationSecs(int minRotationSecs)
  {
    this.minRotationSecs = minRotationSecs;
  }

  public int getMaxRotationSecs()
  {
    return maxRotationSecs;
  }

  public void setMaxRotationSecs(int maxRotationSecs)
  {
    this.maxRotationSecs = maxRotationSecs;
  }

  public void setEnableRotation(boolean enableRotation)
  {
    this.enableRotation = enableRotation;
  }

  public boolean getEnableRotation()
  {
    return enableRotation;
  }

  public String[] getBackgroundNames() {
    return BACKGROUNDS;
  }

  public int getBackgroundIdx()
  {
    return backgroundIdx;
  }

  public void setBackgroundIdx(int backgroundIdx)
  {
    this.backgroundIdx = backgroundIdx;
  }

  public int getBoxSize()
  {
    return boxSize;
  }

  public void setBoxSize(int boxSize)
  {
    this.boxSize = boxSize;
  }
}
