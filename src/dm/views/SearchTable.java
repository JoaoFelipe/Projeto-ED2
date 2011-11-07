package dm.views;

import dm.models.ER.Entity;
import dm.views.base.ComboBoxEditor;
import dm.views.base.ComboBoxRenderer;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SearchTable extends JTable {

    private Entity entity = null;

    public SearchTable(Entity entity) {
        super();
        DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "Atributo", "Condição", "Valor" }
        );
        this.entity = entity;

        this.setModel(model);
        List<String> list = entity.getSearchableAttributes();
        TableColumn column = this.getColumnModel().getColumn(0);
        column.setCellEditor(new ComboBoxEditor(list));
        column.setCellRenderer(new ComboBoxRenderer(list));

        list = Arrays.asList("=", "!=", ">", ">=", "<", "<=", "LIKE", "REGEX");
        column = this.getColumnModel().getColumn(1);
        column.setCellEditor(new ComboBoxEditor(list));
        column.setCellRenderer(new ComboBoxRenderer(list));
    }

    public void removeSelectedRow(){
        int i = this.convertRowIndexToModel(this.getSelectedRow());
        if (i >= 0 && i < getModel().getRowCount())
            ((DefaultTableModel)getModel()).removeRow(i);
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

}
