package dm.views;

import dm.controllers.Command;
import dm.controllers.TableController;
import dm.views.base.NotEditableComboBox;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class EntityComboBox extends NotEditableComboBox {

    private JComboBox attributeComboBox = null;

    public EntityComboBox(JComboBox attributeComboBox) {
        super();
        this.attributeComboBox = attributeComboBox;
        TableController.entityToComboBoxModel((DefaultComboBoxModel) this.getModel());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        TableController.attributeToComboBoxModel(
            (String) this.getModel().getSelectedItem(),
            (DefaultComboBoxModel) attributeComboBox.getModel()
        );
    }

}
