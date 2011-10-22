package gd.controllers;

import gd.GerenciadorDados;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class JanelaController {

    public static void abrir(JDialog dialog) {
        JFrame mainFrame = GerenciadorDados.getApplication().getMainFrame();
        dialog.setLocationRelativeTo(mainFrame);
        GerenciadorDados.getApplication().show(dialog);
    }

    public static void fecharDialog(JDialog dialog) {
        dialog.dispose();
    }

    public static void fecharPrograma() {
        System.exit(0);
    }

    public static void mensagem(String text) {
        JOptionPane.showMessageDialog(null, text);
    }
}
