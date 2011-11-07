package dm.views;

import java.awt.event.ActionListener;
import javax.swing.JComboBox;

public abstract class EditableComboBox extends JComboBox implements ActionListener{

    public EditableComboBox() {
        super();
        addActionListener(this);
        this.setEditable(true);
    }
    
}