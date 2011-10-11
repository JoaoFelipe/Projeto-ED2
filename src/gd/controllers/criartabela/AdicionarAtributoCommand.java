/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers.criartabela;

import gd.controllers.*;
import gd.models.atributos.Atributo;
import gd.views.CriarTabelaAtributosTabela;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Joao
 */
public class AdicionarAtributoCommand extends Command{

    JTextField textField = null;
    JComboBox comboBox = null;
    CriarTabelaAtributosTabela table = null;


    public AdicionarAtributoCommand(JTextField textField, JComboBox comboBox, CriarTabelaAtributosTabela table) {
        super();
        this.textField = textField;
        this.comboBox = comboBox;
        this.table = table;
    }

    @Override
    public void execute(Object... arg) {
        Atributo attr = Atributo.criarAtributo(textField.getText()+":"+comboBox.getSelectedItem());
        if (attr != null) {
            table.inserirAtributo(attr);
        } else {
            (new MensagemErroCommand()).execute("Atributo Inválido!");
        }
        //System.exit(0);
    }



}
