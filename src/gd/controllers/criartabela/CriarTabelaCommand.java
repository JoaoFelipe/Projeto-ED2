/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.controllers.criartabela;

import gd.controllers.*;
import gd.exceptions.ModelException;
import gd.models.ER.Entidade;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.ListaER;
import gd.models.atributos.Atributo;
import gd.views.TelaPrincipalTabelasLista;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Joao
 */
public class CriarTabelaCommand extends Command {

    JTextField textField = null;
    JTable table = null;
    JDialog dialog = null;

    public CriarTabelaCommand(JDialog dialog, JTextField textField, JTable table) {
        super();
        this.dialog = dialog;
        this.textField = textField;
        this.table = table;
    }

    @Override
    public void execute(Object... arg) {
        try {
            TableModel modelo = table.getModel();

            List<Atributo> lista = new ArrayList<Atributo>();
            for (int i = 0; i < modelo.getRowCount(); i++) {
                lista.add(Atributo.criarAtributo(
                    (String) modelo.getValueAt(i, 0),
                    (String) modelo.getValueAt(i, 1),
                    (Boolean) modelo.getValueAt(i, 2)
                ));
            }
            ListaER inst = ListaER.getInstancia();
            inst.add(new Entidade(textField.getText(), lista));
            inst.grava();
            dialog.dispose();
        } catch (ModelException ex) {
            ex.execute();
        }
        TelaPrincipalTabelasLista.getInstancia().atualizar();
    }
}
