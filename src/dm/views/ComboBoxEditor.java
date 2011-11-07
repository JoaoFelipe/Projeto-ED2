package dm.views;

import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

public class ComboBoxEditor extends DefaultCellEditor{

    public ComboBoxEditor(List<String> items) {
        super(new JComboBox(items.toArray()));
    }

}
