/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.base;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Joao
 */
public class ComboBoxRenderer extends JComboBox implements TableCellRenderer{

    public ComboBoxRenderer(List<String> items) {
        super(items.toArray());
    }



    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getForeground());
//            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        setSelectedItem(value);
        return this;
    }
    

}
