package dm.views.maintable;

import dm.models.EntityRelationship;
import java.util.Arrays;

public class EmptyState implements TableState {

    private MainTable table = null;

    public EmptyState(MainTable table) {
        this.table = table;
    }

    public void show() {
        getTable().setModel(Arrays.asList("Selecione uma tabela"), null, false);
        getTable().getPanel().setVisible(false);
    }

    public EntityRelationship getEr() {
        return null;
    }

    public MainTable getTable() {
        return table;
    }

    public void setTable(MainTable table) {
        this.table = table;
    }
    
}
