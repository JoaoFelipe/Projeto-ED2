package dm.views;

import dm.controllers.TableController;
import dm.models.Entity;
import javax.swing.DefaultComboBoxModel;

public class EntityAttributesComboBox extends NotEditableComboBox {

    public EntityAttributesComboBox(Entity entity) {
        super();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        this.setModel(model);
        TableController.entityAttributesToComboBoxModel(entity, model);
    }


}
