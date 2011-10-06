/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import java.awt.event.ActionListener;

/**
 *
 * @author Joao
 */
public abstract class ItemDeMenu extends javax.swing.JMenuItem implements ActionListener{

    public ItemDeMenu() {
        super();
        addActionListener(this);
    }



}
