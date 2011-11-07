package gd.views;

import gd.controllers.TableController;
import gd.views.base.EditableComboBox;
import javax.swing.DefaultComboBoxModel;

public class AttributeOptionsComboBox extends EditableComboBox {

    public AttributeOptionsComboBox() {
        super();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        this.setModel(model);
        TableController.attributesListToComboBoxModel(model);
    }

}
