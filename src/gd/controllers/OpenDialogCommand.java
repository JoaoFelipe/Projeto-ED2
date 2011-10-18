/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers;

import gd.GerenciadorDados;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author Joao
 */
public class OpenDialogCommand extends Command{

    //Templete Method

    public JDialog dialog;
    public JFrame mainFrame;

    public OpenDialogCommand(JDialog dialog){
        super();
        this.dialog = dialog;
        mainFrame = GerenciadorDados.getApplication().getMainFrame();
    }


    @Override
    public void execute(Object... arg) {
        dialog.setLocationRelativeTo(mainFrame);
        GerenciadorDados.getApplication().show(dialog);
    }


}
