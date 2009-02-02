package kingroup.view;
/* Copyright (C) 2004  Dr. Dmitry Konovalov.
* This code is licensed under the GPL license (see www.gnu.org) for academic,
* not-for-profit use or for use within other Open Source software (see www.opensource.org).
* See www.kingroup.org for more details.
*/
import kingroup.model.KinshipIBDModelV1;
import kingroup.model.KnownPedigreeModel;

//import java.text.*;

// View of the Model-View-Controller
public class KnownPedigreeView extends PedigreeView {
  public KnownPedigreeView(KinshipIBDModelV1 model) {
    loadAllKnown();
    loadFrom((float)model.getRm(), (float)model.getRp());
    assemble();
  }
  public KnownPedigreeView(String title, KinshipIBDModelV1 model) {
    loadAllKnown();
    loadFrom((float)model.getRm(), (float)model.getRp());
    assembleWithTitle(title);
  }
  private void loadAllKnown() {
    addType(KnownPedigreeModel.MANUAL_INPUT);
    addType(KnownPedigreeModel.UNRELATED);
    addType(KnownPedigreeModel.FULL_SIBLINGS);
    addType(KnownPedigreeModel.HALF_SIBLINGS);
    addType(KnownPedigreeModel.PARENT_OFFSPRING);
    addType(KnownPedigreeModel.FULL_HAPLOSIBS);
    addType(KnownPedigreeModel.COUSINS);
    addType(KnownPedigreeModel.HAPLO_COISINS);
    addType(KnownPedigreeModel.CENSUS);
  }
}



