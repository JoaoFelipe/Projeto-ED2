package gd.views;

import gd.controllers.TableController;
import gd.views.base.ListBox;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;

public class TableList extends ListBox {

    public TableList() {
        super();
        this.setModel(new DefaultListModel());
        this.update();
    }

    public void update() {
        TableController.entityListToListModel((DefaultListModel) this.getModel());
    }

    public void valueChanged(ListSelectionEvent e) {
        TableController.selectEntityRelationship(this.getSelectedIndex());
    }

    public String getSelectedName() {
        int indice = this.getSelectedIndex();
        if (indice > -1) {
            return (String) this.getModel().getElementAt(indice);
        }
        return null;
    }
}
