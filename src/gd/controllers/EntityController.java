package gd.controllers;

import gd.models.atributos.Attribute;
import gd.views.AttributeTable;
import javax.swing.table.DefaultTableModel;

public class EntityController {

    //Um dos controladores do padrão de projeto MVC
    
    public static void insertAttribute(AttributeTable table, String name, String type) {
        Attribute attribute = Attribute.createAttribute(name+":"+type);
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        if (attribute != null) {
            for (int i = 0; i < model.getRowCount(); i++) {
                if (model.getValueAt(i, 0).equals(attribute.getName())){
                    WindowController.message("Já existe um atributo com este nome!");
                    return;
                }
            }
            model.addRow(new Object[]{attribute.getName(), attribute.getType(), attribute.getPK()});
        } else {
            WindowController.message("Atributo Inválido!");
        }
    }

    public static void removeAttribute(AttributeTable table) {
        int i = table.convertRowIndexToModel(table.getSelectedRow());

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        if (i >= 0 && i < model.getRowCount()) {
            model.removeRow(i);
        }
    }

}
