package dm.views;

import dm.controllers.TableController;
import dm.models.ER.Entity;
import dm.views.base.NotEditableComboBox;
import javax.swing.DefaultComboBoxModel;

public class EntityAttributesComboBox extends NotEditableComboBox {

    public EntityAttributesComboBox(Entity entity) {
        super();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        this.setModel(model);
        TableController.entityAttributesToComboBoxModel(entity, model);
    }


}
