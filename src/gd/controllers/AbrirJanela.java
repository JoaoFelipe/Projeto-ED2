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
public class AbrirJanela {


    public static void abrir(JDialog dialog){
        JFrame mainFrame = GerenciadorDados.getApplication().getMainFrame();
        dialog.setLocationRelativeTo(mainFrame);
        GerenciadorDados.getApplication().show(dialog);
    }
}
