/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.criarreferencia;

import gd.views.criartabela.*;
import gd.controllers.CloseDialogCommand;
import gd.views.principal.*;
import gd.controllers.Command;
import gd.controllers.criarreferencia.AdicionarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.controllers.tabelas.ExcluirTabelaCommand;
import gd.views.Botao;
import javax.swing.JDialog;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

/**
 *
 * @author Joao
 */
public class AdicionarBotao extends Botao {

    Command command = null;

    public AdicionarBotao(JDialog dialog, JComboBox tabela, JComboBox atributo, JComboBox referenciada, JComboBox cod, JComboBox busca){
        super();
        command = new AdicionarReferenciaCommand(dialog, tabela, atributo, referenciada, cod, busca);
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        command.execute();
    }

}
