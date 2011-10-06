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
public abstract class OpenDialogCommand extends Command{

    //Templete Method

    public JDialog dialog;
    public JFrame mainFrame;

    public OpenDialogCommand(){
        super();
        mainFrame = GerenciadorDados.getApplication().getMainFrame();
    }

    public abstract JDialog instanciarJanela();

    @Override
    public void execute(Object... arg) {
        if (dialog == null) {
            dialog = instanciarJanela();
            dialog.setLocationRelativeTo(mainFrame);
        }
        GerenciadorDados.getApplication().show(dialog);
    }


}
