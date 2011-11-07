package dm.views.maintable;

import dm.controllers.TableController;
import dm.models.CollectionUtil;
import dm.models.Entity;
import dm.models.EntityRelationship;
import dm.models.HashFile;
import dm.models.Search;
import dm.models.AttributeCollection;
import dm.views.MainWindow;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EntityState implements TableState {

    Entity entity = null;
    MainTable table = null;

    public EntityState(MainTable table, EntityRelationship entity) {
        this.table = table;
        this.entity = (Entity) entity;
    }

    public void show() {
        table.setModel(
           (List<String>) CollectionUtil.process(entity.getAttributes(), AttributeCollection.processes.getName()),
           (List<Class>) CollectionUtil.process(entity.getAttributes(), AttributeCollection.processes.getClassEx()),
           true
        );
        
        HashFile hashFile = new HashFile(entity);
        try {
            Search search = new Search(hashFile, null);
            search.compile();
            TableController.tupleToTableModel(search, this.table.getModel());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainWindow.getInstance(), "Não foi possível abrir o arquivo!", "Erro!", JOptionPane.ERROR_MESSAGE);
        }
                
        table.getPanel().setVisible(true);
    }


}
