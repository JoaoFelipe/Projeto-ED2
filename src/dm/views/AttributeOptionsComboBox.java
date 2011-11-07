package dm.views;

import dm.controllers.TableController;
import javax.swing.DefaultComboBoxModel;

public class AttributeOptionsComboBox extends EditableComboBox {

    public AttributeOptionsComboBox() {
        super();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        this.setModel(model);
        TableController.attributesListToComboBoxModel(model);
    }

}
