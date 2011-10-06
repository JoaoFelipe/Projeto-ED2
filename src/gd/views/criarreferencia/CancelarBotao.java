/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.criarreferencia;

import gd.views.criartabela.*;
import gd.controllers.CloseDialogCommand;
import gd.views.principal.*;
import gd.controllers.Command;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.controllers.tabelas.ExcluirTabelaCommand;
import gd.views.Botao;
import javax.swing.JDialog;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class CancelarBotao extends Botao {

    Command command = null;

    public CancelarBotao(JDialog dialog) {
        super();
        command = new CloseDialogCommand(dialog);
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        command.execute();
    }

}
