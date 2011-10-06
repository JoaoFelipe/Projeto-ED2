/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal;

import gd.controllers.Command;
import gd.controllers.tabela.AbreConsultarRegistrosCommand;
import gd.controllers.tabela.AbrirInserirRegistroCommand;
import gd.controllers.tabela.AbrirModificarRegistrosCommand;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.views.Botao;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class ModificarRegistrosBotao extends Botao {

    Command command = null;

    public ModificarRegistrosBotao() {
        super();
        command = new AbrirModificarRegistrosCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        command.execute();
    }

}
