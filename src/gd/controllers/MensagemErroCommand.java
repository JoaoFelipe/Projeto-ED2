/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers;

import javax.swing.JOptionPane;

/**
 *
 * @author Joao
 */
public class MensagemErroCommand extends Command{

    public MensagemErroCommand() {
        super();
    }

    @Override
    public void execute(Object... arg) {
        JOptionPane.showMessageDialog(null, arg[0]);
    }



}
