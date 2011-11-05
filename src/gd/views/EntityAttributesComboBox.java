package gd.views;

import gd.controllers.AttributeController;
import gd.models.CollectionUtil;
import gd.models.ComboModelCollection;
import gd.models.ER.Entity;
import gd.views.base.NotEditableComboBox;
import javax.swing.DefaultComboBoxModel;

public class EntityAttributesComboBox extends NotEditableComboBox {

    public EntityAttributesComboBox(Entity entity) {
        super();
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        this.setModel(model);
        AttributeController.entityAttributesToComboBoxModel(entity, model);
    }


}
