package gd.views.base;

import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ModeloTabela extends DefaultTableModel {

    private List<Class> types = null;
    private boolean editavel = true;

    public ModeloTabela(Object[] columnNames, List<Class> types, boolean edit) {
        super(new Object[][]{}, columnNames);
        this.types = types;
        editavel = edit;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return ((row == this.getRowCount() - 1) && isEditavel());
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

    public boolean isEditavel() {
        return editavel;
    }

    public void setEditavel(boolean editavel) {
        this.editavel = editavel;
    }
    
}
