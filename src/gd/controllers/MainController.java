/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers;

import gd.GerenciadorDados;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import gd.views.criarreferencia.CriarReferenciaView;
import gd.views.criartabela.CriarTabelaView;
import gd.views.SobreView;

/**
 *
 * @author Joao
 */
public class MainController {


    //TODO: refatorar para instanciar sem os ifs
    public static JDialog abrirDialog(JDialog dialog, Class c){
        if (dialog == null) {
            JFrame mainFrame = GerenciadorDados.getApplication().getMainFrame();
            if (c.equals(SobreView.class)){
                dialog = new SobreView(mainFrame);
            } else if (c.equals(CriarTabelaView.class)){
                dialog = new CriarTabelaView(mainFrame);
            } else if (c.equals(CriarReferenciaView.class)){
                dialog = new CriarReferenciaView(mainFrame);
            }
            dialog.setLocationRelativeTo(mainFrame);
        }
        return dialog;
    }

}
