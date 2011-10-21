package gd.views.base;

import java.awt.event.ActionListener;
import javax.swing.JComboBox;

public abstract class ComboBoxEditavel extends JComboBox implements ActionListener{

    public ComboBoxEditavel() {
        super();
        addActionListener(this);
        this.setEditable(true);
    }
    
}