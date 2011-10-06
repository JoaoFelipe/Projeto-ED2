/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views.criartabela;

import gd.views.principal.*;
import gd.controllers.Command;
import gd.controllers.criartabela.AdicionarAtributoCommand;
import gd.controllers.tabelas.AbrirCriarReferenciaCommand;
import gd.controllers.tabelas.AbrirCriarTabelaCommand;
import gd.controllers.tabelas.ExcluirTabelaCommand;
import gd.views.Botao;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;


/**
 *
 * @author Joao
 */
public class AdicionarAtributoBotao extends Botao {

    Command command = null;

    public AdicionarAtributoBotao(JTextField textField, JComboBox comboBox, JTable table) {
        super();
        command = new AdicionarAtributoCommand(textField, comboBox, (AtributosTabela)table);
    }
    public void actionPerformed(ActionEvent e) {
        e.getActionCommand();
        command.execute();
    }

}
