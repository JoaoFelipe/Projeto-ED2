/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers;

import gd.controllers.criartabela.*;
import gd.controllers.*;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Joao
 */
public class CloseDialogCommand extends Command{

    JDialog dialog = null;


    public CloseDialogCommand(JDialog dialog) {
        super();
        this.dialog = dialog;
    }

    @Override
    public void execute(Object... arg) {
        dialog.dispose();
    }



}
