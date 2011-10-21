package gd.views.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class ComboBoxNaoEditavel extends JComboBox implements ActionListener{

    public ComboBoxNaoEditavel() {
        super();
        addActionListener(this);
        DefaultComboBoxModel modelo = new DefaultComboBoxModel();
        this.setModel(modelo);
    }

    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
    }

}

