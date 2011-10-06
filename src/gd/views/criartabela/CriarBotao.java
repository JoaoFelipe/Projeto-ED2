/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.criartabela;

import gd.views.principal.*;
import gd.controllers.Command;
import gd.controllers.criartabela.CriarTabelaCommand;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.controllers.tabelas.ExcluirTabelaCommand;
import gd.views.Botao;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Joao
 */
public class CriarBotao extends Botao {

    Command command = null;

    public CriarBotao(JDialog dialog, JTextField textField, JTable table) {
        super();
        command = new CriarTabelaCommand(dialog, textField, table);
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        command.execute();
    }

}
