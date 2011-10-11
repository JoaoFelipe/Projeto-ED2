/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.views.base;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joao
 */
public class ModeloTabela extends DefaultTableModel {

    List<Class> types = null;
    boolean editavel = true;

    public ModeloTabela(Object[] columnNames, List<Class> types, boolean edit) {
        super(new Object[][]{}, columnNames);
        this.types = types;
        editavel = edit;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return ((row == this.getRowCount() - 1) && editavel);
    }

    public Class getColumnClass(int columnIndex) {
        if (types != null)
            return types.get(columnIndex);
        else
            return String.class;
    }
}
