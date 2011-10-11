/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.base;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;

/**
 *
 * @author Joao
 */
public abstract class ComboBoxEditavel extends JComboBox implements ActionListener{

    public ComboBoxEditavel() {
        super();
        addActionListener(this);
        this.setEditable(true);
        
    }
    
}

