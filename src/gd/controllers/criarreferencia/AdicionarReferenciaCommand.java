/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gd.controllers.criarreferencia;

import gd.controllers.criartabela.*;
import gd.controllers.*;
import gd.exceptions.ModelException;
import gd.models.ER.Entidade;
import gd.models.ER.EntidadeRelacionamento;
import gd.models.ER.ListaER;
import gd.models.ER.Relacionamento;
import gd.models.atributos.Atributo;
import gd.views.principal.TabelasLista;
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
public class AdicionarReferenciaCommand extends Command {

    JComboBox tabela = null;
    JComboBox atributo = null;
    JComboBox referenciada = null;
    JComboBox codigo = null;
    JComboBox busca = null;
    JDialog dialog = null;


    public AdicionarReferenciaCommand(JDialog dialog, JComboBox tabela, JComboBox atributo, JComboBox referenciada, JComboBox cod, JComboBox busca) {
        super();
        this.dialog = dialog;
        this.tabela = tabela;
        this.atributo = atributo;
        this.referenciada = referenciada;
        this.codigo = cod;
        this.busca = busca;
    }

    @Override
    public void execute(Object... arg) {
        try {
            ListaER inst = ListaER.getInstancia();
            Entidade tab = (Entidade) inst.buscar((String)tabela.getSelectedItem());
            String attr = (String)atributo.getSelectedItem();
            Entidade ref = (Entidade) inst.buscar((String)referenciada.getSelectedItem());
            String cod = (String)codigo.getSelectedItem();
            String bus = (String)busca.getSelectedItem();

            inst.add(new Relacionamento(tab, attr, ref, cod, bus));
            inst.grava();
            dialog.dispose();
        } catch (ModelException ex) {
            ex.execute();
        }
        TabelasLista.getInstancia().atualizar();
    }
}
