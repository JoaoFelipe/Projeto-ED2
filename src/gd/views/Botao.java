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
public abstract class Botao extends javax.swing.JButton implements ActionListener{

    public Botao() {
        super();
        addActionListener(this);
    }



}
