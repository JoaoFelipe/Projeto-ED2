/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.base;

import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 *
 * @author Joao
 */
public class ComboBoxEditor extends DefaultCellEditor{

    public ComboBoxEditor(List<String> items) {
        super(new JComboBox(items.toArray()));
    }

}
