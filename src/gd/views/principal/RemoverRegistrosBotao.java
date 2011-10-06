/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal;

import gd.controllers.Command;
import gd.controllers.tabela.AbreConsultarRegistrosCommand;
import gd.controllers.tabela.AbrirInserirRegistroCommand;
import gd.controllers.tabela.AbrirModificarRegistrosCommand;
import gd.controllers.tabela.AbrirRemoverRegistrosCommand;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.views.Botao;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class RemoverRegistrosBotao extends Botao {

    Command command = null;

    public RemoverRegistrosBotao() {
        super();
        command = new AbrirRemoverRegistrosCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        command.execute();
    }

}
