package gd.controllers;

import gd.exceptions.ModelException;
import gd.models.ComboModelCollection;
import gd.models.ER.ERList;
import gd.models.ER.Entity;
import gd.models.ER.EntityRelationship;
import gd.models.atributos.Attribute;
import gd.models.atributos.AttributeCollection;
import gd.views.MainWindow;
import gd.views.tabelaer.MainTable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

public class AttributeController {

    //Um dos controladores do padrão de projeto MVC
    
    public static void attributesListToComboBoxModel(DefaultComboBoxModel model) {
        ComboModelCollection.convert(Attribute.allOptions(), model);
    }
    
    public static void entityAttributesToComboBoxModel(Entity entity, DefaultComboBoxModel model) {
        ComboModelCollection.process(entity.getAttributes(), AttributeCollection.processes.getName(), model);
    }
    
    public static void entityListToListModel(DefaultListModel model) {
        try {
            model.clear();
            List<String> list = ERList.getInstance().getNames();
            for (int i = 0; i < list.size(); i++) {
                model.add(i, list.get(i));
            }
        } catch (ModelException ex) {
            ex.execute();
        }
    }
    
    public static void selectEntityRelationship(int i) {
        try {
            EntityRelationship er = null;
            if (ERList.getInstance().getList().size() > i && i > -1){
                er = ERList.getInstance().getList().get(i);
            }
            MainTable table = MainTable.getInstance();
            ERList.setSelected(er);
            table.setState(er);
            MainWindow.getInstance().setInsertDefault();
   
        } catch (ModelException ex) {
            ex.execute();
        }
    }
}
