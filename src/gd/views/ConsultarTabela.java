/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.views;

import gd.controllers.MensagemErroCommand;
import gd.models.ER.Entidade;
import gd.models.atributos.Atributo;
import gd.views.base.ComboBoxEditor;
import gd.views.base.ComboBoxRenderer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Joao
 */
public class ConsultarTabela extends JTable {

    DefaultTableModel modelo = null;
    Entidade entidade = null;

    public ConsultarTabela(Entidade entidade) {
        super();
        modelo = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "Atributo", "Condição", "Valor" }
        );
        this.entidade = entidade;

        this.setModel(modelo);
        List<String> lista = entidade.getAtributosBuscaveis();
        TableColumn col = this.getColumnModel().getColumn(0);
        col.setCellEditor(new ComboBoxEditor(lista));
        col.setCellRenderer(new ComboBoxRenderer(lista));

        lista = Arrays.asList("=","!=",">", ">=", "<", "<=");
        col = this.getColumnModel().getColumn(1);
        col.setCellEditor(new ComboBoxEditor(lista));
        col.setCellRenderer(new ComboBoxRenderer(lista));
    }

    public void removeSelectedRow(){
        int i= this.convertRowIndexToModel(this.getSelectedRow());
        if (i >= 0 && i < modelo.getRowCount())
            modelo.removeRow(i);
    }

}
