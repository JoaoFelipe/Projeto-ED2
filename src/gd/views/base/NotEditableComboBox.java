package gd.views.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class NotEditableComboBox extends JComboBox implements ActionListener{

    public NotEditableComboBox() {
        super();
        addActionListener(this);
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        this.setModel(model);
    }

    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
    }

}

