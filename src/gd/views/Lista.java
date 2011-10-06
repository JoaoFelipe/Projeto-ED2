/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Joao
 */
public abstract class Lista extends javax.swing.JList implements ListSelectionListener{

    public Lista() {
        super();
        addListSelectionListener(this);
    }

    public abstract void atualizar();

}
