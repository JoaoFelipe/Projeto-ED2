/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.principal;

import gd.controllers.Command;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.views.Botao;
import java.awt.event.ActionEvent;

/**
 *
 * @author Joao
 */
public class CriarReferenciaBotao extends Botao {

    Command command = null;

    public CriarReferenciaBotao() {
        super();
        command = new AbrirCriarReferenciaCommand();
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        command.execute();
    }

}
