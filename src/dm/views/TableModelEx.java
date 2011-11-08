package dm.views;

import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TableModelEx extends DefaultTableModel {

    private List<Class> types = null;
    private boolean editable = true;

    public TableModelEx(Object[] columnNames, List<Class> types, boolean edit) {
        super(new Object[][]{}, columnNames);
        this.types = types;
        editable = edit;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return ((row == this.getRowCount() - 1) && isEditable());
    }

    public Class getColumnClass(int columnIndex) {
        if (getTypes() != null)
            return getTypes().get(columnIndex);
        else
            return String.class;
    }

    public List<Class> getTypes() {
        return types;
    }

    public void setTypes(List<Class> types) {
        this.types = types;
    }

    public boolean isEditable() {
        return true;//editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
}
