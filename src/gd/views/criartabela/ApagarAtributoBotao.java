/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.criartabela;

import gd.views.principal.*;
import gd.controllers.Command;
import gd.controllers.criartabela.RemoverAtributoCommand;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.controllers.tabelas.ExcluirTabelaCommand;
import gd.views.Botao;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

/**
 *
 * @author Joao
 */
public class ApagarAtributoBotao extends Botao {

    Command command = null;

    public ApagarAtributoBotao(JTable tabela) {
        super();
        command = new RemoverAtributoCommand((AtributosTabela)tabela);
    }
    
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        command.execute();
    }

}
