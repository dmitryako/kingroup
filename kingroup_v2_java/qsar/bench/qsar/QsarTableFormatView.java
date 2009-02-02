package qsar.bench.qsar;
import javax.iox.TableFormatView;

/**
 * Copyright: www.DmitryKonovalov.org, jc138691, 9/03/2007, 16:16:59
 */
public class QsarTableFormatView   extends TableFormatView
{
  private QsarTableTypeView optView;
  public QsarTableFormatView(QsarTableFormat project)
  {
    init();
    loadFrom(project);
    assemble();
  }
  public void loadFrom(QsarTableFormat model) {
    super.loadFrom(model);
    optView = new QsarTableTypeView(model);
    optView.setEnabled(false);
  }
  public void loadTo(QsarTableFormat model) {
    super.loadTo(model);
    optView.loadTo(model);
  }
  protected void assemble() {
    startRow(optView);
    super.assemble();
  }
}
