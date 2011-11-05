package gd.views;

import gd.controllers.AttributeController;
import gd.views.base.EditableComboBox;
import javax.swing.DefaultComboBoxModel;

public class AttributeOptionsComboBox extends EditableComboBox {

    public AttributeOptionsComboBox() {
        super();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        this.setModel(model);
        AttributeController.attributesListToComboBoxModel(model);
    }

}
