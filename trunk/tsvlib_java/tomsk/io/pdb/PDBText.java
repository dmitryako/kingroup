package tomsk.io.pdb;
/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 8/05/2007, 16:33:56
 */
public class PDBText
{
  private String text;

  public PDBText(String text)
  {
    setText(text);
  }

  public String getText()
  {
    return text;
  }

  public void setText(String text)
  {
    this.text = text;
  }

  public void append(PDBText h)
  {
    text += (" " + h.getText());
  }
}
