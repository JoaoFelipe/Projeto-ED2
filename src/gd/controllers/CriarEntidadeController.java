/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gd.controllers;

import gd.exceptions.ModelException;
import gd.models.ER.Entidade;
import gd.models.ER.ListaER;
import gd.models.atributos.Atributo;
import gd.views.TabelaDeAtributos;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joao
 */
public class CriarEntidadeController {

    public static void inserirAtributo(TabelaDeAtributos table, String nome, String tipo) {
        Atributo atributo = Atributo.criarAtributo(nome+":"+tipo);
        
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();

        if (atributo != null) {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                if (modelo.getValueAt(i, 0).equals(atributo.getNome())){
                    JanelaController.mensagem("Já existe um atributo com este nome!");
                    return;
                }
            }
            modelo.addRow(new Object[]{atributo.getNome(), atributo.getTipo(), atributo.getPK()});
        } else {
            JanelaController.mensagem("Atributo Inválido!");
        }
    }

    public static void removerAtributo(TabelaDeAtributos table) {
        int i= table.convertRowIndexToModel(table.getSelectedRow());

        DefaultTableModel modelo = (DefaultTableModel) table.getModel();

        if (i >= 0 && i < modelo.getRowCount()) {
            modelo.removeRow(i);
        }
    }


}
