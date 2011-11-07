package dm.controllers;

import dm.exceptions.ModelException;
import dm.models.ComboModelCollection;
import dm.models.ER.Entity;
import dm.models.ER.ERList;
import dm.models.ER.EntityRelationship;
import dm.models.ER.Relation;
import dm.models.file.HashFile;
import dm.models.file.Search;
import dm.models.file.Tuple;
import dm.models.file.Result;
import dm.models.file.Value;
import dm.models.attributes.Attribute;
import dm.models.attributes.AttributeCollection;
import dm.views.AttributeTable;
import dm.views.MainWindow;
import dm.views.maintable.MainTable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TableController {
    
    //Um dos controladores do padrão de projeto MVC

    public static void removeTable(String table) {
        if (table != null) {
            try {
                ERList instance = ERList.getInstance();
                instance.removeByName(table);
                instance.save();
            } catch (ModelException ex) {
                ex.execute();
            }
        }
    }

    public static void createTable(TableModel model, String name) throws ModelException {
        List<Attribute> list = new ArrayList<Attribute>();
        for (int i = 0; i < model.getRowCount(); i++) {
            list.add(Attribute.createAttribute(
                (String) model.getValueAt(i, 0),
                (String) model.getValueAt(i, 1),
                (Boolean) model.getValueAt(i, 2)
            ));
        }
        ERList instance = ERList.getInstance();
        instance.add(new Entity(name, list));
        instance.save();
    }

    public static void createReference(String tableName, String attr, String refName, String pk) throws ModelException {
        ERList instance = ERList.getInstance();
        Entity table = (Entity) instance.find(tableName);
        Entity ref = (Entity) instance.find(refName);

        instance.add(new Relation(table, attr, ref, pk));
        instance.save();
    }

    public static void entityToComboBoxModel(DefaultComboBoxModel model){
        try {
            ERList instance = ERList.getInstance();
            ComboModelCollection.convert(
                instance.getTableNames(),
                model
            );
        } catch (ModelException ex) {
            ex.execute();
        }
    }

    public static void attributeToComboBoxModel(String entityName, DefaultComboBoxModel model) {
        try {
            ERList instance = ERList.getInstance();
            Entity entity = (Entity) instance.find(entityName);
            ComboModelCollection.process(
                entity.getAttributes(),
                AttributeCollection.processes.getName(),
                model
            );
        } catch (ModelException ex) {
            ex.execute();
        }
    }
    
    public static void tupleToTableModel(Search search, DefaultTableModel model) throws IOException {
        while (model.getRowCount() != 0){
            model.removeRow(0);
        }
 
        
        HashFile file = search.getHashFile();
        file.open();
        
        for (int i = 0; i < search.getPKs().size(); i++) {
            Value pk = search.getPKs().get(i);
            Result result = file.find(pk);
            Tuple tuple = file.readTuple(result.getPosition());
            model.addRow(tuple.getRow());
        }
        file.close();
    }
    
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
